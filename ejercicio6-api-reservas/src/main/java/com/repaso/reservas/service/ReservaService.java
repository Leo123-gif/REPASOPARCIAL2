package com.repaso.reservas.service;

import com.repaso.reservas.model.Reserva;
import com.repaso.reservas.repository.ReservaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReservaService {

    private static final String ESTADO_CONFIRMADA = "CONFIRMADA";
    private static final String ESTADO_CANCELADA = "CANCELADA";

    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public List<Reserva> consultarReservas() {
        return reservaRepository.buscarTodas();
    }

    public Reserva crearReserva(Reserva reserva) {
        validar(reserva);
        if (reserva.getEstado() == null || reserva.getEstado().isBlank()) {
            reserva.setEstado(ESTADO_CONFIRMADA);
        }
        return reservaRepository.guardar(reserva);
    }

    public Reserva consultarPorId(Long id) {
        return reservaRepository.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No se encontró la reserva con id: " + id));
    }

    public Reserva actualizarReserva(Long id, Reserva datos) {
        validar(datos);
        return reservaRepository.actualizar(id, datos)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No se encontró la reserva con id: " + id));
    }

    /**
     * Cancela la reserva cambiando su estado a CANCELADA en lugar de eliminarla
     * físicamente, para conservar el historial.
     */
    public Reserva cancelarReserva(Long id) {
        return reservaRepository.cambiarEstado(id, ESTADO_CANCELADA)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No se encontró la reserva con id: " + id));
    }

    private void validar(Reserva reserva) {
        if (reserva.getNombreCliente() == null || reserva.getNombreCliente().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del cliente es obligatorio");
        }
        if (reserva.getHabitacion() == null || reserva.getHabitacion().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La habitación es obligatoria");
        }
        if (reserva.getFechaEntrada() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de entrada es obligatoria");
        }
        if (reserva.getFechaSalida() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de salida es obligatoria");
        }
        if (reserva.getFechaSalida().isBefore(reserva.getFechaEntrada())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La fecha de salida no puede ser anterior a la fecha de entrada");
        }
    }
}
