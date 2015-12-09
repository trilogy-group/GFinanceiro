package br.com.gfinanceiro.view.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.gfinanceiro.R;
import br.com.gfinanceiro.model.Categoria;

/**
 * Created by Kabom on 09/12/2015.
 */
public class CategoriasAdapter extends CursorAdapter {

    private Categoria categoria = new Categoria();
    private List<Categoria> categorias = new ArrayList<>();

    public CategoriasAdapter(Context context) {
        super(context, null, 0);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        categoria.loadFromCursor(cursor);

        TextView txtCategoria = (TextView) view.findViewById(R.id.txt_categoria);
        txtCategoria.setText(categoria.getNome());

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflater.inflate(R.layout.adapter_categorias, parent, false);

    }

    public int getPosition(Categoria categoria) {
        return categorias.indexOf(categoria);
    }

    public Categoria getItem(int position) {
        return categorias.get(position);
    }

    public void updateData(){
        Cursor cursor = getCursor();


        int pos = cursor.getPosition();

        if(cursor.moveToFirst()){
            categorias.clear();

            do{
                Categoria categoria = new Categoria();
                categoria.loadFromCursor(cursor);
                categorias.add(cursor.getPosition(),categoria);
            }while(cursor.moveToNext());
        }

        cursor.moveToPosition(pos);
    }

}
