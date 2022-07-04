package com.alura.melhoresdestinos.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.alura.melhoresdestinos.model.Pacote;

public abstract class ImagemUtil {

    public static final String TIPO_DRAWABLE = "drawable";

    @Nullable
    public static Drawable pegarImagemDrawable(Pacote pacote, Context context) {

        Resources resources = context.getResources();
        int idDrawable = resources.getIdentifier(pacote.getImagem(), TIPO_DRAWABLE, context.getPackageName());
        return ResourcesCompat.getDrawable(resources, idDrawable, context.getTheme());

    }


}
