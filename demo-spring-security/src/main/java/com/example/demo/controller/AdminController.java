package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;

@RestController
public class AdminController {

	@Autowired
	private UserRepository  userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@PostMapping("/user")
	public String addUser(@RequestBody User user) {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(principal instanceof UserDetails) {
			UserDetails userDetails = (UserDetails)principal;
			System.out.println("username " + userDetails.getUsername());
		}
		else {
			System.out.println("username " + principal.toString());
		}
		
		String password = user.getPassword();
		String pwd = passwordEncoder.encode(password);
		user.setPassword(pwd);
		userRepository.save(user);
		return "user added!";
	}
}
