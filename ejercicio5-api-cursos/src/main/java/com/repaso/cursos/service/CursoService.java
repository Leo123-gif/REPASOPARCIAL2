package com.repaso.cursos.service;

import com.repaso.cursos.model.Curso;
import com.repaso.cursos.repository.CursoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public List<Curso> consultarCursos() {
        return cursoRepository.buscarTodos();
    }

    public Curso crearCurso(Curso curso) {
        validarDatosBasicos(curso);
        if (cursoRepository.existePorCodigo(curso.getCodigo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Ya existe un curso con el código: " + curso.getCodigo());
        }
        if (curso.getEstado() == null || curso.getEstado().isBlank()) {
            curso.setEstado("ACTIVO");
        }
        return cursoRepository.guardar(curso);
    }

    public Curso consultarPorCodigo(String codigo) {
        return cursoRepository.buscarPorCodigo(codigo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No se encontró un curso con el código: " + codigo));
    }

    public Curso actualizarCurso(Long id, Curso datos) {
        validarDatosBasicos(datos);
        return cursoRepository.actualizar(id, datos)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No se encontró el curso con id: " + id));
    }

    public void eliminarCurso(Long id) {
        boolean eliminado = cursoRepository.eliminar(id);
        if (!eliminado) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No se encontró el curso con id: " + id);
        }
    }

    private void validarDatosBasicos(Curso curso) {
        if (curso.getNombre() == null || curso.getNombre().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre es obligatorio");
        }
        if (curso.getCodigo() == null || curso.getCodigo().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El código es obligatorio");
        }
        if (curso.getCreditos() == null || curso.getCreditos() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Los créditos deben ser un número mayor a cero");
        }
    }
}
