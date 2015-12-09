package br.com.gfinanceiro.service.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import br.com.gfinanceiro.service.data.CategoriaHandler;
import br.com.gfinanceiro.service.data.DBHelper;
import br.com.gfinanceiro.service.data.DataHandler;
import br.com.gfinanceiro.service.data.DespesaHandler;
import br.com.gfinanceiro.service.data.RelatorioHandler;

public class DataProvider extends ContentProvider {

    public static final String AUTHORITY = "br.com.gfinanceiro.provider.DataProvider";
    public static final Uri CONTENT_DESPESAS_URI = Uri.parse("content://" + AUTHORITY + "/despesa");
    public static final Uri CONTENT_CATEGORIAS_URI = Uri.parse("content://" + AUTHORITY + "/categoria");
    public static final Uri CONTENT_RELATORIO_URI = Uri.parse("content://" + AUTHORITY + "/relatorio");


    private SQLiteDatabase db;

    public static final int CODE_DESPESA = 1;
    public static final int CODE_DESPESA_ID = 2;
    public static final int CODE_CATEGORIA = 3;
    public static final int CODE_CATEGORIA_ID = 4;
    public static final int CODE_RELATORIO = 5;

    private UriMatcher matcher;

    private DataHandler despesaHandler;
    private DataHandler categoriaHandler;
    private DataHandler relatorioHandler;

    @Override
    public boolean onCreate() {

        despesaHandler = new DespesaHandler(getContext());
        categoriaHandler = new CategoriaHandler(getContext());
        relatorioHandler = new RelatorioHandler(getContext());

        matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(AUTHORITY, "despesas", CODE_DESPESA);
        matcher.addURI(AUTHORITY, "despesas/#", CODE_DESPESA_ID);

        matcher.addURI(AUTHORITY, "categorias", CODE_CATEGORIA);
        matcher.addURI(AUTHORITY, "categorias/#", CODE_CATEGORIA_ID);

        matcher.addURI(AUTHORITY, "relatorio", CODE_RELATORIO);

        return true;

    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        int code = matcher.match(uri);
        DataHandler handler = getHandler(code);
        Cursor c = handler.query(code, uri, projection, selection, selectionArgs, sortOrder);
        handler.setNotificationUri(c, uri);
        return c;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int code = matcher.match(uri);
        DataHandler handler = getHandler(code);
        Uri newUri = handler.insert(code, uri, values);
        handler.notifyChange(newUri);
        return newUri;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int code = matcher.match(uri);
        DataHandler handler = getHandler(code);
        int count = handler.update(code, uri, values, selection, selectionArgs);
        handler.notifyChange(uri);
        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int code = matcher.match(uri);
        DataHandler handler = getHandler(code);
        int count = handler.delete(code, uri, selection, selectionArgs);
        handler.notifyChange(uri);
        return count;
    }


    @Override
    public String getType(Uri uri) {

        int code = matcher.match(uri);

        if (code == CODE_DESPESA_ID || code == CODE_CATEGORIA_ID || code == CODE_RELATORIO) {
            return "vnd.android.cursor.item/vnd.br.com.gfinanceiro";

        } else if (code == CODE_DESPESA || code == CODE_CATEGORIA) {
            return "vnd.android.cursor.dir/vnd.br.com.gfinanceiro";
        }

        throw new IllegalArgumentException("A URI não é suportada: " + uri);
    }

    private DataHandler getHandler(int code) {
        if (code == CODE_DESPESA || code == CODE_DESPESA_ID) {
            return despesaHandler;

        } else if (code == CODE_CATEGORIA || code == CODE_CATEGORIA_ID) {
            return categoriaHandler;

        } else if (code == CODE_RELATORIO) {
            return relatorioHandler;
        }

        return null;
    }


}
