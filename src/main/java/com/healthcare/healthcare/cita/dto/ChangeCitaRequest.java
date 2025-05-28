package com.healthcare.healthcare.cita.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ChangeCitaRequest {
    private LocalDate nuevaFecha;
}
