package com.eagora.echosoft.eagora.BancoDados;

/**
 * Created by junior on 18/10/17.
 */

public class TipoViagem {
    private int id;
    private String tipo;

    public TipoViagem(){

    }

    public TipoViagem(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
