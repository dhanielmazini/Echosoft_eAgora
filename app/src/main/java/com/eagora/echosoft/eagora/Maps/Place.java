package com.eagora.echosoft.eagora.Maps;

import android.os.Parcel;
import android.os.Parcelable;

import com.eagora.echosoft.eagora.BancoDados.TipoViagemGenerico;

import java.io.Serializable;

/**
 * Created by hhaji on 29/10/17.
 */

public class Place implements Serializable {
    private String id;
    private String name;
    private String foto_ref;
    private String redondezas;
    private String endereco;
    private String telefone;
    private String telefone_int;
    private String estado;
    private String site;
    private double nota;
    private Coordenada localizacao;
    private String icone;

    public Place(String id, String name, double lat, double lng, double nota, String vicinity, String icon) {
        this.id = id;
        this.name = name;
        localizacao = new Coordenada(lat, lng);
        this.nota = nota;
        this.redondezas = vicinity;
        this.icone = icon;
    }

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

    public String getFoto_ref() {
        return foto_ref;
    }

    public void setFoto_ref(String foto_ref) {
        this.foto_ref = foto_ref;
    }

    public String getRedondezas() {
        return redondezas;
    }

    public void setRedondezas(String redondezas) {
        this.redondezas = redondezas;
    }

    public String getEstado() {
        if(estado.equals("1"))
            return "Aberto";
        else if(estado.equals("0"))
            return "Fechado";
        return "Não Informado";
    }

    public void setEstado(boolean estado) {
        if(estado == true)
            this.estado = "1";
        else this.estado = "0";
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public Coordenada getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(double lat, double lng) {
        this.localizacao.setLatitude(lat);
        this.localizacao.setLongitude(lng);
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefone_int() {
        return telefone_int;
    }

    public void setTelefone_int(String telefone_int) {
        this.telefone_int = telefone_int;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
