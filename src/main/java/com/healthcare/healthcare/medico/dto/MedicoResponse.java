package com.healthcare.healthcare.medico.dto;

import com.healthcare.healthcare.medico.entity.Especialidad;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MedicoResponse {
    private Long id;
    private String nombre;
    private String apellido_p;
    private String apellido_m;
    private Especialidad especialidad;
}