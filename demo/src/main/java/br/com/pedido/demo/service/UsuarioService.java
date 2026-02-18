package br.com.pedido.demo.service;

import br.com.pedido.demo.model.UsuarioDTO;
import br.com.pedido.demo.model.UsuarioEntity;
import br.com.pedido.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Service responsável pela lógica de negócio dos usuários.
 * Atua como camada intermediária entre Controller e Repository.
 */
@org.springframework.stereotype.Service
public class UsuarioService {

    // Repositório responsável por acessar os dados no banco
    private final UsuarioRepository usuarioRepository;

    /**
     * Construtor para injeção de dependência do repositório
     */
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Salva um novo usuário no banco de dados.
     * Converte os dados do DTO em Entity antes de persistir.
     */
    public void salvar(UsuarioDTO usuarioDto) {

        // Cria a entidade com base nos dados recebidos
        UsuarioEntity usuario = new UsuarioEntity();

        usuario.setNome(usuarioDto.getNome());
        usuario.setCpf(usuarioDto.getCpf());
        usuario.setEmail(usuarioDto.getEmail());

        // Persiste o usuário no banco
        usuarioRepository.save(usuario);
    }

    /**
     * Retorna a lista de todos os usuários cadastrados.
     * Converte Entity para DTO antes de retornar.
     */
    public List<UsuarioDTO> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toDTO) // Converte cada entidade em DTO
                .toList();
    }

    /**
     * Método auxiliar responsável por converter Entity em DTO.
     */
    private UsuarioDTO toDTO(UsuarioEntity user) {

        UsuarioDTO usuarioDto = new UsuarioDTO();

        usuarioDto.setNome(user.getNome());
        usuarioDto.setCpf(user.getCpf());
        usuarioDto.setEmail(user.getEmail());

        return usuarioDto;
    }

    /**
     * Retorna a quantidade total de usuários cadastrados.
     */
    public long contarUsuarios(){
        return usuarioRepository.count();
    }
}
