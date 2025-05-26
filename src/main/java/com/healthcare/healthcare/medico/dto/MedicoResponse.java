package com.healthcare.healthcare.medico.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MedicoResponse {
    private Long id;
    private String nombre;
    private String apellido;
    private String especialidad;
}