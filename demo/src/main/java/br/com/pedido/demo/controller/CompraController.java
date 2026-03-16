package br.com.pedido.demo.controller;

import br.com.pedido.demo.model.CadastroProdutoEntity;
import br.com.pedido.demo.model.UsuarioEntity;
import br.com.pedido.demo.repository.UsuarioRepository;
import br.com.pedido.demo.repository.CadastroProdutoRepository;
import br.com.pedido.demo.service.CompraService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/compras")
public class CompraController {

    private final CadastroProdutoRepository produtoRepository;
    private final CompraService compraService;
    private final UsuarioRepository usuarioRepository;

    public CompraController(
            CadastroProdutoRepository produtoRepository,
            CompraService compraService,
            UsuarioRepository usuarioRepository) {

        this.produtoRepository = produtoRepository;
        this.compraService = compraService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String telaCompras(Model model) {

        List<CadastroProdutoEntity> produtos = produtoRepository.findAll();
        List<UsuarioEntity> usuarios = usuarioRepository.findAll();

        model.addAttribute("produtos", produtos);
        model.addAttribute("usuarios", usuarios);

        return "compras";
    }

    @PostMapping("/finalizar")
    public String finalizar(
            @RequestParam String descricao,
            @RequestParam(value = "produtosSelecionados", required = false) List<Long> ids,
            @RequestParam Long clienteId,
            Model model) {

        if (ids == null || ids.isEmpty()) {
            model.addAttribute("erro", "Selecione pelo menos um produto.");
            model.addAttribute("produtos", produtoRepository.findAll());
            model.addAttribute("usuarios", usuarioRepository.findAll());
            return "compras";
        }

        compraService.finalizarCompra(ids, clienteId, descricao);

        return "redirect:/dashboard?pedidoSucesso=true";
    }
}