package com.alura.melhoresdestinos.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public abstract class MoedaUtil {


    public static final String LINGUAGEM = "pt";
    public static final String PAIS = "br";

    public static String formatarMoedaBrasileira(BigDecimal valor) {

        DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance(
                new Locale(LINGUAGEM, PAIS));

        return formatter.format(valor);
    }
}
