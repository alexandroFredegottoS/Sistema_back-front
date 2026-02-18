package br.com.pedido.demo.controller;

import br.com.pedido.demo.model.CadastroProdutoDTO;
import br.com.pedido.demo.model.enums.CategoriaProduto;
import br.com.pedido.demo.service.CadastroProdutoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProdutoController {

    private final CadastroProdutoService cadastroProdutoService;

    public ProdutoController(CadastroProdutoService cadastroProdutoService){
        this.cadastroProdutoService = cadastroProdutoService;
    }

    @ModelAttribute("categorias")
    public CategoriaProduto[] categorias() {
        return CategoriaProduto.values();
    }

    @GetMapping("/produtos")
    public String listarProdutos(Model model){

        model.addAttribute("produtos", cadastroProdutoService.listar());

        return "produtos";
    }

    @GetMapping("/produtos/excluir/{id}")
    public String excluirProduto(@PathVariable Long id){

        cadastroProdutoService.excluirPorId(id);

        return "redirect:/produtos";
    }

    @GetMapping("/produtos/editar/{id}")
    public String editarProduto(@PathVariable Long id, Model model){

        model.addAttribute("produtos", cadastroProdutoService.listar());
        model.addAttribute("produtoEditando", cadastroProdutoService.buscarPorId(id));

        return "produtos";
    }

    @PostMapping("/produtos/atualizar")
    public String atualizarProduto(@ModelAttribute CadastroProdutoDTO dto){

        cadastroProdutoService.atualizar(dto);

        return "redirect:/produtos";
    }

    @GetMapping("/produtos/pesquisar")
    public String pesquisarProdutos(@RequestParam String nome, Model model){

        model.addAttribute("produtos", cadastroProdutoService.pesquisarPorNome(nome));

        return "produtos";
    }

}
