package br.com.pedido.demo.controller;

import br.com.pedido.demo.service.CadastroProdutoService;
import br.com.pedido.demo.service.PedidoService;
import br.com.pedido.demo.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final UsuarioService usuarioService;
    private final CadastroProdutoService cadastroProdutoService;
    private final PedidoService pedidoService;

    public DashboardController(UsuarioService usuarioService, CadastroProdutoService cadastroProdutoService, PedidoService pedidoService) {
        this.usuarioService = usuarioService;
        this.cadastroProdutoService = cadastroProdutoService;
        this.pedidoService = pedidoService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("totalUsuarios", usuarioService.contarUsuarios());
        model.addAttribute("totalProdutos", cadastroProdutoService.contarProdutos());
        model.addAttribute("totalPedidos", pedidoService.contarPedidos());

        return "dashboard";
    }
}
