package com.hotelreservas.repository;

import com.hotelreservas.model.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IHuespedRepository extends JpaRepository<Huesped, Long> {
    Optional<Huesped> findByDni(String dni);
    Optional<Huesped> findByCorreo(String correo);
    boolean existsByDni(String dni);
    boolean existsByCorreo(String correo);
}