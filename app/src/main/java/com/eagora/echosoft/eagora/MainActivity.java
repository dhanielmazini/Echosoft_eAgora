package com.eagora.echosoft.eagora;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONObject;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    LoginButton login_button;
    TextView txtStatus;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        InitializeControls();
    }


    private void  InitializeControls(){
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
    }
    protected void onActivityResult(int requestCode, int result, Intent data){
        callbackManager.onActivityResult(requestCode, result, data);
    }
}
