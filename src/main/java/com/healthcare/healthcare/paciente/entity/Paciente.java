package com.healthcare.healthcare.paciente.entity;

import com.healthcare.healthcare.historialMedico.entity.HistorialMedico;
import com.healthcare.healthcare.usuario.entity.User;
import com.healthcare.healthcare.cita.entity.Cita;
import com.healthcare.healthcare.pago.entity.Pago;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Paciente extends User {

    private String telefono;
    private String correo;
    @OneToMany
    @JoinColumn(name = "pago")
    private List<Pago> pagos;

    @OneToMany
    @JoinColumn(name = "cita")
    private List<Cita> citas;

    @OneToMany
    @JoinColumn(name = "historialMedico")
    private List<HistorialMedico> historialMedicos;
}
