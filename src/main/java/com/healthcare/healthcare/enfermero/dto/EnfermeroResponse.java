package com.healthcare.healthcare.enfermero.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EnfermeroResponse {
    private Long id;
    private String nombre;
    private String apellido;
    private String area;
    private String correo;
}