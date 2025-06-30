package com.healthcare.healthcare.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String dni;
    private String password;
}