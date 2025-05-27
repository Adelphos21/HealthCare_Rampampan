package com.healthcare.healthcare.paciente.event;

import com.healthcare.healthcare.paciente.entity.Paciente;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EliminarPacienteEmailEvent extends ApplicationEvent {
    private final Paciente paciente;

    public EliminarPacienteEmailEvent(Paciente paciente) {
        super(paciente);
        this.paciente = paciente;
    }
}
