package br.com.pedido.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "item_pedido")
public class Item_Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantidade", nullable = false)
    private Long quantidade;

    @Column(name = "vl_unitario", precision = 10, scale = 2, nullable = false)
    private BigDecimal vlUnitario;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private CadastroProdutoEntity produto;

    // NÃO salva no banco (melhor prática)
    @Transient
    public BigDecimal getVlTotal() {
        return vlUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

    // GETTERS E SETTERS

    public Long getId() {
        return id;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getVlUnitario() {
        return vlUnitario;
    }

    public void setVlUnitario(BigDecimal vlUnitario) {
        this.vlUnitario = vlUnitario;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public CadastroProdutoEntity getProduto() {
        return produto;
    }

    public void setProduto(CadastroProdutoEntity produto) {
        this.produto = produto;
    }
}