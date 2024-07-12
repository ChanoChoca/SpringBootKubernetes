package org.chanochoca.springcloud.msvc.cursos.services;

import org.chanochoca.springcloud.msvc.cursos.clients.UsuarioClientRest;
import org.chanochoca.springcloud.msvc.cursos.models.Usuario;
import org.chanochoca.springcloud.msvc.cursos.models.entity.Curso;
import org.chanochoca.springcloud.msvc.cursos.models.entity.CursoUsuario;
import org.chanochoca.springcloud.msvc.cursos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;

    private final UsuarioClientRest client;

    @Autowired
    public CursoServiceImpl(CursoRepository cursoRepository, UsuarioClientRest client) {
        this.cursoRepository = cursoRepository;
        this.client = client;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Curso> listar() {
        return (List<Curso>) cursoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porId(Long id) {
        return cursoRepository.findById(id);
    }

    @Override
    @Transactional
    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    @Transactional
    public void elminar(Long id) {
        cursoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = cursoRepository.findById(cursoId);
        if (o.isPresent()) {
            // Obtener los detalles del usuario desde el microservicio Usuarios
            Usuario usuarioMsvc = client.detalle(usuario.getId());

            // Se obtiene el curso del Optional<Curso>.
            Curso curso = o.get();
            // Se crea una nueva instancia de CursoUsuario.
            CursoUsuario cursoUsuario = new CursoUsuario();
            // Se establece el id del usuario en cursoUsuario.
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());

            // Se añade el CursoUsuario al curso.
            curso.addCursoUsuario(cursoUsuario);
            // Se guarda el curso actualizado en el repositorio.
            cursoRepository.save(curso);
            // Se devuelve un Optional que contiene el usuario asignado.
            return Optional.of(usuarioMsvc);
        }
        // Si el curso no fue encontrado, se devuelve un Optional vacío.
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = cursoRepository.findById(cursoId);
        if (o.isPresent()) {
            // Crear un nuevo usuario desde el microservicio Usuarios
            Usuario usuarioNuevoMsvc = client.crear(usuario);

            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioNuevoMsvc.getId());

            curso.addCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return Optional.of(usuarioNuevoMsvc);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = cursoRepository.findById(cursoId);
        if (o.isPresent()) {
            Usuario usuarioMsvc = client.detalle(usuario.getId());

            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());

            // Eliminar usuario (solo en el microservicio Cliente).
            curso.removeCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return Optional.of(usuarioMsvc);
        }

        return Optional.empty();
    }
}
