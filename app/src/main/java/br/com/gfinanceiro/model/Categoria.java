package br.com.gfinanceiro.model;

import java.io.Serializable;

/**
 * Created by Kabom on 06/12/2015.
 */
public class Categoria implements Serializable {

    private Long id;
    private String nome;

    public Categoria(Long id, String nome) {
        this.id = id;
        this.nome = nome;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
