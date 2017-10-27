package com.eagora.echosoft.eagora;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eagora.echosoft.eagora.Usuario.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText txtEmail, txtSenha;
    private Button btnCadastro;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser usuario = mAuth.getCurrentUser();

        if(usuario != null)
            startActivity(new Intent(LoginActivity.this, MenuActivity.class));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = (EditText)findViewById(R.id.emailLogin);
        txtSenha = (EditText)findViewById(R.id.senhaLogin);
        btnCadastro = (Button)findViewById(R.id.botaoCadastro);

        btnCadastro.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent signup = new Intent(getApplicationContext(), SingupActivity.class);
                        startActivity(signup);
                    }
                });
    }

    public void loginUsuario(View view){
        if(!txtEmail.getText().toString().isEmpty() && !txtSenha.getText().toString().isEmpty() ) {
            mAuth.signInWithEmailAndPassword(txtEmail.getText().toString(), txtSenha.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Log.w("log", "signInWithEmail:failed", task.getException());
                                Toast.makeText(LoginActivity.this, "Falha na autenticação",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Usuário conectado",
                                        Toast.LENGTH_SHORT).show();

                                String display = mAuth.getCurrentUser().getDisplayName();
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                final Usuario member = new Usuario();
                                mDatabase.child("usuario").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        // Is better to use a List, because you don't know the size
                                        // of the iterator returned by dataSnapshot.getChildren() to
                                        // initialize the array

                                        String nameSurname = dataSnapshot.child("uid").child("nome").getValue(String.class);
                                        Toast.makeText(LoginActivity.this, nameSurname, Toast.LENGTH_SHORT).show();


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Toast.makeText(LoginActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });




                                Intent mudar = new Intent(getApplicationContext(), MenuActivity.class);
                                startActivity(mudar);
                            }
                        }
                    });
        } else {
            Toast.makeText(LoginActivity.this, "Campo de email ou senha vazio", Toast.LENGTH_SHORT).show();
        }
    }
}
