package br.com.gfinanceiro.view.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;

import br.com.gfinanceiro.model.Despesa;

/**
 * Created by Kabom on 09/12/2015.
 */
public class DespesasAdapter extends CursorAdapter implements CompoundButton.OnCheckedChangeListener {

    private OnItemCheckedChangeListener listener;

    private Despesa despesa = new Despesa();

    public DespesasAdapter(Context context, OnItemCheckedChangeListener listener) {
        super(context, null, 0);
        this.listener = listener;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    public interface OnItemCheckedChangeListener {
        public void onItemCheckedChange(int position, boolean checked);
    }

}
