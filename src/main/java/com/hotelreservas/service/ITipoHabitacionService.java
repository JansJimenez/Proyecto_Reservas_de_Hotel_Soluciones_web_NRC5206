package com.hotelreservas.service;

import com.hotelreservas.model.TipoHabitacion;
import java.util.List;
import java.util.Optional;

public interface ITipoHabitacionService {
    List<TipoHabitacion> listarTodos();
    Optional<TipoHabitacion> buscarPorId(Long id);
    TipoHabitacion guardar(TipoHabitacion tipoHabitacion);
    TipoHabitacion actualizar(Long id, TipoHabitacion tipoHabitacion);
    void eliminar(Long id);
}