package com.eagora.echosoft.eagora;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
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
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.eagora.echosoft.eagora.Maps.Coordenada;
import com.eagora.echosoft.eagora.Maps.ListPlaceActivity;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.Date;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class CriarRoteiroActivity extends AppCompatActivity {

    int nRoteiro;
    EditText ida,volta;
    Calendar mC = Calendar.getInstance();
    int dayOfMonth,monthOfYear,year;
    int iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_roteiro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nRoteiro = getIntent().getIntExtra("NUM_ROTEIRO",nRoteiro);
        ida = (EditText)findViewById(R.id.Ida);
        volta = (EditText)findViewById(R.id.Volta);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mC.set(Calendar.YEAR, year);
                mC.set(Calendar.MONTH, monthOfYear);
                mC.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                if(iv == 0)
                    updateLabel(ida);
                else
                    updateLabel(volta);
            }
        };

        ida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv = 0;
                mC.setTimeInMillis(Calendar.getInstance().getTimeInMillis());
                new DatePickerDialog(CriarRoteiroActivity.this, date, mC.get(Calendar.YEAR),
                        mC.get(Calendar.MONTH),
                        mC.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        volta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv = 1;
                mC.setTimeInMillis(Calendar.getInstance().getTimeInMillis());
                new DatePickerDialog(CriarRoteiroActivity.this, date, mC.get(Calendar.YEAR),
                        mC.get(Calendar.MONTH),
                        mC.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_add_circle_black_24dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                PopupMenu decision = new PopupMenu(CriarRoteiroActivity.this,fab);

                decision.getMenuInflater().inflate(R.menu.popup_menu, decision.getMenu());

                decision.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(
                                CriarRoteiroActivity.this,
                                "You Clicked : " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();
                        return true;
                    }
                });

                decision.show();
            }
        });

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.local_viagem);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: obter informações sobre o local selecionado.
                GlobalAccess.coordenadaLocalViagem = new Coordenada(place.getLatLng().latitude, place.getLatLng().longitude);
                Intent locaisProximos = new Intent(getApplicationContext(), ListPlaceActivity.class);
                startActivity(locaisProximos);
                Log.i("logX", "Place: " + place.getName());
                GlobalAccess.listaLugares.clear();
                GlobalAccess.listaEventos.clear();
            }

            @Override
            public void onError(Status status) {
                // TODO: Solucionar o erro.
                Log.i("logX", "Ocorreu um erro: " + status);
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void updateLabel( EditText txt ) {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());

        txt.setText( new SimpleDateFormat("dd/MM/yy").format(mC.getTime()) );
    }

}
