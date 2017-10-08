package com.eagora.echosoft.eagora;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class PerfilViajanteActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_viajante);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Perfil");
        setSupportActionBar(toolbar);



        //Botão do email que fica voando na tela, apagar esse trecho e o
        // <android.support.design.widget.FloatingActionButton no xml
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button btnStart = (Button)(findViewById(R.id.btnStart));
        final CheckBox cbVidaNoturna = (CheckBox)(findViewById(R.id.cbVidaNoturna));
        final CheckBox cbGourmet = (CheckBox)(findViewById(R.id.cbGourmet));
        final CheckBox cbBaladas = (CheckBox)(findViewById(R.id.cbBaladas));
        final CheckBox cbTrabalho = (CheckBox)(findViewById(R.id.cbTrabalho));
        final CheckBox cbAmanteNatureza = (CheckBox)(findViewById(R.id.cbAmanteNatureza));
        final CheckBox cbPazTranquilidade = (CheckBox) (findViewById(R.id.cbPazTranquilidade));
        final CheckBox cbMochileiro = (CheckBox)(findViewById(R.id.cbMochileiro));
        final CheckBox cbEcoturista = (CheckBox)(findViewById(R.id.cbEcoturista));
        final CheckBox cbDivertEcono = (CheckBox)(findViewById(R.id.cbDivertEcono));
        final CheckBox cbArteHistoria = (CheckBox)(findViewById(R.id.cbArteHistoria));
        final CheckBox cbFamilia = (CheckBox)(findViewById(R.id.cbFamilia));
        final CheckBox cbCriancas = (CheckBox)(findViewById(R.id.cbCriancas));
        final CheckBox cbEsportes = (CheckBox)(findViewById(R.id.cbEsportes));
        final CheckBox cbNovasCulturas = (CheckBox)(findViewById(R.id.cbNovasCulturas));
        final CheckBox cbCompras = (CheckBox)(findViewById(R.id.cbCompras));
        final CheckBox cb50Anos = (CheckBox)(findViewById(R.id.cb50Anos));
        final CheckBox cbRomantico = (CheckBox)(findViewById(R.id.cbRomantico));
        final CheckBox cbAventura = (CheckBox)(findViewById(R.id.cbAventura));



        cbVidaNoturna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbVidaNoturna.isChecked())
                    Toast.makeText(PerfilViajanteActivity.this, "Vida Noturna", Toast.LENGTH_SHORT).show();
            }
        });

        cbGourmet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbGourmet.isChecked())
                    Toast.makeText(PerfilViajanteActivity.this, "Vida Noturna e Gastronômico", Toast.LENGTH_SHORT).show();
            }
        });
        cbBaladas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbBaladas.isChecked())
                    Toast.makeText(PerfilViajanteActivity.this, "Vida Noturna", Toast.LENGTH_SHORT).show();
            }
        });
        cbTrabalho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbTrabalho.isChecked())
                    Toast.makeText(PerfilViajanteActivity.this, "Trabalho", Toast.LENGTH_SHORT).show();
            }
        });
        cbAmanteNatureza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbAmanteNatureza.isChecked())
                    Toast.makeText(PerfilViajanteActivity.this, "Família e Aventura", Toast.LENGTH_SHORT).show();
            }
        });

        cbPazTranquilidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbPazTranquilidade.isChecked())
                    Toast.makeText(PerfilViajanteActivity.this, "Família, Cultural e Paisagem", Toast.LENGTH_SHORT).show();
            }
        });
        cbMochileiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbMochileiro.isChecked())
                    Toast.makeText(PerfilViajanteActivity.this, "Aventura", Toast.LENGTH_SHORT).show();
            }
        });
        cbEcoturista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbEcoturista.isChecked())
                    Toast.makeText(PerfilViajanteActivity.this, "Paisagem", Toast.LENGTH_SHORT).show();
            }
        });
        cbDivertEcono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbDivertEcono.isChecked())
                    Toast.makeText(PerfilViajanteActivity.this, "Cultural", Toast.LENGTH_SHORT).show();
            }
        });
        cbArteHistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbArteHistoria.isChecked())
                    Toast.makeText(PerfilViajanteActivity.this, "Cultural", Toast.LENGTH_SHORT).show();
            }
        });
        cbFamilia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbFamilia.isChecked())
                    Toast.makeText(PerfilViajanteActivity.this, "Família e Paisagem", Toast.LENGTH_SHORT).show();
            }
        });
        cbCriancas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbCriancas.isChecked())
                    Toast.makeText(PerfilViajanteActivity.this, "Família e Cultural", Toast.LENGTH_SHORT).show();
            }
        });
        cbEsportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbEsportes.isChecked())
                    Toast.makeText(PerfilViajanteActivity.this, "Aventura", Toast.LENGTH_SHORT).show();
            }
        });
        cbNovasCulturas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbNovasCulturas.isChecked())
                    Toast.makeText(PerfilViajanteActivity.this, "Cultural e Aventura", Toast.LENGTH_SHORT).show();
            }
        });
        cbCompras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbCompras.isChecked())
                    Toast.makeText(PerfilViajanteActivity.this, "Compras", Toast.LENGTH_SHORT).show();
            }
        });
        cb50Anos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb50Anos.isChecked())
                    Toast.makeText(PerfilViajanteActivity.this, "Cultural e Família", Toast.LENGTH_SHORT).show();
            }
        });
        cbRomantico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbRomantico.isChecked())
                    Toast.makeText(PerfilViajanteActivity.this, "Compras e Cultural", Toast.LENGTH_SHORT).show();
            }
        });
        cbAventura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbAventura.isChecked())
                    Toast.makeText(PerfilViajanteActivity.this, "Aventura", Toast.LENGTH_SHORT).show();
            }
        });

        btnStart.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent ini = new Intent(getApplicationContext(), ComeceViagemActivity.class);
                        startActivity(ini);
                    }
                }
        );

    }




}
