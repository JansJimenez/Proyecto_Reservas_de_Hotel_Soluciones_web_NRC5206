package com.hotelreservas.repository;

import com.hotelreservas.model.TipoHabitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITipoHabitacionRepository extends JpaRepository<TipoHabitacion, Long> {
    boolean existsByNombre(String nombre);
}