package org.chanochoca.springcloud.msvc.cursos.services;

import org.chanochoca.springcloud.msvc.cursos.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {
    List<Curso> listar();
    Optional<Curso> porId(Long id);
    Curso guardar(Curso curso);
    void elminar(Long id);
}
