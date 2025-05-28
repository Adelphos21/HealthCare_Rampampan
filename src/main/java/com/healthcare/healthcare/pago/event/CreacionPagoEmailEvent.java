package com.healthcare.healthcare.pago.event;

import com.healthcare.healthcare.pago.entity.Pago;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CreacionPagoEmailEvent extends ApplicationEvent {
    Pago pago;
    public CreacionPagoEmailEvent(Pago pago){
        super(pago);
        this.pago=pago;
    }
}