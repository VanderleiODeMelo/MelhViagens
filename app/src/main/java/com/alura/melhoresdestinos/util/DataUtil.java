package com.alura.melhoresdestinos.util;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public abstract class DataUtil {

    @NonNull
    public static String formatarData(int data) {

        String formatoData = "dd/MM";
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat(formatoData);

        Calendar dataInicial = Calendar.getInstance();
        Calendar dataFinal = Calendar.getInstance();
        dataFinal.add(Calendar.DATE, data);

        String dataInicialFormatada = sdf.format(dataInicial.getTime());
        String dataFinalFormatada = sdf.format(dataFinal.getTime());


        return dataInicialFormatada.concat(" - ") +
                dataFinalFormatada + " de " + dataFinal.get(Calendar.YEAR);
    }


}
