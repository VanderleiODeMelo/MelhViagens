package com.alura.melhoresdestinos.ui.activity;

import static com.alura.melhoresdestinos.constantes.ConstantesActivitys.CHAVE_PACOTE;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.alura.melhoresdestinos.R;
import com.alura.melhoresdestinos.model.Pacote;
import com.alura.melhoresdestinos.util.DataUtil;
import com.alura.melhoresdestinos.util.DiaUtil;
import com.alura.melhoresdestinos.util.ImagemUtil;
import com.alura.melhoresdestinos.util.MoedaUtil;

public class ResumoPacoteActivity extends AppCompatActivity {

    public static final String TITLO_APPBAR = "Resumo do pacote";
    private Pacote pacote;
    private ImageView idImagem;
    private TextView idLocal;
    private TextView idDias;
    private TextView idDataViagem;
    private TextView idPrecoViagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_pacote);
        setTitle(TITLO_APPBAR);

        iniciarCampos();
        criarAppBarResumoPacote();
        carregarInformacoes();
        botaoRealizarPagamentoListener();
    }

    private void botaoRealizarPagamentoListener() {

        Button idBotaoRealizarPagamento = findViewById(R.id.resumo_pacote_botao_realizar_pagamento);
        idBotaoRealizarPagamento.setOnClickListener(view -> {

            Intent vaiParaPagamentoActivity = new Intent(ResumoPacoteActivity.this,
                    PagamentoActivity.class);

            vaiParaPagamentoActivity.putExtra(CHAVE_PACOTE, pacote);
            startActivity(vaiParaPagamentoActivity);


        });
    }

    private void carregarInformacoes() {

        Intent dadosPacote = getIntent();
        if (temExtra(dadosPacote)) {

            pacote = dadosPacote.getParcelableExtra(CHAVE_PACOTE);

            if (pacote != null) {

                pegarImagem();
                pegarDia();
                pegarData();
                pregarPreco();

            }
        }
    }

    private void pregarPreco() {
        idPrecoViagem.setText(MoedaUtil.formatarMoedaBrasileira(pacote.getPreco()));
    }

    private void pegarData() {
        String dataFormatadaDaViagem = DataUtil.formatarData(pacote.getDias());
        idDataViagem.setText(dataFormatadaDaViagem);
    }

    private void pegarDia() {
        idDias.setText(new StringBuffer(String.valueOf(pacote.getDias()))
                .append(DiaUtil.pegarDia(pacote.getDias())));
    }

    private void pegarImagem() {
        idImagem.setImageDrawable(ImagemUtil.pegarImagemDrawable(pacote, this));
        idLocal.setText(pacote.getLocal());
    }

    private boolean temExtra(Intent dadosPacote) {
        return dadosPacote.hasExtra(CHAVE_PACOTE);
    }

    private void iniciarCampos() {

        idImagem = findViewById(R.id.resumo_pacote_imagem);
        idLocal = findViewById(R.id.resumo_pacote_local);
        idDias = findViewById(R.id.resumo_pacote_dias);
        idDataViagem = findViewById(R.id.resumo_pacote_data_viagem);
        idPrecoViagem = findViewById(R.id.resumo_pacote_preco);
    }

    private void criarAppBarResumoPacote() {
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }


}