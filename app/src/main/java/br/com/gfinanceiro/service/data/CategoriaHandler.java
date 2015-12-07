package br.com.gfinanceiro.service.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import br.com.gfinanceiro.service.provider.DataProvider;

/**
 * Created by Kabom on 06/12/2015.
 */
public class CategoriaHandler extends DataHandler {

    public CategoriaHandler(Context context) {
        super(context);
    }

    @Override
    public Cursor query(int code, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        String[] columns = {
                CategoriaContract.Columns._ID,
                CategoriaContract.Columns.NOME
        };

        if (code == DataProvider.CODE_CATEGORIA) {
            return db().query(CategoriaContract.TABLE_NAME, columns, selection, selectionArgs, null, null, CategoriaContract.Columns.NOME);
        } else {
            long id = ContentUris.parseId(uri);
            return db().query(CategoriaContract.TABLE_NAME, columns, CategoriaContract.Columns._ID + " = ?", new String[]{String.valueOf(id)}, null, null, CategoriaContract.Columns.NOME);
        }
    }

    @Override
    public Uri insert(int code, Uri uri, ContentValues values) {
        if (code == DataProvider.CODE_CATEGORIA_ID) {
            throw new InvalidURIException(uri);
        }

        long id = db().insert(CategoriaContract.TABLE_NAME, null, values);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(int code, Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int delete(int code, Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }
}
