package com.eagora.echosoft.eagora;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eagora.echosoft.eagora.EventosActivity;
import com.eagora.echosoft.eagora.R;

public class TelaDeRedirecionamentoMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_de_redirecionamento_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        InitializeControls();


    }

    private void  InitializeControls() {
        ImageButton eventbtn = (ImageButton) findViewById(R.id.eventbtn);
        ImageButton bar_e_restaurante = (ImageButton)findViewById(R.id.bar_e_restaurante);
        ImageButton pontos_turisticos = (ImageButton)findViewById(R.id.pontos_turisticos);
    /*    TextView EVENTO = (TextView)findViewById(R.id.EVENTO);
        TextView BAR = (TextView)findViewById(R.id.BAR);
        TextView PONTOS = (TextView)findViewById(R.id.PONTOS);*/

        eventbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentEventos = new Intent(getApplicationContext(), EventosActivity.class);
                        startActivity(intentEventos);
                    }
                }
        );
       /* //eventbtn.setX(55);
        eventbtn.setY(60);
        EVENTO.setX(370);
        EVENTO.setY(260);
        bar_e_restaurante.setY(560);
        BAR.setX(200);
        BAR.setY(760);
        pontos_turisticos.setY(1080);
        PONTOS.setX(230);
        PONTOS.setY(1300);*/
}
}