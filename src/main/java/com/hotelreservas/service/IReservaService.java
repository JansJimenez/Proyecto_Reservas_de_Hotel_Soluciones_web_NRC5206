package com.hotelreservas.service;

import com.hotelreservas.model.Reserva;

import java.util.List;

public interface IReservaService extends IGenericService<Reserva, Long> {
    List<Reserva> listarPorHuesped(Long huespedId);
    List<Reserva> listarPorEstado(String estado);
}
