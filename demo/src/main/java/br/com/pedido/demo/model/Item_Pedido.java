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

    @Column(name = "vl_Unitario", precision = 10, scale = 2, nullable = false)
    private BigDecimal vl_Unitario;

    @Column(name = "vl_Total", precision = 10, scale = 2, nullable = false)
    private BigDecimal vl_total;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private CadastroProdutoEntity produto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getVl_Unitario() {
        return vl_Unitario;
    }

    public void setVl_Unitario(BigDecimal vl_Unitario) {
        this.vl_Unitario = vl_Unitario;
    }

    public BigDecimal getVl_total() {
        return vl_total;
    }

    public void setVl_total(BigDecimal vl_total) {
        this.vl_total = vl_total;
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
