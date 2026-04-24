package com.hotelreservas.service.implementation;

import com.hotelreservas.model.DetalleReserva;
import com.hotelreservas.repository.IDetalleReservaRepository;
import com.hotelreservas.service.IDetalleReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleReservaService implements IDetalleReservaService {

    @Autowired
    private IDetalleReservaRepository detalleReservaRepository;

    @Override
    public List<DetalleReserva> listarTodos() {
        return detalleReservaRepository.findAll();
    }

    @Override
    public List<DetalleReserva> listarPorReserva(Long reservaId) {
        return detalleReservaRepository.findByReservaId(reservaId);
    }

    @Override
    public Optional<DetalleReserva> buscarPorId(Long id) {
        return detalleReservaRepository.findById(id);
    }

    @Override
    public void eliminar(Long id) {
        if (!detalleReservaRepository.existsById(id)) {
            throw new RuntimeException("Detalle de reserva no encontrado con id: " + id);
        }
        detalleReservaRepository.deleteById(id);
    }
}
