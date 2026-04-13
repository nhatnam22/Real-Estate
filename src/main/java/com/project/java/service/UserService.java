package com.project.java.service;

import org.springframework.stereotype.Service;

import com.project.java.dto.MoMoIpnRequest;
import com.project.java.dto.UserDTO;
import com.project.java.dto.UserSignInDTO;
import com.project.java.exception.Momo.MoMoException;
import com.project.java.model.User;
import com.project.java.model.Momo.PaymentResponse;

@Service
public interface UserService {
	User createNewUser(UserDTO userDTO) throws Exception;
	
	String signIn (UserSignInDTO userSignInDTO) throws Exception;
	
	PaymentResponse charge(String money, String email) throws MoMoException;
	
	void processMoMoIpn(MoMoIpnRequest request) throws Exception;
}
