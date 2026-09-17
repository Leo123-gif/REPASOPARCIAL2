package com.repaso.reservas.controller;

import com.repaso.reservas.model.Reserva;
import com.repaso.reservas.service.ReservaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@RequestBody Reserva reserva) {
        Reserva creada = reservaService.crearReserva(reserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping
    public ResponseEntity<List<Reserva>> consultarReservas() {
        return ResponseEntity.ok(reservaService.consultarReservas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> consultarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.consultarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizarReserva(@PathVariable Long id, @RequestBody Reserva reserva) {
        return ResponseEntity.ok(reservaService.actualizarReserva(id, reserva));
    }

    /**
     * No elimina físicamente la reserva: la cancela cambiando su estado a
     * CANCELADA (ver documentación en reservas-api.yaml).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Reserva> cancelarReserva(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.cancelarReserva(id));
    }
}
