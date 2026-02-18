package br.com.pedido.demo.repository;

import br.com.pedido.demo.model.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    long count();
}
