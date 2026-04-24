package com.hotelreservas.repository;

import com.hotelreservas.model.DetalleReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDetalleReservaRepository extends JpaRepository<DetalleReserva, Long> {
    List<DetalleReserva> findByReservaId(Long reservaId);
}