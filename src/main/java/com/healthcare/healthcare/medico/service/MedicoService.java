package com.healthcare.healthcare.medico.service;

import com.healthcare.healthcare.exception.BadRequestException;
import com.healthcare.healthcare.exception.ConflictException;
import com.healthcare.healthcare.exception.NotFoundException;
import com.healthcare.healthcare.medico.dto.MedicoRequest;
import com.healthcare.healthcare.medico.dto.MedicoResponse;
import com.healthcare.healthcare.medico.entity.Especialidad;
import com.healthcare.healthcare.medico.entity.Medico;
import com.healthcare.healthcare.medico.event.EliminarMedicoEmailEvent;
import com.healthcare.healthcare.medico.event.RegistrarMedicoEmailEvent;
import com.healthcare.healthcare.medico.repository.MedicoRepository;
import com.healthcare.healthcare.usuario.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final PasswordEncoder passwordEncoder;
//    private final EspecialidadRepository especialidadRepository;

    public MedicoResponse registrar(MedicoRequest request) {
        /*Especialidad especialidad = especialidadRepository.findById(request.getEspecialidadId())
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));*/

        if(medicoRepository.existsByDni(request.getDni())){
            throw new ConflictException("Ya existe un medico con ese DNI");
        }

        if(!isValidEmail(request.getCorreo())){
            throw new BadRequestException("Correo no valido");
        }

        if (medicoRepository.existsByCorreo(request.getCorreo())) {
            throw new ConflictException("Ya existe un usuario con ese correo.");
        }

        Medico medico = Medico.builder()
                .nombre(request.getNombre())
                .apellido_p(request.getApellido_p())
                .apellido_m(request.getApellido_m())
                .dni(request.getDni())
                .sexo(request.getSexo())
                .fecha_nacimiento(request.getFecha_nacimiento())
                .telefono(request.getTelefono())
                .correo(request.getCorreo())
                .especialidad(request.getEspecialidad())
                .estado(true)
                .role(Role.MEDICO)
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .username(request.getCorreo())
                .build();

        medicoRepository.save(medico);
        applicationEventPublisher.publishEvent(new RegistrarMedicoEmailEvent(medico));
        return MedicoResponse.builder()
                .id(medico.getId())
                .nombre(medico.getNombre())
                .apellido_p(medico.getApellido_p())
                .apellido_m(medico.getApellido_m())
                .sexo(medico.getSexo())
                .fecha_nacimiento(medico.getFecha_nacimiento())
                .especialidad(medico.getEspecialidad())
                .build();
    }

    public List<MedicoResponse> listar() {
        return medicoRepository.findAll().stream()
                .map(m -> MedicoResponse.builder()
                        .id(m.getId())
                        .nombre(m.getNombre())
                        .apellido_p(m.getApellido_p())
                        .apellido_m(m.getApellido_m())
                        .sexo(m.getSexo())
                        .fecha_nacimiento(m.getFecha_nacimiento())
                        .especialidad(m.getEspecialidad())
                        .build()
                ).collect(Collectors.toList());
    }

    public List<MedicoResponse> listarPorEspecialidad(Especialidad especialidad) {
        return medicoRepository.findByEspecialidad(especialidad).stream()
                .map(m -> MedicoResponse.builder()
                        .id(m.getId())
                        .nombre(m.getNombre())
                        .apellido_p(m.getApellido_p())
                        .apellido_m(m.getApellido_m())
                        .sexo(m.getSexo())
                        .fecha_nacimiento(m.getFecha_nacimiento())
                        .especialidad(m.getEspecialidad())
                        .build()
                ).collect(Collectors.toList());
    }

    public void eliminar(Long id) {
        Medico medico = medicoRepository.findById(id).orElseThrow(()-> new NotFoundException("Medico no encontrado"));
        medicoRepository.deleteById(id);
        applicationEventPublisher.publishEvent(new EliminarMedicoEmailEvent(medico));
    }
    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$");
    }
}