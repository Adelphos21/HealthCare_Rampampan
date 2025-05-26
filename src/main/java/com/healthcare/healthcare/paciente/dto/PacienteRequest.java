package com.healthcare.healthcare.paciente.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteRequest {
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String correo;
}
