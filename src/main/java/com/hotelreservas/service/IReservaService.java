package com.hotelreservas.service;

import com.hotelreservas.model.Reserva;
import java.util.List;
import java.util.Optional;

public interface IReservaService {
    List<Reserva> listarTodas();
    List<Reserva> listarPorHuesped(Long huespedId);
    List<Reserva> listarPorEstado(String estado);
    Optional<Reserva> buscarPorId(Long id);
    Reserva guardar(Reserva reserva);
    Reserva actualizar(Long id, Reserva reserva);
    void eliminar(Long id);
}