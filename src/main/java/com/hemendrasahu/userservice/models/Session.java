package com.hemendrasahu.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Session extends BaseModel{
    String token;
    Date expireBy;
    @ManyToOne
    User user;
    @Enumerated(EnumType.STRING)
    SessionStatus status;
}
