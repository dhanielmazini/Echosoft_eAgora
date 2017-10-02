package com.eagora.echosoft.eagora;

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
    private FirebaseAuth.AuthStateListener minhaAuthListener;

    Button concluir;
    EditText nome,email,confEmail,senha,confSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        concluir = (Button) findViewById(R.id.button4);
        nome = (EditText) findViewById(R.id.nome);
        email = (EditText) findViewById(R.id.email);
        confEmail = (EditText) findViewById(R.id.confemail);
        senha = (EditText) findViewById(R.id.senha);
        confSenha = (EditText) findViewById(R.id.confsenha);
        minhaAuth = FirebaseAuth.getInstance();


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


//
//        minhaAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    // User is signed in
//                    Log.d("meuLog", "onAuthStateChanged:signed_in:" + user.getUid());
//                } else {
//                    // User is signed out
//                    Log.d("meuLog", "onAuthStateChanged:signed_out");
//                }
//            }
//        };
//    }
//
//    public void clicaCriarUsuario(View view) {
//        minhaAuth.createUserWithEmailAndPassword(email.getText().toString(), senha.getText().toString())
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Log.d("meuLog", "SUCESSO");
//                        } else {
//                            Log.d("meuLog", "FAIL");
////                            FirebaseDatabase database = FirebaseDatabase.getInstance(); //cria o banco
////                            DatabaseReference ref = database.getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()); //cria referencia para o usuario no bd
////                            ref.child("nome").setValue(nome.getText().toString()); //armazena o nome do usuario
////                            ref.child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid()); //armazena o id do usuario
//                        }
//                    }
//                });
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        minhaAuth.addAuthStateListener(minhaAuthListener);
//    }
//
//
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (minhaAuthListener != null) {
//            minhaAuth.removeAuthStateListener(minhaAuthListener);
//        }
    }

    public void createAccount(View view) {
        if( email.getText().toString().equals(confEmail.getText().toString())
                && senha.getText().toString().equals(confSenha.getText().toString())) {
            minhaAuth.createUserWithEmailAndPassword(email.getText().toString(), senha.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("HAIA", "createUserWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
//                        if (!task.isSuccessful()) {
//                            Toast.makeText(SignupActiv'ity.this, R.string.auth_failed,
//                                    Toast.LENGTH_SHORT).show();
//                        }

                            // ...
                        }
                    });
        } else{
            System.out.println("WRONG MODA FOCA");
        }
    }
}


