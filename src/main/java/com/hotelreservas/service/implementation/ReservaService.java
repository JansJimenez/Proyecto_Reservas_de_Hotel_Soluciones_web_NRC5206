package com.hotelreservas.service.implementation;

import com.hotelreservas.model.DetalleReserva;
import com.hotelreservas.model.Habitacion;
import com.hotelreservas.model.Huesped;
import com.hotelreservas.model.Reserva;
import com.hotelreservas.repository.IHabitacionRepository;
import com.hotelreservas.repository.IHuespedRepository;
import com.hotelreservas.repository.IReservaRepository;
import com.hotelreservas.service.IReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService implements IReservaService {

    @Autowired
    private IReservaRepository reservaRepository;

    @Autowired
    private IHuespedRepository huespedRepository;

    @Autowired
    private IHabitacionRepository habitacionRepository;

    @Override
    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }

    @Override
    public List<Reserva> listarPorHuesped(Long huespedId) {
        return reservaRepository.findByHuespedId(huespedId);
    }

    @Override
    public List<Reserva> listarPorEstado(String estado) {
        return reservaRepository.findByEstado(estado);
    }

    @Override
    public Optional<Reserva> buscarPorId(Long id) {
        return reservaRepository.findById(id);
    }

    @Override
    @Transactional
    public Reserva guardar(Reserva reserva) {
        Huesped huesped = huespedRepository.findById(reserva.getHuesped().getId())
                .orElseThrow(() -> new RuntimeException("Huésped no encontrado con id: " + reserva.getHuesped().getId()));
        reserva.setHuesped(huesped);

        if (!reserva.getFechaSalida().isAfter(reserva.getFechaIngreso())) {
            throw new RuntimeException("La fecha de salida debe ser posterior a la fecha de ingreso");
        }

        double total = 0;
        if (reserva.getDetalles() != null) {
            for (DetalleReserva detalle : reserva.getDetalles()) {
                Habitacion habitacion = habitacionRepository.findById(detalle.getHabitacion().getId())
                        .orElseThrow(() -> new RuntimeException("Habitación no encontrada con id: " + detalle.getHabitacion().getId()));
                detalle.setHabitacion(habitacion);
                detalle.setPrecioUnitario(habitacion.getPrecioPorNoche());
                detalle.setSubtotal(habitacion.getPrecioPorNoche() * detalle.getCantidadNoches());
                detalle.setReserva(reserva);
                total += detalle.getSubtotal();
                habitacion.setDisponible(false);
                habitacionRepository.save(habitacion);
            }
        }
        reserva.setTotalPagar(total);

        if (reserva.getEstado() == null || reserva.getEstado().isBlank()) {
            reserva.setEstado("PENDIENTE");
        }
        return reservaRepository.save(reserva);
    }

    @Override
    @Transactional
    public Reserva actualizar(Long id, Reserva reservaActualizada) {
        Reserva existente = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + id));
        existente.setFechaIngreso(reservaActualizada.getFechaIngreso());
        existente.setFechaSalida(reservaActualizada.getFechaSalida());
        existente.setEstado(reservaActualizada.getEstado());
        existente.setObservaciones(reservaActualizada.getObservaciones());
        existente.setTotalPagar(reservaActualizada.getTotalPagar());
        return reservaRepository.save(existente);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + id));
        if (reserva.getDetalles() != null) {
            for (DetalleReserva detalle : reserva.getDetalles()) {
                Habitacion habitacion = detalle.getHabitacion();
                habitacion.setDisponible(true);
                habitacionRepository.save(habitacion);
            }
        }
        reservaRepository.deleteById(id);
    }
}