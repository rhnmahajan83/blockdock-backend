package com.blockdock.datajpa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.blockdock.datajpa.user.model.User;
import com.blockdock.datajpa.user.model.FileDetails;
import com.blockdock.datajpa.user.payload.ResponseStatus;
import com.blockdock.datajpa.user.service.FileService;
import com.blockdock.datajpa.user.service.UserService;
import com.deswaef.spring.examples.datajpa.util.Constants;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class RestApiController {

	@Autowired
	UserService userService;
	
	@Autowired
	FileService fileService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseStatus validateUser(@RequestBody User user) {
		if(user== null) {
			return new ResponseStatus(Constants.LOGIN_FAILED,"Login Failed");
		}else {
			 String userName = userService.validateUser(user.getEmail(), user.getPassword());
			 if(userName != "0" && userName != "1") {
				 return new ResponseStatus(Constants.LOGIN_SUCCESSFUL, userName);
			 }else if(userName == "1"){
				 return new ResponseStatus(Constants.LOGIN_FAILED, "User not found");
			 }else {
				 return new ResponseStatus(Constants.LOGIN_FAILED, "Password Incorrect");
			 }
		}
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseStatus registerUser(@RequestBody User user) {	
		if(userService.getUserByEmail(user.getEmail()) != null) {
			return new ResponseStatus(Constants.USER_ALREADY_REGISTERD,"User Already Registered");
		}
		if(userService.registerUser(user) != null && user !=null) {
			return new ResponseStatus(Constants.REGISTERATION_SUCCESSFUL,"Registration Successfull");
		}else{
			return new ResponseStatus(Constants.REGISTERATION_FAILED,"Registration Failed");
		}
	}
	
	@RequestMapping(value = "/getFiles", method = RequestMethod.POST)
	public List<FileDetails> getUserFiles(@RequestBody User user) {	
		User fileUser = userService.getUserById(user.getId());
		if(fileUser !=null) { 
			List<FileDetails> files = fileService.getAllUserFiles(fileUser.getId());
			return files;
		}else {
			return null;
		}
		
	}
}

