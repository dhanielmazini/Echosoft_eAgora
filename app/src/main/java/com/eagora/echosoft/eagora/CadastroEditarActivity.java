package com.eagora.echosoft.eagora;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eagora.echosoft.eagora.Usuario.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroEditarActivity extends AppCompatActivity {

    private Toolbar toolbar;

    TextView uidTxt, nameText, emailTxt;
    Button excluirbtn, btnnome, btnemail, senhabtn, editPerfil;
    EditText editName, editMAil, editpw;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_editar);
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

        setContentView(R.layout.activity_cadastro_editar);
        uidTxt = (TextView) findViewById(R.id.uidTxt);
        emailTxt = (TextView) findViewById(R.id.emailTxt);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        excluirbtn = (Button)findViewById(R.id.excluirConta);
        editName = (EditText)findViewById(R.id.editName);
        nameText = (TextView)findViewById(R.id.name);
        btnnome = (Button)findViewById(R.id.btnnome);
        editMAil = (EditText) findViewById(R.id.editMail);
        btnemail = (Button)findViewById(R.id.btnemail);
        senhabtn = (Button)findViewById(R.id.senhabtn);
        editpw = (EditText)findViewById(R.id.editpw);
        editPerfil = (Button)findViewById(R.id.editPerfil);


        setSupportActionBar(toolbar);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference();
        DataSnapshot ds;



        if (user != null) {
            Usuario usuario = new Usuario(user.getDisplayName(), " ", user.getEmail(), user.getUid(), "");
            // Name, email address, and profile photo Url
            String uid = usuario.getUid();
            String email = usuario.getEmail();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            emailTxt.setText(email);

            uidTxt.setText(uid);

            btnnome.setOnClickListener(

                    new View.OnClickListener(){
                        @Override
                        public void onClick(View view){

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(String.valueOf(editName))
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(CadastroEditarActivity.this, "Nome atualizado",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
            );
            nameText.setText(user.getDisplayName());

            btnemail.setOnClickListener(

                    new View.OnClickListener(){
                        @Override
                        public void onClick(View view){
                            FirebaseUser user2 = FirebaseAuth.getInstance().getCurrentUser();
                            user2.updateEmail(String.valueOf(editMAil)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(CadastroEditarActivity.this, "Email atualizado",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(CadastroEditarActivity.this, "Falha na atualização",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
            );

            excluirbtn.setOnClickListener(
                    new View.OnClickListener(){
                        @Override
                        public void onClick(View view){
                            user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(CadastroEditarActivity.this, "Conta Excluída",
                                                        Toast.LENGTH_SHORT).show();
                                                FirebaseAuth.getInstance().signOut();
                                                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                                                startActivity(login);
                                            }
                                        }
                                    });
                        }
                    }
            );
            editPerfil.setOnClickListener(
                    new View.OnClickListener(){
                        @Override
                        public void onClick(View view){
                            Intent intentPerfil = new Intent(getApplicationContext(), PerfilViajanteActivity.class);
                            startActivity(intentPerfil);
                        }
                    }
            );

            senhabtn.setOnClickListener(

                    new View.OnClickListener(){
                        @Override
                        public void onClick(View view){

                            user.updatePassword(String.valueOf(editpw))
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(CadastroEditarActivity.this, "Senha alterada!",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                Toast.makeText(CadastroEditarActivity.this, "Falha na alteração",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
            );

            // Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
            AuthCredential credential = EmailAuthProvider
                    .getCredential(user.getEmail(), String.valueOf(user.updatePassword(String.valueOf(editpw))));

// Prompt the user to re-provide their sign-in credentials
            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(CadastroEditarActivity.this, "Reautenticado",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });



        }
    }


}
