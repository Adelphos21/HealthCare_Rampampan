package com.healthcare.healthcare.enfermero.event;

import com.healthcare.healthcare.enfermero.entity.Enfermero;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EliminarEnfermeroEmailEvent extends ApplicationEvent {
    Enfermero enfermero;
    public EliminarEnfermeroEmailEvent(Enfermero enfermero){
        super(enfermero);
        this.enfermero=enfermero;
    }
}
