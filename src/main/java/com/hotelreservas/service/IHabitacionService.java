package com.hotelreservas.service;

import com.hotelreservas.model.Habitacion;
import java.util.List;
import java.util.Optional;

public interface IHabitacionService {
    List<Habitacion> listarTodas();
    List<Habitacion> listarDisponibles();
    Optional<Habitacion> buscarPorId(Long id);
    Habitacion guardar(Habitacion habitacion);
    Habitacion actualizar(Long id, Habitacion habitacion);
    void eliminar(Long id);
}