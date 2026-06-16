package com.hotelreservas.repository;

import com.hotelreservas.model.Reserva;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReservaRepository extends IGenericRepository<Reserva, Long> {
    List<Reserva> findByHuespedId(Long huespedId);
    List<Reserva> findByEstado(String estado);
}