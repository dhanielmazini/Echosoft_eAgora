package com.eagora.echosoft.eagora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InicioActivity extends AppCompatActivity {
    Button cadastro_btn, btn_entrar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser usuario = mAuth.getCurrentUser();

        if(usuario != null)
            startActivity(new Intent(InicioActivity.this, LoginActivity.class));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        btn_entrar = (Button)findViewById(R.id.btn_entrar);
        cadastro_btn = (Button)findViewById(R.id.cadastro_btn);
        cadastro_btn.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent signup = new Intent(getApplicationContext(), SingupActivity.class);
                        startActivity(signup);
                    }
                });
        btn_entrar.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent signin = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(signin);
                    }
                });
    }
}
