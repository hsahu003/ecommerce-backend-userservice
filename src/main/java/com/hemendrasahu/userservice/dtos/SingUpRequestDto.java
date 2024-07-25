package com.hemendrasahu.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingUpRequestDto {
    private String name;
    private String email;
    private String password;
}
