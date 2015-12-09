package br.com.gfinanceiro.service.dao;

import android.content.Context;

/**
 * Created by Kabom on 09/12/2015.
 */
public class DAOFactory {

    private static CategoriaDAO categoriaDAO;
    private static DespesaDAO despesaDAO;
    private static RelatorioDAO relatorioDAO;

    public static CategoriaDAO getCategoriaDAO(Context context) {
        if (categoriaDAO == null) {
            categoriaDAO = new CategoriaDAO();
            categoriaDAO.init(context.getApplicationContext());
        }

        return categoriaDAO;
    }

    public static DespesaDAO getDespesaDAO(Context context) {
        if (despesaDAO == null) {
            despesaDAO = new DespesaDAO();
            despesaDAO.init(context.getApplicationContext());
        }

        return despesaDAO;
    }

    public RelatorioDAO getRelatorioDAO(Context context) {
        if (relatorioDAO == null) {
            relatorioDAO = new RelatorioDAO();
            relatorioDAO.init(context.getApplicationContext());
        }

        return relatorioDAO;
    }

}
