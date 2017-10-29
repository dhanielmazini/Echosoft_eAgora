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
import android.widget.CheckBox;
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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CadastroEditarActivity extends AppCompatActivity {

    private Toolbar toolbar;


    EditText txtNome,txtSobrenome,txtEmail,txtNovaSenha,txtSenhaConfirmar;
    Button btnEditar,btnExcluir;
    CheckBox cbVidaNoturna,cbTrabalho,cbPaisagem,cbGastro,cbFamilia,cbCultural,cbCompras,cbAventura;
    List<String> perfis;
    DatabaseReference mDatabase;
    Usuario usuario;
    String senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_editar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        txtNome = (EditText) findViewById(R.id.txtNome);
        txtSobrenome = (EditText) findViewById(R.id.txtSobrenome);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtNovaSenha = (EditText) findViewById(R.id.txtNovaSenha);
        txtSenhaConfirmar = (EditText) findViewById(R.id.txtSenhaConfirmar);
        btnEditar = (Button) findViewById(R.id.btnEditar);
        cbVidaNoturna = (CheckBox) findViewById(R.id.cbVidaNoturna);
        cbTrabalho = (CheckBox) findViewById(R.id.cbTrabalho);
        cbPaisagem = (CheckBox) findViewById(R.id.cbPaisagem);
        cbGastro = (CheckBox) findViewById(R.id.cbGastro);
        cbFamilia = (CheckBox) findViewById(R.id.cbFamilia);
        cbCultural = (CheckBox) findViewById(R.id.cbCultural);
        cbCompras = (CheckBox) findViewById(R.id.cbCompras);
        cbAventura = (CheckBox) findViewById(R.id.cbAventura);
        btnExcluir = (Button) findViewById(R.id.btnExcluir);

        setSupportActionBar(toolbar);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference();
        DataSnapshot ds;

        usuario = new Usuario(user.getDisplayName(), " ", user.getEmail(), user.getUid(), "");

        //Assíncrono
        mDatabase = FirebaseDatabase.getInstance().getReference("usuarios").child(user.getUid());
        mDatabase.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Usuario usu = dataSnapshot.getValue(Usuario.class);
                    senha = usu.getSenha();
                    txtNome.setText(usu.getNome());
                    txtSobrenome.setText(usu.getSobrenome());
                    txtEmail.setText(usu.getEmail());
                    List<String> perfis;
                    perfis = usu.getPerfil();

                    //Checkbox do tipo de perfil
                    for(int i=0;i<perfis.size();i++){
                        if(perfis.get(i).toString().equals("Aventura")) cbAventura.setChecked(true);
                        else if(perfis.get(i).toString().equals("Compras")) cbCompras.setChecked(true);
                        else if(perfis.get(i).toString().equals("Cultural")) cbCultural.setChecked(true);
                        else if(perfis.get(i).toString().equals("Família")) cbFamilia.setChecked(true);
                        else if(perfis.get(i).toString().equals("Gastronômico")) cbGastro.setChecked(true);
                        else if(perfis.get(i).toString().equals("Paisagem")) cbPaisagem.setChecked(true);
                        else if(perfis.get(i).toString().equals("Trabalho")) cbTrabalho.setChecked(true);
                        else if(perfis.get(i).toString().equals("Vida Noturna")) cbVidaNoturna.setChecked(true);
                    }

                }
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });

        btnExcluir.setOnClickListener(

                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        mDatabase.removeValue();
                        FirebaseAuth.getInstance().signOut();
                        Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(login);
                    }

                }
        );


        btnEditar.setOnClickListener(

                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {

                        List<String> novosPerfils = new ArrayList<String>();
                        if (cbAventura.isChecked()) novosPerfils.add("Aventura");
                        if (cbCompras.isChecked()) novosPerfils.add("Compras");
                        if (cbCultural.isChecked()) novosPerfils.add("Cultural");
                        if (cbFamilia.isChecked()) novosPerfils.add("Família");
                        if (cbGastro.isChecked()) novosPerfils.add("Gastronômico");
                        if (cbPaisagem.isChecked()) novosPerfils.add("Paisagem");
                        if (cbTrabalho.isChecked()) novosPerfils.add("Trabalho");
                        if (cbVidaNoturna.isChecked()) novosPerfils.add("Vida Noturna");

                        if (novosPerfils.size() <= 3) {

                            if (txtSenhaConfirmar.getText().toString().equals(senha)) {

                                Map<String, Object> hopperUpdates = new HashMap<String, Object>();
                                hopperUpdates.put("nome", txtNome.getText().toString());
                                hopperUpdates.put("sobrenome", txtSobrenome.getText().toString());
                                hopperUpdates.put("email", txtEmail.getText().toString());
                                if (!txtNovaSenha.getText().toString().isEmpty()) {
                                    hopperUpdates.put("senha", txtNovaSenha.getText().toString());
                                }

                                hopperUpdates.put("perfil", novosPerfils);

                                mDatabase.updateChildren(hopperUpdates);
                                Toast.makeText(getApplicationContext(), "Dados atualizados!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Senha incorreta", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Selecione no máximo 3 perfis", Toast.LENGTH_LONG).show();
                        }
                    }


                }
        );


    }


}
