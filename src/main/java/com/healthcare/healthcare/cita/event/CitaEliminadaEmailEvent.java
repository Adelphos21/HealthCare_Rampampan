package com.healthcare.healthcare.cita.event;

import com.healthcare.healthcare.cita.entity.Cita;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CitaEliminadaEmailEvent extends ApplicationEvent {
    private final Cita cita;

    public CitaEliminadaEmailEvent(Cita cita){
        super(cita);
        this.cita = cita;
    }
}
