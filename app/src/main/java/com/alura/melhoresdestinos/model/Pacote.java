package com.alura.melhoresdestinos.model;

import java.math.BigDecimal;

public class Pacote {

    private String nome;
    private int dias;
    private BigDecimal preco;
    private String imagem;

    public Pacote(String nome, int dias, BigDecimal preco, String imagem) {
        this.nome = nome;
        this.dias = dias;
        this.preco = preco;
        this.imagem = imagem;
    }

    public Pacote() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
