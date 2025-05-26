package com.healthcare.healthcare.enfermero.entity;


import com.healthcare.healthcare.usuario.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Enfermero extends User {


    private String telefono;
    private String correo;
    private String area;

    private Boolean estado;
    @ManyToOne
    @JoinColumn(name = "medico")
    private Medico medico;
}