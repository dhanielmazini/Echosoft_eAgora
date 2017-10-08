package com.eagora.echosoft.eagora;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ComeceViagemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comece_viagem);



        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        AdapterOptions mAdapter;
        List <OpcoesDoUsuario> optUSer = new ArrayList<>();
        mAdapter= new AdapterOptions(optUSer);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(mAdapter);

        OpcoesDoUsuario option = new OpcoesDoUsuario("Eventos\n");
        optUSer.add(option);

        option = new OpcoesDoUsuario("Bares e Restaurantes");
        optUSer.add(option );

        option = new OpcoesDoUsuario("Pontos Turísticos");
        optUSer.add(option);

        option = new OpcoesDoUsuario("Outros");
        optUSer.add(option);

    }
}
