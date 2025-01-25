package com.project.java.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.java.config.Environment;
import com.project.java.config.Environment.EnvTarget;
import com.project.java.dto.GetUserDTO;
import com.project.java.dto.UserDTO;
import com.project.java.dto.UserSignInDTO;
import com.project.java.enums.RequestType;
import com.project.java.exception.Momo.MoMoException;
import com.project.java.model.Role;
import com.project.java.model.User;
import com.project.java.model.Momo.PaymentResponse;
import com.project.java.repository.UserRepository;
import com.project.java.responses.ClassResponse;
import com.project.java.responses.authorizationServer.AuthorizationResponse;
import com.project.java.responses.token.TokenResponse;
import com.project.java.responses.user.UserResponse;
import com.project.java.responses.user.UserSignInResponse;
import com.project.java.service.IUserService;
import com.project.java.service.JwtTokenService;
import com.project.java.service.ProviderSocialService;
import com.project.java.service.Momo.CreateOrderMoMo;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth/users")
public class UserController {
	private final IUserService userService;
	private final JwtTokenService jwtTokenService;
	private final UserRepository userRepository;
	private final ProviderSocialService providerSocialService;
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@PostMapping("/register")
	public ResponseEntity<ClassResponse> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult)
			throws Exception {
		if (bindingResult.hasErrors()) {
			List<String> errorMessages = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage)
					.toList();

			return ResponseEntity.badRequest()
					.body(ClassResponse.builder().status(HttpStatus.BAD_REQUEST).data(errorMessages).build());
		}
		if (!userDTO.getPassword().equals(userDTO.getReTypePassword())) {
			return ResponseEntity.badRequest().body(
					ClassResponse.builder().status(HttpStatus.BAD_REQUEST).message("mật khẩu không khớp").build());
		}
		User newUser = userService.createNewUser(userDTO);
		return ResponseEntity.ok().body(ClassResponse.builder().status(HttpStatus.OK)
				.message("Create user successfully").data(UserResponse.fromUser(newUser)).build());
	}

	@PostMapping("/login")
	public ResponseEntity<ClassResponse> signIn(@Valid @RequestBody UserSignInDTO userSignInDTO,
			BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			List<String> errorMessages = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage)
					.toList();

			return ResponseEntity.badRequest()
					.body(ClassResponse.builder().status(HttpStatus.BAD_REQUEST).data(errorMessages).build());
		}

		String token = userService.signIn(userSignInDTO);
		if (token == null) {
			return ResponseEntity.badRequest()
					.body(ClassResponse.builder().status(HttpStatus.BAD_REQUEST).data("No Login").build());
		}
		String email = jwtTokenService.getEmailFromExtract(token);
		String refreshToken = userRepository.findByEmail(email).get().getRefreshAccessToken();
		String name = userRepository.findByEmail(email).get().getName();
		Map<String, Object> claims = jwtTokenService.extractClaim(token);

		// Sử dụng asList để lấy danh sách roles
		List<String> rolesFromToken = (List<String>) claims.get("roles");

		// Tạo Set từ danh sách roles
		Set<String> roles = new HashSet<>(rolesFromToken); // Chuyển đổi trực tiếp sang Set

		return ResponseEntity.ok().body(ClassResponse.builder().status(HttpStatus.OK) // Thay đổi status về OK
				.data(new UserSignInResponse.Builder().userName(name).email(email).accessToken(token)
						.refreshToken(refreshToken).roles(roles).build())
				.build());
	}

	@PostMapping("/get-refresh-token")
	public ResponseEntity<ClassResponse> getRefreshToken(@RequestBody String refreshToken) throws Exception {
		TokenResponse generateTokenFromRefreshToken = jwtTokenService.generateAccessTokenFromRefreshToken(refreshToken);
		if (generateTokenFromRefreshToken == null) {
			ResponseEntity.badRequest().body(ClassResponse.builder().status(HttpStatus.BAD_REQUEST).data("")
					.message("No generate token ").build());
		}
		return ResponseEntity.ok().body(ClassResponse.builder().data(generateTokenFromRefreshToken)
				.message("Generate successfully").status(HttpStatus.ACCEPTED).build());

	}
	
	@PostMapping("/charge")
	public ResponseEntity<ClassResponse> chargeMoney(@RequestParam("money") String money) throws MoMoException{
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null) {
	        logger.error("Authentication is null");
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	    }
	    
	    User user = (User) authentication.getPrincipal();
	    
	    PaymentResponse response = userService.charge(money);
	    String payURL = response.getPayUrl();
	    Float floatMoney = Float.valueOf(money);
	    user.setMoney(floatMoney);
	    userRepository.save(user);
	    return ResponseEntity.ok().body(new ClassResponse.Builder().status(HttpStatus.CREATED).message("direct payURL").data(payURL).build());
	}

	@GetMapping("/login/login-type/{type}")
	public ResponseEntity<ClassResponse> callToAuthorizationServer(@PathVariable String type) throws Exception {
		String uriAuthorizationServer = providerSocialService.generateURLToProvider(type);

		AuthorizationResponse authorizationResponse = AuthorizationResponse.builder().uri(uriAuthorizationServer)
				.build();

		return ResponseEntity.ok().body(ClassResponse.builder().data(authorizationResponse)
				.message("Generate successfully").status(HttpStatus.OK).build());
	}

	@GetMapping("/login/exchange-code")
	public ResponseEntity<ClassResponse> callback(@RequestParam("code") String authorizationCode) throws Exception {
		logger.info("Authorization Code: " + authorizationCode);
		String AccessToken = providerSocialService.exchangeAccessToken(authorizationCode);
		Map<String, Object> userInfoMap = providerSocialService.getUserInfo(AccessToken);
		String email = (String) userInfoMap.get("email");
		String googleAccountId = (String) userInfoMap.get("sub");
		String picture = (String) userInfoMap.get("picture");
		String name = (String) userInfoMap.get("name");

		UserDTO newUser = new UserDTO().builder().email(email).facebookAccountId(null).googleAccountId(googleAccountId)
				.password(null).phone(null).reTypePassword(null).roleID(null).name(name).build();
		User user = userService.createNewUser(newUser);

		String jwtToken = jwtTokenService.generateAccessToken(user);
		String refreshToken = jwtTokenService.generateRefreshToken(user);
		user.setRefreshAccessToken(refreshToken);

		return ResponseEntity.ok()
				.body(ClassResponse.builder()
						.data(new TokenResponse.Builder().refreshToken(refreshToken).accessToken(jwtToken).build())
						.message("Generate successfully").status(HttpStatus.OK).build());
	}

	@GetMapping("/getuser")
	public ResponseEntity<ClassResponse> getUser() throws Exception {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null) {
	        logger.error("Authentication is null");
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	    }
	    
	    User user = (User) authentication.getPrincipal();
	    GetUserDTO userDTO = new GetUserDTO(user);
	    logger.info("User retrieved: {}", user.getUsername());

	    return ResponseEntity.ok()
	            .body(ClassResponse.builder().status(HttpStatus.OK)
	            		.message("done")
	                    .data(userDTO)
	                    .build());
	}


	public UserController(IUserService userService, JwtTokenService jwtTokenService, UserRepository userRepository,
			ProviderSocialService providerSocialService) {
		super();
		this.userService = userService;
		this.jwtTokenService = jwtTokenService;
		this.userRepository = userRepository;
		this.providerSocialService = providerSocialService;
	}

}
