package com.hotelreservas.controller;

import com.hotelreservas.model.Huesped;
import com.hotelreservas.service.IHuespedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/huespedes")
@CrossOrigin(origins = "*")
public class HuespedController {

    @Autowired
    private IHuespedService huespedService;

    @GetMapping
    public ResponseEntity<List<Huesped>> listarTodos() {
        return ResponseEntity.ok(huespedService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return huespedService.buscarPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Huésped no encontrado con id: " + id));
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<?> buscarPorDni(@PathVariable String dni) {
        return huespedService.buscarPorDni(dni)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Huésped no encontrado con DNI: " + dni));
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Huesped huesped) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(huespedService.guardar(huesped));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Huesped huesped) {
        try {
            return ResponseEntity.ok(huespedService.actualizar(id, huesped));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            huespedService.eliminar(id);
            return ResponseEntity.ok("Huésped eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
