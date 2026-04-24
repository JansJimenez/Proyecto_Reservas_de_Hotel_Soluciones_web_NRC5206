package com.hotelreservas.repository;

import com.hotelreservas.model.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHabitacionRepository extends JpaRepository<Habitacion, Long> {
    List<Habitacion> findByDisponibleTrue();
    List<Habitacion> findByTipoHabitacionId(Long tipoId);
    boolean existsByNumero(String numero);
}