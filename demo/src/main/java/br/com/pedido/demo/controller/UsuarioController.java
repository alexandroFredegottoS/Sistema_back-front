package br.com.pedido.demo.controller;

import br.com.pedido.demo.model.UsuarioDTO;
import br.com.pedido.demo.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute("usuarios", usuarioService.listar());
        model.addAttribute("userDTO", new UsuarioDTO());

        return "usuarios";
    }

    @PostMapping
    public String salvar(
            @Valid @ModelAttribute("userDTO") UsuarioDTO usuarioDto,
            BindingResult result,
            Model model
    ) {

        if (result.hasErrors()) {
            model.addAttribute("usuarios", usuarioService.listar());
            return "usuarios";
        }

        usuarioService.salvar(usuarioDto);
        return "redirect:/usuarios";
    }
}
