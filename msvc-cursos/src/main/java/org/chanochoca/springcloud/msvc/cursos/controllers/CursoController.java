package org.chanochoca.springcloud.msvc.cursos.controllers;

import feign.FeignException;
import jakarta.validation.Valid;
import org.chanochoca.springcloud.msvc.cursos.models.Usuario;
import org.chanochoca.springcloud.msvc.cursos.models.entity.Curso;
import org.chanochoca.springcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CursoController {

    private final CursoService cursoService;

    @Autowired
    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    /** Devuelve una lista de todos los cursos. */
    @GetMapping
    public ResponseEntity<List<Curso>> listar() {
        return ResponseEntity.ok(cursoService.listar());
    }

    /** Devuelve el detalle de un curso específico por su id. */
    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        /* El método porId devuelve un Optional<Curso>,
        que puede contener el curso encontrado
        o estar vacío si no se encuentra ningún curso con ese id. */
        Optional<Curso> o = cursoService.porId(id);
        if (o.isPresent()) {
            return ResponseEntity.ok(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    /** Crea un nuevo curso. */
    //Nota: BindingResult debe colocarse justo luego de lo que se quiere validar
    @PostMapping("/")
    public ResponseEntity<?> crear(@Valid @RequestBody Curso curso, BindingResult result) {

        /* Se comprueba si hay errores de validación en el objeto Curso. */
        if (result.hasErrors()) {
            /* Generar una respuesta con los errores de validación y se retorna dicha respuesta. */
            return validar(result);
        }

        Curso cursoDb = cursoService.guardar(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoDb);
    }

    /** Actualiza un curso existente. */
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            return validar(result);
        }

        /* Buscar el curso por su id. */
        Optional<Curso> o = cursoService.porId(id);
        if (o.isPresent()) {
            /* Se obtiene el Curso desde la base de datos. */
            Curso cursoDb = o.get();
            /* Se actualiza el nombre del curso existente con el de la solicitud. */
            cursoDb.setNombre(curso.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.guardar(cursoDb));
        }
        return ResponseEntity.notFound().build();
    }

    /** Elimina un curso existente. */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Curso> o = cursoService.porId(id);
        if (o.isPresent()) {
            cursoService.elminar(o.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /** Asigna un usuario a un curso. */
    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> o;
        try {
            // asignarUsuario puede contener el usuario asignado o estar vacío.
            o = cursoService.asignarUsuario(usuario, cursoId);
        } catch (FeignException e) {
            // Si ocurre problemas en la comunicación con un servicio remoto.
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No existe el usuario por " +
                            "el id o error en la comunicación: " + e.getMessage()));
        }

        // Comprobar si Optional<Usuario> tiene un valor.
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    /** Crea y asigna un nuevo usuario a un curso. */
    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> o;
        try {
            o = cursoService.crearUsuario(usuario, cursoId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No se pudo crear el usuario " +
                            "o error en la comunicación: " + e.getMessage()));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    /** Elimina un usuario de un curso. */
    @DeleteMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> o;
        try {
            // Se desasigna el usuario del curso, no se elimina desde el microservicio Usuarios.
            o = cursoService.eliminarUsuario(usuario, cursoId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No existe el usuario por " +
                            "el id o error en la comunicación: " + e.getMessage()));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err ->
                errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errores);
    }
}
