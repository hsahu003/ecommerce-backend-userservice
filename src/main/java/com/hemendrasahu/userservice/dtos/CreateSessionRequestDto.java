package com.hemendrasahu.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSessionRequestDto {
    private String name;
    private String email;
}
