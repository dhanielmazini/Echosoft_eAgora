package com.eagora.echosoft.eagora.Maps;

import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.eagora.echosoft.eagora.GlobalAccess;
import com.eagora.echosoft.eagora.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import im.delight.android.location.SimpleLocation;

public class ListPlaceActivity extends AppCompatActivity {
    private JSONObject jsonResponse;
    ListView lstPlaces;
    private SimpleLocation location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_place);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lstPlaces = (ListView) findViewById(R.id.lstPlaces);
        if (getJsonOfNearbyPlaces(GlobalAccess.coordenadaUsuario) == 1) {
            MakePlacesList();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected int getJsonOfNearbyPlaces(Coordenada origin) {

        Uri urlRequest = new PlacesBuilder()
                .header()
                .location(origin)
                .radius(500)
                .keyword("university")
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
            JSONArray results = jsonResponse.getJSONArray("results");
            for(int i=0;i<results.length();i++) {
                Place e;
                e = new Place(
                    jsonResponse.getJSONArray("results").getJSONObject(i).getString("id"),
                    jsonResponse.getJSONArray("results").getJSONObject(i).getString("name"),
                    jsonResponse.getJSONArray("results").getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat"),
                    jsonResponse.getJSONArray("results").getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng"),
                    jsonResponse.getJSONArray("results").getJSONObject(i).getDouble("rating"),
                    jsonResponse.getJSONArray("results").getJSONObject(i).getString("vicinity"));

                try {
                    e.setEstado(jsonResponse.getJSONArray("results").getJSONObject(i).getJSONArray("opening_hours").getJSONObject(0).getBoolean("open_now"));
                }
                catch (JSONException OpenEx) {
                    e.setEstado("-1");
                }

                try {
                    e.setFoto_ref(jsonResponse.getJSONArray("results").getJSONObject(i).getJSONArray("photos").getJSONObject(0).getString("photo_reference"));
                }
                catch (JSONException photoEx) {
                    e.setFoto_ref("SF");
                }

                listaEstab.add(e);
            }
        }
        catch (JSONException jsonEx){
            Log.d("Exception", jsonEx.toString());
        }
        lstPlaces.setAdapter(new PlacesAdapter(this,R.layout.item_place_card,listaEstab));

    }

}
