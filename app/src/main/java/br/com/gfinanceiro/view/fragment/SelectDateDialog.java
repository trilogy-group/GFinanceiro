package br.com.gfinanceiro.view.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import br.com.gfinanceiro.Utils.DateUtils;

import android.widget.DatePicker;

/**
 * Created by Kabom on 11/12/2015.
 */
public class SelectDateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private OnDateSetListener listener;
    private int dayOfMonth;
    private int monthOfYear;
    private int year;
    private boolean isDateSet;

    public static SelectDateDialog newInstance(OnDateSetListener listener) {
        SelectDateDialog dialog = new SelectDateDialog();
        dialog.listener = listener;
        return dialog;

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (isDateSet) {
            return new DatePickerDialog(getActivity(), this, year, monthOfYear - 1, dayOfMonth);
        } else {
            int[] today = DateUtils.today();

            return new DatePickerDialog(getActivity(), this, today[2], today[1] - 1, today[0]);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if (listener != null) {
            listener.onDateSet(year, monthOfYear + 1, dayOfMonth);
        }
    }

    public void setDate(int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.monthOfYear = monthOfYear;
        this.dayOfMonth = dayOfMonth;
        this.isDateSet = true;


    }


    public interface OnDateSetListener {
        public void onDateSet(int year, int monthOfYear, int dayOfMonth);
    }

}
