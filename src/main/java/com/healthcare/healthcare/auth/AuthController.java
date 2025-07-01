package com.healthcare.healthcare.auth;


import com.healthcare.healthcare.paciente.dto.PacienteResponse;
import com.healthcare.healthcare.paciente.entity.Paciente;
import com.healthcare.healthcare.paciente.repository.PacienteRepository;
import com.healthcare.healthcare.usuario.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final PacienteRepository pacienteRepository;

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
    public PacienteResponse me(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        System.out.println("Authentication: " + auth);
        System.out.println("Is authenticated? " + auth.isAuthenticated());
        System.out.println("Auth name → " + auth.getName());
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
                .build();
    }
}
