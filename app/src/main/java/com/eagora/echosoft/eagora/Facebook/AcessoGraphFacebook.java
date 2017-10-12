package com.eagora.echosoft.eagora.Facebook;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import android.os.Bundle;


/**
 * Created by junior on 11/10/17.
 */

public class AcessoGraphFacebook {


    //Token definido no Login do MenuActivity
    //Necessário para todas as requisições do Facebook
    public static AccessToken accessToken;

    //Variável de retorno das consultas da API Graph do Facebook
    GraphResponse responseRetorno;



    public GraphResponse eventosTeste(){
        final GraphRequest request = GraphRequest.newGraphPathRequest(
                accessToken,
                "/542158045980461/events",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        responseRetorno = response;
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,start_time,cover");
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

}
