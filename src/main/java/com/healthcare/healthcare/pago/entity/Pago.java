package com.healthcare.healthcare.pago.entity;

import com.healthcare.healthcare.cita.entity.Cita;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "cita_id")
    private Cita cita;

    @Column(nullable = false)
    private Double monto;


    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago; // EFECTIVO, TARJETA, YAPE, etc.

    @CreationTimestamp
    private LocalDateTime fechaPago;

    @Column(nullable = false)
    private Boolean pagado;
}
