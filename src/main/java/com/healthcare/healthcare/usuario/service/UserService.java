package com.healthcare.healthcare.usuario.service;

import com.healthcare.healthcare.enfermero.dto.EnfermeroResponse;
import com.healthcare.healthcare.enfermero.entity.Enfermero;
import com.healthcare.healthcare.enfermero.repository.EnfermeroRepository;
import com.healthcare.healthcare.medico.dto.MedicoResponse;
import com.healthcare.healthcare.medico.entity.Medico;
import com.healthcare.healthcare.medico.repository.MedicoRepository;
import com.healthcare.healthcare.paciente.dto.PacienteResponse;
import com.healthcare.healthcare.paciente.entity.Paciente;
import com.healthcare.healthcare.paciente.repository.PacienteRepository;
import com.healthcare.healthcare.usuario.entity.Role;
import com.healthcare.healthcare.usuario.entity.User;
import com.healthcare.healthcare.usuario.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final EnfermeroRepository enfermeroRepository;

    /**
     * Busca un usuario (paciente, médico, enfermero o admin) por su ID y devuelve
     * el Response DTO correspondiente.
     */
    public Object findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuario no encontrado con ID: " + id));

        Role role = user.getRole();
        switch (role) {
            case PACIENTE:
                Paciente p = pacienteRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Paciente no encontrado con ID: " + id));
                return PacienteResponse.builder()
                        .id(p.getId())
                        .nombre(p.getNombre())
                        .apellido_p(p.getApellido_p())
                        .apellido_m(p.getApellido_m())
                        .dni(p.getDni())
                        .sexo(p.getSexo())
                        .fecha_nacimiento(p.getFecha_nacimiento())
                        .telefono(p.getTelefono())
                        .correo(p.getCorreo())
                        .role(p.getRole())
                        .build();

            case MEDICO:
                Medico medico = medicoRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Médico no encontrado con ID: " + id));
                return MedicoResponse.builder()
                        .id(medico.getId())
                        .nombre(medico.getNombre())
                        .apellido_p(medico.getApellido_p())
                        .apellido_m(medico.getApellido_m())
                        .fecha_nacimiento(medico.getFecha_nacimiento())
                        .especialidad(medico.getEspecialidad())
                        .sexo(medico.getSexo())
                        .role(medico.getRole())
                        .build();

            case ENFERMERO:
                Enfermero enfermero = enfermeroRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Enfermero no encontrado con ID: " + id));
                return EnfermeroResponse.builder()
                        .id(enfermero.getId())
                        .nombre(enfermero.getNombre())
                        .apellido_p(enfermero.getApellido_p())
                        .apellido_m(enfermero.getApellido_m())
                        .fecha_nacimiento(enfermero.getFecha_nacimiento())
                        .sexo(enfermero.getSexo())
                        .area(enfermero.getArea())
                        .correo(enfermero.getCorreo())
                        .role(enfermero.getRole())
                        .build();

            default:
                throw new ResponseStatusException(BAD_REQUEST, "Rol no soportado: " + role);
        }
    }
}