package com.eagora.echosoft.eagora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import android.content.Intent;

import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    LoginButton login_button;
    TextView txtStatus;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                txtStatus.setText("Login sucess \n" +
                        loginResult.getAccessToken().getUserId()
                        +"\n"+ loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                //txtStatus.setText("Login Cancelled");
            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }
    protected void onActivityResult(int requestCode, int result, Intent data){
        callbackManager.onActivityResult(requestCode, result, data);
    }
}
