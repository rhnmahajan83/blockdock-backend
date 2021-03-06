package com.blockdock.datajpa.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blockdock.Interface.UserInterface;
import com.blockdock.datajpa.user.model.FileDetails;
import com.blockdock.datajpa.user.model.User;
import com.blockdock.datajpa.user.repository.BaseRepository;
import com.blockdock.datajpa.user.repository.UserRepository;


@Service
@Transactional
public class UserService implements UserInterface{

    @Autowired
    private UserRepository userRepository;

    public String validateUser(String email, String password) {
    	User emailUser = userRepository.findByEmail(email);
    	
    	String response = null;
    	
    	if(emailUser != null) {
    		if((emailUser.getEmail().equals(email) && emailUser.getPassword().equals(password))) {
        		response = emailUser.getName();
        	}
    		else {
    			response = "0";
    		}
    	}else {
    		response = "1";
    	}
    	
    	
		return response;
	}

    @Override
	public Boolean registerUser(User user) {
		if(userRepository.save(user) != null) {
			return true;
		}
		return false;
	}
	
    @Override
	public User getUserByEmail(String email) {	
    	User user = userRepository.findByEmail(email);
		if(user != null) {
			return user;
		}
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserById(Long id) {
		User user = userRepository.findById(id);
		if(user != null) {
			return user;
		}
		return null;
	}

}
