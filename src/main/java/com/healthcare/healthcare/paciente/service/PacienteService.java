package com.healthcare.healthcare.paciente.service;

import com.healthcare.healthcare.paciente.dto.PacienteRequest;
import com.healthcare.healthcare.paciente.dto.PacienteResponse;
import com.healthcare.healthcare.paciente.entity.Paciente;
import com.healthcare.healthcare.paciente.repository.PacienteRepository;
import com.healthcare.healthcare.usuario.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PacienteService {
    private final PasswordEncoder passwordEncoder;
    private final PacienteRepository repository;

    public PacienteResponse registrar(PacienteRequest request) {

        if (repository.existsByDni(request.getDni())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese DNI.");
        }

        Paciente paciente = Paciente.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .dni(request.getDni())
                .telefono(request.getTelefono())
                .correo(request.getCorreo())
                .role(Role.PACIENTE)
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .username(request.getCorreo())
                .build();
        repository.save(paciente);
        return PacienteResponse.builder()
                .id(paciente.getId())
                .nombre(paciente.getNombre())
                .apellido(paciente.getApellido())
                .dni(paciente.getDni())
                .telefono(paciente.getTelefono())
                .correo(paciente.getCorreo())
                .build();
    }

    public List<PacienteResponse> listar() {
        return repository.findAll().stream().map(p ->
                PacienteResponse.builder()
                        .id(p.getId())
                        .nombre(p.getNombre())
                        .apellido(p.getApellido())
                        .dni(p.getDni())
                        .telefono(p.getTelefono())
                        .correo(p.getCorreo())
                        .build()
        ).collect(Collectors.toList());
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
