package com.eagora.echosoft.eagora.BancoDados;

/**
 * Created by junior on 25/10/17.
 */

public class TipoViagemGenerico {
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TipoViagemGenerico(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TipoViagemGenerico() {
    }
}
