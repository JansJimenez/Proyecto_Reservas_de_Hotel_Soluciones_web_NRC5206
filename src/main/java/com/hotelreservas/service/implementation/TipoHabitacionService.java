package com.hotelreservas.service.implementation;

import com.hotelreservas.model.TipoHabitacion;
import com.hotelreservas.repository.ITipoHabitacionRepository;
import com.hotelreservas.service.ITipoHabitacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoHabitacionService implements ITipoHabitacionService {

    @Autowired
    private ITipoHabitacionRepository tipoHabitacionRepository;

    @Override
    public List<TipoHabitacion> listarTodos() {
        return tipoHabitacionRepository.findAll();
    }

    @Override
    public Optional<TipoHabitacion> buscarPorId(Long id) {
        return tipoHabitacionRepository.findById(id);
    }

    @Override
    public TipoHabitacion guardar(TipoHabitacion tipoHabitacion) {
        if (tipoHabitacionRepository.existsByNombre(tipoHabitacion.getNombre())) {
            throw new RuntimeException("Ya existe un tipo de habitación con el nombre: " + tipoHabitacion.getNombre());
        }
        return tipoHabitacionRepository.save(tipoHabitacion);
    }

    @Override
    public TipoHabitacion actualizar(Long id, TipoHabitacion tipoActualizado) {
        TipoHabitacion existente = tipoHabitacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de habitación no encontrado con id: " + id));
        existente.setNombre(tipoActualizado.getNombre());
        existente.setDescripcion(tipoActualizado.getDescripcion());
        return tipoHabitacionRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        if (!tipoHabitacionRepository.existsById(id)) {
            throw new RuntimeException("Tipo de habitación no encontrado con id: " + id);
        }
        tipoHabitacionRepository.deleteById(id);
    }
}