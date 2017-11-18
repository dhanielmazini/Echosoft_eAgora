package com.eagora.echosoft.eagora;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.eagora.echosoft.eagora.Facebook.AcessoGraphFacebook;
import com.eagora.echosoft.eagora.Facebook.RoteiroFacebook;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetalhesEventoActivity extends AppCompatActivity {

    TextView txtNomeEvento,txtLocal,txtHorario,txtEndereco;
    ImageView imgEvento;
    Button btnEscolherEvento;
    private DatabaseReference mDatabase,mDatabase2;
    String idEvento,nomeEvento,dataEvento,horarioEvento,localEvento,
            enderecoEvento,cepEvento,cidadeEvento,estadoEvento;
    long ident=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_evento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        InitializeControls();
    }

    private void InitializeControls(){






        txtNomeEvento = (TextView)findViewById(R.id.txtNomeEvento);
        txtLocal = (TextView)findViewById(R.id.txtLocal);
        txtHorario = (TextView)findViewById(R.id.txtHorario);
        txtEndereco = (TextView)findViewById(R.id.txtEndereco);
        imgEvento = (ImageView)findViewById(R.id.imgEvento);
        btnEscolherEvento = (Button)findViewById(R.id.btnEscolherEvento);

        AcessoGraphFacebook acesso = new AcessoGraphFacebook();
        LinearLayout linear = (LinearLayout)findViewById(R.id.linearDetalhesEvento);

        Intent it = getIntent();
        idEvento = it.getStringExtra("idEvento");


        JSONObject dados = acesso.detalhesEvento(idEvento);

        int i=0;
        try {


            //Nome do Evento
            txtNomeEvento.setText(dados.get("name").toString());
            nomeEvento = dados.get("name").toString();

            //Converter Data - JSON
            String data = dados.get("start_time").toString().substring(0,10);
            SimpleDateFormat formatoJSON = new SimpleDateFormat("yyyy-MM-dd");
            Date dataJSON = formatoJSON.parse(data);
            dataEvento = dataJSON.toString();
            SimpleDateFormat formatoDesejado = new SimpleDateFormat("EEE, d MMM");

            //Data e Horário
            String horario = dados.get("start_time").toString().substring(11,16);
            txtHorario.setText(formatoDesejado.format(dataJSON).toString() + " às " + horario );
            dataEvento = formatoDesejado.format(dataJSON).toString();
            horarioEvento = horario;


            try {
                //Cover (capa) da imagem do evento do Facebook
                JSONObject cover = dados.getJSONObject("cover");
                AQuery aq = new AQuery(this);
                aq.id(imgEvento).image(cover.get("source").toString());
            }catch(Exception e){
                e.printStackTrace();
            }

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

            String street,zip,city,state;
            try{
                street = localizacao.get("street").toString();
            }catch (Exception e){
                street="";
            }
            try{
                zip = localizacao.get("zip").toString();
            }catch (Exception e){
                zip="";
            }
            try{
                city = localizacao.get("city").toString();
            }catch (Exception e){
                city="";
            }
            try{
                state = localizacao.get("state").toString();
            }catch (Exception e){
                state="";
            }


            txtEndereco.setText("(" + street + ", " +
                    zip + "\n" +
                    city + ", " + state + ")"+ "\n\n\n\n" + "");

            localEvento = local.get("name").toString();
            enderecoEvento = street;
            cepEvento = zip;
            cidadeEvento = city;
            estadoEvento = state;


            txtNomeEvento.setTypeface(null, Typeface.BOLD);
            txtNomeEvento.setTextColor(Color.BLACK);
            txtNomeEvento.setTextSize(16);

            imgEvento.setMaxWidth(259);

            txtHorario.setAllCaps(true);
            txtHorario.setTypeface(null, Typeface.BOLD);
            txtHorario.setTextColor(Color.RED);
            txtHorario.setTextSize(14);

            txtLocal.setTypeface(null, Typeface.BOLD);
            txtLocal.setTextColor(Color.BLACK);
            txtLocal.setTextSize(13);

            txtEndereco.setTextColor(Color.BLACK);
            txtEndereco.setTextSize(13);
        }
        catch(Exception e){
            e.printStackTrace();
        }


        btnEscolherEvento.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        RoteiroFacebook roteiroFb = new RoteiroFacebook();
                        roteiroFb.setId(idEvento);
                        roteiroFb.setNome(nomeEvento);
                        roteiroFb.setData(dataEvento);
                        roteiroFb.setHorario(horarioEvento);
                        roteiroFb.setLocal(localEvento);
                        roteiroFb.setEndereco(enderecoEvento);
                        roteiroFb.setCep(cepEvento);
                        roteiroFb.setCidade(cidadeEvento);
                        roteiroFb.setEstado(estadoEvento);


                        //Olha aqui Tom
                        GlobalAccess.idRoteiro=0;
                        String testeidRoteiro = String.valueOf(GlobalAccess.idRoteiro);

                        mDatabase = FirebaseDatabase.getInstance().getReference("usuarios").child(user.getUid())
                                .child("roteiros").child(testeidRoteiro).child("facebook").child(idEvento);
                        mDatabase.setValue(roteiroFb);

                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReference();
                        String caminho = "imgEventos/" + idEvento + ".png";
                        StorageReference mountainImagesRef = storageRef.child(caminho);


                        // Get the data from an ImageView as bytes
                        imgEvento.setDrawingCacheEnabled(true);
                        imgEvento.buildDrawingCache();
                        Bitmap bitmap = imgEvento.getDrawingCache();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] data = baos.toByteArray();

                        UploadTask uploadTask = mountainImagesRef.putBytes(data);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Intent intentEvento = new Intent(getApplicationContext(), EventosRoteiroActivity.class);
                                startActivity(intentEvento);
                                finish();
                            }
                        });




                    }
                }
        );


    }



}
