package com.healthcare.healthcare.medico.dto;

import com.healthcare.healthcare.medico.entity.Especialidad;
import com.healthcare.healthcare.usuario.entity.Sexo;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MedicoRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido_p;
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido_m;

    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 8, max = 8, message = "El DNI debe tener exactamente 8 caracteres")
    private String dni;

    @NotNull(message = "La fecha de la cita es obligatoria")
    @Past(message = "La fecha de la cita debe ser futura")
    private LocalDate fecha_nacimiento;

    @NotNull
    private Sexo sexo;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "\\d{9}", message = "El teléfono debe tener 9 dígitos")
    private String telefono;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    private String correo;

    @NotBlank(message = "La contraseña no puede ser vacia")
    private String password;

    @NotNull(message = "La especialidad es obligatoria")
    private Especialidad especialidad;
}