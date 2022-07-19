package com.alura.melhoresdestinos.ui.recyclerview.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alura.melhoresdestinos.R;
import com.alura.melhoresdestinos.model.Pacote;
import com.alura.melhoresdestinos.ui.activity.ResumoPacoteActivity;
import com.alura.melhoresdestinos.util.DiaUtil;
import com.alura.melhoresdestinos.util.ImagemUtil;
import com.alura.melhoresdestinos.util.MoedaUtil;

import java.util.List;

public class SearchableListaAdapter extends RecyclerView.Adapter<SearchableListaAdapter.PesquisaViewHolder> {

    private final List<Pacote> listaPacote;
    private final Context context;
    private OnItemClickListener onItemClickListener;


    public SearchableListaAdapter(List<Pacote> listaPacote, Context context) {
        this.listaPacote = listaPacote;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public SearchableListaAdapter.PesquisaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_pacote, parent, false);

        return new PesquisaViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchableListaAdapter.PesquisaViewHolder holder, int posicao) {

        Pacote pacotePesquisado = listaPacote.get(posicao);

        holder.vincularInformacoes(pacotePesquisado, context);



    }

    @Override
    public int getItemCount() {
        return listaPacote.size();
    }


    class PesquisaViewHolder extends RecyclerView.ViewHolder {

        private final ImageView idImagem;
        private final TextView idPreco;
        private final TextView idDias;
        private final TextView idLocal;


        public PesquisaViewHolder(@NonNull View itemView) {
            super(itemView);

            idImagem = itemView.findViewById(R.id.item_pacote_imagem);
            idPreco = itemView.findViewById(R.id.item_pacote_preco);
            idDias = itemView.findViewById(R.id.item_pacote_dias);
            idLocal = itemView.findViewById(R.id.item_pacote_local);


        }

        public void vincularInformacoes(Pacote pacotePesquisado, Context context) {

            idImagem.setImageDrawable(ImagemUtil.pegarImagemDrawable(pacotePesquisado, context));
            idPreco.setText(MoedaUtil.formatarMoedaBrasileira(pacotePesquisado.getPreco()));
            idDias.setText(DiaUtil.pegarDia(pacotePesquisado.getDias()));
            idLocal.setText(pacotePesquisado.getLocal());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onItemClickListener.onClick(pacotePesquisado);

                }
            });


        }
    }
    public  interface OnItemClickListener{

        void onClick(Pacote pacote);
    }
}
