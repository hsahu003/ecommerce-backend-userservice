package com.hemendrasahu.userservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Session extends BaseModel{
    String token;
    Date expireBy;
    SessionStatus status;
}
