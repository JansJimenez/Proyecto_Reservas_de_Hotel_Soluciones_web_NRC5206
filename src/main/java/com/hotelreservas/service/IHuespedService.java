package com.hotelreservas.service;

import com.hotelreservas.model.Huesped;
import java.util.List;
import java.util.Optional;

public interface IHuespedService {
    List<Huesped> listarTodos();
    Optional<Huesped> buscarPorId(Long id);
    Optional<Huesped> buscarPorDni(String dni);
    Huesped guardar(Huesped huesped);
    Huesped actualizar(Long id, Huesped huesped);
    void eliminar(Long id);
}