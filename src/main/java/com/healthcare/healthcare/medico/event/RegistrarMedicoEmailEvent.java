package com.healthcare.healthcare.medico.event;

import com.healthcare.healthcare.medico.entity.Medico;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class RegistrarMedicoEmailEvent extends ApplicationEvent {
    private final Medico medico;

    public RegistrarMedicoEmailEvent(Medico medico) {
        super(medico);
        this.medico = medico;
    }
}