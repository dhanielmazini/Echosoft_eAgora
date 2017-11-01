package com.eagora.echosoft.eagora.Maps;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.eagora.echosoft.eagora.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListPlaceActivity extends AppCompatActivity {
    private JSONObject jsonResponse;
    ListView lstPlaces;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_place);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lstPlaces = (ListView) findViewById(R.id.lstPlaces);
        String teste = "Rua Cassiopeia São José dos Campos";
        if(getJsonOfNearbyPlaces(teste) == 1) {
            MakePlacesList();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected int getJsonOfNearbyPlaces(String origin) {
        List<String> lista = new ArrayList<>();
        lista.add(origin);

        List<Coordenada> coordenadas = CoordenadaListFactory.createCoordenadas(lista, this);
        Coordenada local = coordenadas.get(0);

        Uri urlRequest = new PlacesBuilder()
                .header()
                .location(local)
                .radius(5000)
                .keyword("restaurants")
                .build();

        try {
            jsonResponse = new DownloadMapsUrl().execute(urlRequest.toString()).get();
            return 1;
        }
        catch (Exception e) {
            Log.d("Exception", e.toString());
            return 0;
        }
    }

    public void MakePlacesList() {
        List<Place> listaEstab = new ArrayList<>();
        try {
            Place e;
            for(int i=0;i<10;i++) {
                e = new Place(
                        jsonResponse.getJSONArray("results").getJSONObject(i).getString("id"),
                        jsonResponse.getJSONArray("results").getJSONObject(i).getString("name"));
                //e.setLocalizacao(jsonResponse.getJSONArray("results").getJSONObject(i).getJSONArray("geometry").getJSONObject(0).getJSONArray("location").getDouble(0),
                        //jsonResponse.getJSONArray("results").getJSONObject(i).getJSONArray("geometry").getJSONObject(0).getJSONArray("location").getDouble(1);
                //e.setId(jsonResponse.getJSONArray("results").getJSONObject(i).getString("id"));
                //e.setNome(jsonResponse.getJSONArray("results").getJSONObject(i).getString("name"));
                //e.setEstado(jsonResponse.getJSONArray("results").getJSONObject(i).getJSONArray("opening_hours").getBoolean(0));
                //e.setFoto_ref(jsonResponse.getJSONArray("results").getJSONObject(i).getJSONArray("photos").getJSONObject(2).getString("photo_reference"));
                //e.setNota(jsonResponse.getJSONArray("results").getJSONObject(i).getDouble("rating"));
                //e.setRedondezas(jsonResponse.getJSONArray("results").getJSONObject(i).getString("vicinity"));
                listaEstab.add(e);
            }
/*
            ArrayAdapter<Place> arrayAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    listaEstab
                    );*/


            lstPlaces.setAdapter(new PlacesAdapter(this,R.layout.item_place_card,listaEstab));

        }
        catch (JSONException jsonEx){
            Log.d("Exception", jsonEx.toString());
        }
    }

}
