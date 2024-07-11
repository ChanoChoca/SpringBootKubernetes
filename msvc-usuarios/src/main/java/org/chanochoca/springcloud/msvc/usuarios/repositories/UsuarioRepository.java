package org.chanochoca.springcloud.msvc.usuarios.repositories;

import org.chanochoca.springcloud.msvc.usuarios.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;
//no es necesario utilizar @Repostory o @Component, porque
//ya es un objeto componente Spring
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {}
