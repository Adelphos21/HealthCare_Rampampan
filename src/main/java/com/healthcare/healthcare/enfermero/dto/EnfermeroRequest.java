package com.healthcare.healthcare.enfermero.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnfermeroRequest {
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String correo;
    private String area;
}