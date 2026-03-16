package br.com.pedido.demo.service;

import br.com.pedido.demo.model.CadastroProdutoDTO;
import br.com.pedido.demo.model.CadastroProdutoEntity;
import br.com.pedido.demo.model.enums.CategoriaProduto;
import br.com.pedido.demo.repository.CadastroProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsável pela lógica de negócio relacionada ao cadastro de produtos.
 * Faz a ponte entre Controller e Repository.
 */
@Service
public class CadastroProdutoService {

    // Repositório responsável por acessar o banco de dados
    private final CadastroProdutoRepository cadastroProdutoRepository;

    /**
     * Injeção de dependência do repositório via construtor
     */
    public CadastroProdutoService(CadastroProdutoRepository cadastroProdutoRepository){
        this.cadastroProdutoRepository = cadastroProdutoRepository;
    }

    /**
     * Salva um novo produto no banco de dados
     * Converte DTO em Entity antes de salvar
     */
    public void salvar(CadastroProdutoDTO cadastroProdutoDTO){

        // Cria uma entidade a partir dos dados do DTO
        CadastroProdutoEntity produto = new CadastroProdutoEntity();

        produto.setNome(cadastroProdutoDTO.getNome());
        produto.setVlUnitario(cadastroProdutoDTO.getVlUnitario());
        produto.setEstoque(cadastroProdutoDTO.getEstoque());
        produto.setCategoria(cadastroProdutoDTO.getCategoria());

        // Persiste no banco
        cadastroProdutoRepository.save(produto);
    }

    /**
     * Lista todos os produtos cadastrados
     * Converte Entity para DTO antes de retornar
     */
    public List<CadastroProdutoDTO> listar(){
        return cadastroProdutoRepository.findAll()
                .stream()
                .map(this::toDTO) // Converte cada Entity em DTO
                .toList();
    }

    /**
     * Método auxiliar para converter Entity em DTO
     */
    private CadastroProdutoDTO toDTO(CadastroProdutoEntity produto){

        CadastroProdutoDTO dto = new CadastroProdutoDTO();

        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setVlUnitario(produto.getVlUnitario());
        dto.setEstoque(produto.getEstoque());
        dto.setCategoria(produto.getCategoria());

        return dto;
    }

    /**
     * Exclui um produto pelo ID
     */
    public void excluirPorId(Long id){
        cadastroProdutoRepository.deleteById(id);
    }

    /**
     * Retorna a quantidade total de produtos cadastrados
     */
    public long contarProdutos(){
        return cadastroProdutoRepository.count();
    }

    /**
     * Busca um produto pelo ID
     * Retorna null caso não encontre
     */
    public CadastroProdutoDTO buscarPorId(Long id){
        return cadastroProdutoRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    /**
     * Atualiza um produto existente
     */
    public void atualizar(CadastroProdutoDTO dto){

        // Busca o produto no banco ou lança exceção se não existir
        CadastroProdutoEntity produto =
                cadastroProdutoRepository.findById(dto.getId()).orElseThrow();

        // Atualiza os dados
        produto.setNome(dto.getNome());
        produto.setVlUnitario(dto.getVlUnitario());
        produto.setEstoque(dto.getEstoque());
        produto.setCategoria(dto.getCategoria());

        // Salva as alterações
        cadastroProdutoRepository.save(produto);
    }

    /**
     * Pesquisa produtos pelo nome (ignorando maiúsculas/minúsculas)
     */
    public List<CadastroProdutoDTO> pesquisarPorNome(String nome){
        return cadastroProdutoRepository
                .findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public void atualizarEstoque(
            List<Long> ids,
            List<Integer> estoque) {

        for (int i = 0; i < ids.size(); i++) {

            CadastroProdutoEntity produto =
                    cadastroProdutoRepository.findById(ids.get(i))
                            .orElseThrow();

            produto.setEstoque(
                    produto.getEstoque() - estoque.get(i)
            );

            cadastroProdutoRepository.save(produto);
        }
    }

}
