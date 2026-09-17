package com.repaso.reservas.repository;

import com.repaso.reservas.model.Reserva;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservaRepository {

    private final List<Reserva> reservas = new ArrayList<>();
    private final AtomicLong secuenciaId = new AtomicLong(0);

    public ReservaRepository() {
        guardar(new Reserva(null, "Ana Torres", "101",
                LocalDate.of(2026, 3, 10), LocalDate.of(2026, 3, 15), "CONFIRMADA"));
        guardar(new Reserva(null, "Luis Fernández", "102",
                LocalDate.of(2026, 4, 1), LocalDate.of(2026, 4, 5), "CONFIRMADA"));
        guardar(new Reserva(null, "Carla Gómez", "205",
                LocalDate.of(2026, 5, 20), LocalDate.of(2026, 5, 22), "CONFIRMADA"));
        guardar(new Reserva(null, "Pedro Ruiz", "310",
                LocalDate.of(2026, 6, 12), LocalDate.of(2026, 6, 18), "FINALIZADA"));
        guardar(new Reserva(null, "María López", "111",
                LocalDate.of(2026, 7, 1), LocalDate.of(2026, 7, 3), "CANCELADA"));
    }

    public List<Reserva> buscarTodas() {
        return new ArrayList<>(reservas);
    }

    public Optional<Reserva> buscarPorId(Long id) {
        return reservas.stream()
                .filter(reserva -> reserva.getId().equals(id))
                .findFirst();
    }

    public Reserva guardar(Reserva reserva) {
        reserva.setId(secuenciaId.incrementAndGet());
        reservas.add(reserva);
        return reserva;
    }

    public Optional<Reserva> actualizar(Long id, Reserva datos) {
        return buscarPorId(id).map(existente -> {
            existente.setNombreCliente(datos.getNombreCliente());
            existente.setHabitacion(datos.getHabitacion());
            existente.setFechaEntrada(datos.getFechaEntrada());
            existente.setFechaSalida(datos.getFechaSalida());
            existente.setEstado(datos.getEstado());
            return existente;
        });
    }

    public Optional<Reserva> cambiarEstado(Long id, String nuevoEstado) {
        return buscarPorId(id).map(existente -> {
            existente.setEstado(nuevoEstado);
            return existente;
        });
    }
}
