package com.hemendrasahu.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpireSessionRequestDto {
    private String token;
}
