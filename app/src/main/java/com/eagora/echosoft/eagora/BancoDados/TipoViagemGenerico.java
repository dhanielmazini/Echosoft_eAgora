package com.eagora.echosoft.eagora.BancoDados;

/**
 * Created by junior on 25/10/17.
 */

public class TipoViagemGenerico {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TipoViagemGenerico(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public TipoViagemGenerico() {
    }
}
