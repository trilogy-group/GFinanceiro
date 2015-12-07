package br.com.gfinanceiro.service.data;

/**
 * Created by Kabom on 06/12/2015.
 */
public class CategoriaContract {

    public static final String TABLE_NAME = "despesa";

    public final class Columns {

        public static final String _ID = "_id";
        public static final String NOME = "nome";

    }

    public static final class FullColumns {
        public static final String _ID = TABLE_NAME + "." + Columns._ID;
        public static final String NOME = TABLE_NAME + "." + Columns.NOME;
    }

    public static final class Aliases {
        public static final String _ID = TABLE_NAME + "_" + Columns._ID;
        public static final String NOME = TABLE_NAME + "_" + Columns.NOME;
    }

}
