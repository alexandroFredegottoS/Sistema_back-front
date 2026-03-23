package br.com.pedido.demo.repository;

import br.com.pedido.demo.model.Item_Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<Item_Pedido, Long> {
}