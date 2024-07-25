package com.hemendrasahu.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;

@Getter
@Setter
@Entity
public class User extends BaseModel{
    String name;
    String email;
    String password;
    @ManyToMany
    HashSet<Role> roles;
}
