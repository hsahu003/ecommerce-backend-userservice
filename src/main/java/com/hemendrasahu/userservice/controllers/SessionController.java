package com.hemendrasahu.userservice.controllers;

import com.hemendrasahu.userservice.dtos.CreateSessionRequestDto;
import com.hemendrasahu.userservice.dtos.ExpireSessionRequestDto;
import com.hemendrasahu.userservice.dtos.UserDto;
import com.hemendrasahu.userservice.dtos.ValidateTokenRequestDto;
import com.hemendrasahu.userservice.models.SessionStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sessions")
public class SessionController {

    @PostMapping("/login")
    public ResponseEntity<UserDto> createSession(@RequestBody CreateSessionRequestDto requestDto){
        ResponseEntity<UserDto> response = new ResponseEntity<>(new UserDto(), HttpStatus.CREATED);
        return response;
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> expireSession(@RequestBody ExpireSessionRequestDto requestDto){
        ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.OK);
        return response;
    }

    @PostMapping("/validate")
    public SessionStatus validateToken(@RequestBody ValidateTokenRequestDto requestDto){
        return SessionStatus.ACTIVE;
    }
}
