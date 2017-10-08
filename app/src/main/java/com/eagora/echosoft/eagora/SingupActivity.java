package com.eagora.echosoft.eagora;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SingupActivity extends AppCompatActivity {

    private FirebaseAuth minhaAuth;

    private Button btnConcluir;
    private EditText txtNome, txtEmail, txtConfEmail, txtSenha, txtConfSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnConcluir = (Button) findViewById(R.id.button4);
        txtNome = (EditText) findViewById(R.id.nome);
        txtEmail = (EditText) findViewById(R.id.email);
        txtConfEmail = (EditText) findViewById(R.id.confemail);
        txtSenha = (EditText) findViewById(R.id.senha);
        txtConfSenha = (EditText) findViewById(R.id.confsenha);
        minhaAuth = FirebaseAuth.getInstance();
    }

    public void createAccount(View view) {
        if( txtEmail.getText().toString().equals(txtConfEmail.getText().toString())
                && txtSenha.getText().toString().equals(txtConfSenha.getText().toString())) {
            minhaAuth.createUserWithEmailAndPassword(txtEmail.getText().toString(), txtSenha.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("HAIA", "createUserWithEmail:onComplete:" + task.isSuccessful());

                           if (!task.isSuccessful()) {
                                Toast.makeText(SingupActivity.this, "Falha na criação de usuário",
                                    Toast.LENGTH_SHORT).show();
                            }
                            else{
                               Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                               startActivity(login);
                           }
                        }
                    });
        } else{
            Toast.makeText(SingupActivity.this, "Confira os campos novamente", Toast.LENGTH_SHORT).show();
        }
    }
}


