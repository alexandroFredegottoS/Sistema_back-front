package br.com.pedido.demo.service;

import br.com.pedido.demo.model.*;
import br.com.pedido.demo.repository.CadastroProdutoRepository;
import br.com.pedido.demo.repository.ItemPedidoRepository;
import br.com.pedido.demo.repository.PedidoRepository;
import br.com.pedido.demo.repository.UsuarioRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class CompraService {

    private final CadastroProdutoRepository produtoRepository;
    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    public CompraService(CadastroProdutoRepository produtoRepository,
                         PedidoRepository pedidoRepository,
                         UsuarioRepository usuarioRepository,
                         ItemPedidoRepository itemPedidoRepository) {

        this.produtoRepository = produtoRepository;
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public Pedido finalizarCompra(List<Long> produtosIds, Long clienteId, String descricao) {

        List<CadastroProdutoEntity> produtos =
                produtoRepository.findAllById(produtosIds);

        UsuarioEntity usuario =
                usuarioRepository.findById(clienteId).orElseThrow();

        Pedido pedido = new Pedido();
        pedido.setDescricao(descricao);
        pedido.setCliente(usuario);

        pedidoRepository.save(pedido);

        BigDecimal total = BigDecimal.ZERO;

        for (CadastroProdutoEntity p : produtos) {

            if (p.getEstoque() <= 0) {
                throw new RuntimeException(
                        "Produto sem estoque: " + p.getNome());
            }

            // BAIXA ESTOQUE
            p.setEstoque(p.getEstoque() - 1);
            produtoRepository.save(p);

            // CRIA ITEM DO PEDIDO
            Item_Pedido item = new Item_Pedido();
            item.setPedido(pedido);
            item.setProduto(p);
            item.setQuantidade(1L); // depois podemos pegar do front
            item.setVlUnitario(p.getVlUnitario());

            itemPedidoRepository.save(item);

            total = total.add(p.getVlUnitario());
        }

        System.out.println("Pedido com itens salvo!");

        return pedido; // IMPORTANTE
    }
}