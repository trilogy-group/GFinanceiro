package br.com.gfinanceiro.view.fragment;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import br.com.gfinanceiro.R;
import br.com.gfinanceiro.Utils.DateUtils;
import br.com.gfinanceiro.service.dao.DAOFactory;
import br.com.gfinanceiro.service.dao.DespesaDAO;
import br.com.gfinanceiro.view.adapter.DespesasAdapter;

/**
 * Created by Kabom on 11/12/2015.
 */
public class DespesasListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>, AbsListView.MultiChoiceModeListener
        , DeleteDespesaDialog.OnDeleteDespesaListener, View.OnClickListener, DespesasAdapter.OnItemCheckedChangeListener {


    private DespesasAdapter despesasAdapter;
    private OnDespesaClickListener listener;

    private Spinner spiMes;
    private Spinner spiAno;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof OnDespesaClickListener) {
            this.listener = (OnDespesaClickListener) activity;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null) {
            despesasAdapter = new DespesasAdapter(getActivity(), this);
            setListAdapter(despesasAdapter);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_despesaslist, container, false);

        int[] today = DateUtils.today();

        spiMes = (Spinner) view.findViewById(R.id.spi_mes);
        ArrayAdapter<CharSequence> mesAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.meses, android.R.layout.simple_dropdown_item_1line);
        spiMes.setAdapter(mesAdapter);
        spiMes.setSelection(today[1] - 1);

        spiAno = (Spinner) view.findViewById(R.id.spi_ano);
        ArrayAdapter<Integer> anoAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_dropdown_item_1line);
        int anoAtual = today[2];

        for (int i = anoAtual - 3; i <= anoAtual; i++) {
            anoAdapter.add(i);
        }

        spiAno.setAdapter(anoAdapter);
        spiAno.setSelection(anoAdapter.getCount() - 1);

        Button btnUpdate = (Button) view.findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);

        return view;


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        getListView().setMultiChoiceModeListener(this);

        getListView().setLongClickable(false);

        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        int mes = spiMes.getSelectedItemPosition() + 1;
        int ano = (Integer) spiAno.getSelectedItem();

        return DAOFactory.getDespesaDAO(getActivity()).getDespesas(mes, ano);


    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        despesasAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        despesasAdapter.swapCursor(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        if (listener != null) {
            listener.onDespesaClick(id);
        }
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            DeleteDespesaDialog dialog = DeleteDespesaDialog.newInstance(getListView().getCheckedItemIds(), this);

            dialog.show(getFragmentManager(), "deleteDialog");
            return true;
        }

        return false;
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.actionmode, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }


    @Override
    public void onDestroyActionMode(ActionMode mode) {
        despesasAdapter.unselectedItens();
    }

    @Override
    public void onDeleteDespesa(long[] ids) {

        DespesaDAO despesaDAO = DAOFactory.getDespesaDAO(getActivity());

        for (long id : ids) {
            despesaDAO.delete(id);
        }

        despesasAdapter.unselectedItens();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_update) {
            getLoaderManager().restartLoader(0, null, this);
        }

    }

    @Override
    public void onItemCheckedChange(int position, boolean checked) {
        getListView().setItemChecked(position, checked);
    }

    public interface OnDespesaClickListener {
        void onDespesaClick(long despesaId);
    }
}
