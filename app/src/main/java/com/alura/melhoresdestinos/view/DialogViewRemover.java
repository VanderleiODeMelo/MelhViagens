package com.alura.melhoresdestinos.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.provider.SearchRecentSuggestions;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.alura.melhoresdestinos.R;

public class DialogViewRemover {


    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static ConstraintLayout dialog_container;
    private static SearchRecentSuggestions suggestions;


    public DialogViewRemover(Context context, ConstraintLayout dialog_container, SearchRecentSuggestions suggestions) {
        DialogViewRemover.context = context;
        DialogViewRemover.dialog_container = dialog_container;
        DialogViewRemover.suggestions = suggestions;
    }

    @NonNull
    public static AlertDialog.Builder criarMenuTema() {
        return new AlertDialog.Builder(context,
                R.style.AlertDialogTheme);
    }

    public static View inflarMeuTema(AlertDialog.Builder builder) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.layout_personalizado_dialog_remocao, dialog_container);
        builder.setView(view);
        return view;
    }

    public static void carregarIconeDialog(View view) {
        ((ImageView) view.findViewById(R.id.layout_dialog_lixeira)).setImageResource(R.drawable.ic_action_remover);
    }

    public static void carregarMensagemRemoverDialog(View view) {
        ((TextView) view.findViewById(R.id.layout_dialog_confirmacao_mensagem_remocao))
                .setText(R.string.dialog_personalizado_mensagem_remoção);
    }

    public static void carregarPalavraBotaoNaoDialog(View view) {
        ((Button) view.findViewById(R.id.layout_dialog_button_nao)).setText(R.string.botao_dialog_personalizado_nao);
    }

    @NonNull
    public static AlertDialog criarDialogPersonalizado(AlertDialog.Builder builder) {
        return builder.create();
    }

    public static void carregarPalavraBotaoSimDialog(View view) {
        ((Button) view.findViewById(R.id.layout_dialog_button_sim)).setText(R.string.botao_dialog_personalizado_sim);
    }

    public static void aplicarListenerBotoesDialog(View view, AlertDialog alertDialog) {

        view.findViewById(R.id.layout_dialog_button_sim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();

                suggestions.clearHistory();
            }
        });
        view.findViewById(R.id.layout_dialog_button_nao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();
            }
        });
        if (alertDialog.getWindow() != null) {

            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(1));

        }
        alertDialog.show();

    }


}
