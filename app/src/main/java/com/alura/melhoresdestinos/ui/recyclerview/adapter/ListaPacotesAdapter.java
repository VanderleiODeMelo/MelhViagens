package com.alura.melhoresdestinos.ui.recyclerview.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alura.melhoresdestinos.R;
import com.alura.melhoresdestinos.model.Pacote;
import com.alura.melhoresdestinos.util.DiaUtil;
import com.alura.melhoresdestinos.util.ImagemUtil;
import com.alura.melhoresdestinos.util.MoedaUtil;

import java.util.List;

public class ListaPacotesAdapter extends RecyclerView.Adapter<ListaPacotesAdapter.PacoteViewHolder> {


    private final Context context;
    private final List<Pacote> listaPacotes;

    private static OnItemClickListener onItemClickListener;


    public ListaPacotesAdapter(Context context, List<Pacote> listaPacotes) {
        this.context = context;
        this.listaPacotes = listaPacotes;

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        ListaPacotesAdapter.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ListaPacotesAdapter.PacoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_pacote, parent, false);
        return new PacoteViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaPacotesAdapter.PacoteViewHolder holder, int position) {

        int posicao = holder.getAdapterPosition();
        Pacote pacote = listaPacotes.get(posicao);
        holder.vincular(pacote, context);

    }

    @Override
    public int getItemCount() {
        return listaPacotes.size();
    }

    static class PacoteViewHolder extends RecyclerView.ViewHolder {

        private final ImageView idImagem;
        private final TextView idLocal;
        private final TextView idDias;
        private final TextView idPreco;

        public PacoteViewHolder(@NonNull View itemView) {
            super(itemView);

            idImagem = itemView.findViewById(R.id.item_pacote_imagem);
            idLocal = itemView.findViewById(R.id.item_pacote_local);
            idDias = itemView.findViewById(R.id.item_pacote_dias);
            idPreco = itemView.findViewById(R.id.item_pacote_preco);

        }

        public void vincular(Pacote pacote, Context context) {

            pegarLocal(pacote);
            pegarDia(pacote);
            pegarPreco(pacote);
            pegarImagem(pacote, context);

            configurarListenerClick(pacote);

        }

        private void configurarListenerClick(Pacote pacote) {

            itemView.setOnClickListener(view -> onItemClickListener.onClick(pacote));
        }

        private void pegarImagem(Pacote pacote, Context context) {
            Drawable drawable = ImagemUtil.pegarImagemDrawable(pacote, context);
            idImagem.setImageDrawable(drawable);
        }

        private void pegarPreco(Pacote pacote) {
            idPreco.setText(MoedaUtil.formatarMoedaBrasileira(pacote.getPreco()));
        }

        private void pegarDia(Pacote pacote) {
            idDias.setText(new StringBuffer()
                    .append(pacote.getDias())
                    .append(DiaUtil.pegarDia(pacote.getDias())));
        }

        private void pegarLocal(Pacote pacote) {
            idLocal.setText(pacote.getLocal());
        }

    }


    public interface OnItemClickListener {

        void onClick(Pacote pacote);
    }
}

