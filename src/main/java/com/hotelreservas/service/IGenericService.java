package com.hotelreservas.service;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio genérico con operaciones CRUD básicas.
 *
 * @param <T>  Tipo de la entidad
 * @param <ID> Tipo del identificador de la entidad
 */
public interface IGenericService<T, ID> {

    List<T> listarTodos();

    Optional<T> buscarPorId(ID id);

    T guardar(T entidad);

    T actualizar(ID id, T entidad);

    void eliminar(ID id);
}
