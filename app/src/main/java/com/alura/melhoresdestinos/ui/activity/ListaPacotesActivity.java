package com.alura.melhoresdestinos.ui.activity;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.alura.melhoresdestinos.constantes.ConstantesActivitys.CHAVE_PACOTE;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alura.melhoresdestinos.R;
import com.alura.melhoresdestinos.dao.PacoteDao;
import com.alura.melhoresdestinos.model.Pacote;
import com.alura.melhoresdestinos.ui.recyclerview.adapter.ListaPacotesAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListaPacotesActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Melhores destinos";
    public static final String TITULO_DIALOG = "Fale algo normalmente";

    private List<Pacote> listaPacotes = new ArrayList<>();
    private RecyclerView idRecyclerview;
    private EditText idMenuPesquisa;
    private Button idBotaoCancelar;
    private ImageButton idBotaoVoz;
    private ActivityResultLauncher<Intent> intentActivityResultLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lista_pacotes);

        setTitle(TITULO_APPBAR);
        inicializarCampos();
        configurarLista();
        pesquisarPacoteViagens();

        configurarBotaoPesquisaPorVoz();
        resultadoBotaoPesquisaPorVoz();
    }

    private void resultadoBotaoPesquisaPorVoz() {

        intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                        ArrayList<String> resultado = result.getData().getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                        //estou pegando só a primeira linha
                        String ditado = resultado.get(0);

                        idMenuPesquisa.setText(ditado);
                        idMenuPesquisa.setSelection(ditado.length());
                    }

                });
    }

    private void configurarBotaoPesquisaPorVoz() {

        idBotaoVoz.setOnClickListener(view -> criarIntentPesquisaPorVoz());
    }

    private void criarIntentPesquisaPorVoz() {

        Intent iVoz = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        iVoz.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        iVoz.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        iVoz.putExtra(RecognizerIntent.EXTRA_PROMPT,
                TITULO_DIALOG);

        try {

            intentActivityResultLauncher.launch(iVoz);

        } catch (ActivityNotFoundException a) {
            Toast.makeText(ListaPacotesActivity.this,
                    "Seu celular não suporta comando de voz",
                    Toast.LENGTH_SHORT).show();

        }
    }

    private void pesquisarPacoteViagens() {

        idMenuPesquisa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void afterTextChanged(Editable editable) {

                String textoDigitado = editable.toString().toLowerCase();

                List<Pacote> copiaLista = new ArrayList<>(listaPacotes);
                List<Pacote> novoPacote = new ArrayList<>();
                configuraAdapter(idRecyclerview, novoPacote);

                if (estaVazio(textoDigitado)) {
                    visibilidadeDoBotaoCancelar(INVISIBLE);
                }
                for (Pacote lista : copiaLista) {

                    if (contemTextoDigitado(textoDigitado, lista)) {

                        novoPacote.add(new Pacote(lista.getLocal(), lista.getImagem(), lista.getDias(), lista.getPreco()));
                        configuraAdapter(idRecyclerview, novoPacote);

                        if (!estaVazio(textoDigitado)) {
                            visibilidadeDoBotaoCancelar(VISIBLE);
                        }
                    } else {

                        visibilidadeDoBotaoCancelar(VISIBLE);
                    }
                }
                botaoCancelarListener();
            }
        });

    }

    private boolean contemTextoDigitado(String textoDigitado, Pacote lista) {
        return lista.getLocal().toLowerCase().contains(textoDigitado);
    }

    private boolean estaVazio(String textoDigitado) {
        return textoDigitado.isEmpty();
    }

    public void visibilidadeDoBotaoCancelar(int visibilidade) {


        idBotaoCancelar.setBackgroundResource(R.drawable.ic_action_botao_cancelar);
        idBotaoCancelar.setVisibility(visibilidade);
    }

    private void botaoCancelarListener() {

        idBotaoCancelar.setOnClickListener(view -> {

            idMenuPesquisa.setText("");
            idBotaoCancelar.setVisibility(INVISIBLE);

        });
    }

    private void inicializarCampos() {
        idBotaoCancelar = findViewById(R.id.lista_pacotes_botao_cancelar);
        idBotaoVoz = findViewById(R.id.lista_pacotes_botao_voz_pesquisar);
        idMenuPesquisa = findViewById(R.id.lista_pacotes_pesquisar);

    }

    private void configurarLista() {

        configuraRecyclerView();
    }

    private void configuraRecyclerView() {

        idRecyclerview = configuraLayoutManager();
        listaPacotes = PacoteDao.listaPacotes();

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