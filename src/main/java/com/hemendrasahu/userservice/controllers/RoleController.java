package com.hemendrasahu.userservice.controllers;

import com.hemendrasahu.userservice.dtos.CreateRoleRequestDto;
import com.hemendrasahu.userservice.models.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("roles")
public class RoleController {

    @PostMapping()
    public ResponseEntity<Role> createRole(@RequestBody CreateRoleRequestDto requestDto){
        ResponseEntity<Role> response = new ResponseEntity<>(new Role(), HttpStatus.CREATED);
        return response;
    }
}
