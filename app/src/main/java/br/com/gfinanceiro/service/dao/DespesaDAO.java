package br.com.gfinanceiro.service.dao;

import android.content.ContentUris;
import android.content.CursorLoader;
import android.net.Uri;

import br.com.gfinanceiro.Utils.DateUtils;
import br.com.gfinanceiro.model.Despesa;
import br.com.gfinanceiro.service.data.DespesaContract;
import br.com.gfinanceiro.service.provider.DataProvider;

/**
 * Created by Kabom on 09/12/2015.
 */
public class DespesaDAO extends DAO {

    public void save(Despesa despesa) {
        Uri uri = getContentResolver().insert(DataProvider.CONTENT_DESPESAS_URI, despesa.values());
    }

    public void update(Despesa despesa) {
        Uri uri = ContentUris.withAppendedId(DataProvider.CONTENT_DESPESAS_URI, despesa.getId());
        getContentResolver().update(uri, despesa.values(), null, null);
    }

    public void delete(long id) {
        Uri uri = ContentUris.withAppendedId(DataProvider.CONTENT_DESPESAS_URI, id);
        getContentResolver().delete(uri, null, null);
    }

    public CursorLoader getDespesas(int mes, int ano) {
        String where = DespesaContract.FullColumns.DATA + " >= ? AND " + DespesaContract.FullColumns.DATA + "<= ?";

        long[] dates = DateUtils.getRange(mes, ano);
        String[] args = {String.valueOf(dates[0]), String.valueOf(dates[1])};

        return new CursorLoader(getContext(), DataProvider.CONTENT_DESPESAS_URI, null, where, args, null);
    }

    public CursorLoader getDespesaById(long id) {
        Uri uri = ContentUris.withAppendedId(DataProvider.CONTENT_DESPESAS_URI, id);
        return new CursorLoader(getContext(), uri, null, null, null, null);
    }

}
