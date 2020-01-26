package br.com.catossi.bebedor_da_rodada.model;

import java.util.List;

public class Round {

    private String descricao;
    private String titulo;
    private List<Integer> bebidas;
    private String idUsuarioCriador;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Integer> getBebidas() {
        return bebidas;
    }

    public void setBebidas(List<Integer> bebidas) {
        this.bebidas = bebidas;
    }

    public String getIdUsuarioCriador() {
        return idUsuarioCriador;
    }

    public void setIdUsuarioCriador(String idUsuarioCriador) {
        this.idUsuarioCriador = idUsuarioCriador;
    }
}
