package com.healthcare.healthcare.medico.dto;

import com.healthcare.healthcare.medico.entity.Especialidad;
import com.healthcare.healthcare.usuario.entity.Role;
import com.healthcare.healthcare.usuario.entity.Sexo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class MedicoResponse {
    private Long id;
    private String nombre;
    private String apellido_p;
    private String apellido_m;
    private LocalDate fecha_nacimiento;
    private Sexo sexo;
    private Especialidad especialidad;
    private Role role;
}