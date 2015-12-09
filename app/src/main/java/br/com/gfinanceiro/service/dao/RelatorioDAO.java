package br.com.gfinanceiro.service.dao;

import android.content.CursorLoader;

import br.com.gfinanceiro.service.provider.DataProvider;

/**
 * Created by Kabom on 09/12/2015.
 */
public class RelatorioDAO extends DAO {


    public CursorLoader createReport() {
        return new CursorLoader(getContext(), DataProvider.CONTENT_RELATORIO_URI, null, null, null, null);
    }

}
