package com.hemendrasahu.userservice.services;

import com.hemendrasahu.userservice.exceptions.DuplicateEntryException;
import com.hemendrasahu.userservice.exceptions.InvalidInputException;
import com.hemendrasahu.userservice.models.User;
import com.hemendrasahu.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(String name, String email, String password) throws DuplicateEntryException, InvalidInputException {

        //validation starts
        if(email.isEmpty()){throw new InvalidInputException("Email can't be empty");}
        if(password.isEmpty()){throw new InvalidInputException("Password can't be empty");}

        Optional<User> existingUserOptional = userRepository.getByEmail(email);
        if(existingUserOptional.isPresent()){
            throw new DuplicateEntryException("Email " + email + " is already registered. Please log in");
        }
        //validation ends


        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        User savedUser = userRepository.save(user);
        return savedUser;
    }
}
