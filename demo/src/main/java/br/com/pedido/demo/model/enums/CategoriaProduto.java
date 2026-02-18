package br.com.pedido.demo.model.enums;

public enum CategoriaProduto {

    ELETRONICO("Eletrônico"),
    ROUPA("Roupa"),
    ALIMENTO("Alimento"),
    LIMPEZA("Limpeza"),
    OUTROS("Outros");

    private final String descricao;

    CategoriaProduto(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
