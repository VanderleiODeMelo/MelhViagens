package com.alura.melhoresdestinos.ui.activity;

import static androidx.appcompat.widget.SearchView.OnQueryTextListener;
import static com.alura.melhoresdestinos.constantes.ConstantesActivitys.CHAVE_PACOTE;

import android.content.Intent;
import android.os.Bundle;

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
    private ListaPacotesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pacotes);

        setTitle(TITULO_APPBAR);
        configurarLista();
        pesquisarPacoteViagens();

    }

    private void pesquisarPacoteViagens() {

        SearchView idMenuPesquisa = findViewById(R.id.lista_pacotes_pesquisar);
        idMenuPesquisa.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                adapter.getFilter().filter(newText);

                return true;
            }
        });
    }

    private void configurarLista() {

        configuraRecyclerView();
    }

    private void configuraRecyclerView() {

        RecyclerView idRecyclerview = configuraLayoutManager();

        configuraAdapter(idRecyclerview);
    }

    private void configuraAdapter(RecyclerView idRecyclerview) {

        List<Pacote> listaPacotes = PacoteDao.listaPacotes();
        adapter = new ListaPacotesAdapter(ListaPacotesActivity.this, listaPacotes);
        idRecyclerview.setAdapter(adapter);

        configurarItemPorClickListener(adapter);
    }

    private void configurarItemPorClickListener(ListaPacotesAdapter adapter) {

        adapter.setOnItemClickListener(pacote -> {


            Intent vaiParaResumoPacoteActivity = new Intent(ListaPacotesActivity.this, ResumoPacoteActivity.class);
            vaiParaResumoPacoteActivity.putExtra(CHAVE_PACOTE, pacote);
            startActivity(vaiParaResumoPacoteActivity);


        });
    }

    @NonNull
    private RecyclerView configuraLayoutManager() {
        RecyclerView idRecyclerview = findViewById(R.id.listaPacotes_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        idRecyclerview.setLayoutManager(linearLayoutManager);
        return idRecyclerview;
    }
}