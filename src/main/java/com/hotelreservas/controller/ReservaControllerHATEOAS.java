package com.hotelreservas.controller;

// ---------------------------------------------------------------
// NIVEL 3 HATEOAS — ejemplo aplicado a ReservaController
// Lo mismo aplica para los demás controllers (Habitacion, Huesped, etc.)
// ---------------------------------------------------------------
// Dependencia a agregar en pom.xml:
//   <dependency>
//       <groupId>org.springframework.boot</groupId>
//       <artifactId>spring-boot-starter-hateoas</artifactId>
//   </dependency>
// ---------------------------------------------------------------

import com.hotelreservas.model.Reserva;
import com.hotelreservas.service.IReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class ReservaControllerHATEOAS {

    @Autowired
    private IReservaService reservaService;

    // GET /api/reservas — lista con link self por cada item + link a colección
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Reserva>>> listarTodas() {

        List<EntityModel<Reserva>> reservas = reservaService.listarTodas().stream()
                .map(reserva -> EntityModel.of(reserva,
                        // self: link al recurso individual
                        linkTo(methodOn(ReservaControllerHATEOAS.class)
                                .buscarPorId(reserva.getId())).withSelfRel(),
                        // colección: link de vuelta a la lista
                        linkTo(methodOn(ReservaControllerHATEOAS.class)
                                .listarTodas()).withRel("reservas")
                ))
                .collect(Collectors.toList());

        // Link self de la colección completa
        Link selfLink = linkTo(methodOn(ReservaControllerHATEOAS.class)
                .listarTodas()).withSelfRel();

        return ResponseEntity.ok(CollectionModel.of(reservas, selfLink));
    }

    // GET /api/reservas/{id} — recurso individual con links de acción
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Reserva>> buscarPorId(@PathVariable Long id) {

        return reservaService.buscarPorId(id)
                .map(reserva -> {
                    EntityModel<Reserva> model = EntityModel.of(reserva,
                            // self
                            linkTo(methodOn(ReservaControllerHATEOAS.class)
                                    .buscarPorId(id)).withSelfRel(),
                            // colección
                            linkTo(methodOn(ReservaControllerHATEOAS.class)
                                    .listarTodas()).withRel("reservas"),
                            // huésped asociado
                            linkTo(methodOn(HuespedController.class)
                                    .buscarPorId(reserva.getHuesped().getId())).withRel("huesped"),
                            // detalles de esta reserva
                            linkTo(methodOn(DetalleReservaController.class)
                                    .listarPorReserva(id)).withRel("detalles")
                    );
                    return ResponseEntity.ok(model);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/reservas/huesped/{huespedId}
    @GetMapping("/huesped/{huespedId}")
    public ResponseEntity<CollectionModel<EntityModel<Reserva>>> listarPorHuesped(
            @PathVariable Long huespedId) {

        List<EntityModel<Reserva>> reservas = reservaService.listarPorHuesped(huespedId).stream()
                .map(reserva -> EntityModel.of(reserva,
                        linkTo(methodOn(ReservaControllerHATEOAS.class)
                                .buscarPorId(reserva.getId())).withSelfRel(),
                        linkTo(methodOn(ReservaControllerHATEOAS.class)
                                .listarTodas()).withRel("reservas")
                ))
                .collect(Collectors.toList());

        Link selfLink = linkTo(methodOn(ReservaControllerHATEOAS.class)
                .listarPorHuesped(huespedId)).withSelfRel();

        return ResponseEntity.ok(CollectionModel.of(reservas, selfLink));
    }

    // POST, PUT, DELETE no requieren HATEOAS según la actividad (solo GET)
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Reserva reserva) {
        try {
            Reserva nueva = reservaService.guardar(reserva);
            // Opcionalmente podés retornar con link self al recurso creado
            EntityModel<Reserva> model = EntityModel.of(nueva,
                    linkTo(methodOn(ReservaControllerHATEOAS.class)
                            .buscarPorId(nueva.getId())).withSelfRel()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(model);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Reserva reserva) {
        try {
            return ResponseEntity.ok(reservaService.actualizar(id, reserva));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            reservaService.eliminar(id);
            return ResponseEntity.ok("Reserva eliminada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
