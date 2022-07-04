package com.alura.melhoresdestinos.util;

public abstract class DiaUtil {


    public static final String DIASPLURAL = " dias";
    private static final String DIASINGULAR = " dia";

    public static String pegarDia(int dia) {

        if (dia > 1) {

            return DIASPLURAL;

        }

        return DIASINGULAR;

    }

}
