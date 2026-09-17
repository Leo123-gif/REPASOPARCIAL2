package com.repaso.cursos.repository;

import com.repaso.cursos.model.Curso;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CursoRepository {

    private final List<Curso> cursos = new ArrayList<>();
    private final AtomicLong secuenciaId = new AtomicLong(0);

    public CursoRepository() {
        guardar(new Curso(null, "Programación I", "PROG101", 4, "ACTIVO"));
        guardar(new Curso(null, "Estructuras de Datos", "EDA201", 5, "ACTIVO"));
        guardar(new Curso(null, "Bases de Datos", "BDD301", 4, "ACTIVO"));
        guardar(new Curso(null, "Ingeniería de Software", "ISW401", 3, "ACTIVO"));
        guardar(new Curso(null, "Redes de Computadoras", "RED501", 3, "INACTIVO"));
    }

    public List<Curso> buscarTodos() {
        return new ArrayList<>(cursos);
    }

    public Optional<Curso> buscarPorId(Long id) {
        return cursos.stream()
                .filter(curso -> curso.getId().equals(id))
                .findFirst();
    }

    public Optional<Curso> buscarPorCodigo(String codigo) {
        return cursos.stream()
                .filter(curso -> curso.getCodigo().equalsIgnoreCase(codigo))
                .findFirst();
    }

    public boolean existePorCodigo(String codigo) {
        return buscarPorCodigo(codigo).isPresent();
    }

    public Curso guardar(Curso curso) {
        curso.setId(secuenciaId.incrementAndGet());
        cursos.add(curso);
        return curso;
    }

    public Optional<Curso> actualizar(Long id, Curso datos) {
        return buscarPorId(id).map(existente -> {
            existente.setNombre(datos.getNombre());
            existente.setCodigo(datos.getCodigo());
            existente.setCreditos(datos.getCreditos());
            existente.setEstado(datos.getEstado());
            return existente;
        });
    }

    public boolean eliminar(Long id) {
        return cursos.removeIf(curso -> curso.getId().equals(id));
    }
}
