package com.hemendrasahu.userservice.controllers;

import com.hemendrasahu.userservice.dtos.CreateRoleRequestDto;
import com.hemendrasahu.userservice.dtos.RoleDto;
import com.hemendrasahu.userservice.models.Role;
import com.hemendrasahu.userservice.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleDto> createRole(@RequestBody CreateRoleRequestDto requestDto) {
        Role role = roleService.createRole(requestDto.getName());
        return new ResponseEntity<>(convertToDto(role), HttpStatus.CREATED);
    }

    private RoleDto convertToDto(Role role) {
        RoleDto dto = new RoleDto();
        dto.setId(role.getId());
        dto.setName(role.getName());
        return dto;
    }
}
