package vostore.policlinica.meuexame;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import vostore.policlinica.meuexame.Firebase.ConfiguracaoFirebase;


public class Login extends AppCompatActivity  {

    //Services
    private static final String TAG = "GoogleActivity";
    private static final String SETTINGS_PREFER = "SERVICE_ENABLE";
    private boolean isServiceActivated;
    private ToggleButton mToggleButton;
    private FirebaseDatabase database;
    private DatabaseReference refe1,refe2,refe3,refe4,refe5,refe6;
    private SignInButton btnGoogleLogin;
    private Button btnLogin;
    private TextView btnRegistro;
    private TextView registrar;
    private EditText senhausuario,emailusuario;
    private FirebaseAuth mAuth,firebaseAuth;
    private DatabaseReference databaseReference;



    //Login Google
    private GoogleApiClient googleApiClient;
    public static final int RC_SIGN_IN = 9001;

    public Login() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        verificarUsuarioLogado();
        setContentView(R.layout.activity_login);
//        verificarUsuarioLogado();
//hj
        firebaseAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();


        //Fazendo o Cast dos botões e campos
        btnLogin = findViewById(R.id.entrar_id);
        btnRegistro = findViewById(R.id.id_registrar);
        senhausuario = findViewById(R.id.senhaid);
        emailusuario = findViewById(R.id.emailid);
        databaseReference = FirebaseDatabase.getInstance().getReference();


        //Instanciando o servidor de dados


        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Login.this, Registro.class);
                startActivity(intent);
                finish();
            }
        });

        // Adicionando uma ação ao evento do click
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();


            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }
    private boolean validaremail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    boolean validarsenha(){
        String contraseña;
        contraseña = senhausuario.getText().toString();
        if(contraseña.length()>=6 && contraseña.length()<=16){
            return true;
        }else return false;
    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Login.this, MainActivityMenu.class);
        startActivity(intent);
        finish();


    }


    private void login() {

        final String email = emailusuario.getText().toString();
        String contraseña;
        contraseña = senhausuario.getText().toString();



            if (validaremail(email) && validarsenha()) {
                String senha = senhausuario.getText().toString();
                mAuth.signInWithEmailAndPassword(email, senha)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    if (email == "suporte@lexdjus.com.br"){
                                         Toast.makeText(Login.this,"login bem sucedido",Toast.LENGTH_SHORT).show();
                                    }
                                    // Toast.makeText(login.this,"login bem sucedido",Toast.LENGTH_SHORT).show();


                                    recuperarRegulacao();

                                    //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                } else {
                                    Toast toast = new Toast(Login.this);
                                    ImageView view = new ImageView(Login.this);
                                    //view.setImageResource(R.drawable.toast_erro);
                                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                    toast.setView(view);
                                    toast.show();


                                    Toast.makeText(Login.this, "Não foi possivel realizar o login.", Toast.LENGTH_SHORT).show();
                                    // Toast.makeText(login.this,"Não foi possível entrar no ambiente",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                Toast.makeText(Login.this, "Erro de login.", Toast.LENGTH_SHORT).show();
            }

    }
    private void verificarUsuarioLogado(){
        mAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();
        FirebaseUser user = mAuth.getCurrentUser();

        if(mAuth.getCurrentUser()!= null || user != null ){
          recuperarRegulacao();

        }
    }

    private void abrirTelaPrincipal() {


        Intent intent = new Intent(Login.this,Chat.class);
        startActivity(intent);


    }


    private void recuperarRegulacao (){

        final FirebaseUser currentUser = mAuth.getCurrentUser();
        String idUsuario = currentUser.getUid();

        refe1 = ConfiguracaoFirebase.getFirebase().child("Usuario").child(""+idUsuario);
        refe1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    Usuario usuario = dataSnapshot.getValue(Usuario.class);



                    Boolean perfil = usuario.getPerfilAdministrador();

                    if (perfil == true) {
                        Intent intent = new Intent(Login.this, ReguladoresChat.class);
                        startActivity(intent);
                        finish();
                    }else{
                        String nomeRegula = usuario.getNomeRegulacao();
                        Intent intent = new Intent(Login.this, Chat.class);
                        intent.putExtra("site", nomeRegula);
                        startActivity(intent);
                        finish();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private  void updateUI(){
        // Toast.makeText(Login.this, "Login Realizado", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

}

