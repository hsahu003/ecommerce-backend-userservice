package com.hemendrasahu.userservice.services;

import com.hemendrasahu.userservice.exceptions.DuplicateEntryException;
import com.hemendrasahu.userservice.exceptions.InvalidInputException;
import com.hemendrasahu.userservice.exceptions.NotFoundException;
import com.hemendrasahu.userservice.models.Session;
import com.hemendrasahu.userservice.models.SessionStatus;
import com.hemendrasahu.userservice.models.User;
import com.hemendrasahu.userservice.repositories.SessionRepository;
import com.hemendrasahu.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Service
public class SessionService {

    UserRepository userRepository;
    SessionRepository sessionRepository;

    @Autowired
    public SessionService(UserRepository userRepository, SessionRepository sessionRepository){
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public Session createSession(String email, String password, String cookieToken) throws NotFoundException, DuplicateEntryException, InvalidInputException {
        //check if user exists
        Optional<User> optionalUser = userRepository.getByEmail(email);
        if(optionalUser.isEmpty()){
            throw new NotFoundException("user with email: " + email + " does not exist");
        }

        //check if session is already active
        Optional<Session> optionalSession = sessionRepository.getByToken(cookieToken);
        if(optionalSession.isPresent() && optionalSession.get().getStatus() == SessionStatus.ACTIVE){
            throw new DuplicateEntryException("session is already active");
        }

        //check if user is valid
        if(!optionalUser.get().getPassword().equals(password)){
            throw new InvalidInputException("Password and email does not match");
        }

        //create new session
        String token = String.valueOf(UUID.randomUUID());
        Session session = new Session();
        session.setToken(token);
        session.setUser(optionalUser.get());
        session.setStatus(SessionStatus.ACTIVE);
        return sessionRepository.save(session);
    }
    public void expireSession(String cookieToken) throws Exception {
        Optional<Session> optionalSession = sessionRepository.getByToken(cookieToken);
        if(optionalSession.isEmpty()){
            throw new Exception("Not a valid session to logout");
        }

        Session session = optionalSession.get();
        session.setStatus(SessionStatus.EXPIRED);
        sessionRepository.save(session);
    }

    public SessionStatus validateSession(String cookieToken) throws Exception {
        Optional<Session> optionalSession = sessionRepository.getByToken(cookieToken);
        if(optionalSession.isEmpty()){
            throw new Exception("Not a valid session to validate");
        }

        Session session = optionalSession.get();
        return session.getStatus();
    }
}
