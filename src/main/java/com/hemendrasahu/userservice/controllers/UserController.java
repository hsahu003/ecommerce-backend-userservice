package com.hemendrasahu.userservice.controllers;

import com.hemendrasahu.userservice.dtos.SetUserRolesRequestDto;
import com.hemendrasahu.userservice.dtos.SingUpRequestDto;
import com.hemendrasahu.userservice.dtos.UserDto;
import com.hemendrasahu.userservice.exceptions.DuplicateEntryException;
import com.hemendrasahu.userservice.exceptions.InvalidInputException;
import com.hemendrasahu.userservice.models.User;
import com.hemendrasahu.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<UserDto> signUp(@RequestBody SingUpRequestDto requestDto) throws InvalidInputException, DuplicateEntryException {
        User user = userService.createUser(requestDto.getName(), requestDto.getEmail(), requestDto.getPassword());
        ResponseEntity<UserDto> response = new ResponseEntity<>(UserDto.from(user), HttpStatus.CREATED);
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
