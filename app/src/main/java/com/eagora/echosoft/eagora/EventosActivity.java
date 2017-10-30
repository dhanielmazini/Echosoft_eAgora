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

    Button btnBuscarEventosVidaNoturna,btnBuscarEventosGastronomico;


    List<TipoViagemGenerico> listaLugaresVidaNoturna = new ArrayList<TipoViagemGenerico>();
    List<TipoViagemGenerico> listaLugaresGastronomico = new ArrayList<TipoViagemGenerico>();

    DatabaseReference mDatabase;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        InitializeControls();
    }

    private void listarGastronomico(){
        mDatabase = FirebaseDatabase.getInstance().getReference("classificacao_tipo_local").child("Gastronômico");
        mDatabase.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dados : dataSnapshot.getChildren()) {
                        TipoViagemGenerico tv = dados.getValue(TipoViagemGenerico.class);
                        listaLugaresGastronomico.add(tv);
                    }
                    Toast.makeText(getApplicationContext(), "Quantidade de Locais Gastronomico : " + String.valueOf(listaLugaresGastronomico.size()) , Toast.LENGTH_LONG).show();
                }

            }
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void listarVidaNoturna(){
        mDatabase = FirebaseDatabase.getInstance().getReference("classificacao_tipo_local").child("Vida Noturna");
        mDatabase.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dados : dataSnapshot.getChildren()) {
                        TipoViagemGenerico tv = dados.getValue(TipoViagemGenerico.class);
                        listaLugaresVidaNoturna.add(tv);
                    }
                    Toast.makeText(getApplicationContext(), "Quantidade de Locais Vida Noturna : " + String.valueOf(listaLugaresVidaNoturna.size()) , Toast.LENGTH_LONG).show();
                }

            }
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void  InitializeControls(){
        listarVidaNoturna();
        listarGastronomico();

        btnBuscarEventosVidaNoturna = (Button)findViewById(R.id.btnBuscarEventosVidaNoturna);
        btnBuscarEventosVidaNoturna.setOnClickListener(
                new View.OnClickListener(){
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onClick(View view){
                        eventosProximos(listaLugaresVidaNoturna);
                    }
                }
        );

        btnBuscarEventosGastronomico = (Button)findViewById(R.id.btnBuscarEventosGastronomico);
        btnBuscarEventosGastronomico.setOnClickListener(
                new View.OnClickListener(){
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onClick(View view){
                        eventosProximos(listaLugaresGastronomico);
                    }
                }
        );

    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void eventosProximos(List<TipoViagemGenerico> listaLugares){


        AcessoGraphFacebook acesso = new AcessoGraphFacebook();
        double latitude = -23.1994939;
        double longitude = -45.8918287;
        double raio = 1000;

        //Mapeia o LinearLayout
        LinearLayout linear = (LinearLayout)findViewById(R.id.linearEventos);

        List<JSONObject> listaEventos = acesso.procurarEventos(latitude,longitude,raio,listaLugares);

        Toast.makeText(getApplicationContext(), "Resultados : " + String.valueOf(listaEventos.size()) , Toast.LENGTH_LONG).show();


        for(int i=0;i<listaEventos.size();i++){
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


                    //Objeto pai
                    JSONObject dados = listaEventos.get(i);

                    //Nome do Evento
                    txtNomeEvento.setText(dados.get("name").toString());

                    //Converter Data - JSON
                    String data = dados.get("start_time").toString().substring(0,10);
                    SimpleDateFormat formatoJSON = new SimpleDateFormat("yyyy-MM-dd");
                    Date dataJSON = formatoJSON.parse(data);
                    SimpleDateFormat formatoDesejado = new SimpleDateFormat("EEE, d MMM");

                    //Data e Horário
                    String horario = dados.get("start_time").toString().substring(11,16);
                    txtHorario.setText(formatoDesejado.format(dataJSON).toString() + " às " + horario );


                    //Cover (capa) da imagem do evento do Facebook
                    JSONObject cover = dados.getJSONObject("cover");
                    AQuery aq=new AQuery(this);
                    aq.id(imgEvento).image(cover.get("source").toString());

                    //Cria a URL
                    final String url = "https://www.facebook.com/events/" + dados.get("id") + "/";

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
                    JSONObject local = dados.getJSONObject("place");
                    txtLocal.setText("Local: " + local.get("name").toString() );
                    JSONObject localizacao = local.getJSONObject("location");
                    txtEndereco.setText("(" + localizacao.get("street").toString() + ", " +
                            localizacao.get("zip").toString() + "\n" +
                            localizacao.get("city").toString() + ", " + localizacao.get("state").toString() + ")"+ "\n\n\n\n" + "");



                    linear.addView(txtNomeEvento);
                    txtNomeEvento.setTypeface(null, Typeface.BOLD);
                    txtNomeEvento.setTextColor(Color.BLACK);
                    txtNomeEvento.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    txtNomeEvento.setTextSize(16);


                    linear.addView(imgEvento);
                    imgEvento.getAdjustViewBounds();
                    imgEvento.setMaxWidth(259);


                    linear.addView(txtHorario);
                    txtHorario.setAllCaps(true);
                    txtHorario.setTypeface(null, Typeface.BOLD);
                    txtHorario.setTextColor(Color.RED);
                    txtHorario.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    txtHorario.setTextSize(14);

                    linear.addView(txtLocal);
                    txtLocal.setTypeface(null, Typeface.BOLD);
                    txtLocal.setTextColor(Color.BLACK);
                    txtLocal.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    txtLocal.setTextSize(13);

                    linear.addView(txtEndereco);
                    txtEndereco.setTextColor(Color.BLACK);
                    txtEndereco.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    txtEndereco.setTextSize(13);
            }
            catch(Exception e){
                e.printStackTrace();
            }

        }
    }



}
