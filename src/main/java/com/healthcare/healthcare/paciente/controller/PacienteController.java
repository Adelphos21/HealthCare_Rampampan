package com.healthcare.healthcare.paciente.controller;

import com.healthcare.healthcare.paciente.dto.PacienteRequest;
import com.healthcare.healthcare.paciente.dto.PacienteResponse;
import com.healthcare.healthcare.cita.service.CitaService;
import com.healthcare.healthcare.cita.dto.CitaResponse;
import com.healthcare.healthcare.paciente.service.PacienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;
    private final CitaService citaService;

    @PostMapping
    public PacienteResponse registrar(@Valid @RequestBody PacienteRequest request) {
        return pacienteService.registrar(request);
    }

    @GetMapping
    public List<PacienteResponse> listar() {
        return pacienteService.listar();
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        pacienteService.eliminar(id);
    }

    @GetMapping("/{id}/citas")
    public List<CitaResponse> historialCitas(@PathVariable Long id) {
        return citaService.listarPorPaciente(id);
    }
}
