package com.eagora.echosoft.eagora;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eagora.echosoft.eagora.Facebook.AcessoGraphFacebook;
import com.eagora.echosoft.eagora.Maps.MapsActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.Arrays;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtStatus,reqTest;
    EditText insertText;
    Button testReq,testInsert,btnMaps, tagbtn,btnDefinirRoteiro, btnLogout,btnEventos, update;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        InitializeControls();
    }

    private void  InitializeControls(){

        reqTest = (TextView)findViewById(R.id.reqTest);
        testReq = (Button)findViewById(R.id.testReq);
        testInsert = (Button)findViewById
(R.id.testInsert);
        insertText = (EditText)findViewById(R.id.insertText);
        btnMaps = (Button)findViewById(R.id.btnMaps);
        btnLogout = (Button)findViewById(R.id.btnLogout);
        tagbtn = (Button)findViewById(R.id.tagbtn);
        btnDefinirRoteiro = (Button)findViewById(R.id.btnDefinirRoteiro);
        btnEventos = (Button)findViewById(R.id.btnEventos);
        update = (Button)findViewById(R.id.update);

        txtStatus = (TextView)findViewById(R.id.txtStatus);

        testReq.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           FirebaseDatabase database = FirebaseDatabase.getInstance();
                                           DatabaseReference myRef = database.getReference("message");
                                           myRef.addValueEventListener(new ValueEventListener() {

                                               @Override
                                               public void onDataChange(DataSnapshot dataSnapshot) {
                                                   String value = dataSnapshot.getValue(String.class);
                                                   reqTest.setText(value);
                                               }

                                               @Override
                                               public void onCancelled(DatabaseError databaseError) {
                                                   reqTest.setText("Fail");
                                               }
                                           });
                                       }
                                   }
        );

        testInsert.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              FirebaseDatabase database = FirebaseDatabase.getInstance();
                                              DatabaseReference myRef = database.getReference("message");
                                              String messageSending = insertText.getText().toString();
                                              myRef.setValue(messageSending);
                                          }
                                      }

        );

        btnMaps.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent maps = new Intent(getApplicationContext(), MapsActivity.class);
                        startActivity(maps);
                    }
                }
        );

        tagbtn.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent intentPerfil = new Intent(getApplicationContext(), PerfilViajanteActivity.class);
                        startActivity(intentPerfil);
                    }
                }
        );

        btnLogout.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Toast.makeText(MenuActivity.this, "Usuário deslogado.",
                                Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(login);
                    }
                }
        );

        btnLogout.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Toast.makeText(MenuActivity.this, "Usuário deslogado.",
                                Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(login);
                    }
                }
        );
        btnDefinirRoteiro.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent intentDefinirRoteiro = new Intent(getApplicationContext(), DefinirRoteiroActivity.class);
                        startActivity(intentDefinirRoteiro);
                    }
                }
        );
        update.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent conta = new Intent(getApplicationContext(),CadastroEditarActivity.class);
                        startActivity(conta);
                    }
                }
        );
        btnEventos.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent intentEventos = new Intent(getApplicationContext(), TelaDeRedirecionamentoMainActivity.class);
                        startActivity(intentEventos);
                    }
                }
        );

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager frag = getSupportFragmentManager();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
