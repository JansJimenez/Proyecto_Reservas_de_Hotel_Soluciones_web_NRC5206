package com.hotelreservas.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaRequestDTO {
    private Long huespedId;
    private LocalDate fechaIngreso;
    private LocalDate fechaSalida;
    private String estado;
    private String observaciones;
    private List<DetalleReservaRequestDTO> detalles;
}
