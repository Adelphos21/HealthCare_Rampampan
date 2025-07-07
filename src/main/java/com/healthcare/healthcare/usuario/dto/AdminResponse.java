package com.healthcare.healthcare.usuario.dto;


import com.healthcare.healthcare.usuario.entity.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminResponse {
    private Long id;
    private String dni;
    private Role role;
}
