package com.hotelreservas.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabitacionRequestDTO {
    private String numero;
    private Long tipoHabitacionId;   // solo el ID, no el objeto completo
    private Double precioPorNoche;
    private Integer capacidad;
    private String servicios;
    private Boolean disponible;
}