package br.com.gfinanceiro.view.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.gfinanceiro.R;
import br.com.gfinanceiro.Utils.StringUtils;

/**
 * Created by Kabom on 10/12/2015.
 */
public class CreateCategoriaDialog extends DialogFragment implements DialogInterface.OnClickListener, TextWatcher, DialogInterface.OnShowListener {

    private EditText edtNome;
    private Button btnGravar;
    private OnCategoriaCreateListener listener;


    public static CreateCategoriaDialog newInstance(OnCategoriaCreateListener listener) {
        CreateCategoriaDialog dialog = new CreateCategoriaDialog();
        dialog.listener = listener;

        return dialog;

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.dialog_createcategoria, null);

        edtNome = (EditText) view.findViewById(R.id.edt_nome);
        edtNome.addTextChangedListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.dialog_title_novacategoria);
        builder.setNegativeButton(R.string.btn_cancelar, this);
        builder.setPositiveButton(R.string.btn_gravar, this);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);
        return dialog;
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE && listener != null) {
            listener.onCategoriaCreate(edtNome.getText().toString().trim());
        }
    }


    @Override
    public void onShow(DialogInterface dialog) {
        btnGravar = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
        btnGravar.setEnabled(false);
    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        btnGravar.setEnabled(!StringUtils.isEmptyOrWhiteSpaces(edtNome.getText().toString()));

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }


    @Override
    public void afterTextChanged(Editable s) {

    }


    public interface OnCategoriaCreateListener {
        void onCategoriaCreate(String nome);
    }
}
