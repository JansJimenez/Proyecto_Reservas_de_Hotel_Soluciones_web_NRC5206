package com.hotelreservas.service;

import com.hotelreservas.model.DetalleReserva;
import java.util.List;
import java.util.Optional;

public interface IDetalleReservaService {
    List<DetalleReserva> listarTodos();
    List<DetalleReserva> listarPorReserva(Long reservaId);
    Optional<DetalleReserva> buscarPorId(Long id);
    void eliminar(Long id);
}
