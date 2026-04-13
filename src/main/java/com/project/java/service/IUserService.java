package com.project.java.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.java.config.Environment;
import com.project.java.dto.MoMoIpnRequest;
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
import com.project.java.utils.Encoder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IUserService implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(IUserService.class);

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
	public PaymentResponse charge(String money, String email) throws MoMoException {
		// Use user's email combined with timestamp for tracking
		String requestId = email + "_" + System.currentTimeMillis();
		String orderId = email + "_" + System.currentTimeMillis();

		String partnerClientId = "partnerClientId";
		String orderInfo = "Top-up Wallet for user " + email;
		String returnURL = "http://localhost:5173/user/nap-tien/thanh-cong";
		// The IPN webhook endpoint
		String notifyURL = "http://localhost:8080/auth/users/momo-ipn";
		Environment environment = Environment.selectEnv("dev");
		try {
		    // Adding email to extraData to identify user upon callback
			PaymentResponse reponse = CreateOrderMoMo.process(environment, orderId, requestId, money,
					orderInfo, returnURL, notifyURL, email, RequestType.PAY_WITH_ATM, Boolean.TRUE);
			return reponse;
		} catch (Exception e) {
			Throwable cause = e.getCause();
			throw new MoMoException(cause);
		}
	}
	
	@Override
    @Transactional
	public void processMoMoIpn(MoMoIpnRequest request) throws Exception {
	    logger.info("Received IPN request from MoMo: {}", request);
	    
	    Environment environment = Environment.selectEnv("dev");
	    String secretKey = environment.getPartnerInfo().getSecretKey();
	    
	    // Verify signature
	    String rawData = "accessKey=" + environment.getPartnerInfo().getAccessKey()
	        + "&amount=" + request.getAmount()
	        + "&extraData=" + request.getExtraData()
	        + "&message=" + request.getMessage()
	        + "&orderId=" + request.getOrderId()
	        + "&orderInfo=" + request.getOrderInfo()
	        + "&orderType=" + request.getOrderType()
	        + "&partnerCode=" + request.getPartnerCode()
	        + "&payType=" + request.getPayType()
	        + "&requestId=" + request.getRequestId()
	        + "&responseTime=" + request.getResponseTime()
	        + "&resultCode=" + request.getResultCode()
	        + "&transId=" + request.getTransId();
	        
        String expectedSignature = Encoder.signHmacSHA256(rawData, secretKey);
        
        if (!expectedSignature.equals(request.getSignature())) {
            logger.error("Invalid MoMo signature! Expected: {}, but got: {}", expectedSignature, request.getSignature());
            throw new SecurityException("Invalid MoMo signature");
        }
        
        // resultCode == 0 means successful transaction
        if (request.getResultCode() == 0) {
            String userEmail = request.getExtraData(); // We passed email in extraData
            logger.info("Transaction successful for user: {}", userEmail);
            
            User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new Exception("User not found with email: " + userEmail));
                
            // Top up user's money
            Float currentMoney = user.getMoney() != null ? user.getMoney() : 0f;
            user.setMoney(currentMoney + request.getAmount());
            userRepository.save(user);
            logger.info("Successfully updated balance for user {}. New balance: {}", userEmail, user.getMoney());
            
        } else {
            logger.warn("Transaction failed or canceled. ResultCode: {}, Message: {}", request.getResultCode(), request.getMessage());
        }
	}

}
