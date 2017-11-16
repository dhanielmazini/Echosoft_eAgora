package com.eagora.echosoft.eagora;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.eagora.echosoft.eagora.Usuario.Usuario;
import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class EditCadastrofbActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    TextView namefb;
    ProfilePictureView pic;
    Button btnEditar;
    CheckBox cbVidaNoturna, cbTrabalho, cbPaisagem, cbGastro, cbFamilia, cbCultural, cbCompras, cbAventura;
    List<String> perfis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cadastrofb);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Definir nome e perfil do usuário como variável global
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        namefb = (TextView) findViewById(R.id.namef);
        pic = (ProfilePictureView) findViewById(R.id.image);
        btnEditar = (Button) findViewById(R.id.btnEditar);
        cbVidaNoturna = (CheckBox) findViewById(R.id.cbVidaNoturna);
        cbTrabalho = (CheckBox) findViewById(R.id.cbTrabalho);
        cbPaisagem = (CheckBox) findViewById(R.id.cbPaisagem);
        cbGastro = (CheckBox) findViewById(R.id.cbGastro);
        cbFamilia = (CheckBox) findViewById(R.id.cbFamilia);
        cbCultural = (CheckBox) findViewById(R.id.cbCultural);
        cbCompras = (CheckBox) findViewById(R.id.cbCompras);
        cbAventura = (CheckBox) findViewById(R.id.cbAventura);

        mDatabase = FirebaseDatabase.getInstance().getReference("usuarios").child(user.getUid());
        final String caminho = "fotoPerfil/" + GlobalAccess.nomeUsuario + ".png";
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(caminho);
        mDatabase.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Usuario usu = dataSnapshot.getValue(Usuario.class);

                    GlobalAccess.nomeUsuario = usu.getNome();
                    GlobalAccess.emailUsuario = usu.getEmail();
                    GlobalAccess.perfilUsuario = usu.getPerfil();
                    namefb.setText("Olá " + GlobalAccess.nomeUsuario + ", edite seu perfil de viajante e inicie agora seu planejamento!");
                    pic.setProfileId(Profile.getCurrentProfile().getId());


                } else {
                    Intent volta = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(volta);
                }
            }

            public void onCancelled(DatabaseError databaseError) {
                Intent volta = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(volta);
            }
        });

        btnEditar.setOnClickListener(
                new View.OnClickListener() {
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

                        if (novosPerfils.size()>0 && novosPerfils.size() <= 3) {

                            Toast.makeText(getApplicationContext(), "Dados atualizados!", Toast.LENGTH_LONG).show();
                            Intent volta = new Intent(getApplicationContext(), MenuActivity.class);
                            startActivity(volta);
                        }
                        if (novosPerfils.size() == 0)
                            Toast.makeText(getApplicationContext(), "Selecione pelo menos 1 perfil", Toast.LENGTH_LONG).show();

                        if(novosPerfils.size()>3){
                            Toast.makeText(getApplicationContext(), "Selecione no máximo 3 perfis", Toast.LENGTH_LONG).show();
                        }
                    }


                }
        );

    }

}
