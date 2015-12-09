package br.com.gfinanceiro.service.dao;

import android.content.ContentResolver;
import android.content.Context;

/**
 * Created by Kabom on 08/12/2015.
 */
public abstract class DAO {

    Context context;
    ContentResolver cr;

    DAO() {
    }

    void init(Context context) {
        this.context = context;
        this.cr = context.getContentResolver();

    }

    protected Context getContext() {
        return context;

    }

    protected ContentResolver getContentResolver() {
        return cr;
    }

}
