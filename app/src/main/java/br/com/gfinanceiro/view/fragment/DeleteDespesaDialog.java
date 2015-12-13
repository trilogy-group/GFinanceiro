package br.com.gfinanceiro.view.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import br.com.gfinanceiro.R;

/**
 * Created by Kabom on 10/12/2015.
 */
public class DeleteDespesaDialog extends DialogFragment implements DialogInterface.OnClickListener {

    private long[] ids;
    private OnDeleteDespesaListener listener;


    public static DeleteDespesaDialog newInstance(long[] ids, OnDeleteDespesaListener listener) {
        DeleteDespesaDialog dialog = new DeleteDespesaDialog();
        dialog.ids = ids;
        dialog.listener = listener;

        return dialog;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.dialog_title_delete);

        if (ids.length == 1) {
            builder.setMessage("Deseja excluir a despesa selecionada?");
        } else {
            builder.setMessage("Deseja excluir as " + ids + " despesas selecionadas?");
        }

        builder.setNegativeButton("Não", this);
        builder.setPositiveButton("Sim", this);

        return builder.create();

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE && listener != null) {
            listener.onDeleteDespesa(ids);
        }

    }


    public interface OnDeleteDespesaListener {
        void onDeleteDespesa(long[] ids);

    }
}
