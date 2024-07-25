package com.hemendrasahu.userservice.controllers;

import com.hemendrasahu.userservice.dtos.SetUserRolesRequestDto;
import com.hemendrasahu.userservice.dtos.SingUpRequestDto;
import com.hemendrasahu.userservice.dtos.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    public UserController(){}

    @PostMapping()
    public ResponseEntity<UserDto> signUp(@RequestBody SingUpRequestDto requestDto){
        UserDto userDto = new UserDto();
        ResponseEntity<UserDto> response = new ResponseEntity<>(userDto, HttpStatus.CREATED);
        return response;
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable("id") Long userId){
        UserDto userDto = new UserDto();
        ResponseEntity<UserDto> response = new ResponseEntity<>(userDto, HttpStatus.OK);
        return response;
    }

    @PatchMapping("{id}/roles")
    public ResponseEntity<UserDto> setUserRoles(@PathVariable("id") Long userId, @RequestBody SetUserRolesRequestDto requestDto){
        System.out.print(requestDto.getRoleIds());
        UserDto userDto = new UserDto();
        ResponseEntity<UserDto> response = new ResponseEntity<>(userDto, HttpStatus.OK);
        return response;
    }
}
