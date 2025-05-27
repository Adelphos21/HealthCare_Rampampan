package com.healthcare.healthcare.medico.service;

import com.healthcare.healthcare.medico.dto.MedicoRequest;
import com.healthcare.healthcare.medico.dto.MedicoResponse;
import com.healthcare.healthcare.medico.entity.Medico;
import com.healthcare.healthcare.medico.event.EliminarMedicoEmailEvent;
import com.healthcare.healthcare.medico.event.RegistrarMedicoEmailEvent;
import com.healthcare.healthcare.medico.repository.MedicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
//    private final EspecialidadRepository especialidadRepository;

    public MedicoResponse registrar(MedicoRequest request) {
        /*Especialidad especialidad = especialidadRepository.findById(request.getEspecialidadId())
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));*/

        Medico medico = Medico.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .dni(request.getDni())
                .telefono(request.getTelefono())
                .correo(request.getCorreo())
                .especialidad(request.getEspecialidadId())
                .estado(true)
                .build();

        medicoRepository.save(medico);
        applicationEventPublisher.publishEvent(new RegistrarMedicoEmailEvent(medico));
        return MedicoResponse.builder()
                .id(medico.getId())
                .nombre(medico.getNombre())
                .apellido(medico.getApellido())
                .especialidad(medico.getEspecialidad())
                .build();
    }

    public List<MedicoResponse> listar() {
        return medicoRepository.findAll().stream()
                .map(m -> MedicoResponse.builder()
                        .id(m.getId())
                        .nombre(m.getNombre())
                        .apellido(m.getApellido())
                        .especialidad(m.getEspecialidad())
                        .build()
                ).collect(Collectors.toList());
    }

    public void eliminar(Long id) {
        Medico medico = medicoRepository.findById(id).orElseThrow(()-> new RuntimeException("Medico no encontrado"));
        medicoRepository.deleteById(id);
        applicationEventPublisher.publishEvent(new EliminarMedicoEmailEvent(medico));
    }
}