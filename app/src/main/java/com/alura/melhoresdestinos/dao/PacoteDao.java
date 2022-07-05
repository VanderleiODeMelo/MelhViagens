package com.alura.melhoresdestinos.dao;

import com.alura.melhoresdestinos.R;
import com.alura.melhoresdestinos.model.Pacote;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PacoteDao {

    public static List<Pacote> listaPacotes() {


        List<Pacote> lista = new ArrayList<Pacote>();

        lista.add(new Pacote("São paulo", String.valueOf(R.drawable.sao_paulo_sp), 2, new BigDecimal("299.99")));
        lista.add(new Pacote("Belo Horizonte", String.valueOf(R.drawable.belo_horizonte_mg), 3, new BigDecimal("500.70")));

        lista.add(new Pacote("Rio de janeiro", String.valueOf(R.drawable.rio_de_janeiro_rj), 1, new BigDecimal("500.00")));

        lista.add(new Pacote("Foz do iguacu", String.valueOf(R.drawable.foz_do_iguacu_pr), 7, new BigDecimal("300.15")));
        lista.add(new Pacote("Recife", String.valueOf(R.drawable.recife_pe), 2, new BigDecimal("200.39")));
        lista.add(new Pacote("Salvador", String.valueOf(R.drawable.salvador_ba), 5, new BigDecimal("700.60")));

        return lista;

    }


}
