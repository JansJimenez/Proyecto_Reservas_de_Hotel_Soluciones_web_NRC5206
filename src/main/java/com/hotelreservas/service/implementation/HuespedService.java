package com.hotelreservas.service.implementation;

import com.hotelreservas.model.Huesped;
import com.hotelreservas.repository.IHuespedRepository;
import com.hotelreservas.service.IHuespedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HuespedService implements IHuespedService {

    @Autowired
    private IHuespedRepository huespedRepository;

    @Override
    public List<Huesped> listarTodos() {
        return huespedRepository.findAll();
    }

    @Override
    public Optional<Huesped> buscarPorId(Long id) {
        return huespedRepository.findById(id);
    }

    @Override
    public Optional<Huesped> buscarPorDni(String dni) {
        return huespedRepository.findByDni(dni);
    }

    @Override
    public Huesped guardar(Huesped huesped) {
        if (huespedRepository.existsByDni(huesped.getDni())) {
            throw new RuntimeException("Ya existe un huésped con el DNI: " + huesped.getDni());
        }
        if (huespedRepository.existsByCorreo(huesped.getCorreo())) {
            throw new RuntimeException("Ya existe un huésped con el correo: " + huesped.getCorreo());
        }
        return huespedRepository.save(huesped);
    }

    @Override
    public Huesped actualizar(Long id, Huesped huespedActualizado) {
        Huesped existente = huespedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Huésped no encontrado con id: " + id));
        existente.setNombre(huespedActualizado.getNombre());
        existente.setApellido(huespedActualizado.getApellido());
        existente.setDni(huespedActualizado.getDni());
        existente.setCorreo(huespedActualizado.getCorreo());
        existente.setTelefono(huespedActualizado.getTelefono());
        existente.setDireccion(huespedActualizado.getDireccion());
        return huespedRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        if (!huespedRepository.existsById(id)) {
            throw new RuntimeException("Huésped no encontrado con id: " + id);
        }
        huespedRepository.deleteById(id);
    }
}