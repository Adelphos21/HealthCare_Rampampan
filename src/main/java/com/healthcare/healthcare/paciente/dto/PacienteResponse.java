package com.healthcare.healthcare.paciente.dto;

import com.healthcare.healthcare.usuario.entity.Role;
import com.healthcare.healthcare.usuario.entity.Sexo;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class PacienteResponse {
    private Long id;
    private String nombre;
    private String apellido_p;
    private String apellido_m;
    private String dni;
    private LocalDate fecha_nacimiento;
    private Sexo sexo;
    private String telefono;
    private String correo;
    private Role role;
}
