package com.hotelreservas.service.implementation;

import com.hotelreservas.model.Habitacion;
import com.hotelreservas.model.TipoHabitacion;
import com.hotelreservas.repository.IHabitacionRepository;
import com.hotelreservas.repository.ITipoHabitacionRepository;
import com.hotelreservas.service.IHabitacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HabitacionService implements IHabitacionService {

    @Autowired
    private IHabitacionRepository habitacionRepository;

    @Autowired
    private ITipoHabitacionRepository tipoHabitacionRepository;

    @Override
    public List<Habitacion> listarTodas() {
        return habitacionRepository.findAll();
    }

    @Override
    public List<Habitacion> listarDisponibles() {
        return habitacionRepository.findByDisponibleTrue();
    }

    @Override
    public Optional<Habitacion> buscarPorId(Long id) {
        return habitacionRepository.findById(id);
    }

    @Override
    public Habitacion guardar(Habitacion habitacion) {
        if (habitacionRepository.existsByNumero(habitacion.getNumero())) {
            throw new RuntimeException("Ya existe una habitación con el número: " + habitacion.getNumero());
        }
        TipoHabitacion tipo = tipoHabitacionRepository.findById(habitacion.getTipoHabitacion().getId())
                .orElseThrow(() -> new RuntimeException("Tipo de habitación no encontrado"));
        habitacion.setTipoHabitacion(tipo);
        return habitacionRepository.save(habitacion);
    }

    @Override
    public Habitacion actualizar(Long id, Habitacion habitacionActualizada) {
        Habitacion existente = habitacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada con id: " + id));
        TipoHabitacion tipo = tipoHabitacionRepository.findById(habitacionActualizada.getTipoHabitacion().getId())
                .orElseThrow(() -> new RuntimeException("Tipo de habitación no encontrado"));
        existente.setNumero(habitacionActualizada.getNumero());
        existente.setTipoHabitacion(tipo);
        existente.setPrecioPorNoche(habitacionActualizada.getPrecioPorNoche());
        existente.setCapacidad(habitacionActualizada.getCapacidad());
        existente.setServicios(habitacionActualizada.getServicios());
        existente.setDisponible(habitacionActualizada.getDisponible());
        return habitacionRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        if (!habitacionRepository.existsById(id)) {
            throw new RuntimeException("Habitación no encontrada con id: " + id);
        }
        habitacionRepository.deleteById(id);
    }
}