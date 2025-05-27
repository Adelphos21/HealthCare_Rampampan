package com.healthcare.healthcare.enfermero.event;

import com.healthcare.healthcare.enfermero.entity.Enfermero;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class RegistrarEnfermeroEmailEvent extends ApplicationEvent {
    Enfermero enfermero;
    public RegistrarEnfermeroEmailEvent(Enfermero enfermero){
        super(enfermero);
        this.enfermero=enfermero;
    }
}
