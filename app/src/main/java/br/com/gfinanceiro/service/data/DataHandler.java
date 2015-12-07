package br.com.gfinanceiro.service.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Kabom on 06/12/2015.
 */
public abstract class DataHandler {


    private ContentResolver cr;
    private SQLiteDatabase db;

    protected DataHandler(Context context) {
        this.cr = context.getContentResolver();
        this.db = DBHelper.getInstance(context).getWritableDatabase();
    }

    protected SQLiteDatabase db() {
        return db;
    }

    public void notifyChange(Uri uri) {
        cr.notifyChange(uri, null);
    }

    public void setNotificationUri(Cursor c, Uri uri) {
        c.setNotificationUri(cr, uri);
    }

    public abstract Cursor query(int code, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder);

    public abstract Uri insert(int code, Uri uri, ContentValues values);

    public abstract int update(int code, Uri uri, ContentValues values, String selection, String[] selectionArgs);

    public abstract int delete(int code, Uri uri, String selection, String[] selectionArgs);
}


