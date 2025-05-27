package com.healthcare.healthcare.medico.entity;

import com.healthcare.healthcare.enfermero.entity.Enfermero;
import com.healthcare.healthcare.usuario.entity.User;
import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.JoinColumn;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Medico extends User {


    private String telefono;
    private String correo;
    private Especialidad especialidad;

    @OneToMany
    @JoinColumn(name = "enfermero")
    private List<Enfermero> enfermero;

    private boolean estado = true;
}