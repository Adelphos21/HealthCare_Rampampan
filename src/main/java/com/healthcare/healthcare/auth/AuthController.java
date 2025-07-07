package com.healthcare.healthcare.auth;


import com.healthcare.healthcare.enfermero.dto.EnfermeroResponse;
import com.healthcare.healthcare.enfermero.entity.Enfermero;
import com.healthcare.healthcare.enfermero.repository.EnfermeroRepository;
import com.healthcare.healthcare.medico.dto.MedicoResponse;
import com.healthcare.healthcare.medico.entity.Medico;
import com.healthcare.healthcare.medico.repository.MedicoRepository;
import com.healthcare.healthcare.paciente.dto.PacienteResponse;
import com.healthcare.healthcare.paciente.entity.Paciente;
import com.healthcare.healthcare.paciente.repository.PacienteRepository;
import com.healthcare.healthcare.usuario.dto.AdminResponse;
import com.healthcare.healthcare.usuario.entity.Role;
import com.healthcare.healthcare.usuario.entity.User;
import com.healthcare.healthcare.usuario.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final EnfermeroRepository enfermeroRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getDni(), request.getPassword())
            );

            User user = (User) auth.getPrincipal();
            return ResponseEntity.ok(jwtService.generateToken(user));
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales inválidas");
        }
    }
    @GetMapping("/me")
    public Object me(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByDni(username).orElseThrow(() -> new UsernameNotFoundException("Paciente" +
                " no autenticado con DNI: " + username));
        System.out.println("Authentication: " + auth);
        System.out.println("Is authenticated? " + auth.isAuthenticated());
        System.out.println("Auth name → " + auth.getName());
        if(user.getRole()== Role.PACIENTE){
            Paciente paciente = pacienteRepository.findByDni(username)
                    .orElseThrow(() -> new RuntimeException("Paciente no encontrado con DNI: " + username));
            return PacienteResponse.builder()
                    .id(paciente.getId())
                    .nombre(paciente.getNombre())
                    .apellido_p(paciente.getApellido_p())
                    .apellido_m(paciente.getApellido_m())
                    .dni(paciente.getDni())
                    .sexo(paciente.getSexo())
                    .fecha_nacimiento(paciente.getFecha_nacimiento())
                    .telefono(paciente.getTelefono())
                    .correo(paciente.getCorreo())
                    .role(paciente.getRole())
                    .build();
        }
        else if(user.getRole() ==  Role.MEDICO){
            Medico medico = medicoRepository.findByDni(username)
                    .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
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

        }
        else if (user.getRole() == Role.ADMIN) {
            User admin = userRepository.findByDni(username).orElseThrow(()-> new RuntimeException("Admin no encontrado"));
            return AdminResponse.builder()
                    .id(admin.getId())
                    .dni(admin.getDni())
                    .role(admin.getRole())
                    .build();

        }

        else if(user.getRole() == Role.ENFERMERO){
            Enfermero enfermero = enfermeroRepository.findByDni(username)
                    .orElseThrow(()-> new RuntimeException("Enfermero no encontrado"));
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

        }
        return null;
    }
}
