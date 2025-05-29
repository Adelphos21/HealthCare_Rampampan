package com.healthcare.healthcare.paciente.service;

import com.healthcare.healthcare.exception.BadRequestException;
import com.healthcare.healthcare.exception.ConflictException;
import com.healthcare.healthcare.exception.NotFoundException;
import com.healthcare.healthcare.paciente.dto.PacienteRequest;
import com.healthcare.healthcare.paciente.dto.PacienteResponse;
import com.healthcare.healthcare.paciente.entity.Paciente;
import com.healthcare.healthcare.paciente.event.EliminarPacienteEmailEvent;
import com.healthcare.healthcare.paciente.event.RegistrarPacienteEmailEvent;
import com.healthcare.healthcare.paciente.repository.PacienteRepository;
import com.healthcare.healthcare.usuario.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PacienteService {
    private final PasswordEncoder passwordEncoder;
    private final PacienteRepository repository;
    private final ApplicationEventPublisher applicationEventPublisher;
    public PacienteResponse registrar(PacienteRequest request) {

        if (repository.existsByDni(request.getDni())) {
            throw new ConflictException("Ya existe un usuario con ese DNI.");
        }
        if(!isValidEmail(request.getCorreo())){
            throw new BadRequestException("Correo no valido");
        }
        if (repository.existsByCorreo(request.getCorreo())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo.");
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
        applicationEventPublisher.publishEvent(new RegistrarPacienteEmailEvent(paciente));
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
        Paciente paciente = repository.findById(id).orElseThrow(() ->new NotFoundException("No existe un paciente con el id dado"));
        repository.deleteById(id);
        applicationEventPublisher.publishEvent(new EliminarPacienteEmailEvent(paciente));
    }
    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$");
    }
}
