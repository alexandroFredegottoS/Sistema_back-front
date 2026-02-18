package br.com.pedido.demo.repository;

import br.com.pedido.demo.model.CadastroProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CadastroProdutoRepository extends JpaRepository<CadastroProdutoEntity, Long> {
    long count();

    List<CadastroProdutoEntity>
    findByNomeContainingIgnoreCase(String nome);
}
