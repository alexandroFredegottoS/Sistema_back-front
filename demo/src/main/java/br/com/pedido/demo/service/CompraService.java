package br.com.pedido.demo.service;

import br.com.pedido.demo.model.*;
import br.com.pedido.demo.repository.CadastroProdutoRepository;
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

    public CompraService(CadastroProdutoRepository produtoRepository,
                         PedidoRepository pedidoRepository,
                         UsuarioRepository usuarioRepository) {

        this.produtoRepository = produtoRepository;
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void finalizarCompra(List<Long> produtosIds, Long clienteId, String descricao) {

        List<CadastroProdutoEntity> produtos =
                produtoRepository.findAllById(produtosIds);

        BigDecimal total = BigDecimal.ZERO;

        for (CadastroProdutoEntity p : produtos) {

            if (p.getEstoque() <= 0) {
                throw new RuntimeException(
                        "Produto sem estoque: " + p.getNome());
            }

            total = total.add(p.getVlUnitario());

            p.setEstoque(p.getEstoque() - 1);

            produtoRepository.save(p);
        }

        UsuarioEntity usuario =
                usuarioRepository.findById(clienteId).orElseThrow();

        Pedido pedido = new Pedido();
        pedido.setDescricao(descricao);
        pedido.setCliente(usuario);

        pedidoRepository.save(pedido);

        System.out.println("Pedido salvo com sucesso!");
    }
}