package com.hotelreservas.service.implementation;

import com.hotelreservas.exception.ResourceNotFoundException;
import com.hotelreservas.repository.IGenericRepository;
import com.hotelreservas.service.IGenericService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación genérica de operaciones CRUD reutilizables por
 * los servicios concretos. Cada servicio específico extiende esta
 * clase indicando su entidad, su tipo de ID y el repositorio a usar,
 * y sólo necesita implementar la lógica de negocio particular
 * (validaciones, mapeos, reglas, etc.).
 *
 * @param <T>  Tipo de la entidad
 * @param <ID> Tipo del identificador de la entidad
 */
public abstract class GenericServiceImpl<T, ID> implements IGenericService<T, ID> {

    protected final IGenericRepository<T, ID> repository;
    private final String nombreEntidad;

    protected GenericServiceImpl(IGenericRepository<T, ID> repository, String nombreEntidad) {
        this.repository = repository;
        this.nombreEntidad = nombreEntidad;
    }

    @Override
    public List<T> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<T> buscarPorId(ID id) {
        return repository.findById(id);
    }

    @Override
    public T guardar(T entidad) {
        return repository.save(entidad);
    }

    @Override
    public T actualizar(ID id, T entidad) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(nombreEntidad + " no encontrado con id: " + id);
        }
        return repository.save(entidad);
    }

    @Override
    public void eliminar(ID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(nombreEntidad + " no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }
}
