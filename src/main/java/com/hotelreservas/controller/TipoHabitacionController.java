package com.hotelreservas.controller;

import com.hotelreservas.model.TipoHabitacion;
import com.hotelreservas.service.ITipoHabitacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-habitacion")
@CrossOrigin(origins = "*")
public class TipoHabitacionController {

    @Autowired
    private ITipoHabitacionService tipoHabitacionService;

    @GetMapping
    public ResponseEntity<List<TipoHabitacion>> listarTodos() {
        return ResponseEntity.ok(tipoHabitacionService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return tipoHabitacionService.buscarPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Tipo de habitación no encontrado con id: " + id));
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody TipoHabitacion tipoHabitacion) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(tipoHabitacionService.guardar(tipoHabitacion));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody TipoHabitacion tipoHabitacion) {
        try {
            return ResponseEntity.ok(tipoHabitacionService.actualizar(id, tipoHabitacion));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            tipoHabitacionService.eliminar(id);
            return ResponseEntity.ok("Tipo de habitación eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}