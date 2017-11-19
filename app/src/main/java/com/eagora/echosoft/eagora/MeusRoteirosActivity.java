package com.eagora.echosoft.eagora;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eagora.echosoft.eagora.Facebook.RoteiroFacebook;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MeusRoteirosActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    List<String> listRoteiros = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_roteiros);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mostrarRoteiros();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_add_circle_black_24dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rotsel = new Intent(getApplicationContext(), CriarRoteiroActivity.class);
                rotsel.putExtra("NUM_ROTEIRO",listRoteiros.size());
                startActivity(rotsel);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void mostrarRoteiros() {
        mDatabase = FirebaseDatabase.getInstance().getReference("usuarios").child(user.getUid())
                .child("roteiros");

        mDatabase.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dados : dataSnapshot.getChildren()) {
                        String nRoteiro = dados.getKey();
                        listRoteiros.add(nRoteiro);
                    }
                    if(listRoteiros.size() != 0){
                        preencheRoteiros();
                    }else{
                        nenhumRoteiro();
                    }
                }
            }
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    public void preencheRoteiros(){
        LinearLayout ll = (LinearLayout)findViewById(R.id.linearRoteiros);
        for(int i=0;i<listRoteiros.size();i++){
            final TextView txtRot = new TextView(this);
            txtRot.setId(Integer.parseInt(listRoteiros.get(i)));
            txtRot.setText("Roteiro " + listRoteiros.get(i));

            txtRot.setClickable(true);
            final int finalI = i;
            txtRot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent rotsel = new Intent(getApplicationContext(), CriarRoteiroActivity.class);
                    rotsel.putExtra("NUM_ROTEIRO",txtRot.getId());
                    startActivity(rotsel);
                }
            });

            ll.addView(txtRot);
            txtRot.setTypeface(null, Typeface.BOLD);
            txtRot.setTextColor(Color.BLACK);
            txtRot.setTextSize(16);
        }
    }

    public void nenhumRoteiro(){
        LinearLayout ll = (LinearLayout)findViewById(R.id.linearRoteiros);
        TextView txtNone = new TextView(this);
        txtNone.setText("NAO TENHO ROTEIROS");

        ll.addView(txtNone);
        txtNone.setTypeface(null, Typeface.BOLD);
        txtNone.setTextColor(Color.BLACK);
        txtNone.setTextSize(16);
    }

}
