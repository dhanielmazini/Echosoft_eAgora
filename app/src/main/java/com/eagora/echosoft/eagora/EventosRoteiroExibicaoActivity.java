package com.eagora.echosoft.eagora;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.bumptech.glide.Glide;
import com.eagora.echosoft.eagora.BancoDados.TipoViagemGenerico;
import com.eagora.echosoft.eagora.Facebook.RoteiroFacebook;
import com.eagora.echosoft.eagora.Maps.Coordenada;
import com.eagora.echosoft.eagora.Usuario.Usuario;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class EventosRoteiroExibicaoActivity extends AppCompatActivity {

    ImageView imgTeste;
    DatabaseReference mDatabase;
    List<RoteiroFacebook> listaRoteiros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos_roteiro_exibicao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inicializarListaRoteiros();



    }

    private void inicializarListaRoteiros(){

        listaRoteiros = new ArrayList<RoteiroFacebook>();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("usuarios").child(user.getUid())
                .child("roteiros").child("facebook");
        mDatabase.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dados : dataSnapshot.getChildren()) {
                        RoteiroFacebook roteiro = dados.getValue(RoteiroFacebook.class);
                        listaRoteiros.add(roteiro);
                    }
                }
                preencherTela();
            }
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void preencherTela(){
        LinearLayout linear = (LinearLayout)findViewById(R.id.linearEventosBanco);
        int i=0;
        for(i=0;i<listaRoteiros.size();i++){

            try {
                //Para cada resultado de evento, cria os elementos
                //Padrão +10,+20,..,+40 para os id's
                TextView txtNomeEvento = new TextView(this);
                txtNomeEvento.setId(i);
                TextView txtLocal = new TextView(this);
                txtLocal.setId(i+100);
                ImageView imgEvento = new ImageView(this);
                imgEvento.setId(i+200);
                TextView txtHorario = new TextView(this);
                txtHorario.setId(i+300);
                TextView txtEndereco = new TextView(this);
                txtEndereco.setId(i+400);



                //Nome do Evento
                txtNomeEvento.setText(listaRoteiros.get(i).getNome());

                //Converter Data - JSON
                String data = listaRoteiros.get(i).getData();

                //Data e Horário
                String horario = listaRoteiros.get(i).getHorario();
                txtHorario.setText(data + " às " + horario );


          /*      //Cover (capa) da imagem do evento do Facebook
                JSONObject cover = dados.getJSONObject("cover");
                AQuery aq=new AQuery(this);
                aq.id(imgEvento).image(cover.get("source").toString());*/

                //Cria a URL
                final String url = "https://www.facebook.com/events/" + listaRoteiros.get(i).getId() + "/";
                String caminho = "imgEventos/" + listaRoteiros.get(i).getId() + ".png";

                try {
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(caminho);
                    Glide.with(this).using(new FirebaseImageLoader()).load(storageReference).into(imgEvento);
                }catch (Exception e){
                    e.printStackTrace();
                }
                //Seta a imagemview como clicável
                imgEvento.setClickable(true);

                //Abre o link do evento
                imgEvento.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    }
                });


                //Local e Endereço
                txtLocal.setText("Local: " + listaRoteiros.get(i).getLocal());
                txtEndereco.setText("(" + listaRoteiros.get(i).getEndereco() + ", " +
                        listaRoteiros.get(i).getCep() + "\n" +
                        listaRoteiros.get(i).getCidade() + ", " + listaRoteiros.get(i).getEstado() + ")"+ "\n\n\n\n" + "");



                linear.addView(txtNomeEvento);
                txtNomeEvento.setTypeface(null, Typeface.BOLD);
                txtNomeEvento.setTextColor(Color.BLACK);
                txtNomeEvento.setTextSize(16);


                linear.addView(imgEvento);
                imgEvento.setMaxWidth(259);


                linear.addView(txtHorario);
                txtHorario.setAllCaps(true);
                txtHorario.setTypeface(null, Typeface.BOLD);
                txtHorario.setTextColor(Color.RED);
                txtHorario.setTextSize(14);

                linear.addView(txtLocal);
                txtLocal.setTypeface(null, Typeface.BOLD);
                txtLocal.setTextColor(Color.BLACK);
                txtLocal.setTextSize(13);

                linear.addView(txtEndereco);
                txtEndereco.setTextColor(Color.BLACK);
                txtEndereco.setTextSize(13);
            }
            catch(Exception e){
                e.printStackTrace();
            }


        }

    }

}
