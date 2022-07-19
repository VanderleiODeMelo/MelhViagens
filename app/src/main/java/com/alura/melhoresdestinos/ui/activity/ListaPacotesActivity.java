package com.alura.melhoresdestinos.ui.activity;

import static com.alura.melhoresdestinos.constantes.ConstantesActivitys.CHAVE_PACOTE;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alura.melhoresdestinos.R;
import com.alura.melhoresdestinos.dao.PacoteDao;
import com.alura.melhoresdestinos.model.Pacote;
import com.alura.melhoresdestinos.ui.recyclerview.adapter.ListaPacotesAdapter;

import java.util.List;

public class ListaPacotesActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Melhores destinos";

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lista_pacotes);

        setTitle(TITULO_APPBAR);
        configurarLista();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_pesquisar_lista_pacotes_activity, menu);
        configurarSearchView(menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void configurarSearchView(Menu menu) {

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView idSearchView = (SearchView) menu.findItem(R.id.menu_pesquisa_lista_pacotes_activity).getActionView();

        idSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        idSearchView.setIconified(true);
        idSearchView.setIconifiedByDefault(true);
        idSearchView.setVisibility(View.VISIBLE);
        idSearchView.setMaxWidth(Integer.MAX_VALUE);
        idSearchView.setQueryRefinementEnabled(true);
        //Permite mostrar um botão de envio quando a consulta não está vazia.
        idSearchView.setSubmitButtonEnabled(true);
    }

    private void configurarLista() {

        configuraRecyclerView();
    }

    private void configuraRecyclerView() {

        RecyclerView idRecyclerview = configuraLayoutManager();
        List<Pacote> listaPacotes = PacoteDao.listaPacotes();

        configuraAdapter(idRecyclerview, listaPacotes);
    }

    private void configuraAdapter(RecyclerView idRecyclerview, List<Pacote> listaPacotes) {


        ListaPacotesAdapter adapter = new ListaPacotesAdapter(ListaPacotesActivity.this, listaPacotes);
        idRecyclerview.setAdapter(adapter);
        configurarItemPorClickListener(adapter);
    }

    private void configurarItemPorClickListener(ListaPacotesAdapter adapter) {

        adapter.setOnItemClickListener(pacote -> {


            Intent vaiParaResumoPacoteActivity = new Intent(ListaPacotesActivity.this,
                    ResumoPacoteActivity.class);
            vaiParaResumoPacoteActivity.putExtra(CHAVE_PACOTE, pacote);
            startActivity(vaiParaResumoPacoteActivity);


        });
    }

    @NonNull
    private RecyclerView configuraLayoutManager() {
        RecyclerView idRecyclerview = findViewById(R.id.lista_pacotes_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        idRecyclerview.setLayoutManager(linearLayoutManager);

        return idRecyclerview;
    }
}