package com.eagora.echosoft.eagora;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.bumptech.glide.Glide;
import com.eagora.echosoft.eagora.BancoDados.TipoViagemGenerico;
import com.eagora.echosoft.eagora.Facebook.AcessoGraphFacebook;
import com.facebook.GraphResponse;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventosRoteiroActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    List<TipoViagemGenerico> listaLugares = new ArrayList<TipoViagemGenerico>();
    List<String> perfilUsuario;
    Button btnTeste,btnEventosBanco;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos_roteiro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        perfilUsuario = GlobalAccess.perfilUsuario;
        InitializeControls();
    }

    private void listarLugares1(){
        mDatabase = FirebaseDatabase.getInstance().getReference("classificacao_tipo_local").child(perfilUsuario.get(0));
        mDatabase.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dados : dataSnapshot.getChildren()) {
                        TipoViagemGenerico tg = dados.getValue(TipoViagemGenerico.class);
                        listaLugares.add(tg);
                    }
                }

            }
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }



    private void listarLugares2(){
        mDatabase = FirebaseDatabase.getInstance().getReference("classificacao_tipo_local").child(perfilUsuario.get(1));
        mDatabase.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dados : dataSnapshot.getChildren()) {
                        TipoViagemGenerico tg = dados.getValue(TipoViagemGenerico.class);
                        listaLugares.add(tg);
                    }
                }

            }
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    private void listarLugares3(){
        mDatabase = FirebaseDatabase.getInstance().getReference("classificacao_tipo_local").child(perfilUsuario.get(2));
        mDatabase.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dados : dataSnapshot.getChildren()) {
                        TipoViagemGenerico tg = dados.getValue(TipoViagemGenerico.class);
                        listaLugares.add(tg);
                    }
                }

            }
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    private void  InitializeControls(){

        listarLugares2();
        listarLugares3();
        listarLugares1();



        btnEventosBanco = (Button)findViewById(R.id.btnEventosBanco);
        btnEventosBanco.setOnClickListener(
                new View.OnClickListener(){
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onClick(View view){
                        Intent intentEvento = new Intent(getApplicationContext(), EventosRoteiroExibicaoActivity.class);
                        startActivity(intentEvento);
                    }
                }
        );

       btnTeste = (Button)findViewById(R.id.btnTeste);
        btnTeste.setOnClickListener(
                new View.OnClickListener(){
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onClick(View view){
                        eventosProximos(listaLugares);
                    }
                }
        );
    }

    private void eventosProximos(List<TipoViagemGenerico> listaLugares){


        AcessoGraphFacebook acesso = new AcessoGraphFacebook();
        double latitude = GlobalAccess.coordenadaUsuario.getLatitude();
        double longitude = GlobalAccess.coordenadaUsuario.getLongitude();
        double raio = 1000;

        //Mapeia o LinearLayout
        LinearLayout linear = (LinearLayout)findViewById(R.id.linearEventos);

        List<JSONObject> listaEventos = acesso.procurarEventos(latitude,longitude,raio,listaLugares);

        for(int i=0;i<listaEventos.size();i++){
            try {
                ImageView imgEvento = new ImageView(this);
                imgEvento.setId(i+200);

                //Objeto pai
                JSONObject dados = listaEventos.get(i);


                //Cover (capa) da imagem do evento do Facebook
                JSONObject cover = dados.getJSONObject("cover");
                AQuery aq=new AQuery(this);
                aq.id(imgEvento).image(cover.get("source").toString());

                final String idEvento = dados.get("id").toString();


                //Seta a imagemview como clicável
                imgEvento.setClickable(true);

                //Abre o link do evento
                imgEvento.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        Intent intentEvento = new Intent(getApplicationContext(), DetalhesEventoActivity.class);
                        intentEvento.putExtra("idEvento",idEvento);
                        intentEvento.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intentEvento);
                    }
                });


                linear.addView(imgEvento);
                imgEvento.setMaxWidth(259);



            }
            catch(Exception e){
                e.printStackTrace();
            }

        }
    }

}
