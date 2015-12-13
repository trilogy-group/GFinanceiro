package br.com.gfinanceiro.view.fragment;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import br.com.gfinanceiro.R;
import br.com.gfinanceiro.Utils.DateUtils;
import br.com.gfinanceiro.Utils.StringUtils;
import br.com.gfinanceiro.model.Categoria;
import br.com.gfinanceiro.model.Despesa;
import br.com.gfinanceiro.service.dao.DAOFactory;
import br.com.gfinanceiro.view.adapter.CategoriasAdapter;

/**
 * Created by Kabom on 12/12/2015.
 */
public class EditDespesaFragment extends Fragment implements View.OnClickListener, SelectDateDialog.OnDateSetListener,
        LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemSelectedListener, CreateCategoriaDialog.OnCategoriaCreateListener, TextWatcher {


    private static final int LOADER_DESPESA = 1;
    private static final int LOADER_CATEGORIAS = 2;

    private EditText edtData;
    private EditText edtDescricao;
    private Spinner spiCategoria;
    private EditText edtValor;
    private Button btnGravar;

    private ViewGroup layout;
    private ProgressBar progress;

    private Long despesaId;
    private Despesa despesa = new Despesa();
    private Categoria novaCategoria;

    private OnDespesaEditFinished listener;
    private CategoriasAdapter categoriasAdapter;

    private boolean configurationChanged;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editdespesa, container, false);

        edtData = (EditText) view.findViewById(R.id.edt_data);
        edtData.setOnClickListener(this);

        edtDescricao = (EditText) view.findViewById(R.id.edt_descricao);
        edtDescricao.addTextChangedListener(this);

        spiCategoria = (Spinner) view.findViewById(R.id.spi_categoria);
        spiCategoria.setOnItemSelectedListener(this);

        edtValor = (EditText) view.findViewById(R.id.edt_valor);
        edtValor.addTextChangedListener(this);

        btnGravar = (Button) view.findViewById(R.id.btn_gravar);
        btnGravar.setOnClickListener(this);
        btnGravar.setEnabled(false);

        Button btnCancelar = (Button) view.findViewById(R.id.btn_cancelar);
        btnCancelar.setOnClickListener(this);

        Button btnCategoria = (Button) view.findViewById(R.id.btn_categoria);
        btnCategoria.setOnClickListener(this);

        layout = (ViewGroup) view.findViewById(R.id.layout);
        progress = (ProgressBar) view.findViewById(R.id.progress);

        categoriasAdapter = new CategoriasAdapter(getActivity());
        spiCategoria.setAdapter(categoriasAdapter);

        if (savedInstanceState != null) {
            this.despesa = (Despesa) savedInstanceState.getSerializable("despesa");
            this.novaCategoria = (Categoria) savedInstanceState.getSerializable("categoria");
            this.spiCategoria.setSelection(savedInstanceState.getInt("selectedCategoria"));
            if (savedInstanceState.containsKey("despesaId")) {
                this.despesaId = savedInstanceState.getLong("despesaId");
            }

            configurationChanged = true;
        }
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        layout.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);

        getLoaderManager().initLoader(LOADER_CATEGORIAS, null, this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.edt_data) {
            SelectDateDialog dialog = SelectDateDialog.newInstance(this);

            String date = edtData.getText().toString();

            if (!StringUtils.isEmptyOrWhiteSpaces(date)) {
                int[] info = DateUtils.parseDateInfo(date);
                dialog.setDate(info[2], info[1], info[0]);
            }

            dialog.show(getFragmentManager(), "dateDialog");
        } else if (v.getId() == R.id.btn_gravar) {
            despesa.setData(DateUtils.createDate(edtData.getText().toString().trim()));
            despesa.setDescricao(edtDescricao.getText().toString().trim());
            despesa.setValor(Double.parseDouble(edtValor.getText().toString().trim()));

            if (despesaId == null) {
                DAOFactory.getDespesaDAO(getActivity()).save(despesa);
            } else {
                DAOFactory.getDespesaDAO(getActivity()).update(despesa);
            }

            if (listener != null) {
                listener.onDespesaEditFinished(despesa, true);
            }

        } else if (v.getId() == R.id.btn_cancelar) {
            if (listener != null) {
                listener.onDespesaEditFinished(despesa, false);
            }

        } else if (v.getId() == R.id.btn_categoria) {
            CreateCategoriaDialog dialog = CreateCategoriaDialog.newInstance(this);
            dialog.show(getFragmentManager(), "categoriaDialog");
        }
    }


    @Override
    public void onDateSet(int year, int monthOfYear, int dayOfMonth) {
        edtData.setText(DateUtils.formatDate(year, monthOfYear, dayOfMonth));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if (id == LOADER_DESPESA) {
            return DAOFactory.getDespesaDAO(getActivity()).getDespesaById(despesaId);

        } else {
            return DAOFactory.getCategoriaDAO(getActivity()).getCategorias();
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (loader.getId() == LOADER_CATEGORIAS) {
            categoriasAdapter.swapCursor(data);
            categoriasAdapter.updateData();


            if (!configurationChanged) {
                edtDescricao.requestFocus();

                if (despesaId != null) {
                } else {
                    int[] today = DateUtils.today();
                    edtData.setText(DateUtils.formatDate(today[2], today[1], today[0]));

                    layout.setVisibility(View.VISIBLE);
                    progress.setVisibility(View.GONE);
                }
            } else {
                spiCategoria.setSelection(categoriasAdapter.getPosition(novaCategoria));
                novaCategoria = null;
            }

        } else if (loader.getId() == LOADER_DESPESA) {
            data.moveToFirst();
            despesa.loadFromCursor(data);

            edtData.setText(DateUtils.formatDate(despesa.getData()));
            edtDescricao.setText(despesa.getDescricao());
            edtValor.setText(String.valueOf(despesa.getValor()));
            spiCategoria.setSelection(categoriasAdapter.getPosition(despesa.getCategoria()));

            layout.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() == LOADER_CATEGORIAS) {
            categoriasAdapter.swapCursor(null);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        despesa.setCategoria(categoriasAdapter.getItem(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCategoriaCreate(String nome) {
        novaCategoria = new Categoria();
        novaCategoria.setNome(nome);
        DAOFactory.getCategoriaDAO(getActivity()).save(novaCategoria);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        boolean valid = true;

        if (StringUtils.isEmptyOrWhiteSpaces(edtDescricao.getText())) {
            valid = false;
        }

        if (StringUtils.isEmptyOrWhiteSpaces(edtValor.getText())) {
            valid = false;
        }

        btnGravar.setEnabled(valid);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("despesa", despesa);
        outState.putSerializable("categoria", novaCategoria);
        outState.putInt("selectedCategoria", spiCategoria.getSelectedItemPosition());

        if (despesaId != null) {
            outState.putLong("despesaId", despesaId);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public interface OnDespesaEditFinished {
        public void onDespesaEditFinished(Despesa despesa, boolean record);

    }
}
