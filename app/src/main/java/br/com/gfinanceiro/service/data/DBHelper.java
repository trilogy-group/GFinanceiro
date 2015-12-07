package br.com.gfinanceiro.service.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.InputStream;
import java.util.Scanner;

import br.com.gfinanceiro.R;
import br.com.gfinanceiro.Utils.StringUtils;

/**
 * Created by Kabom on 06/12/2015.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "moneydb";
    public static final int DB_VERSION = 1;


    private static DBHelper instance;
    private Context context;

    public static DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }

    private DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        InputStream in = context.getResources().openRawResource(R.raw.db_script);
        Scanner scanner = new Scanner(in);
        scanner.useDelimiter(";");
        try {
            while (scanner.hasNext()) {
                String sql = scanner.next().trim();

                if (!StringUtils.isEmptyOrWhiteSpaces(sql)) {
                    db.execSQL(sql);
                }
            }
        } finally {
            scanner.close();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }


    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
}