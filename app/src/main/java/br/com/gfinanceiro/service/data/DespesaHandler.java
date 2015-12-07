package br.com.gfinanceiro.service.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import br.com.gfinanceiro.model.Despesa;
import br.com.gfinanceiro.service.provider.DataProvider;

/**
 * Created by Kabom on 06/12/2015.
 */
public class DespesaHandler extends DataHandler {

    public DespesaHandler(Context context) {
        super(context);
    }

    @Override
    public Cursor query(int code, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String[] columns = {DespesaContract.FullColumns._ID, DespesaContract.FullColumns.DATA, DespesaContract.FullColumns.DESCRICAO, DespesaContract.FullColumns.VALOR, CategoriaContract.FullColumns._ID, CategoriaContract.FullColumns.NOME};

        String[] aliases = {DespesaContract.Aliases._ID, DespesaContract.Aliases.DATA, DespesaContract.Aliases.DESCRICAO, DespesaContract.Aliases.VALOR, CategoriaContract.Aliases._ID, CategoriaContract.Aliases.NOME};

        StringBuilder query = new StringBuilder();
        query.append("SELECT ");

        boolean first = true;
        for (int i = 0; i < columns.length; i++) {
            if (!first) {
                query.append(", ");
            }
            query.append(columns[i]).append(" AS ").append(aliases[i]);
            first = false;
        }

        query.append(" FROM ").append(DespesaContract.TABLE_NAME).append(" INNER JOIN ").append(CategoriaContract.TABLE_NAME);
        query.append(" ON ").append(DespesaContract.FullColumns.CATEGORIA_ID).append(" = ").append(CategoriaContract.FullColumns._ID);

        if (code == DataProvider.CODE_DESPESA) {
            if (selection != null) {
                query.append(" WHERE ").append(selection);
            }

            query.append(" ORDER BY ").append(DespesaContract.FullColumns.DATA).append(", ").append(DespesaContract.FullColumns.DESCRICAO);
            return db().rawQuery(query.toString(), selectionArgs);

        } else {
            long id = ContentUris.parseId(uri);
            query.append(" WHERE ").append(DespesaContract.FullColumns._ID).append(" = ?");
            return db().rawQuery(query.toString(), new String[]{String.valueOf(id)});
        }
    }


    @Override
    public Uri insert(int code, Uri uri, ContentValues values) {
        if (code == DataProvider.CODE_DESPESA_ID) {
            throw new InvalidURIException(uri);
        }

        long id = db().insert(DespesaContract.TABLE_NAME, null, values);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(int code, Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (code == DataProvider.CODE_DESPESA) {
            throw new InvalidURIException(uri);
        }

        long id = ContentUris.parseId(uri);
        return db().update(DespesaContract.TABLE_NAME, values, DespesaContract.Columns._ID + " = ?", new String[]{String.valueOf(id)});
    }


    @Override
    public int delete(int code, Uri uri, String selection, String[] selectionArgs) {
        if (code == DataProvider.CODE_DESPESA) {
            throw new InvalidURIException(uri);
        }

        long id = ContentUris.parseId(uri);
        return db().delete(DespesaContract.TABLE_NAME, DespesaContract.Columns._ID + " = ?", new String[]{String.valueOf(id)});

    }
}
