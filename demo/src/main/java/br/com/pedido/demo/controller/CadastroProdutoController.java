package br.com.pedido.demo.controller;

import br.com.pedido.demo.model.CadastroProdutoDTO;
import br.com.pedido.demo.model.enums.CategoriaProduto;
import br.com.pedido.demo.service.CadastroProdutoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cadastroeprodutos")
public class CadastroProdutoController {

    private final CadastroProdutoService cadastroProdutoService;

    public CadastroProdutoController(CadastroProdutoService cadastroProdutoService){
        this.cadastroProdutoService = cadastroProdutoService;
    }

    @GetMapping
    public String listar(Model model){

        model.addAttribute("cadastroeprodutos", cadastroProdutoService.listar());
        model.addAttribute("prodDTO",new CadastroProdutoDTO());
        model.addAttribute("categorias", CategoriaProduto.values());

        return "cadastroeprodutos";
    }

    @PostMapping
    public String salvar(
            @Valid @ModelAttribute("prodDTO") CadastroProdutoDTO cadastroProdutoDTO,
            BindingResult result,
            Model model
    ){
        model.addAttribute("categorias", CategoriaProduto.values());

        if (result.hasErrors()) {
            model.addAttribute("cadastroeprodutos", cadastroProdutoService.listar());
            return "cadastroeprodutos";
        }

        cadastroProdutoService.salvar(cadastroProdutoDTO);
        return "redirect:/cadastroeprodutos";
    }

    @GetMapping("/pesquisar")
    public String pesquisarProdutos(@RequestParam String nome, Model model){

        model.addAttribute("cadastroeprodutos",
                cadastroProdutoService.pesquisarPorNome(nome));

        model.addAttribute("prodDTO", new CadastroProdutoDTO());
        model.addAttribute("categorias", CategoriaProduto.values());

        return "cadastroeprodutos";
    }

}
