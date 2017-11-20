package com.eagora.echosoft.eagora.Maps;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.eagora.echosoft.eagora.ApiAccessResponse;
import com.eagora.echosoft.eagora.EventosRoteiroActivity;
import com.eagora.echosoft.eagora.Facebook.RoteiroFacebook;
import com.eagora.echosoft.eagora.GlobalAccess;
import com.eagora.echosoft.eagora.MeusRoteirosActivity;
import com.eagora.echosoft.eagora.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.androidquery.util.AQUtility.getContext;

public class PlaceActivity extends AppCompatActivity {

    private JSONObject jsonResponse;
    private Uri urlRequest;
    private Place place;
    Button btnEscolherLugar;
    private TextView lblNomeLugar, lblTelefoneLugar, lblTelefoneIntLugar, lblEstadoLugar, lblNotaLugar, lblWebsiteLugar;
    private ImageView imgMapsLugar;
    private DatabaseReference mDatabase;
    private int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        place = (Place)getIntent().getSerializableExtra("PlaceObj");
        flag = getIntent().getIntExtra("flag", 0);

        InitializeControls();

        if(getJsonOfNearbyPlaces() == 1)
            completePlace();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected int getJsonOfNearbyPlaces() {

        urlRequest = Uri.parse("https://maps.googleapis.com/maps/api/place/details/json?placeid=" + place.getId() +
                "&key=AIzaSyCKjXv6h28YUPQP9JAb6800mlI6355dcmM");

        try {
            jsonResponse = new DownloadMapsUrl().execute(urlRequest.toString()).get();
            return 1;
        }

        catch (Exception e) {
            Log.d("Exception", e.toString());
            return 0;

        }
    }

    public void completePlace() {

        try {
            JSONObject result = jsonResponse.getJSONObject("result");
            place.setEndereco(result.getString("formatted_address"));
            place.setTelefone(result.getString("formatted_phone_number"));
            place.setTelefone_int(result.getString("international_phone_number"));
            place.setSite(result.getString("website"));

            try {
                place.setSite(result.getString("website"));
            } catch (JSONException e) {
                place.setSite("Sem website");
            }

            lblNomeLugar.setText(place.getName());
            lblTelefoneLugar.setText(place.getTelefone());
            lblTelefoneIntLugar.setText(place.getTelefone_int());
            if (place.getEstado() == "-1")
                lblEstadoLugar.setText("Fechado");
            else lblEstadoLugar.setText("Aberto");
            lblNotaLugar.setText(String.valueOf(place.getNota()));
            lblWebsiteLugar.setText(place.getSite());

            String url = URLImageRequest.mapsImageRequestURL(1000, 1000, place.getLocalizacao());
            Picasso.with(getContext()).load(url).into(imgMapsLugar);
        }
        catch(JSONException e) {
            Log.d("Exception", e.toString());
        }
    }

    private void InitializeControls() {
        btnEscolherLugar = (Button) findViewById(R.id.btnEscolherLugar);
        if(flag == 1)
            btnEscolherLugar.setVisibility(View.VISIBLE);
        else btnEscolherLugar.setVisibility(View.INVISIBLE);
        imgMapsLugar = (ImageView) findViewById(R.id.imgMapsLugar);
        lblNomeLugar = (TextView) findViewById(R.id.lblNomeLugar);
        lblTelefoneLugar = (TextView) findViewById(R.id.lblTelefoneLugar);
        lblTelefoneIntLugar = (TextView) findViewById(R.id.lblTelefoneIntLugar);
        lblEstadoLugar = (TextView) findViewById(R.id.lblEstadoLugar);
        lblNotaLugar = (TextView) findViewById(R.id.lblNotaLugar);
        lblWebsiteLugar = (TextView) findViewById(R.id.lblWebsiteLugar);
        try {
            btnEscolherLugar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        //GlobalAccess.idRoteiro = 0;
                        String testeidRoteiro = String.valueOf(GlobalAccess.idRoteiro);
                        mDatabase = FirebaseDatabase.getInstance().getReference("usuarios").child(user.getUid())
                                .child("roteiros").child(testeidRoteiro).child("maps").child(place.getId());
                        mDatabase.setValue(place);

                        Intent intentEvento = new Intent(getApplicationContext(), EventosRoteiroActivity.class);
                        startActivity(intentEvento);
                        finish();
                    }
                }
            );
        }
        catch(Exception e) {
            Log.d("Exception", e.toString());
        }
    }
}

