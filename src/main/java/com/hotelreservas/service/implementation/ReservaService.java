package com.hotelreservas.service.implementation;

import com.hotelreservas.exception.BadRequestException;
import com.hotelreservas.exception.ResourceNotFoundException;
import com.hotelreservas.model.DetalleReserva;
import com.hotelreservas.model.Habitacion;
import com.hotelreservas.model.Huesped;
import com.hotelreservas.model.Reserva;
import com.hotelreservas.repository.IHabitacionRepository;
import com.hotelreservas.repository.IHuespedRepository;
import com.hotelreservas.repository.IReservaRepository;
import com.hotelreservas.service.IReservaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservaService extends GenericServiceImpl<Reserva, Long> implements IReservaService {

    private final IReservaRepository reservaRepository;
    private final IHuespedRepository huespedRepository;
    private final IHabitacionRepository habitacionRepository;

    public ReservaService(IReservaRepository reservaRepository,
                           IHuespedRepository huespedRepository,
                           IHabitacionRepository habitacionRepository) {
        super(reservaRepository, "Reserva");
        this.reservaRepository = reservaRepository;
        this.huespedRepository = huespedRepository;
        this.habitacionRepository = habitacionRepository;
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
    @Transactional
    public Reserva guardar(Reserva reserva) {
        Huesped huesped = huespedRepository.findById(reserva.getHuesped().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Huésped no encontrado con id: " + reserva.getHuesped().getId()));
        reserva.setHuesped(huesped);

        if (!reserva.getFechaSalida().isAfter(reserva.getFechaIngreso())) {
            throw new BadRequestException("La fecha de salida debe ser posterior a la fecha de ingreso");
        }

        double total = 0;
        if (reserva.getDetalles() != null) {
            for (DetalleReserva detalle : reserva.getDetalles()) {
                Habitacion habitacion = habitacionRepository.findById(detalle.getHabitacion().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Habitación no encontrada con id: " + detalle.getHabitacion().getId()));

                if (!Boolean.TRUE.equals(habitacion.getDisponible())) {
                    throw new BadRequestException("La habitación " + habitacion.getNumero() + " no está disponible");
                }

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
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + id));
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
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + id));
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
