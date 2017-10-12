package com.eagora.echosoft.eagora;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.eagora.echosoft.eagora.Facebook.AcessoGraphFacebook;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class EventosActivity extends AppCompatActivity {

    TextView txtEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        InitializeControls();
    }

    private void  InitializeControls() {

        txtEventos = (TextView) findViewById(R.id.txtEventos);


        AcessoGraphFacebook acesso = new AcessoGraphFacebook();

        txtEventos.setText("");
        GraphResponse response = acesso.eventosTeste();
        try {
            JSONArray ar = response.getJSONObject().getJSONArray("data");
            for(int i=0;i<ar.length();i++){
                JSONObject obj2 = ar.getJSONObject(i);
                String nome = obj2.get("name").toString();
                txtEventos.append("Evento " + i + ": " +  nome + "\n");
            }
        }
        catch(Exception e){
            txtEventos.setText(e.toString());
        }

    }



}
