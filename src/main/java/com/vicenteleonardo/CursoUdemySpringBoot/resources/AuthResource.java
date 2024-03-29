package com.vicenteleonardo.CursoUdemySpringBoot.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vicenteleonardo.CursoUdemySpringBoot.dto.EmailDTO;
import com.vicenteleonardo.CursoUdemySpringBoot.security.JWTUtil;
import com.vicenteleonardo.CursoUdemySpringBoot.security.UserSS;
import com.vicenteleonardo.CursoUdemySpringBoot.services.AuthService;
import com.vicenteleonardo.CursoUdemySpringBoot.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthResource {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("/refresh_token")
	public ResponseEntity<Void>refreshToken(HttpServletResponse response){
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/forgot")
	public ResponseEntity<Void>forgot(@RequestBody @Valid EmailDTO emailDto){
		authService.sendNewPassword(emailDto.getEmail());
		return ResponseEntity.noContent().build();
	}
	
	

}
