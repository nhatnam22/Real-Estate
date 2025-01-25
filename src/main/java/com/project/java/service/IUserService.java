package com.project.java.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.java.config.Environment;
import com.project.java.dto.UserDTO;
import com.project.java.dto.UserSignInDTO;
import com.project.java.enums.RequestType;
import com.project.java.exception.CheckDataException;
import com.project.java.exception.Momo.MoMoException;
import com.project.java.model.Role;
import com.project.java.model.User;
import com.project.java.model.Momo.PaymentResponse;
import com.project.java.repository.RoleRepository;
import com.project.java.repository.UserRepository;
import com.project.java.service.Momo.CreateOrderMoMo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IUserService implements UserService {

	public IUserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder,
			JwtTokenService jwtTokenService) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenService = jwtTokenService;
	}

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenService jwtTokenService;

	@Override
	public User createNewUser(UserDTO userDTO) throws Exception {

		if (userDTO.getGoogleAccountId() != null) {
			if (userRepository.existsByGoogleAccountId(userDTO.getGoogleAccountId())) {
				throw new CheckDataException("account is exist");
			}
			Role role = roleRepository.findByName("USER").orElseThrow(() -> new CheckDataException("Data no exist"));
			Set<Role> roles = new HashSet<>();
			roles.add(role);
			User newUser = User.builder().email(userDTO.getEmail()).name(userDTO.getName()).phone(null).password(null)
					.facebookAccountId(userDTO.getFacebookAccountId()).googleAccountId(userDTO.getGoogleAccountId())
					.roles(roles).build();
			return userRepository.save(newUser);
		}

		String email = userDTO.getEmail();
		if (userRepository.existsByEmail(email)) {
			throw new CheckDataException("email is exist");
		}

		if (!roleRepository.existsById(userDTO.getRoleID())) {
			throw new CheckDataException("Data no exist");
		}
		Role role = roleRepository.findById(userDTO.getRoleID())
				.orElseThrow(() -> new CheckDataException("Data no exist"));
		Set<Role> roles = new HashSet<>();
		roles.add(role);

		// convert userDTO to user
		User newUser = User.builder().email(userDTO.getEmail()).name(userDTO.getName()).phone(userDTO.getPhone())
				.password(userDTO.getPassword()).facebookAccountId(userDTO.getFacebookAccountId())
				.googleAccountId(userDTO.getGoogleAccountId()).roles(roles).build();

		String password = userDTO.getPassword();
		String hashPassword = passwordEncoder.encode(password);
		newUser.setPassword(hashPassword);

		return userRepository.save(newUser);

	}

	@Override
	public String signIn(UserSignInDTO userSignInDTO) throws Exception {
		User user = userRepository.findByEmail(userSignInDTO.getEmail()).get();
		if (user == null) {
			throw new CheckDataException("user not found");
		} else {
			// check password
			if (user.getFacebookAccountId() == null && user.getGoogleAccountId() == null) {
				Boolean IsCheckPassword = passwordEncoder.matches(userSignInDTO.getPassword(), user.getPassword());
				if (IsCheckPassword == false) {
					throw new CheckDataException("password no matches");
				}
			}

			// check role
			Set<Role> roles = new HashSet<>();
			user.getRoles().stream().forEach(role -> roles.add(role));

			String newRefreshToken = jwtTokenService.generateRefreshToken(user);
			user.setRefreshAccessToken(newRefreshToken);
			userRepository.save(user);
		}
		return jwtTokenService.generateAccessToken(user);
	}

	@Override
	public PaymentResponse charge(String money) throws MoMoException {
		String requestId = String.valueOf(System.currentTimeMillis());
		String orderId = String.valueOf(System.currentTimeMillis());

		String partnerClientId = "partnerClientId";
		String orderInfo = "Pay With MoMo";
		String returnURL = "http://localhost:5173/user/nap-tien/thanh-cong";
		String notifyURL = "https://google.com.vn";
		Environment environment = Environment.selectEnv("dev");
		try {
			PaymentResponse reponse = CreateOrderMoMo.process(environment, orderId, requestId, money,
					orderInfo, returnURL, notifyURL, "", RequestType.PAY_WITH_ATM, Boolean.TRUE);
			return reponse;
		} catch (Exception e) {
			Throwable cause = e.getCause();
			throw new MoMoException(cause);
		}
	}

}
