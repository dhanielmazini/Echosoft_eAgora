package com.eagora.echosoft.eagora;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PerfilViajanteActivity extends AppCompatActivity {

    private List<String> perfil = new ArrayList<String>();
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_viajante);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Perfil");
        setSupportActionBar(toolbar);

        mDatabase = FirebaseDatabase.getInstance().getReference();


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
                if (cbVidaNoturna.isChecked()) {
                    if (!perfil.contains(cbVidaNoturna.getText().toString())) {
                        perfil.add(cbVidaNoturna.getText().toString());
                    }
                }
                if (!cbVidaNoturna.isChecked()){
                    if (perfil.contains(cbVidaNoturna.getText().toString()))
                        perfil.remove(cbVidaNoturna.getText().toString());
                }
            }
        });

        cbGourmet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbGourmet.isChecked()){
                    Toast.makeText(PerfilViajanteActivity.this, "Vida Noturna e Gastronômico", Toast.LENGTH_SHORT).show();
                    if (!perfil.contains(cbGourmet.getText().toString())) {
                        perfil.add(cbGourmet.getText().toString());
                    }
                }
                if (!cbGourmet.isChecked()){
                    if (perfil.contains(cbGourmet.getText().toString()))
                        perfil.remove(cbGourmet.getText().toString());
                }
            }
        });
        cbBaladas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbBaladas.isChecked()) {
                    if (!perfil.contains(cbBaladas.getText().toString())) {
                        perfil.add(cbBaladas.getText().toString());
                    }
                }
                if (!cbBaladas.isChecked()){
                    if (perfil.contains(cbBaladas.getText().toString()))
                        perfil.remove(cbBaladas.getText().toString());
                }
            }
        });
        cbTrabalho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbTrabalho.isChecked()) {
                    if (!perfil.contains(cbTrabalho.getText().toString())) {
                        perfil.add(cbTrabalho.getText().toString());
                    }
                }
                if (!cbTrabalho.isChecked()){
                    if (perfil.contains(cbTrabalho.getText().toString()))
                        perfil.remove(cbTrabalho.getText().toString());
                }
            }
        });
        cbAmanteNatureza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbAmanteNatureza.isChecked()) {
                    if (!perfil.contains(cbAmanteNatureza.getText().toString())) {
                        perfil.add(cbAmanteNatureza.getText().toString());
                    }
                }
                if (!cbAmanteNatureza.isChecked()){
                    if (perfil.contains(cbAmanteNatureza.getText().toString()))
                        perfil.remove(cbAmanteNatureza.getText().toString());
                }
            }
        });

        cbPazTranquilidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbPazTranquilidade.isChecked()) {
                    if (!perfil.contains(cbPazTranquilidade.getText().toString())) {
                        perfil.add(cbPazTranquilidade.getText().toString());
                    }
                }
                if (!cbPazTranquilidade.isChecked()){
                    if (perfil.contains(cbPazTranquilidade.getText().toString()))
                        perfil.remove(cbPazTranquilidade.getText().toString());
                }
            }
        });
        cbMochileiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbMochileiro.isChecked()) {
                    if (!perfil.contains(cbMochileiro.getText().toString())) {
                        perfil.add(cbMochileiro.getText().toString());
                    }
                }
                if (!cbMochileiro.isChecked()){
                    if (perfil.contains(cbMochileiro.getText().toString()))
                        perfil.remove(cbMochileiro.getText().toString());
                }
            }
        });
        cbEcoturista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbEcoturista.isChecked()) {
                    if (!perfil.contains(cbEcoturista.getText().toString())) {
                        perfil.add(cbEcoturista.getText().toString());
                    }
                }
                if (!cbEcoturista.isChecked()){
                    if (perfil.contains(cbEcoturista.getText().toString()))
                        perfil.remove(cbEcoturista.getText().toString());
                }
            }
        });
        cbDivertEcono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbDivertEcono.isChecked()) {
                    if (!perfil.contains(cbDivertEcono.getText().toString())) {
                        perfil.add(cbDivertEcono.getText().toString());
                    }
                }
                if (!cbDivertEcono.isChecked()){
                    if (perfil.contains(cbDivertEcono.getText().toString()))
                        perfil.remove(cbDivertEcono.getText().toString());
                }
            }
        });
        cbArteHistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbArteHistoria.isChecked()) {
                    if (!perfil.contains(cbArteHistoria.getText().toString())) {
                        perfil.add(cbArteHistoria.getText().toString());
                    }
                }
                if (!cbArteHistoria.isChecked()){
                    if (perfil.contains(cbArteHistoria.getText().toString()))
                        perfil.remove(cbArteHistoria.getText().toString());
                }
            }
        });
        cbFamilia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbFamilia.isChecked()) {
                    if (!perfil.contains(cbFamilia.getText().toString())) {
                        perfil.add(cbFamilia.getText().toString());
                    }
                }
                if (!cbFamilia.isChecked()){
                    if (perfil.contains(cbFamilia.getText().toString()))
                        perfil.remove(cbFamilia.getText().toString());
                }
            }
        });
        cbCriancas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbCriancas.isChecked()) {
                    if (!perfil.contains(cbCriancas.getText().toString())) {
                        perfil.add(cbCriancas.getText().toString());
                    }
                }
                if (!cbCriancas.isChecked()){
                    if (perfil.contains(cbCriancas.getText().toString()))
                        perfil.remove(cbCriancas.getText().toString());
                }
            }
        });
        cbEsportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbEsportes.isChecked()) {
                    if (!perfil.contains(cbEsportes.getText().toString())) {
                        perfil.add(cbEsportes.getText().toString());
                    }
                }
                if (!cbEsportes.isChecked()){
                    if (perfil.contains(cbEsportes.getText().toString()))
                        perfil.remove(cbEsportes.getText().toString());
                }
            }
        });
        cbNovasCulturas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbNovasCulturas.isChecked()) {
                    if (!perfil.contains(cbNovasCulturas.getText().toString())) {
                        perfil.add(cbNovasCulturas.getText().toString());
                    }
                }
                if (!cbNovasCulturas.isChecked()){
                    if (perfil.contains(cbNovasCulturas.getText().toString()))
                        perfil.remove(cbNovasCulturas.getText().toString());
                }
            }
        });
        cbCompras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbCompras.isChecked()) {
                    if (!perfil.contains(cbCompras.getText().toString())) {
                        perfil.add(cbCompras.getText().toString());
                    }
                }
                if (!cbCompras.isChecked()){
                    if (perfil.contains(cbCompras.getText().toString()))
                        perfil.remove(cbCompras.getText().toString());
                }
            }
        });
        cb50Anos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb50Anos.isChecked()) {
                    if (!perfil.contains(cb50Anos.getText().toString())) {
                        perfil.add(cb50Anos.getText().toString());
                    }
                }
                if (!cb50Anos.isChecked()){
                    if (perfil.contains(cb50Anos.getText().toString()))
                        perfil.remove(cb50Anos.getText().toString());
                }
            }
        });
        cbRomantico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbRomantico.isChecked()) {
                    if (!perfil.contains(cbRomantico.getText().toString())) {
                        perfil.add(cbRomantico.getText().toString());
                    }
                }
                if (!cbRomantico.isChecked()){
                    if (perfil.contains(cbRomantico.getText().toString()))
                        perfil.remove(cbRomantico.getText().toString());
                }
            }
        });
        cbAventura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbAventura.isChecked()) {
                    if (!perfil.contains(cbAventura.getText().toString())) {
                        perfil.add(cbAventura.getText().toString());
                    }
                }
                if (!cbAventura.isChecked()){
                    if (perfil.contains(cbAventura.getText().toString()))
                        perfil.remove(cbAventura.getText().toString());
                }
            }
        });

        btnStart.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        List<String> finalperf = new ArrayList<String>();
                        if (perfil.contains("Gosto da vida noturna") || perfil.contains("Gourmet") || perfil.contains("Baladas e Clubes") )
                            finalperf.add("Vida Noturna");
                        if (perfil.contains("Gourmet"))
                            finalperf.add("Gastronômicas");
                        if (perfil.contains("Sou amante da natureza") || perfil.contains("Mochileiro") || perfil.contains("Praticante de esportes") || perfil.contains("Gosto de conhecer novas culturas") || perfil.contains("Busco aventura"))
                            finalperf.add("Aventura");
                        if (perfil.contains("Busco paz e tranquilidade") || perfil.contains("Busco divertimento e economia") || perfil.contains("Gosto de arte e história") || perfil.contains("Viajante com crianças") || perfil.contains("Gosto de conhecer novas culturas") || perfil.contains("Tenho mais de 50 anos") || perfil.contains("Romântico"))
                            finalperf.add("Cultural");
                        if (perfil.contains("Sou amante da natureza") || perfil.contains("Busco paz e tranqulidade") || perfil.contains("Viajante com a família") || perfil.contains("Viajante com crianças") || perfil.contains("Tenho mais de 50 anos"))
                            finalperf.add("Família");
                        if (perfil.contains("Busco paz e tranqulidade") || perfil.contains("Ecoturista") || perfil.contains("Viajante com a família") )
                            finalperf.add("Paisagem");
                        if (perfil.contains("Busco fazer compras") || perfil.contains("Romântico"))
                            finalperf.add("Compras");
                        if (perfil.contains("Viajando a trabalho"))
                            finalperf.add("Trabalho");

                        mDatabase.child("usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("perfil").setValue(finalperf);
                        Intent ini = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(ini);
                    }
                }
        );

    }




}
