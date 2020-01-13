package vostore.policlinica.meuexame;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import vostore.policlinica.meuexame.Firebase.ConfiguracaoFirebase;


public class EnviarMensagem extends AppCompatActivity  {

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
    private EditText senhausuario,emailusuario;
    private FirebaseAuth mAuth,firebaseAuth;
    private DatabaseReference databaseReference;



    //Login Google
    private GoogleApiClient googleApiClient;
    public static final int RC_SIGN_IN = 9001;

    public EnviarMensagem() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviarmsg);


    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }



    @Override
    public void onBackPressed() {

        Intent intent = new Intent(EnviarMensagem.this, MainActivityMenu.class);
        startActivity(intent);
        finish();


    }

    private void abrirTelaPrincipal() {


        Intent intent = new Intent(EnviarMensagem.this,MainActivity.class);
        startActivity(intent);


    }



    private  void updateUI(){
        // Toast.makeText(Login.this, "Login Realizado", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(EnviarMensagem.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

}

