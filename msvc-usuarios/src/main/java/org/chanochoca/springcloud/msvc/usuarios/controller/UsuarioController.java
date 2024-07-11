package org.chanochoca.springcloud.msvc.usuarios.controller;

import org.chanochoca.springcloud.msvc.usuarios.models.entity.Usuario;
import org.chanochoca.springcloud.msvc.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
//ControllerAdvice (manejar errores)
public class UsuarioController {

    private UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    List<Usuario> listar() {
        return usuarioService.listar();
    }

    //Ejemplo con PathVariable
//    @GetMapping("/{id}")
//    public Usuario detalle(@PathVariable(name="id") Long ida)

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioService.porId(id);
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.ok().body(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED). se hizo lo mismo debajo
    public ResponseEntity<?> crear(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Usuario usuario, @PathVariable Long id) {
        Optional<Usuario> optional = usuarioService.porId(id);
        if (optional.isPresent()) {
            Usuario usuarioDb = optional.get();
            usuarioDb.setNombre(usuario.getNombre());
            usuarioDb.setEmail(usuario.getEmail());
            usuarioDb.setPassword(usuario.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(usuarioDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Usuario> o = usuarioService.porId(id);
        if (o.isPresent()) {
            usuarioService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
