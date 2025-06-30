package com.healthcare.healthcare.enfermero.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EnfermeroResponse {
    private Long id;
    private String nombre;
    private String apellido_p;
    private String apellido_m;
    private String area;
    private String correo;
}