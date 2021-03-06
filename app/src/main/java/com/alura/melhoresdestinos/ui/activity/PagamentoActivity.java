package com.alura.melhoresdestinos.ui.activity;

import static com.alura.melhoresdestinos.constantes.ConstantesActivitys.CHAVE_PACOTE;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.alura.melhoresdestinos.R;
import com.alura.melhoresdestinos.model.Pacote;
import com.alura.melhoresdestinos.util.MoedaUtil;

public class PagamentoActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Pagamento";
    private Pacote pacote;
    private TextView idPreco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);

        inicializarCampoPreco();
        setTitle(TITULO_APPBAR);

        carregarInformacoes();
        configurarBotaoFinalizarComprarListener();
        criarBotaoVoltar();

    }

    private void criarBotaoVoltar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){

            actionBar.setDisplayHomeAsUpEnabled(true);


        }
    }

    private void configurarBotaoFinalizarComprarListener() {

        Button idBotaoFinalizarComprar = findViewById(R.id.pagamento_botao_finalizar_comprar);
        idBotaoFinalizarComprar.setOnClickListener(view -> {


            Intent vaiParaResumoDaCompra = new Intent(PagamentoActivity.this,
                    ResumoDaCompraActivity.class);

            vaiParaResumoDaCompra.putExtra(CHAVE_PACOTE, pacote);
            startActivity(vaiParaResumoDaCompra);
        });
    }

    private void carregarInformacoes() {

        Intent dadosPagamento = getIntent();
        if (temExtra(dadosPagamento)) {

            pacote = dadosPagamento.getParcelableExtra(CHAVE_PACOTE);
            if (pacote != null) {

                pegarPreco();

            }
        }
    }

    private void pegarPreco() {
        String preco = MoedaUtil.formatarMoedaBrasileira(pacote.getPreco());
        idPreco.setText(preco);
    }

    private boolean temExtra(Intent dadosPagamento) {

        return dadosPagamento.hasExtra(CHAVE_PACOTE);
    }

    private void inicializarCampoPreco() {
        idPreco = findViewById(R.id.pagamento_preco);
    }

}