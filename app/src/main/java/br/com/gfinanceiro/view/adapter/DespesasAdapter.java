package br.com.gfinanceiro.view.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import br.com.gfinanceiro.R;
import br.com.gfinanceiro.Utils.DateUtils;
import br.com.gfinanceiro.Utils.NumberUtils;
import br.com.gfinanceiro.model.Despesa;

/**
 * Created by Kabom on 09/12/2015.
 */
public class DespesasAdapter extends CursorAdapter implements CompoundButton.OnCheckedChangeListener {

    private OnItemCheckedChangeListener listener;

    private Despesa despesa = new Despesa();
    private Map<Long, Info> infoMap = new HashMap<>();

    public DespesasAdapter(Context context, OnItemCheckedChangeListener listener) {
        super(context, null, 0);
        this.listener = listener;
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        despesa.loadFromCursor(cursor);

        CheckBox checkBox = (CheckBox) view.findViewById(R.id.chk_item);
        checkBox.setOnCheckedChangeListener(this);

        TextView txtDescricao = (TextView) view.findViewById(R.id.txt_descricao);
        txtDescricao.setText(despesa.getDescricao());

        TextView txtCategoria = (TextView) view.findViewById(R.id.txt_categoria);
        txtCategoria.setText(despesa.getCategoria().getNome());

        TextView txtValor = (TextView) view.findViewById(R.id.txt_valor);
        txtValor.setText(NumberUtils.formatAsCurrency(despesa.getValor()));

        TextView txtData = (TextView) view.findViewById(R.id.txt_data);
        txtData.setText(DateUtils.formatDate(despesa.getData()));


        Info info = infoMap.get(despesa.getId());

        if (info == null) {
            info = new Info();

            Despesa despesa = new Despesa(this.despesa);

            info.despesa = despesa;
            info.selected = false;

            infoMap.put(despesa.getId(), info);
        }

        info.view = view;
        info.position = cursor.getPosition();
        info.checkBox = checkBox;

        checkBox.setTag(info);
        checkBox.setChecked(info.selected);


    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflater.inflate(R.layout.adapter_despesas, parent, false);


    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        Info model = (Info) buttonView.getTag();

        if (listener != null) {
            listener.onItemCheckedChange(model.position, isChecked);
        }

        model.selected = isChecked;

        if (isChecked) {
            model.view.setBackgroundColor(Color.LTGRAY);
        } else {
            model.view.setBackgroundColor(Color.TRANSPARENT);
        }

    }

    public interface OnItemCheckedChangeListener {
         void onItemCheckedChange(int position, boolean checked);
    }

    public void unselectedItens() {
        for (Info model : infoMap.values()) {
            model.checkBox.setChecked(false);
        }
    }

    public static class Info {

        public boolean selected;

        public int position;

        public View view;

        public Despesa despesa;

        public CheckBox checkBox;

    }

}
