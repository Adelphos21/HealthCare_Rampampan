package com.healthcare.healthcare.medico.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicoRequest {
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String correo;
    private Long especialidadId;
}