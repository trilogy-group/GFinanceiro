package br.com.gfinanceiro.service.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by Kabom on 06/12/2015.
 */
public class RelatorioHandler extends DataHandler {

    public RelatorioHandler(Context context) {
        super(context);
    }

    @Override
    public Cursor query(int code, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        String[] columns = {
                DespesaContract.Columns.DATA,
                "SUM(" + DespesaContract.Columns.VALOR + ") AS " + DespesaContract.Columns.VALOR
        };

        return db().query(DespesaContract.TABLE_NAME, columns, null, null, DespesaContract.Columns.DATA, null, DespesaContract.Columns.DATA);
    }

    @Override
    public Uri insert(int code, Uri uri, ContentValues values) {
        throw new UnsupportedOperationException();
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
