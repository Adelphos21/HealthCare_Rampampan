package com.healthcare.healthcare.paciente.controller;

import com.healthcare.healthcare.paciente.dto.PacienteRequest;
import com.healthcare.healthcare.paciente.dto.PacienteResponse;
import com.healthcare.healthcare.cita.service.CitaService;
import com.healthcare.healthcare.cita.dto.CitaResponse;
import com.healthcare.healthcare.paciente.entity.Paciente;
import com.healthcare.healthcare.paciente.repository.PacienteRepository;
import com.healthcare.healthcare.paciente.service.PacienteService;
import com.healthcare.healthcare.usuario.entity.User;
import com.healthcare.healthcare.usuario.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;
    private final CitaService citaService;
    private final PacienteRepository pacienteRepository;

    @PostMapping
    public ResponseEntity<PacienteResponse> registrar(@Valid @RequestBody PacienteRequest request) {
        PacienteResponse response = pacienteService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEDICO')")
    public List<PacienteResponse> listar() {
        return pacienteService.listar();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?>  eliminar(@PathVariable Long id) {
        pacienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MEDICO')")
    @GetMapping("/{id}/citas")
    public Page<CitaResponse> historialCitas(
            @PathVariable Long id,
            @PageableDefault(size = 10, sort = "fechaCita", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return citaService.listarPorPaciente(id, pageable);
    }

    //Endpoints para PACIENTE
    @PreAuthorize("hasRole('PACIENTE')")
    @GetMapping("/mis-citas")
    public Page<CitaResponse> citasDelPacienteAutenticado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Paciente paciente = pacienteRepository.findByDni(username).orElseThrow();
        return citaService.listarPorPaciente(paciente.getId(), PageRequest.of(page, size));
    }

    @PreAuthorize("hasRole('PACIENTE')")
    @DeleteMapping("/")
    public ResponseEntity<?>  eliminar() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Paciente paciente = pacienteRepository.findByUsername(username).orElseThrow();
        pacienteService.eliminar(paciente.getId());
        return ResponseEntity.noContent().build();
    }
}
