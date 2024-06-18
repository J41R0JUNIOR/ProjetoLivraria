package org.example;

public enum ClientState {
    NORMAL("normal"),
    ALUGANDO_LIVRO("alugando livro"),
    DEVOLVENDO_LIVRO("devolvendo livro"),
    CADASTRANDO_LIVRO("cadastrando livro");

    private String descricao;

    ClientState(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
