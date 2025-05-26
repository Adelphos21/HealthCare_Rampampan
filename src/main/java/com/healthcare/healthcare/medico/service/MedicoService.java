package com.healthcare.healthcare.medico.service;

import com.healthcare.healthcare.medico.dto.MedicoRequest;
import com.healthcare.healthcare.medico.dto.MedicoResponse;
import com.healthcare.healthcare.medico.entity.Medico;
import com.healthcare.healthcare.medico.repository.MedicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.healthcare.healthcare.especialidad.entity.Especialidad;
import com.healthcare.healthcare.especialidad.repository.EspecialidadRepository;;

@Service
@RequiredArgsConstructor
public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final EspecialidadRepository especialidadRepository;

    public MedicoResponse registrar(MedicoRequest request) {
        Especialidad especialidad = especialidadRepository.findById(request.getEspecialidadId())
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));

        Medico medico = Medico.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .dni(request.getDni())
                .telefono(request.getTelefono())
                .correo(request.getCorreo())
                .especialidad(especialidad)
                .estado(true)
                .build();

        medicoRepository.save(medico);

        return MedicoResponse.builder()
                .id(medico.getId())
                .nombre(medico.getNombre())
                .apellido(medico.getApellido())
                .especialidad(medico.getEspecialidad().getNombre())
                .build();
    }

    public List<MedicoResponse> listar() {
        return medicoRepository.findAll().stream()
                .map(m -> MedicoResponse.builder()
                        .id(m.getId())
                        .nombre(m.getNombre())
                        .apellido(m.getApellido())
                        .especialidad(m.getEspecialidad().getNombre())
                        .build()
                ).collect(Collectors.toList());
    }

    public void eliminar(Long id) {
        medicoRepository.deleteById(id);
    }
}