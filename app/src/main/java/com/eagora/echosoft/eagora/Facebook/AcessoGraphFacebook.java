package com.eagora.echosoft.eagora.Facebook;

import com.eagora.echosoft.eagora.BancoDados.TipoViagemGenerico;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by junior on 11/10/17.
 */

public class AcessoGraphFacebook {


    //Token definido no Login do MenuActivity
    //Necessário para todas as requisições do Facebook
    public static AccessToken accessToken;

    //Variável de retorno das consultas da API Graph do Facebook
    GraphResponse responseRetorno;


    public List<JSONObject> procurarEventos(double latitude, double longitude, double raio,List<TipoViagemGenerico> listaLugares){
        responseRetorno = locais(latitude,longitude,raio);
        List<String> listaLocais = new ArrayList<String>();
        try {
            JSONArray ar = responseRetorno.getJSONObject().getJSONArray("data");
            for (int i = 0; i < ar.length(); i++) {
                JSONObject dados = ar.getJSONObject(i);
                String categoriaFb = dados.get("category").toString();
                for(int j=0;j<listaLugares.size();j++){
                    String categoriaBanco = listaLugares.get(j).getName();
                    if(categoriaBanco.equals(categoriaFb)){
                        listaLocais.add(dados.get("id").toString());
                    }
                }
            }
        }        catch(Exception e){
            e.printStackTrace();
        }


        List<JSONObject> listaEventos = new ArrayList<JSONObject>();
        int tam=0;
        for(int i=0; i<listaLocais.size();i++){
            try {
                 responseRetorno = eventos(listaLocais.get(i));

                JSONArray ar = responseRetorno.getJSONObject().getJSONArray("data");
                if(ar.length()>0){
                    for(int j=0;j<ar.length();j++){
                        JSONObject dados = ar.getJSONObject(j);
                        listaEventos.add(j,dados);
                    }
                }

            }        catch(Exception e){
                e.printStackTrace();
            }

        }
        return listaEventos;

    }

    public GraphResponse eventos(String id){
        // "/542158045980461/events"
        String queryId = "/"+ id +"/events";
        final GraphRequest request = GraphRequest.newGraphPathRequest(
                accessToken,
                queryId,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        responseRetorno = response;
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,start_time,cover{source},place{name,location{city,street,zip,state}}");
        parameters.putString("time_filter", "upcoming");
        request.setParameters(parameters);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                GraphResponse gResponse = request.executeAndWait();
            }
        });
        t.start();

        try {
            t.join();
        }catch(Exception e){
            e.printStackTrace();
        }
        return responseRetorno;
    }

    public GraphResponse locais(double latitude, double longitude, double raio){
        final GraphRequest request = GraphRequest.newGraphPathRequest(
                accessToken,
                "/search",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        responseRetorno = response;
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("q", "*");
        parameters.putString("type", "place");
        String coordenadas = String.valueOf(latitude) +","+String.valueOf(longitude);
        parameters.putString("center", coordenadas);
        parameters.putString("distance", String.valueOf(raio));
        parameters.putString("fields", "name,category");
        parameters.putString("limit", "500"); //Limite de 500 respostas
        request.setParameters(parameters);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                GraphResponse gResponse = request.executeAndWait();
            }
        });
        t.start();

        try {
            t.join();
        }catch(Exception e){
            e.printStackTrace();
        }
        return responseRetorno;
    }

}
