package com.eagora.echosoft.eagora;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eagora.echosoft.eagora.Maps.ListPlaceActivity;
import com.eagora.echosoft.eagora.Maps.Coordenada;
import com.eagora.echosoft.eagora.Usuario.Usuario;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView getn, text_mail;
    private Button btnMaps;
    private NavigationView navigationView;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnMaps = (Button) findViewById(R.id.btnMaps);
        btnMaps.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), ListPlaceActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FacebookSdk.sdkInitialize(getApplicationContext());

        View header= navigationView.getHeaderView(0);
        getn = (TextView)header.findViewById(R.id.getn);
        text_mail = (TextView)header.findViewById(R.id.text_mail);


        //Definir nome e perfil do usuário como variável global
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("usuarios").child(user.getUid());
        mDatabase.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Usuario usu = dataSnapshot.getValue(Usuario.class);

                    GlobalAccess.nomeUsuario = usu.getNome();
                    GlobalAccess.emailUsuario = usu.getEmail();
                    GlobalAccess.perfilUsuario = usu.getPerfil();
                    //Coordenada Fake
                    Coordenada c = new Coordenada(-23.1920446,-45.8950326);
                    GlobalAccess.coordenadaUsuario =c;

                    getn.setText(GlobalAccess.nomeUsuario);
                    text_mail.setText(GlobalAccess.emailUsuario);
                }
            }
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    protected void onActivityResult(int requestCode, int result, Intent data){

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_criar_roteiro) {
            // Handle the camera action
        } else if (id == R.id.nav_meus_roteiros) {

        } else if (id == R.id.nav_procurar_eventos) {
            //Depois criar uma tela para procurar todos os tipos de eventos
            Intent intentEventos = new Intent(getApplicationContext(), EventosRoteiroActivity.class);
            startActivity(intentEventos);
        } else if (id == R.id.nav_procurar_pontos) {
            Intent intentPontosTur = new Intent(getApplicationContext(), PontosTuristicosActivity.class);
            startActivity(intentPontosTur);

        } else if (id == R.id.nav_procurar_bares) {
            Intent intentbarRest = new Intent(getApplicationContext(), EstabelecimentosActivity.class);
            startActivity(intentbarRest);

        } else if (id == R.id.nav_editar_cadastro) {
            Intent conta = new Intent(getApplicationContext(),CadastroEditarActivity.class);
            startActivity(conta);
        }
        else if (id == R.id.nav_sair) {
            Toast.makeText(MenuActivity.this, "Usuário deslogado.",
                    Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(login);
        }
        else if (id == R.id.nav_blank) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
