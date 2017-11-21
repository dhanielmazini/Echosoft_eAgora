package com.eagora.echosoft.eagora;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.eagora.echosoft.eagora.BancoDados.TipoViagemGenerico;
import com.eagora.echosoft.eagora.Facebook.AcessoGraphFacebook;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class EventosActivity extends AppCompatActivity {

    Button btnAventura,btnCompras,btnCultural,btnFamilia,
            btnGastronomico,btnPaisagem,btnTrabalho,
            btnVidaNoturna, btnOutros;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnAventura = (Button)findViewById(R.id.btnAventura);
        btnAventura.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){

                        Intent intentEventos = new Intent(getApplicationContext(), EventosExibirActivity.class);
                        intentEventos.putExtra("tipo","Aventura");
                        startActivity(intentEventos);
                        finish();

                    }
                }
        );


        btnCompras = (Button)findViewById(R.id.btnCompras);
        btnCompras.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){

                        Intent intentEventos = new Intent(getApplicationContext(), EventosExibirActivity.class);
                        intentEventos.putExtra("tipo","Compras");
                        startActivity(intentEventos);
                        finish();

                    }
                }
        );


        btnCultural = (Button)findViewById(R.id.btnCultural);
        btnCultural.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent intentEventos = new Intent(getApplicationContext(), EventosExibirActivity.class);
                        intentEventos.putExtra("tipo","Cultural");
                        startActivity(intentEventos);
                        finish();
                    }
                }
        );


        btnFamilia = (Button)findViewById(R.id.btnFamilia);
        btnFamilia.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent intentEventos = new Intent(getApplicationContext(), EventosExibirActivity.class);
                        intentEventos.putExtra("tipo","Familia");
                        startActivity(intentEventos);
                        finish();
                    }
                }
        );


        btnGastronomico = (Button)findViewById(R.id.btnGastronomico);
        btnGastronomico.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent intentEventos = new Intent(getApplicationContext(), EventosExibirActivity.class);
                        intentEventos.putExtra("tipo","Gastronômico");
                        startActivity(intentEventos);
                        finish();
                    }
                }
        );


        btnPaisagem = (Button)findViewById(R.id.btnPaisagem);
        btnPaisagem.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent intentEventos = new Intent(getApplicationContext(), EventosExibirActivity.class);
                        intentEventos.putExtra("tipo","Paisagem");
                        startActivity(intentEventos);
                        finish();
                    }
                }
        );


        btnTrabalho = (Button)findViewById(R.id.btnTrabalho);
        btnTrabalho.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent intentEventos = new Intent(getApplicationContext(), EventosExibirActivity.class);
                        intentEventos.putExtra("tipo","Trabalho");
                        startActivity(intentEventos);
                        finish();
                    }
                }
        );


        btnVidaNoturna = (Button)findViewById(R.id.btnVidaNoturna);
        btnVidaNoturna.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent intentEventos = new Intent(getApplicationContext(), EventosExibirActivity.class);
                        intentEventos.putExtra("tipo","Vida Noturna");
                        startActivity(intentEventos);
                        finish();
                    }
                }
        );

        btnOutros = (Button)findViewById(R.id.btnOutros);
        btnOutros.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent intentEventos = new Intent(getApplicationContext(), EventosExibirActivity.class);
                        intentEventos.putExtra("tipo","Outros");
                        startActivity(intentEventos);
                        finish();
                    }
                }
        );

    }



}
