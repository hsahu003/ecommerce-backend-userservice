package com.hemendrasahu.userservice.services;

import com.hemendrasahu.userservice.exceptions.DuplicateEntryException;
import com.hemendrasahu.userservice.exceptions.InvalidInputException;
import com.hemendrasahu.userservice.exceptions.NotFoundException;
import com.hemendrasahu.userservice.models.Session;
import com.hemendrasahu.userservice.models.SessionStatus;
import com.hemendrasahu.userservice.models.User;
import com.hemendrasahu.userservice.repositories.SessionRepository;
import com.hemendrasahu.userservice.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.*;

@Service
public class SessionService {

    UserRepository userRepository;
    SessionRepository sessionRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    private SecretKey secretKey;

    @Autowired
    public SessionService(UserRepository userRepository, SessionRepository sessionRepository, BCryptPasswordEncoder  bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

        secretKey = Jwts.SIG.HS256.key().build();
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
        if(!bCryptPasswordEncoder.matches(password, optionalUser.get().getPassword())){
            throw new InvalidInputException("Password and email does not match");
        }

        //create new session
        Map<String, Object> jwtData = new HashMap<>();
        jwtData.put("email", email);
        jwtData.put("createdAt", new Date());
        jwtData.put("expiryAt", new Date(LocalDate.now().plusDays(3).toEpochDay()));

        String token = Jwts
                .builder()
                .claims(jwtData)
                .signWith(secretKey)
                .compact();

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

        //Token verification
        Jws<Claims> claimsJws = Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(cookieToken);

        //Fetching data from token
        String email = (String) claimsJws.getPayload().get("email");

        //Returning token status
        Session session = optionalSession.get();
        return session.getStatus();
    }
}
