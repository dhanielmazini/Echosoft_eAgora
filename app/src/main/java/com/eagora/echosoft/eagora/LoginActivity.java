package com.eagora.echosoft.eagora;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eagora.echosoft.eagora.Facebook.AcessoGraphFacebook;
import com.eagora.echosoft.eagora.Usuario.Usuario;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.eagora.echosoft.eagora.R.id.getn;

public class LoginActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    List<String> userlist = new ArrayList<String>();
    LoginButton login_button;
    CallbackManager callbackManager;
    private EditText txtEmail, txtSenha;
    private Button btnCadastro;
    private FirebaseAuth mAuth;
    GoogleApiClient mGoogleApiClient;
    private static int RC_SIGN_IN = 100;
    private DatabaseReference mDatabase;
    String email = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDatabase = FirebaseDatabase.getInstance().getReference("usuarios");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot dados : dataSnapshot.getChildren()){
                        String data = dados.getKey();
                        if (!userlist.contains(data))
                            userlist.add(data);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        txtEmail = (EditText)findViewById(R.id.emailLogin);
        txtSenha = (EditText)findViewById(R.id.senhaLogin);
        btnCadastro = (Button)findViewById(R.id.botaoCadastro);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        login_button = (LoginButton)findViewById(R.id.login_button);

        callbackManager = CallbackManager.Factory.create();
//        faceLog();

        login_button.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        faceLog();
                    }
                }
        );

        btnCadastro.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent signup = new Intent(getApplicationContext(), SingupActivity.class);
                        startActivity(signup);
                    }
                });
    }

//    @Override
//    public void onResume(){
//        super.onResume();
//
//        FirebaseUser usuario = mAuth.getCurrentUser();
//
//        if(usuario == null)
//
//    }
    @Override
    public void onStart(){
        super.onStart();

        FirebaseUser usuario = mAuth.getCurrentUser();

        if(usuario != null)
            startActivity(new Intent(LoginActivity.this, MenuActivity.class));
    }

    @Override
    public void onClick (View v){
        switch (v.getId()){
            case R.id.sign_in_button:
                signIn();
                break;
//            case R.id.login_button:
//                faceLog();
//                break;
//            case R.id.disconnect_button:
//                revokeAccess();
        }
    }

    private void faceLog(){
        login_button.setReadPermissions("email","public_profile");
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                //Define variável global de Token de Acesso  do Facebook
                AcessoGraphFacebook.accessToken = loginResult.getAccessToken();
                new GraphRequest.GraphJSONObjectCallback(){
                    @Override
                    public void onCompleted( JSONObject object, GraphResponse response){
                        try {
                            email = object.getString("email");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                handleFacebookAccessToken(loginResult.getAccessToken(),email);
            }

            @Override
            public void onCancel() {
                Log.d("FB","facebook:onCancel");
            }

            @Override
            public void onError(FacebookException e)
            {
                Log.d("FB","facebook:onError", e);
            }

        });
    }

    private void handleFacebookAccessToken(AccessToken token, final String email) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("FBLog", "signInWithCredential:success");
                            String verify = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            System.out.println(userlist.contains(verify));
                            if(!userlist.contains(verify)) {
                                Log.d("contains","heyobro,newuser");
                                Usuario user = new Usuario(Profile.getCurrentProfile().getFirstName(), Profile.getCurrentProfile().getLastName(),
                                        email, Profile.getCurrentProfile().getId(), null);
                                mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);
                                Intent login = new Intent(getApplicationContext(), PerfilViajanteActivity.class);
                                startActivity(login);
                            } else {
                                Intent login = new Intent(getApplicationContext(), MenuActivity.class);
                                startActivity(login);
                            }
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Falha na autenticação.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


    private void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

//        if(requestCode == RC_SIGN_IN){
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
////            handleSignInResult(result);
//            if (result.isSuccess()) {
//                GoogleSignInAccount acc = result.getSignInAccount();
//                firebaseAuthWithGoogle(acc);
//            } else{
//                Toast.makeText(LoginActivity.this, "Falha na autenticação",
//                        Toast.LENGTH_SHORT).show();
//            }
//        }
    }

//    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
//        Log.d("logafirebase", "firebaseAuthWithGoogle:" + acct.getId());
//
//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Toast.makeText(LoginActivity.this, "Autenticado com sucesso",
//                                    Toast.LENGTH_SHORT).show();
////                            FirebaseUser user = mAuth.getCurrentUser();
////                            updateUI(user);
//                            Usuario user = new Usuario(acct.getDisplayName(), acct.getFamilyName(),
//                                    acct.getEmail(), FirebaseAuth.getInstance().getCurrentUser().getUid(), null);
//
//                            mDatabase.child("usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);
//                            Intent login = new Intent(getApplicationContext(), PerfilViajanteActivity.class);
//                            startActivity(login);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Toast.makeText(LoginActivity.this, "Falha na autenticação",
//                                    Toast.LENGTH_SHORT).show();
////                            updateUI(null);
//                        }
//
//                        // ...
//                    }
//                });
//    }


}
