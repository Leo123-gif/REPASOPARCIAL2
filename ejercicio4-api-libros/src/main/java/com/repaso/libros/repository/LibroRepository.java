package com.repaso.libros.repository;

import com.repaso.libros.model.Libro;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class LibroRepository {

    private final List<Libro> libros = new ArrayList<>();
    private final AtomicLong secuenciaId = new AtomicLong(0);

    public LibroRepository() {
        guardar(new Libro(null, "Cien años de soledad", "Gabriel García Márquez", "978-0307474728", 1967, "DISPONIBLE"));
        guardar(new Libro(null, "Don Quijote de la Mancha", "Miguel de Cervantes", "978-8420412146", 1605, "DISPONIBLE"));
        guardar(new Libro(null, "1984", "George Orwell", "978-0451524935", 1949, "DISPONIBLE"));
        guardar(new Libro(null, "El principito", "Antoine de Saint-Exupéry", "978-0156012195", 1943, "PRESTADO"));
        guardar(new Libro(null, "Rayuela", "Julio Cortázar", "978-8437604572", 1963, "DISPONIBLE"));
    }

    public List<Libro> buscarTodos() {
        return new ArrayList<>(libros);
    }

    public Optional<Libro> buscarPorId(Long id) {
        return libros.stream()
                .filter(libro -> libro.getId().equals(id))
                .findFirst();
    }

    public Optional<Libro> buscarPorTitulo(String titulo) {
        return libros.stream()
                .filter(libro -> libro.getTitulo().equalsIgnoreCase(titulo))
                .findFirst();
    }

    public Libro guardar(Libro libro) {
        libro.setId(secuenciaId.incrementAndGet());
        libros.add(libro);
        return libro;
    }

    public Optional<Libro> actualizar(Long id, Libro datos) {
        return buscarPorId(id).map(existente -> {
            existente.setTitulo(datos.getTitulo());
            existente.setAutor(datos.getAutor());
            existente.setIsbn(datos.getIsbn());
            existente.setAnioPublicacion(datos.getAnioPublicacion());
            existente.setEstado(datos.getEstado());
            return existente;
        });
    }

    public boolean eliminar(Long id) {
        return libros.removeIf(libro -> libro.getId().equals(id));
    }
}
