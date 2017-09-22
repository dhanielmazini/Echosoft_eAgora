package com.eagora.echosoft.eagora;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
    private FirebaseAuth.AuthStateListener minhaAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
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

        minhaAuth = FirebaseAuth.getInstance();

        minhaAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("meuLog", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("meuLog", "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    public void clicaCriarUsuario(View view) {
        minhaAuth.createUserWithEmailAndPassword(campoEmail, campoSenha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.d("meuLog", "Falha na crição de conta");
                        } else {
                            FirebaseDatabase database = FirebaseDatabase.getInstance(); //cria o banco
                            DatabaseReference ref = database.getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()); //cria referencia para o usuario no bd
                            ref.child("nome").setValue(campoNome.getText().toString); //armazena o nome do usuario
                            ref.child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid()); //armazena o id do usuario
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        minhaAuth.addAuthStateListener(minhaAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (minhaAuthListener != null) {
            minhaAuth.removeAuthStateListener(minhaAuthListener);
        }
    }
}
