package com.hemendrasahu.userservice.dtos;

import com.hemendrasahu.userservice.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private HashSet<Role> roles = new HashSet<>();
}
