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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eagora.echosoft.eagora.Maps.Coordenada;
import com.eagora.echosoft.eagora.Maps.ListPlaceActivity;
import com.eagora.echosoft.eagora.Usuario.Usuario;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import im.delight.android.location.SimpleLocation;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView getn, text_mail;
    private Button btnMaps;
    private NavigationView navigationView;
    private DatabaseReference mDatabase;
    private SimpleLocation location;
    ProfilePictureView image;

    ImageView img, foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        img = (ImageView)findViewById(R.id.imageView6);
        btnMaps = (Button) findViewById(R.id.btnMaps);
        btnMaps.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), ListPlaceActivity.class);
                startActivity(intent);
            }
        });

        String[] permissoes = new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                                           android.Manifest.permission.ACCESS_COARSE_LOCATION};
        PermissionUtils.validate(this, 0, permissoes);

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
        image = (ProfilePictureView)header.findViewById(R.id.image);
        foto = (ImageView)header.findViewById(R.id.foto);


        //implementação do fragment de AutoComplete, salvar coordenadas do local desejado e inicio
        //da activity de locais proximos
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

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

                    getn.setText(GlobalAccess.nomeUsuario);
                    text_mail.setText(GlobalAccess.emailUsuario);
                    if(Profile.getCurrentProfile()==null){
                        foto.setImageURI(GlobalAccess.userfoto);
                    }
                    if(Profile.getCurrentProfile()!= null) {
                        image.setProfileId(Profile.getCurrentProfile().getId());
                        foto.setVisibility(View.INVISIBLE);
                    }

                }}

            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //pegando localização
        location = new SimpleLocation(this, true);
        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this);
        }
        //location.beginUpdates();
        GlobalAccess.coordenadaUsuario = new Coordenada(location.getLatitude(), location.getLongitude());

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
            Intent intentcriarroteiro = new Intent(getApplicationContext(), CriarRoteiroActivity.class);
            startActivity(intentcriarroteiro);
            finish();
        } else if (id == R.id.nav_meus_roteiros) {
            //Depois criar uma tela para procurar todos os tipos de eventos
            Intent intentmeuseventos = new Intent(getApplicationContext(), MeusRoteirosActivity.class);
            startActivity(intentmeuseventos);
            finish();
        } else if (id == R.id.nav_procurar_eventos) {
                Coordenada c = GlobalAccess.coordenadaLocalViagem;
                if(c!=null){
                    Intent intentEventos = new Intent(getApplicationContext(), EventosRoteiroActivity.class);
                    startActivity(intentEventos);
                    finish();
                }
                else{
                    Toast.makeText(MenuActivity.this, "Por favor selecione um destino",
                            Toast.LENGTH_SHORT).show();
                }

        } else if (id == R.id.nav_procurar_pontos) {
            Intent intentPontosTur = new Intent(getApplicationContext(), PontosTuristicosActivity.class);
            startActivity(intentPontosTur);
            finish();
        } else if (id == R.id.nav_procurar_bares) {
            Intent intentbarRest = new Intent(getApplicationContext(), EstabelecimentosActivity.class);
            startActivity(intentbarRest);
            finish();
        } else if (id == R.id.nav_editar_cadastro) {
            if(Profile.getCurrentProfile()!=null){
                Intent contafb = new Intent(getApplicationContext(),EditCadastrofbActivity.class);
                startActivity(contafb);
            }
            else{
                Intent conta = new Intent(getApplicationContext(),CadastroEditarActivity.class);
                startActivity(conta);
            }
            finish();
        }
        else if (id == R.id.nav_sair) {
            Toast.makeText(MenuActivity.this, "Usuário deslogado.",
                    Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            Intent login = new Intent(getApplicationContext(), InicioActivity.class);
            startActivity(login);
            finish();
        }
        else if (id == R.id.nav_blank) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        location.endUpdates();
    }

}
