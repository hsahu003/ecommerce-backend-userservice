package com.hemendrasahu.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class User extends BaseModel{
    String name;
    String email;
    String password;
    @ManyToMany
    Set<Role> roles;
}
