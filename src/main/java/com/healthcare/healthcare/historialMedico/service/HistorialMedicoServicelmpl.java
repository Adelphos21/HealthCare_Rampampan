package com.healthcare.healthcare.historialMedico.service;

import com.healthcare.healthcare.historialMedico.dto.HistorialMedicoRequest;
import com.healthcare.healthcare.historialMedico.dto.HistorialMedicoResponse;

import java.util.List;

import com.healthcare.healthcare.historialMedico.entity.HistorialMedico;
import com.healthcare.healthcare.historialMedico.repository.HistorialMedicoRepository;
import com.healthcare.healthcare.paciente.entity.Paciente;
import com.healthcare.healthcare.paciente.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistorialMedicoServicelmpl implements HistorialMedicoService {

    private final HistorialMedicoRepository historialMedicoRepo;
    private final PacienteRepository pacienteRepo;

    @Override
    public HistorialMedicoResponse crear(HistorialMedicoRequest request) {
        Paciente paciente = pacienteRepo.findById(request.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        HistorialMedico historial = HistorialMedico.builder()
                .paciente(paciente)
                .diagnostico(request.getDiagnostico())
                .tratamiento(request.getTratamiento())
                .build();

        historial = historialMedicoRepo.save(historial);

        return toResponse(historial);
    }

    @Override
    public List<HistorialMedicoResponse> listarPorPaciente(Long pacienteId) {
        return historialMedicoRepo.findByPacienteId(pacienteId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private HistorialMedicoResponse toResponse(HistorialMedico h) {
        return HistorialMedicoResponse.builder()
                .id(h.getId())
                .pacienteId(h.getPaciente().getId())
                .diagnostico(h.getDiagnostico())
                .tratamiento(h.getTratamiento())
                .fechaRegistro(h.getFechaRegistro())
                .build();
    }
}