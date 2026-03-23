package br.com.pedido.demo.controller;

import br.com.pedido.demo.service.PedidoService;
import br.com.pedido.demo.service.RelatorioService;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import jakarta.servlet.http.HttpServletResponse;
import br.com.pedido.demo.model.CadastroProdutoEntity;
import br.com.pedido.demo.model.Pedido;
import br.com.pedido.demo.model.UsuarioEntity;
import br.com.pedido.demo.repository.UsuarioRepository;
import br.com.pedido.demo.repository.CadastroProdutoRepository;
import br.com.pedido.demo.service.CompraService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String telaCompras(
            @RequestParam(required = false) Boolean pedidoSucesso,
            @RequestParam(required = false) Long pedidoId,
            Model model) {

        List<CadastroProdutoEntity> produtos = produtoRepository.findAll();
        List<UsuarioEntity> usuarios = usuarioRepository.findAll();

        model.addAttribute("produtos", produtos);
        model.addAttribute("usuarios", usuarios);

        // ESSA PARTE É O SEGREDO
        model.addAttribute("pedidoSucesso", pedidoSucesso);
        model.addAttribute("pedidoId", pedidoId);

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

        Pedido pedido = compraService.finalizarCompra(ids, clienteId, descricao);

        // ENVIA ID + FLAG DE SUCESSO
        return "redirect:/compras?pedidoSucesso=true&pedidoId=" + pedido.getId();
    }

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping("/pedido/{id}/pdf")
    public ResponseEntity<byte[]> gerarPdf(@PathVariable Long id) {

        Pedido pedido = pedidoService.buscarPorId(id);

        byte[] pdf = relatorioService.gerarRelatorio(pedido);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=pedido.pdf")
                .header("Content-Type", "application/pdf")
                .body(pdf);
    }
}
