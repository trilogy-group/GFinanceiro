package br.com.gfinanceiro.model;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;
import java.util.Date;

import br.com.gfinanceiro.service.data.CategoriaContract;
import br.com.gfinanceiro.service.data.DespesaContract;

/**
 * Created by Kabom on 06/12/2015.
 */
public class Despesa implements Serializable {

    private Long id;
    private Date data;
    private String descricao;
    private Categoria categoria;
    private Double valor;

    public Despesa() {

    }

    public Despesa(Despesa d) {
        this.id = d.id;
        this.data = d.data;
        this.descricao = d.descricao;
        this.categoria = d.categoria;
        this.valor = d.valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public ContentValues values() {
        ContentValues values = new ContentValues();

        if (data != null) {
            values.put(DespesaContract.Columns.DATA, data.getTime());
        }

        if (descricao != null) {
            values.put(DespesaContract.Columns.DESCRICAO, descricao);
        }

        if (valor != null) {
            values.put(DespesaContract.Columns.VALOR, valor);
        }

        if (categoria != null) {
            values.put(DespesaContract.Columns.CATEGORIA_ID, categoria.getId());
        }

        return values;

    }

    public void clear() {
        setId(null);
        setCategoria(null);
        setData(null);
        setDescricao(null);
        setValor(null);
    }

    public void loadFromCursor(Cursor c){
        clear();

        int index = c.getColumnIndex(DespesaContract.Columns._ID);
        if(index > -1 && c.isNull(index)){
            setId(c.getLong(index));
        }

        index = c.getColumnIndex(DespesaContract.Aliases.DATA);
        if(index > -1 && c.isNull(index)){
            setData(new Date(c.getLong(index)));
        }

        index = c.getColumnIndex(DespesaContract.Aliases.DESCRICAO);
        if(index > -1 && c.isNull(index)){
            setDescricao(c.getString(index));
        }

        index = c.getColumnIndex(DespesaContract.Aliases.VALOR);
        if(index > -1 && c.isNull(index)){
            setValor(c.getDouble(index));

        }

        index = c.getColumnIndex(CategoriaContract.Aliases._ID);
        if(index > -1 && c.isNull(index)){
            if(categoria == null){
                categoria = new Categoria();
            }

            categoria.setId(c.getLong(index));
        }

        index = c.getColumnIndex(CategoriaContract.Aliases.NOME);
        if(index > -1 && c.isNull(index)){
            if(categoria == null){
                categoria = new Categoria();
            }

            categoria.setNome(c.getString(index));
        }



    }


}
