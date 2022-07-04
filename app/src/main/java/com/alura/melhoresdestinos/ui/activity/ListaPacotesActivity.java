package com.alura.melhoresdestinos.ui.activity;

import static com.alura.melhoresdestinos.constantes.ConstantesActivitys.CHAVE_PACOTE;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alura.melhoresdestinos.R;
import com.alura.melhoresdestinos.dao.PacoteDao;
import com.alura.melhoresdestinos.model.Pacote;
import com.alura.melhoresdestinos.ui.recyclerview.adapter.ListaPacotesAdapter;

import java.util.List;

public class ListaPacotesActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Melhores destinos";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pacotes);

        setTitle(TITULO_APPBAR);
        configurarLista();


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
        ListaPacotesAdapter adapter = new ListaPacotesAdapter(ListaPacotesActivity.this, listaPacotes);
        idRecyclerview.setAdapter(adapter);

        configurarItemPorClickListener(adapter);
    }

    private void configurarItemPorClickListener(ListaPacotesAdapter adapter) {

        adapter.setOnItemClickListener(new ListaPacotesAdapter.OnItemClickListener() {
            @Override
            public void onClick(Pacote pacote) {


                Intent vaiParaResumoPacoteActivity = new Intent(ListaPacotesActivity.this, ResumoPacoteActivity.class);
                vaiParaResumoPacoteActivity.putExtra(CHAVE_PACOTE, pacote);
                startActivity(vaiParaResumoPacoteActivity);


            }
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