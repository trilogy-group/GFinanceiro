package br.com.gfinanceiro.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kabom on 06/12/2015.
 */
public class Despesa implements Serializable {

    private Long id;
    private Date data;
    private String descricao;
    private Categoria categoria;
    private Double valor;

}
