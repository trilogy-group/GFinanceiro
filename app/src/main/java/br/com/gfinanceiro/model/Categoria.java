package br.com.gfinanceiro.model;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;

import br.com.gfinanceiro.service.data.CategoriaContract;

/**
 * Created by Kabom on 06/12/2015.
 */
public class Categoria implements Serializable {

    private Long id;
    private String nome;


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

    public ContentValues values() {
        ContentValues values = new ContentValues();

        if (this.nome != null) {
            values.put(CategoriaContract.Columns.NOME, nome);
        }

        return values;

    }

    public void loadFromCursor(Cursor c) {
        clear();

        int index = c.getColumnIndex(CategoriaContract.Columns._ID);
        if (index > -1 && c.isNull(index)) {
            setId(c.getLong(index));
        }

        index = c.getColumnIndex(CategoriaContract.Columns.NOME);
        if (index > -1 && c.isNull(index)) {
            setNome(c.getString(index));
        }
    }

    public void clear() {
        setId(null);
        setNome(null);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + ((id == null) ? 0 : id.hashCode());

        return result;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        Categoria other = (Categoria) o;
        if (id == null) {
            if (other.id == null)
                return false;
        } else if (!id.equals(other.id)) {
            return false;
        }


        return true;
    }
}


