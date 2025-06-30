package com.healthcare.healthcare.enfermero.service;

import com.healthcare.healthcare.enfermero.dto.EnfermeroRequest;
import com.healthcare.healthcare.enfermero.dto.EnfermeroResponse;
import com.healthcare.healthcare.enfermero.entity.Enfermero;
import com.healthcare.healthcare.enfermero.repository.EnfermeroRepository;
import com.healthcare.healthcare.medico.dto.MedicoResponse;
import com.healthcare.healthcare.medico.entity.Especialidad;
import com.healthcare.healthcare.usuario.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnfermeroService {

    private final PasswordEncoder passwordEncoder;
    private final EnfermeroRepository repository;

    public EnfermeroResponse registrar(EnfermeroRequest request) {
        Enfermero enfermero = Enfermero.builder()
                .nombre(request.getNombre())
                .apellido_p(request.getApellido_p())
                .apellido_m(request.getApellido_m())
                .dni(request.getDni())
                .sexo(request.getSexo())
                .fecha_nacimiento(request.getFecha_nacimiento())
                .telefono(request.getTelefono())
                .correo(request.getCorreo())
                .role(Role.ENFERMERO)
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .area(request.getArea())
                .estado(true)
                .build();

        repository.save(enfermero);

        return EnfermeroResponse.builder()
                .id(enfermero.getId())
                .nombre(enfermero.getNombre())
                .apellido_p(enfermero.getApellido_p())
                .apellido_m(enfermero.getApellido_m())
                .sexo(enfermero.getSexo())
                .correo(enfermero.getCorreo())
                .area(enfermero.getArea())
                .build();
    }

    public List<EnfermeroResponse> listar() {
        return repository.findAll().stream()
                .map(e -> EnfermeroResponse.builder()
                        .id(e.getId())
                        .nombre(e.getNombre())
                        .apellido_p(e.getApellido_p())
                        .apellido_m(e.getApellido_m())
                        .sexo(e.getSexo())
                        .fecha_nacimiento(e.getFecha_nacimiento())
                        .correo(e.getCorreo())
                        .area(e.getArea())
                        .build())
                .collect(Collectors.toList());
    }

    public List<EnfermeroResponse> listarPorMedico(Long medicoId) {
        return repository.findByMedicoId(medicoId).stream()
                .map(m -> EnfermeroResponse.builder()
                        .id(m.getId())
                        .nombre(m.getNombre())
                        .apellido_p(m.getApellido_p())
                        .apellido_m(m.getApellido_m())
                        .sexo(m.getSexo())
                        .correo(m.getCorreo())
                        .build()
                ).collect(Collectors.toList());
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}