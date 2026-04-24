package com.hotelreservas.controller;

import com.hotelreservas.model.DetalleReserva;
import com.hotelreservas.service.IDetalleReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalles-reserva")
@CrossOrigin(origins = "*")
public class DetalleReservaController {

    @Autowired
    private IDetalleReservaService detalleReservaService;

    @GetMapping
    public ResponseEntity<List<DetalleReserva>> listarTodos() {
        return ResponseEntity.ok(detalleReservaService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return detalleReservaService.buscarPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Detalle de reserva no encontrado con id: " + id));
    }

    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<List<DetalleReserva>> listarPorReserva(@PathVariable Long reservaId) {
        return ResponseEntity.ok(detalleReservaService.listarPorReserva(reservaId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            detalleReservaService.eliminar(id);
            return ResponseEntity.ok("Detalle de reserva eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}