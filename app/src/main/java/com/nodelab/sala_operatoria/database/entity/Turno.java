package com.nodelab.sala_operatoria.database.entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Turno {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "data")
    private String data;

    @ColumnInfo(name = "nome_dottore_giorno")
    private String nomeDottoreGiorno;

    @ColumnInfo(name = "numero_dottore_giorno")
    private String numeroDottoreGiorno;

    @ColumnInfo(name = "nome_dottore_notte")
    private String nomeDottoreNotte;

    @ColumnInfo(name = "numero_dottore_notte")
    private String numeroDottoreNotte;


    public int getUid() {
        return uid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNomeDottoreGiorno() {
        return nomeDottoreGiorno;
    }

    public void setNomeDottoreGiorno(String nomeDottoreGiorno) {
        this.nomeDottoreGiorno = nomeDottoreGiorno;
    }

    public String getNumeroDottoreGiorno() {
        return numeroDottoreGiorno;
    }

    public void setNumeroDottoreGiorno(String numeroDottoreGiorno) {
        this.numeroDottoreGiorno = numeroDottoreGiorno;
    }

    public String getNomeDottoreNotte() {
        return nomeDottoreNotte;
    }

    public void setNomeDottoreNotte(String nomeDottoreNotte) {
        this.nomeDottoreNotte = nomeDottoreNotte;
    }

    public String getNumeroDottoreNotte() {
        return numeroDottoreNotte;
    }

    public void setNumeroDottoreNotte(String numeroDottoreNotte) {
        this.numeroDottoreNotte = numeroDottoreNotte;
    }
}
