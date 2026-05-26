package com.hotelreservas.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleReservaRequestDTO {
    private Long habitacionId;
    private Integer cantidadNoches;
}
