package com.alura.melhoresdestinos.ui.activity;

import static com.alura.melhoresdestinos.constantes.ConstantesActivitys.CHAVE_PACOTE;
import static com.alura.melhoresdestinos.view.DialogViewRemover.aplicarListenerBotoesDialog;
import static com.alura.melhoresdestinos.view.DialogViewRemover.carregarIconeDialog;
import static com.alura.melhoresdestinos.view.DialogViewRemover.carregarMensagemRemoverDialog;
import static com.alura.melhoresdestinos.view.DialogViewRemover.carregarPalavraBotaoNaoDialog;
import static com.alura.melhoresdestinos.view.DialogViewRemover.carregarPalavraBotaoSimDialog;
import static com.alura.melhoresdestinos.view.DialogViewRemover.criarDialogPersonalizado;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alura.melhoresdestinos.R;
import com.alura.melhoresdestinos.dao.PacoteDao;
import com.alura.melhoresdestinos.model.Pacote;
import com.alura.melhoresdestinos.provider.SearchProvider;
import com.alura.melhoresdestinos.ui.recyclerview.adapter.SearchableListaAdapter;
import com.alura.melhoresdestinos.view.DialogViewRemover;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchableActivity extends AppCompatActivity {


    public static final String VOLTAR = "Voltar";
    private List<Pacote> copiaListaPacotes;
    private RecyclerView idSearchableRecyclerview;
    private SearchableListaAdapter adapter;
    private List<Pacote> listaAuxiliar;
    private SearchRecentSuggestions suggestions;
    private ActionBar actionBar;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        listaAuxiliar = new ArrayList<>();
        inicializarCampos();

        List<Pacote> listaPacotes = PacoteDao.listaPacotes();
        copiaListaPacotes = new ArrayList<>(listaPacotes);

        configurarBotaoVoltar();
        configurarLayoutManager();
        handleIntent(getIntent());

        adapter.setOnItemClickListener(pacote -> {

            Intent vaiParaResumoPacote = new Intent(SearchableActivity.this,
                    ResumoPacoteActivity.class);
            vaiParaResumoPacote.putExtra(CHAVE_PACOTE, pacote);
            startActivity(vaiParaResumoPacote);

        });
    }

    private void configurarLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        idSearchableRecyclerview.setLayoutManager(linearLayoutManager);
        configurarAdapter();
    }

    private void configurarAdapter() {

        adapter = new SearchableListaAdapter(listaAuxiliar, this);
        idSearchableRecyclerview.setAdapter(adapter);
    }

    private void configurarBotaoVoltar() {
        actionBar = Objects.requireNonNull(getSupportActionBar());
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeActionContentDescription(VOLTAR);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);


    }

    @SuppressLint("NotifyDataSetChanged")
    private void handleIntent(Intent intent) {


        if (Intent.ACTION_SEARCH.equalsIgnoreCase(intent.getAction())) {

            String textoDigitadoBarraPesquisa = intent.getStringExtra(SearchManager.QUERY);
            filtrarPacote(textoDigitadoBarraPesquisa);
            suggestions = new SearchRecentSuggestions(this,
                    SearchProvider.AUTHORITY,
                    SearchProvider.MODE);
            suggestions.saveRecentQuery(textoDigitadoBarraPesquisa, null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Infla o menu de opções do XML
        getMenuInflater().inflate(R.menu.menu_pesquisar_searchable_activity, menu);
        getMenuInflater().inflate(R.menu.menu_remover, menu);
        configurarSearchView(menu);


        return super.onCreateOptionsMenu(menu);
    }

    private void configurarSearchView(Menu menu) {

        //Obtenha o SearchView e defina a configuração pesquisável
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView idSearchView = (SearchView) menu.findItem(R.id.menu_pesquisa_searchable_activity).getActionView();
        // Assume que a atividade atual é a atividade pesquisável
        idSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        idSearchView.setMaxWidth(Integer.MAX_VALUE);
        idSearchView.setQueryRefinementEnabled(true);
        idSearchView.setSubmitButtonEnabled(true);
        idSearchView.setSubmitButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemIdRemover = item.getItemId();

        if (itemIdRemover == R.id.menu_remover) {

            new DialogViewRemover(this, findViewById(R.id.dialog_container), suggestions);
            AlertDialog.Builder builder = DialogViewRemover.criarMenuTema();
            View view = DialogViewRemover.inflarMeuTema(builder);

            carregarIconeDialog(view);
            carregarMensagemRemoverDialog(view);
            carregarPalavraBotaoSimDialog(view);
            carregarPalavraBotaoNaoDialog(view);
            AlertDialog alertDialog = criarDialogPersonalizado(builder);
            aplicarListenerBotoesDialog(view, alertDialog);
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressLint({"NotifyDataSetChanged", "ResourceType"})
    private void filtrarPacote(String textoPesquisa) {

        listaAuxiliar.clear();
        for (Pacote itemPacote : copiaListaPacotes) {

            if (itemPacote.getLocal().toLowerCase().contains(textoPesquisa.toLowerCase())) {

                listaAuxiliar.add(itemPacote);
                actionBar.setTitle(textoPesquisa);
            }
        }
        idSearchableRecyclerview.setVisibility(listaAuxiliar.isEmpty() ? View.GONE : View.VISIBLE);

        if (listaAuxiliar.isEmpty()) {

            colocarTextoQuandoNaoTiverPacoteNaPesquisa();

        }
        //temos que remover a view do nosso container
        else if (constraintLayout.findViewById(1) != null) {

            constraintLayout.removeView(constraintLayout.findViewById(1));
        }
        adapter.notifyDataSetChanged();
    }

    @SuppressLint("ResourceType")
    private void colocarTextoQuandoNaoTiverPacoteNaPesquisa() {

        //VOU COLOCAER UM TEXTO NO MEU RECYCLERVIEW

        TextView textoListaVazia = new TextView(this);
        textoListaVazia.setText(R.string.texto_pacote_não_encontrado);
        //vou colocar um id para esse textView
        textoListaVazia.setId(1);
        textoListaVazia.setTextSize(18);
        //vamos centralizar esse textView
        textoListaVazia.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        textoListaVazia.setTextColor(getColor(R.color.black));
        //vamos centralizar o conteudo desse textView agora
        textoListaVazia.setGravity(Gravity.CENTER);
        constraintLayout.addView(textoListaVazia);
    }

    private void inicializarCampos() {

        idSearchableRecyclerview = findViewById(R.id.searchable_recyclerview);
        constraintLayout = findViewById(R.id.constraintlayout_container);

    }

}