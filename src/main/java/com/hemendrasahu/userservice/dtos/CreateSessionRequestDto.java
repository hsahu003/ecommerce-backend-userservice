package com.hemendrasahu.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSessionRequestDto {
    private String email;
    private String password;
}
