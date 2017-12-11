package com.eagora.echosoft.eagora.Maps;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.eagora.echosoft.eagora.CriarRoteiroActivity;
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
    private ListView lstPlaces;
    private Uri urlRequest;
    private SimpleLocation location;
    private int flag;
    List<Place> listaEstab;
    ImageView imgFilter;
    String typeOfLocation = "restaurant";
    Dialog dialog;
    Activity activity;
    private AlertDialog alertDialog1;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_place);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        activity = this;

        imgFilter = (ImageView) findViewById(R.id.imgFilter);
        //AlertDialog (AlertDialog) alertDialog1
        location = new SimpleLocation(this, true);
        location.beginUpdates();
        GlobalAccess.coordenadaUsuario = new Coordenada(location.getLatitude(), location.getLongitude());

        flag = getIntent().getIntExtra("flag", 0);

        lstPlaces = (ListView) findViewById(R.id.lstPlaces);
        if (getJsonOfNearbyPlaces(GlobalAccess.coordenadaLocalViagem, "") == 1) {
            MakePlacesList();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        imgFilter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CreateAlertDialogWithRadioButtonGroup();
            }
        });

    }

    @Override
    public void onBackPressed(){
        Intent meuRoteiro = new Intent(getApplicationContext(), CriarRoteiroActivity.class);
        startActivity(meuRoteiro);
        finish();
        super.onBackPressed();
    }

    protected int getJsonOfNearbyPlaces(Coordenada origin, String next_page) {
        urlRequest = new PlacesBuilder()
                .header()
                .location(origin)
                .radius(10000)
                .type(typeOfLocation)
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
        listaEstab = new ArrayList<>();
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

    public void CreateAlertDialogWithRadioButtonGroup(){
        CharSequence[] itens = {"Restaurantes", "Bares", "Lojas"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ListPlaceActivity.this);

        builder.setTitle("Selecione um tipo");

        builder.setSingleChoiceItems(itens, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch(item)
                {
                    case 0:
                        typeOfLocation = "restaurant";
                        break;
                    case 1:
                        typeOfLocation = "bar";
                        break;
                    case 2:
                        typeOfLocation = "store";
                        break;
                }
                if (getJsonOfNearbyPlaces(GlobalAccess.coordenadaLocalViagem, "") == 1) {
                    MakePlacesList();
                }
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();

    }

}

