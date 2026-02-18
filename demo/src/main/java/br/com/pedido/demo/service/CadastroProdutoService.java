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
        produto.setQuantidade(cadastroProdutoDTO.getQuantidade());
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

        CadastroProdutoDTO cadastroProdutoDTO = new CadastroProdutoDTO();

        cadastroProdutoDTO.setId(produto.getId());
        cadastroProdutoDTO.setNome(produto.getNome());
        cadastroProdutoDTO.setVlUnitario(produto.getVlUnitario());
        cadastroProdutoDTO.setQuantidade(produto.getQuantidade());
        cadastroProdutoDTO.setCategoria(produto.getCategoria());

        return cadastroProdutoDTO;
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
        produto.setQuantidade(dto.getQuantidade());
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
}
