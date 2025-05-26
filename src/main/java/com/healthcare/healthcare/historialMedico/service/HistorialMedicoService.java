package com.healthcare.healthcare.historialMedico.service;

import com.healthcare.healthcare.historialMedico.dto.HistorialMedicoRequest;
import com.healthcare.healthcare.historialMedico.dto.HistorialMedicoResponse;

import java.util.List;

public interface HistorialMedicoService {
    HistorialMedicoResponse crear(HistorialMedicoRequest request);
    List<HistorialMedicoResponse> listarPorPaciente(Long pacienteId);
}