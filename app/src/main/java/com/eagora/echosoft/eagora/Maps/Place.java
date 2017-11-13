package com.eagora.echosoft.eagora.Maps;

/**
 * Created by hhaji on 29/10/17.
 */

public class Place {
    //pra pegar foto https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=CnRtAAAATLZNl354RwP_9UKbQ_5Psy40texXePv4oAlgP4qNEkdIrkyse7rPXYGd9D_Uj1rVsQdWT4oRz4QrYAJNpFX7rzqqMlZw2h2E2y5IKMUZ7ouD_SlcHxYq1yL4KbKUv3qtWgTK0A6QbGh87GB3sscrHRIQiG2RrmU_jF4tENr9wGS_YxoUSSDrYjWmrNfeEHSGSc3FyhNLlBU&key=YOUR_API_KEY
    private String id;
    private String nome;
    private String foto_ref;
    private String redondezas;
    private String estado;
    private double nota;
    private Coordenada localizacao;

    public Place(String id, String nome, double lat, double lng, double nota, String vicinity) {
        this.id = id;
        this.nome = nome;
        localizacao = new Coordenada(lat, lng);
        this.nota = nota;
        this.redondezas = vicinity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String isEstado() {
        return estado;
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
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public Coordenada getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(double lat, double lng) {
        this.localizacao.setLatitude(lat);
        this.localizacao.setLongitude(lng);
    }
}
