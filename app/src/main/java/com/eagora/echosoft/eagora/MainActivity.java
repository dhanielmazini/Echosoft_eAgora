package com.eagora.echosoft.eagora;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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


public class MainActivity extends AppCompatActivity {

    LoginButton login_button;
    TextView txtStatus,reqTest;
    CallbackManager callbackManager;
    EditText insertText;
    Button testReq,testInsert,button2,btnMenu,button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        InitializeControls();
    }


    private void  InitializeControls(){
        reqTest = (TextView)findViewById(R.id.reqTest);
        testReq = (Button)findViewById(R.id.testReq );
        testInsert = (Button)findViewById(R.id.testInsert);
        insertText = (EditText)findViewById(R.id.insertText);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);

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

        btnMenu.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent menu = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(menu);
                    }
                }
        );



    }
    protected void onActivityResult(int requestCode, int result, Intent data){
        callbackManager.onActivityResult(requestCode, result, data);
    }

}
