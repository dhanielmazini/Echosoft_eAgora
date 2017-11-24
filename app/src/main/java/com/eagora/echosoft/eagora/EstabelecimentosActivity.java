package com.eagora.echosoft.eagora;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.eagora.echosoft.eagora.Maps.Coordenada;
import com.eagora.echosoft.eagora.Maps.DownloadMapsUrl;
import com.eagora.echosoft.eagora.Maps.Place;
import com.eagora.echosoft.eagora.Maps.PlacesAdapter;
import com.eagora.echosoft.eagora.Maps.PlacesBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import im.delight.android.location.SimpleLocation;

public class EstabelecimentosActivity extends AppCompatActivity {
    private JSONObject jsonResponse;
    private ListView lstPlaces;
    private Uri urlRequest;
    private SimpleLocation location;
    private int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_place);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        location = new SimpleLocation(this, true);
        location.beginUpdates();
        GlobalAccess.coordenadaUsuario = new Coordenada(location.getLatitude(), location.getLongitude());

        flag = getIntent().getIntExtra("flag", 0);

        lstPlaces = (ListView) findViewById(R.id.lstPlaces);
        if (getJsonOfNearbyPlaces(GlobalAccess.coordenadaUsuario, "") == 1) {
            MakePlacesList();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected int getJsonOfNearbyPlaces(Coordenada origin, String next_page) {

        urlRequest = new PlacesBuilder()
                .header()
                .location(origin)
                .radius(10000)
                .type("restaurant")
                .type("bar")
                .type("museum")
                .build();
        if(!next_page.equals(""))
            urlRequest = new PlacesBuilder().next(urlRequest.toString(), next_page);

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
            JSONArray results = jsonResponse.getJSONArray("results");
            for(int i=0;i<results.length();i++) {
                Place e;
                e = new Place(
                        jsonResponse.getJSONArray("results").getJSONObject(i).getString("place_id"),
                        jsonResponse.getJSONArray("results").getJSONObject(i).getString("name"),
                        jsonResponse.getJSONArray("results").getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat"),
                        jsonResponse.getJSONArray("results").getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng"),
                        jsonResponse.getJSONArray("results").getJSONObject(i).getDouble("rating"),
                        jsonResponse.getJSONArray("results").getJSONObject(i).getString("vicinity"),
                        jsonResponse.getJSONArray("results").getJSONObject(i).getString("icon"));


                try {
                    e.setEstado(jsonResponse.getJSONArray("results").getJSONObject(i).getJSONArray("opening_hours").getJSONObject(0).getBoolean("open_now"));
                }
                catch (JSONException OpenEx) {
                    e.setEstado(false);
                }

                try {
                    e.setFoto_ref(jsonResponse.getJSONArray("results").getJSONObject(i).getJSONArray("photos").getJSONObject(0).getString("photo_reference"));
                }
                catch (JSONException photoEx) {
                    e.setFoto_ref("SF");
                }

                listaEstab.add(e);

                try {
                    if(results.length() == i+1) {
                        getJsonOfNearbyPlaces(GlobalAccess.coordenadaLocalViagem, jsonResponse.getString("next_page_token"));
                        results = jsonResponse.getJSONArray("results");
                        i = 0;
                    }
                }
                catch (JSONException nextTok) {
                    Log.d("Exception", nextTok.toString());
                    continue;
                }
            }
        }
        catch (JSONException jsonEx){
            Log.d("Exception", jsonEx.toString());
        }
        lstPlaces.setAdapter(new PlacesAdapter(this,R.layout.item_place_card,listaEstab, flag));
    }
}
