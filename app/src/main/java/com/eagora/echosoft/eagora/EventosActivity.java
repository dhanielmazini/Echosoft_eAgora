package com.eagora.echosoft.eagora;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.eagora.echosoft.eagora.Facebook.AcessoGraphFacebook;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EventosActivity extends AppCompatActivity {

    Button btnBuscarEventos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        InitializeControls();

    }

    private void  InitializeControls(){

        btnBuscarEventos = (Button)findViewById(R.id.btnBuscarEventos);
        btnBuscarEventos.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        eventosProximos();
                    }
                }
        );
    }


    private void eventosProximos(){
        AcessoGraphFacebook acesso = new AcessoGraphFacebook();
        double latitude = -23.1994939;
        double longitude = -45.8918287;
        double raio = 1000;

        //Mapeia o LinearLayout
        LinearLayout linear = (LinearLayout)findViewById(R.id.linearEventos);

        List<JSONObject> listaEventos = acesso.procurarEventos(latitude,longitude,raio);
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
                    txtNomeEvento.setText("Evento " +  dados.get("name").toString());

                    //Converter Data - JSON
                    String data = dados.get("start_time").toString().substring(0,10);
                    SimpleDateFormat formatoJSON = new SimpleDateFormat("yyyy-MM-dd");
                    Date dataJSON = formatoJSON.parse(data);
                    SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");

                    //Data e Horário
                    String horario = dados.get("start_time").toString().substring(11,16);
                    txtHorario.setText("Data e Horário: " + formatoDesejado.format(dataJSON).toString() + " - " + horario );


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
                    txtEndereco.setText("Endereço: " + localizacao.get("street").toString() + ", " +
                            localizacao.get("zip").toString() + "\n" +
                            localizacao.get("city").toString() + ", " + localizacao.get("state").toString());

                    //Itens do layout adicionados
                    linear.addView(imgEvento);
                    linear.addView(txtNomeEvento);
                    linear.addView(txtHorario);
                    linear.addView(txtLocal);
                    linear.addView(txtEndereco);
            }
            catch(Exception e){
                e.printStackTrace();
            }

        }
    }

    private void locais(){
        //Mapeia o LinearLayout
        LinearLayout linear = (LinearLayout)findViewById(R.id.linearEventos);

        //Acessando Método para Pesquisar Eventos
        AcessoGraphFacebook acesso = new AcessoGraphFacebook();
        double latitude = -23.1994939;
        double longitude = -45.8918287;
        double raio = 500;
        GraphResponse response = acesso.locais(latitude,longitude,raio);
        try {
            //Criando um array do objeto pai "data"
            JSONArray ar = response.getJSONObject().getJSONArray("data");
            for(int i=0;i<ar.length();i++){

                //Para cada resultado de evento, cria os elementos
                //Padrão +10,+20,..,+40 para os id's
                TextView txtNomeLocal = new TextView(this);
                txtNomeLocal.setId(i);
                TextView txtCategoria = new TextView(this);
                txtCategoria.setId(i+10);

                //Objeto pai
                JSONObject dados = ar.getJSONObject(i);

                //Nome e Categoria do Local
                txtNomeLocal.setText("Local: " +  dados.get("name").toString());
                txtCategoria.setText("Categoria: " + dados.get("category").toString());


                //Itens do layout adicionados
                linear.addView(txtNomeLocal);
                linear.addView(txtCategoria);

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    private void  eventos() {

       //Mapeia o LinearLayout
        LinearLayout linear = (LinearLayout)findViewById(R.id.linearEventos);

        //Acessando Método para Pesquisar Eventos
        AcessoGraphFacebook acesso = new AcessoGraphFacebook();
        GraphResponse response = acesso.eventos("");
        try {
            //Criando um array do objeto pai "data"
            JSONArray ar = response.getJSONObject().getJSONArray("data");
            for(int i=0;i<ar.length();i++){

                //Para cada resultado de evento, cria os elementos
                //Padrão +10,+20,..,+40 para os id's
                TextView txtNomeEvento = new TextView(this);
                txtNomeEvento.setId(i);
                TextView txtLocal = new TextView(this);
                txtLocal.setId(i+10);
                ImageView imgEvento = new ImageView(this);
                imgEvento.setId(i+20);
                TextView txtHorario = new TextView(this);
                txtHorario.setId(i+30);
                TextView txtEndereco = new TextView(this);
                txtEndereco.setId(i+40);

                //Objeto pai
                JSONObject dados = ar.getJSONObject(i);

                //Nome do Evento
                txtNomeEvento.setText("Evento " +  dados.get("name").toString());

                //Converter Data - JSON
                String data = dados.get("start_time").toString().substring(0,10);
                SimpleDateFormat formatoJSON = new SimpleDateFormat("yyyy-MM-dd");
                Date dataJSON = formatoJSON.parse(data);
                SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");

                //Data e Horário
                String horario = dados.get("start_time").toString().substring(11,16);
                txtHorario.setText("Data e Horário: " + formatoDesejado.format(dataJSON).toString() + " - " + horario );


                //Cover (capa) da imagem do evento do Facebook
                JSONObject cover = dados.getJSONObject("cover");
                AQuery aq=new AQuery(this);
                aq.id(imgEvento).image(cover.get("source").toString());


                //Local e Endereço
                JSONObject local = dados.getJSONObject("place");
                txtLocal.setText("Local: " + local.get("name").toString() );
                JSONObject localizacao = local.getJSONObject("location");
                txtEndereco.setText("Endereço: " + localizacao.get("street").toString() + ", " +
                        localizacao.get("zip").toString() + "\n" +
                        localizacao.get("city").toString() + ", " + localizacao.get("state").toString());

                //Itens do layout adicionados
                linear.addView(imgEvento);
                linear.addView(txtNomeEvento);
                linear.addView(txtHorario);
                linear.addView(txtLocal);
                linear.addView(txtEndereco);


            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }



}
