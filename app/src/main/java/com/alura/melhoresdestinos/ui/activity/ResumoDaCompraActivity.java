package com.alura.melhoresdestinos.ui.activity;

import static com.alura.melhoresdestinos.constantes.ConstantesActivitys.CHAVE_PACOTE;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alura.melhoresdestinos.R;
import com.alura.melhoresdestinos.model.Pacote;
import com.alura.melhoresdestinos.util.DataUtil;
import com.alura.melhoresdestinos.util.ImagemUtil;
import com.alura.melhoresdestinos.util.MoedaUtil;

public class ResumoDaCompraActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Resumo da comprar";
    private ImageView idImagem;
    private TextView idLocal;
    private TextView idDataViagem;
    private TextView idPreco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_da_compra);
        setTitle(TITULO_APPBAR);

        inicializarCampos();
        carregarInformacoes();


    }

    private void carregarInformacoes() {

        Intent dadosPacote = getIntent();

        if (temExtra(dadosPacote)) {

            Pacote pacote = dadosPacote.getParcelableExtra(CHAVE_PACOTE);

            if (pacote != null) {

                pegarImagem(pacote);
                pegarLocal(pacote);
                pegarData(pacote);
                pegarPreco(pacote);

            }
        }
    }

    private boolean temExtra(Intent dadosPacote) {

        return dadosPacote.hasExtra(CHAVE_PACOTE);
    }

    private void pegarPreco(Pacote pacote) {
        idPreco.setText(MoedaUtil.formatarMoedaBrasileira(pacote.getPreco()));
    }

    private void pegarData(Pacote pacote) {
        idDataViagem.setText(DataUtil.formatarData(pacote.getDias()));
    }

    private void pegarLocal(Pacote pacote) {
        idLocal.setText(pacote.getLocal());
    }

    private void pegarImagem(Pacote pacote) {
        Drawable imagemDrawable = ImagemUtil.pegarImagemDrawable(pacote, ResumoDaCompraActivity.this);
        idImagem.setImageDrawable(imagemDrawable);
    }

    private void inicializarCampos() {

        idImagem = findViewById(R.id.resumo_da_comprar_imagem);
        idLocal = findViewById(R.id.resumo_da_comprar_local);
        idDataViagem = findViewById(R.id.resumo_da_comprar_data_viagem);
        idPreco = findViewById(R.id.resumo_da_comprar_preco);
    }

}