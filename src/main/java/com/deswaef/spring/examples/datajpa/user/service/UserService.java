package com.deswaef.spring.examples.datajpa.user.service;

import com.blockdock.Interface.UserInterface;
import com.deswaef.spring.examples.datajpa.user.model.User;
import com.deswaef.spring.examples.datajpa.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class UserService implements UserInterface{

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Boolean validateUser(String email, String password) {
    	User user = userRepository.findByEmailAndPassword(email, password);
    	if(user != null) {
        	String userEmail = user.getEmail().trim();
        	String userPassword = user.getPassword().trim();
    		if(userEmail.equals(email) && userPassword.equals(password)) {
        		return true;
        	}else {
        		return false;
        	}
    	} else {
			return false;
		}
    }

	@Override
	public Boolean registerUser(User user) {
		if(userRepository.save(user) != null) {
			return true;
		}
		return false;
	}
	
	public Boolean getUserByEmail(String email) {	
		if(userRepository.findByEmail(email) != null) {
			return true;
		}
		return false;
	}
    
    
}