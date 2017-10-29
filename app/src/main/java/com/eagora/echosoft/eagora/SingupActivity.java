package com.eagora.echosoft.eagora;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eagora.echosoft.eagora.Usuario.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SingupActivity extends AppCompatActivity {

    private FirebaseAuth minhaAuth;

    private Button btnConcluir;
    private EditText txtSobrenome, txtNome, txtEmail, txtConfEmail, txtSenha, txtConfSenha;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnConcluir = (Button) findViewById(R.id.button4);
        txtNome = (EditText) findViewById(R.id.nome);
        txtSobrenome = (EditText) findViewById(R.id.sobrenome);
        txtEmail = (EditText) findViewById(R.id.email);
        txtConfEmail = (EditText) findViewById(R.id.confemail);
        txtSenha = (EditText) findViewById(R.id.senha);
        txtConfSenha = (EditText) findViewById(R.id.confsenha);

        minhaAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void createAccount(View view) {
        System.out.println("Heya");
        if(txtEmail.getText().toString().equals(txtConfEmail.getText().toString())
                && txtSenha.getText().toString().equals(txtConfSenha.getText().toString())
                && !txtEmail.getText().toString().isEmpty() && !txtSenha.getText().toString().isEmpty()) {
            minhaAuth.createUserWithEmailAndPassword(txtEmail.getText().toString(), txtSenha.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            Log.d("HAIA", "createUserWithEmail:onComplete:" + task.isSuccessfu2l());

                           if (!task.isSuccessful()) {
                                Toast.makeText(SingupActivity.this, "Falha na criação de usuário",
                                    Toast.LENGTH_SHORT).show();
                            }
                            else{
                               Usuario user = new Usuario(txtNome.getText().toString(), txtSobrenome.getText().toString(),
                                       txtEmail.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getUid(), txtSenha.getText().toString());

                               mDatabase.child("usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);



                               Intent login = new Intent(getApplicationContext(), PerfilViajanteActivity.class);
                               startActivity(login);
                           }
                        }
                    });

        } else{
            Toast.makeText(SingupActivity.this, "Confira os campos novamente", Toast.LENGTH_SHORT).show();
        }
    }
}


