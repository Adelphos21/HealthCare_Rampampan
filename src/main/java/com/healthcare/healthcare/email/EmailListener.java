package com.healthcare.healthcare.email;


import com.healthcare.healthcare.cita.dto.CitaResponse;
import com.healthcare.healthcare.cita.entity.Cita;
import com.healthcare.healthcare.cita.event.CambiarCitaEmailEvent;
import com.healthcare.healthcare.cita.event.CitaEliminadaEmailEvent;
import com.healthcare.healthcare.cita.event.CitaRegistradaEmailEvent;
import com.healthcare.healthcare.cita.service.CitaService;
import com.healthcare.healthcare.enfermero.entity.Enfermero;
import com.healthcare.healthcare.enfermero.event.EliminarEnfermeroEmailEvent;
import com.healthcare.healthcare.enfermero.event.RegistrarEnfermeroEmailEvent;
import com.healthcare.healthcare.medico.entity.Medico;
import com.healthcare.healthcare.medico.event.EliminarMedicoEmailEvent;
import com.healthcare.healthcare.medico.event.RegistrarMedicoEmailEvent;
import com.healthcare.healthcare.paciente.entity.Paciente;
import com.healthcare.healthcare.paciente.event.EliminarPacienteEmailEvent;
import com.healthcare.healthcare.paciente.event.HistorialCitasEmailEvent;
import com.healthcare.healthcare.paciente.event.RegistrarPacienteEmailEvent;
import com.healthcare.healthcare.pago.entity.Pago;
import com.healthcare.healthcare.pago.event.CreacionPagoEmailEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmailListener {
    private final EmailService emailService;
    private final CitaService citaService;
    @EventListener
    @Async
    public void handleRegistrarPacienteEmailEvent(RegistrarPacienteEmailEvent event) {
        Paciente paciente = event.getPaciente();

        Context context = new Context();
        context.setVariable("nombre", paciente.getNombre());
        context.setVariable("apellidos", paciente.getApellido_p() + " " + paciente.getApellido_m());
        context.setVariable("dni", paciente.getDni());
        context.setVariable("telefono", paciente.getTelefono());
        context.setVariable("correo", paciente.getCorreo());

        emailService.sendHtmlMessage(
                paciente.getCorreo(),
                "Registro exitoso de paciente",
                "registrar-paciente",
                context
        );
    }

    @EventListener
    @Async
    public void handlePacienteEliminadoEmailEvent(EliminarPacienteEmailEvent event) {
        Paciente paciente = event.getPaciente();
        Context context = new Context();
        context.setVariable("nombre", paciente.getNombre());

        emailService.sendHtmlMessage(
                paciente.getCorreo(),
                "Te esperamos pronto de vuelta en la clínica",
                "eliminar-paciente",
                context
        );
    }

    @EventListener
    @Async
    public void handleHistorialCitasEvent(HistorialCitasEmailEvent event) {
        Paciente paciente = event.getPaciente();
        List<CitaResponse> citas = citaService.listarPorPacienteSinPaginar(paciente.getId());

        Context context = new Context();
        context.setVariable("nombre", paciente.getNombre());
        context.setVariable("citas", citas);

        emailService.sendHtmlMessage(
                paciente.getCorreo(),
                "Historial de tus citas médicas",
                "historial-paciente",
                context
        );
    }

    @EventListener
    @Async
    public void handleRegistrarEnfermeroEmailEvent(RegistrarEnfermeroEmailEvent event) {
        Enfermero enfermero = event.getEnfermero();

        Context context = new Context();
        context.setVariable("nombre", enfermero.getNombre());
        context.setVariable("apellido", enfermero.getApellido_p() + " " + enfermero.getApellido_m());
        context.setVariable("dni", enfermero.getDni());
        context.setVariable("telefono", enfermero.getTelefono());
        context.setVariable("correo", enfermero.getCorreo());
        context.setVariable("area", enfermero.getArea());

        emailService.sendHtmlMessage(
                enfermero.getCorreo(),
                "Registro exitoso de enfermero",
                "registrar-enfermero",
                context
        );
    }


    @EventListener
    @Async
    public void handleEliminarEnfermeroEmailEvent(EliminarEnfermeroEmailEvent event) {
        Enfermero enfermero = event.getEnfermero();

        Context context = new Context();
        context.setVariable("nombre", enfermero.getNombre());

        emailService.sendHtmlMessage(
                enfermero.getCorreo(),
                "Gracias por tu servicio",
                "eliminar-enfermero",
                context
        );
    }

    @EventListener
    @Async
    public void handleRegistrarMedicoEmailEvent(RegistrarMedicoEmailEvent event) {
        Medico medico = event.getMedico();
        Context context = new Context();
        context.setVariable("nombre", medico.getNombre());
        context.setVariable("apellido", medico.getApellido_p()+" "+medico.getApellido_m());
        context.setVariable("dni", medico.getDni());
        context.setVariable("correo", medico.getCorreo());
        context.setVariable("telefono", medico.getTelefono());
        context.setVariable("especialidad", medico.getEspecialidad());

        emailService.sendHtmlMessage(
                medico.getCorreo(),
                "Registro exitoso de médico",
                "registrar-medico",
                context
        );
    }

    @EventListener
    @Async
    public void handleEliminarMedicoEmailEvent(EliminarMedicoEmailEvent event) {
        Medico medico = event.getMedico();

        Context context = new Context();
        context.setVariable("nombre", medico.getNombre());
        context.setVariable("apellido", medico.getApellido_p() + " "+medico.getApellido_m());
        context.setVariable("dni", medico.getDni());
        context.setVariable("correo", medico.getCorreo());

        emailService.sendHtmlMessage(
                medico.getCorreo(),
                "Médico eliminado del sistema",
                "eliminar-medico",
                context
        );
    }

    @EventListener
    @Async
    public void handleCitaRegistradaEmailEvent(CitaRegistradaEmailEvent event) {
        Cita cita = event.getCita();

        Context context = new Context();
        context.setVariable("nombre", cita.getPaciente().getNombre());
        context.setVariable("apellido", cita.getPaciente().getApellido_p() +" "+cita.getPaciente().getApellido_m());
        context.setVariable("fecha", cita.getFechaCita());
        context.setVariable("medico_nombre", cita.getMedico().getNombre());
        context.setVariable("medico_apellido", cita.getMedico().getApellido_p()+" " + cita.getPaciente().getApellido_m());
        emailService.sendHtmlMessage(event.getCita().getPaciente().getCorreo(),
                "Registro de cita exitoso", "crear-cita", context);
    }

    @EventListener
    @Async
    public void handleCitaEliminadaEmailEvent(CitaEliminadaEmailEvent event) {
        Cita cita = event.getCita();

        Context context = new Context();
        context.setVariable("nombre", cita.getPaciente().getNombre());
        context.setVariable("apellido", cita.getPaciente().getApellido_p() + " " + cita.getPaciente().getApellido_m());
        context.setVariable("fecha", cita.getFechaCita());
        context.setVariable("medico_nombre", cita.getMedico().getNombre());
        context.setVariable("medico_apellido", cita.getMedico().getApellido_p()+" " + cita.getPaciente().getApellido_m());

        emailService.sendHtmlMessage(
                cita.getPaciente().getCorreo(),
                "Cancelación de cita",
                "cancelar-cita",
                context
        );
    }

    @EventListener
    @Async
    public void handleCambiarEmailEvent(CambiarCitaEmailEvent event) {
        Cita cita = event.getCita();

        Context context = new Context();
        context.setVariable("nombre", cita.getPaciente().getNombre());
        context.setVariable("apellido", cita.getPaciente().getApellido_p() +" "+cita.getPaciente().getApellido_m());
        context.setVariable("fecha", cita.getFechaCita());
        context.setVariable("medico_nombre", cita.getMedico().getNombre());
        context.setVariable("medico_apellido", cita.getMedico().getApellido_p()+" " + cita.getPaciente().getApellido_m());

        emailService.sendHtmlMessage(
                cita.getPaciente().getCorreo(),
                "Reprogramacion de cita",
                "cambiar-cita",
                context
        );
    }

    @EventListener
    @Async
    public void handleCreacionPagoEmailEvent(CreacionPagoEmailEvent event) {
        Pago pago = event.getPago();
        Cita cita = pago.getCita();

        Context context = new Context();
        context.setVariable("nombre", cita.getPaciente().getNombre());
        context.setVariable("apellido", cita.getPaciente().getApellido_p() +" "+cita.getPaciente().getApellido_m());
        context.setVariable("fechaCita", cita.getFechaCita());
        context.setVariable("monto", pago.getMonto());
        context.setVariable("metodoPago", pago.getMetodoPago().toString());
        context.setVariable("pagado", pago.getPagado() ? "Sí" : "No");
        context.setVariable("fechaPago", pago.getFechaPago());

        emailService.sendHtmlMessage(
                cita.getPaciente().getCorreo(),
                "Confirmación de Pago",
                "creacion-pago",
                context);


    }
}
