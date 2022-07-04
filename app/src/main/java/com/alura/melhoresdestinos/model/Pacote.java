package com.alura.melhoresdestinos.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

public class Pacote implements Parcelable {

    private String local;
    private String imagem;
    private int dias;
    private BigDecimal preco;

    public Pacote(String local, String imagem, int dias, BigDecimal preco) {
        this.local = local;
        this.imagem = imagem;
        this.dias = dias;
        this.preco = preco;
    }

    protected Pacote(Parcel in) {
        //Recupera a String que representa
        // o valor do BigDecimal e depois o recria-lo
        preco = new BigDecimal(in.readString());
        local = in.readString();
        imagem = in.readString();
        dias = in.readInt();
    }

    public static final Creator<Pacote> CREATOR = new Creator<Pacote>() {
        @Override
        public Pacote createFromParcel(Parcel in) {
            return new Pacote(in);
        }

        @Override
        public Pacote[] newArray(int size) {
            return new Pacote[size];
        }
    };

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        //Guarda uma String com a representação do valor
        //do BigDecimal
        parcel.writeString(preco.toEngineeringString());
        parcel.writeString(local);
        parcel.writeString(imagem);
        parcel.writeInt(dias);
    }
}
