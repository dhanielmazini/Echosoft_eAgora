package com.eagora.echosoft.eagora;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


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

        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                txtStatus.setText("Login sucess! \n" + "User ID: "+
                        loginResult.getAccessToken().getUserId()
                        +"\n"+ "Last Refresh:\n " +loginResult.getAccessToken().getLastRefresh() + "\n");

                if(Profile.getCurrentProfile()!=null){
                    txtStatus.setText("Login sucess! \n" + "User ID: "+
                            loginResult.getAccessToken().getUserId()
                            +"\n"+ "Last Refresh:\n " +loginResult.getAccessToken().getLastRefresh() + "\n" +
                            "nome: \n" + Profile.getCurrentProfile().getName());
                }
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
