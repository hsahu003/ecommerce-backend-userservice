package com.hemendrasahu.userservice.controllers;

import com.hemendrasahu.userservice.dtos.CreateSessionRequestDto;
import com.hemendrasahu.userservice.dtos.ExpireSessionRequestDto;
import com.hemendrasahu.userservice.dtos.UserDto;
import com.hemendrasahu.userservice.dtos.ValidateTokenRequestDto;
import com.hemendrasahu.userservice.exceptions.DuplicateEntryException;
import com.hemendrasahu.userservice.exceptions.InvalidInputException;
import com.hemendrasahu.userservice.exceptions.NotFoundException;
import com.hemendrasahu.userservice.models.Session;
import com.hemendrasahu.userservice.models.SessionStatus;
import com.hemendrasahu.userservice.models.User;
import com.hemendrasahu.userservice.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("sessions")
public class SessionController {

    SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService){
        this.sessionService = sessionService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> createSession(@RequestBody CreateSessionRequestDto requestDto, @CookieValue(name="auth-token", required = false) String cookieToken) throws InvalidInputException, NotFoundException, DuplicateEntryException {
        Session session = sessionService.createSession(requestDto.getEmail(), requestDto.getPassword(), cookieToken);

        MultiValueMap<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token=" + session.getToken() + ";");

        return new ResponseEntity<>(UserDto.from(session.getUser()), headers, HttpStatus.CREATED);
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> expireSession(@CookieValue(name="auth-token", required = false) String cookieToken) throws Exception {
        sessionService.expireSession(cookieToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/validate")
    public SessionStatus validateToken(@CookieValue(name="auth-token", required = false) String cookieToken) throws Exception {
        return sessionService.validateSession(cookieToken);
    }
}
