package com.eagora.echosoft.eagora;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.eagora.echosoft.eagora.BancoDados.TipoViagemGenerico;
import com.eagora.echosoft.eagora.Facebook.AcessoGraphFacebook;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class EventosExibirActivity extends AppCompatActivity {

    String tipo;
    DatabaseReference mDatabase;
    AcessoGraphFacebook acesso;
    double raio;
    List<TipoViagemGenerico> listaLugares = new ArrayList<TipoViagemGenerico>();
    List<JSONObject> listaEventos = new ArrayList<JSONObject>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos_exibir);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent it = getIntent();
        tipo = it.getStringExtra("tipo");

        acesso = new AcessoGraphFacebook();
        raio = 5000;

        listarLugares();

        SystemClock.sleep(2000);


        final ProgressBar Bar = (ProgressBar) findViewById(R.id.progressBar);


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Bar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Bar.setVisibility(View.GONE);
                eventosProximos();
            }

            @Override
            protected Void doInBackground(Void... params) {
                listaEventos = acesso.procurarEventos(GlobalAccess.coordenadaLocalViagem.getLatitude(),
                        GlobalAccess.coordenadaLocalViagem.getLongitude(), raio, listaLugares);
                return null;
            }
        }.execute();



    }


    private void listarLugares(){
        mDatabase = FirebaseDatabase.getInstance().getReference("classificacao_tipo_local").child(tipo);
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


    private void eventosProximos() {

        //Mapeia o LinearLayout
        LinearLayout linear = (LinearLayout) findViewById(R.id.linearProcuraEventos);

        for (int i = 0; i < listaEventos.size(); i++) {
            try {
                final ImageView imgEvento = new ImageView(this);
                imgEvento.setId(i + 200);

                //Objeto pai
                JSONObject dados = listaEventos.get(i);


                //Cover (capa) da imagem do evento do Facebook
                JSONObject cover = dados.getJSONObject("cover");
                AQuery aq = new AQuery(this);
                aq.id(imgEvento).image(cover.get("source").toString());

               final String idEvento = dados.get("id").toString();

                final String url = "https://www.facebook.com/events/" + idEvento + "/";

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


                linear.addView(imgEvento);
                imgEvento.setMaxWidth(259);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}
