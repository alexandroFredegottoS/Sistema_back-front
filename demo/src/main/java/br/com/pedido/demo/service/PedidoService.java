package br.com.pedido.demo.service;

import br.com.pedido.demo.model.Pedido;
import br.com.pedido.demo.model.UsuarioEntity;
import br.com.pedido.demo.repository.PedidoRepository;
import br.com.pedido.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         UsuarioRepository usuarioRepository){
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void salvarPedido(String descricao, Long clienteId){

        UsuarioEntity cliente = usuarioRepository
                .findById(clienteId)
                .orElseThrow();

        Pedido pedido = new Pedido();
        pedido.setDescricao(descricao);
        pedido.setCliente(cliente);

        pedidoRepository.save(pedido);
    }
    /**
     * Retorna a quantidade total de pedidos cadastrados.
     */
    public long contarPedidos(){
        return pedidoRepository.count();
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }
}
