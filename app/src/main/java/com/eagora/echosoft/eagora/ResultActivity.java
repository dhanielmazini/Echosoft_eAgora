package com.eagora.echosoft.eagora;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.MonthDisplayHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.bumptech.glide.Glide;
import com.eagora.echosoft.eagora.Facebook.RoteiroFacebook;
import com.eagora.echosoft.eagora.Maps.Coordenada;
import com.eagora.echosoft.eagora.Maps.ListPlaceActivity;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    int nRoteiro;
    Button editButton;
    DatabaseReference mDatabase;
    boolean isold;
    List<String> listSelecionados = new ArrayList<String>();
    List<RoteiroFacebook> listRot = new ArrayList<RoteiroFacebook>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        isold = getIntent().getBooleanExtra("IS_OLD",isold);
        editButton = (Button)findViewById(R.id.btnEditRot);

        nRoteiro = getIntent().getIntExtra("NUM_ROTEIRO",nRoteiro);

        GlobalAccess.idRoteiro = nRoteiro;

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent RotEdition = new Intent(getApplicationContext(), CriarRoteiroActivity.class);
                RotEdition.putExtra("ISOLD", isold);
                RotEdition.putExtra("NUM_ROTEIRO", nRoteiro);
                startActivity(RotEdition);
            }
        });

        listSelected();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void listSelected() {
        mDatabase = FirebaseDatabase.getInstance().getReference("usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("roteiros").child(String.valueOf(nRoteiro));

        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance().getReference("usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("roteiros");
            mDatabase.setValue(String.valueOf(nRoteiro));
        }

        mDatabase = FirebaseDatabase.getInstance().getReference("usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("roteiros").child(String.valueOf(nRoteiro));

        System.out.println(String.valueOf(nRoteiro));

        mDatabase.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dados : dataSnapshot.getChildren()) {
                        String nSelec = dados.getKey();
                        listSelecionados.add(nSelec);
                    }

                    updateUI();
                }
            }
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void updateUI() {
        LinearLayout ll = (LinearLayout)findViewById(R.id.showFull);
        if(listSelecionados.size() == 0){
            TextView txt = new TextView(this);
            txt.setText("Ainda não selecionou nenhum evento/local...");
            ll.addView(txt);
        } else {
            for(int i=0;i<listSelecionados.size();i++)
                insert(listSelecionados.get(i));
        }
    }

    private void insert(final String s) {
        mDatabase = FirebaseDatabase.getInstance().getReference("usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("roteiros").child(String.valueOf(nRoteiro)).child(s);

        mDatabase.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dados : dataSnapshot.getChildren()) {
                        RoteiroFacebook nSelec = dados.getValue(RoteiroFacebook.class);
                        listRot.add(nSelec);
                    }

//                    if (s.equals("Facebook"))
                    iface();

                }
            }
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void iface() {
        LinearLayout linear = (LinearLayout)findViewById(R.id.showFull);
        for(int i=0; i<listRot.size();i++){
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

            txtNomeEvento.setText(listRot.get(i).getNome());

            txtLocal.setText("Local: " + listRot.get(i).getLocal());
            txtEndereco.setText("(" + listRot.get(i).getEndereco() + ", " +
                    listRot.get(i).getCep() + "\n" +
                    listRot.get(i).getCidade() + ", " + listRot.get(i).getEstado() + ")"+ "\n\n\n\n" + "");

            final String url = "https://www.facebook.com/events/" + listRot.get(i).getId() + "/";
            String caminho = "imgEventos/" + listRot.get(i).getId() + ".png";


            AQuery aq = new AQuery(this);
            aq.id(imgEvento).image(listRot.get(i).getImagem());

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


            /*try {
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(caminho);
                Glide.with(this).using(new FirebaseImageLoader()).load(storageReference).into(imgEvento);
            }catch (Exception e){
                e.printStackTrace();
            }*/

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
    }

}
