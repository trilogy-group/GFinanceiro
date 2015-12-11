package br.com.gfinanceiro.service.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Kabom on 10/12/2015.
 */
public class ServiceOrderImplSQLite implements IServiceOrder {

    SQLiteDatabase sq;

    public ServiceOrderImplSQLite(Context context){

        sq = DBHelper.getInstance(context).getWritableDatabase();
    }



}
