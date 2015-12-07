package br.com.gfinanceiro.service.data;

/**
 * Created by Kabom on 06/12/2015.
 */
public final class DespesaContract {

    public static final String TABLE_NAME = "DESPESA";

    public final class Columns {

        public static final String _ID = "_id";
        public static final String DATA = "data";
        public static final String DESCRICAO = "descricao";
        public static final String VALOR = "valor";
        public static final String CATEGORIA_ID = "categoria_id";


    }

    public static final class FullColumns {
        public static final String _ID = TABLE_NAME + "." + Columns._ID;
        public static final String DATA = TABLE_NAME + "." + Columns.DATA;
        public static final String DESCRICAO = TABLE_NAME + "." + Columns.DESCRICAO;
        public static final String CATEGORIA_ID = TABLE_NAME + "." + Columns.CATEGORIA_ID;
        public static final String VALOR = TABLE_NAME + "." + Columns.VALOR;
    }

    public static final class Aliases {
        public static final String _ID = Columns._ID;
        public static final String DATA = TABLE_NAME + "_" + Columns.DATA;
        public static final String DESCRICAO = TABLE_NAME + "_" + Columns.DESCRICAO;
        public static final String VALOR = TABLE_NAME + "_" + Columns.VALOR;
    }


}
