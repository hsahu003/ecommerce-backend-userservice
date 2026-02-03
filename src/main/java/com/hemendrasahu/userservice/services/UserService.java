package com.hemendrasahu.userservice.services;

import com.hemendrasahu.userservice.exceptions.DuplicateEntryException;
import com.hemendrasahu.userservice.exceptions.InvalidInputException;
import com.hemendrasahu.userservice.models.Role;
import com.hemendrasahu.userservice.models.User;
import com.hemendrasahu.userservice.repositories.RoleRepository;
import com.hemendrasahu.userservice.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    BCryptPasswordEncoder  bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,  RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User createUser(String name, String email, String password) throws DuplicateEntryException, InvalidInputException {

        //validation starts
        if(email == null){throw new InvalidInputException("Email can't be empty");}
        if(password == null){throw new InvalidInputException("Password can't be empty");}

        Optional<User> existingUserOptional = userRepository.getByEmail(email);
        if(existingUserOptional.isPresent()){
            throw new DuplicateEntryException("Email " + email + " is already registered. Please log in");
        }
        //validation ends


        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        return userRepository.save(user);
    }

    @Transactional
    public User setUserRole(Long userId, List<Long> roleIds) throws InvalidInputException {
        //check if user exist
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidInputException("User with id " + userId + " does not exist"));

        // Fetch all roles in a single database hit
        List<Role> foundRoles = roleRepository.findAllById(roleIds);

        // Ensure all requested roles actually exist
        if (foundRoles.size() != roleIds.size()) {
            throw new InvalidInputException("One or more Role IDs are invalid.");
        }


        user.setRoles(new HashSet<>(foundRoles));
        userRepository.save(user);
        return user;
    }
}
