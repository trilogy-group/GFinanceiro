package br.com.gfinanceiro.service.dao;

import android.content.ContentUris;
import android.content.CursorLoader;
import android.net.Uri;

import br.com.gfinanceiro.model.Categoria;
import br.com.gfinanceiro.service.provider.DataProvider;

/**
 * Created by Kabom on 08/12/2015.
 */
public class CategoriaDAO extends DAO {

    public void save(Categoria categoria) {
        Uri uri = getContentResolver().insert(DataProvider.CONTENT_CATEGORIAS_URI, categoria.values());
        Long id = ContentUris.parseId(uri);
        categoria.setId(id);

    }

    public CursorLoader getCategorias() {
        return new CursorLoader(getContext(), DataProvider.CONTENT_CATEGORIAS_URI, null, null, null, null);

    }

    public CursorLoader getCategoriasById(Long id) {
        Uri uri = ContentUris.withAppendedId(DataProvider.CONTENT_CATEGORIAS_URI, id);
        return new CursorLoader(getContext(), uri, null, null, null, null);
    }
}
