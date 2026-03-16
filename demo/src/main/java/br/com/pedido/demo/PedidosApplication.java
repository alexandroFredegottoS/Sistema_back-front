package br.com.pedido.demo;

import br.com.pedido.demo.model.Pedido;
import br.com.pedido.demo.model.UsuarioEntity;
import br.com.pedido.demo.repository.PedidoRepository;
import br.com.pedido.demo.repository.UsuarioRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PedidosApplication {

	public static void main(String[] args) {
		SpringApplication.run(PedidosApplication.class, args);
	}

}
