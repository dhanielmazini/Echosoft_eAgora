package com.eagora.echosoft.eagora;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.Arrays;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    LoginButton login_button;
    TextView txtStatus,reqTest, getn;
    CallbackManager callbackManager;
    EditText insertText;
    Button testReq,testInsert,button2;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
        InitializeControls();
    }

    private void  InitializeControls(){
        reqTest = (TextView)findViewById(R.id.reqTest);
        testReq = (Button)findViewById(R.id.testReq);
        testInsert = (Button)findViewById(R.id.testInsert);
        insertText = (EditText)findViewById(R.id.insertText);
        button2 = (Button)findViewById(R.id.button2);

        txtStatus = (TextView)findViewById(R.id.txtStatus);
        login_button = (LoginButton)findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        login_button.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                login_button.setReadPermissions("email,public_profile");
                txtStatus.setText("Login sucess! \n" + "User ID: " +
                        loginResult.getAccessToken().getUserId()
                        + "\n" + "Last Refresh:\n " + loginResult.getAccessToken().getLastRefresh() + "\n");

                if (Profile.getCurrentProfile() != null) {
                    txtStatus.setText("Login sucess! \n" + "User ID: " +
                            loginResult.getAccessToken().getUserId()
                            + "\n" + "Last Refresh:\n " + loginResult.getAccessToken().getLastRefresh() + "\n" +
                            "nome: \n" + Profile.getCurrentProfile().getName());
                    View header= navigationView.getHeaderView(0);
/*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
                    getn = (TextView)header.findViewById(R.id.getn);
                    getn.setText(Profile.getCurrentProfile().getName());
                }
                GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject me, GraphResponse response) {
                                if (response.getError() != null) {
                                    // handle error
                                } else {
                                    String email = me.optString("email");
                                    String id = me.optString("id");
                                    String name = me.optString("name");
                                    Toast.makeText(getApplicationContext(),

                                            email + " / " + name,

                                            Toast.LENGTH_LONG).show();
                                    // send email and id to your web server
                                }
                            }
                        }).executeAsync();

            }
            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
                e.printStackTrace();
            }
        });

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

        button2.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent maps = new Intent(getApplicationContext(), MapsActivity.class);
                        startActivity(maps);
                    }
                }
        );


    }

    protected void onActivityResult(int requestCode, int result, Intent data){
        callbackManager.onActivityResult(requestCode, result, data);
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