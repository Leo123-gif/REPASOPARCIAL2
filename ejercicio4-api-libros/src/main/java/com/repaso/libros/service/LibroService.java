package com.repaso.libros.service;

import com.repaso.libros.model.Libro;
import com.repaso.libros.repository.LibroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Year;
import java.util.List;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public List<Libro> consultarLibros() {
        return libroRepository.buscarTodos();
    }

    public Libro registrarLibro(Libro libro) {
        validar(libro);
        if (libro.getEstado() == null || libro.getEstado().isBlank()) {
            libro.setEstado("DISPONIBLE");
        }
        return libroRepository.guardar(libro);
    }

    public Libro consultarPorTitulo(String titulo) {
        return libroRepository.buscarPorTitulo(titulo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No se encontró un libro con el título: " + titulo));
    }

    public Libro actualizarLibro(Long id, Libro datos) {
        validar(datos);
        return libroRepository.actualizar(id, datos)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No se encontró el libro con id: " + id));
    }

    public void eliminarLibro(Long id) {
        boolean eliminado = libroRepository.eliminar(id);
        if (!eliminado) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No se encontró el libro con id: " + id);
        }
    }

    private void validar(Libro libro) {
        if (libro.getTitulo() == null || libro.getTitulo().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El título es obligatorio");
        }
        if (libro.getAutor() == null || libro.getAutor().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El autor es obligatorio");
        }
        if (libro.getIsbn() == null || libro.getIsbn().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ISBN es obligatorio");
        }
        if (libro.getAnioPublicacion() == null
                || libro.getAnioPublicacion() < 0
                || libro.getAnioPublicacion() > Year.now().getValue()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El año de publicación debe ser válido");
        }
    }
}
