package com.eagora.echosoft.eagora.Maps;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.eagora.echosoft.eagora.R;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void onClickMaps(View view) {
        List<String> lista = new ArrayList<>();
        /*TO DO - Pegar as informações do banco*/
        lista.add("Rua Matias Peres");
        lista.add("Rua Lea Maria Brandao Russo");
        lista.add("Rua Cassiopeia São José dos Campos");

        List<Coordenada> coordenadas = CoordenadaListFactory.createCoordenadas(lista, this);
        Coordenada coordenadaOrigem = coordenadas.get(0);
        Coordenada coordenadaDestino = coordenadas.get(coordenadas.size() - 1);

        coordenadas.remove(0);
        coordenadas.remove(coordenadas.size() - 1);

        Uri gmmIntentUri = new EnderecoBuilder()
                .header()
                .origem(coordenadaOrigem)
                .destino(coordenadaDestino)
                .waypoints(coordenadas)
                .travelMode()
                .build();

        Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            try {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                startActivity(unrestrictedIntent);
            } catch (ActivityNotFoundException innerEx) {
                Toast.makeText(this, "Please install a maps application", Toast.LENGTH_LONG).show();
            }
        }
    }

}
