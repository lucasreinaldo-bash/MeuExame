package vostore.policlinica.meuexame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import vostore.policlinica.meuexame.Firebase.ConfiguracaoFirebase;


public class Registro extends AppCompatActivity {

    private Button btnInsta, btnFace,btnLinke,btnVerQr,btnConcluirInscricao;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button btnRegistrar, btnPalestrante,btnParticipante;
    private EditText txtNome,txtEmail,txtCpf,txtNomeProfissional,txtFormacao,txtNacionalidade,txtInformacaoComplementar;
    private EditText txtSenha;
    private DatabaseReference refe1,refe2,refe3,refe4,refe5,refe6;
    private LinearLayout linear_curriculo;
    private String tipoDoUsuario,cpfValido;
    private DatabaseReference reference,reference4, referenciaCPF;
    private String LeiFavorita;
    private DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("CPF");
    private RadioGroup rg;
    private FirebaseUser user;
    private static final String SETTINGS_PREFER = "SERVICE_ENABLE";
    private String id = "1";
    private Button btnLimpar;

    private String situacaoCadastro = "false";


    private RecyclerView recyclerView;
    private TextView responsavelTecnica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
//      verificarUsuarioLogado();
        txtNome = (EditText) findViewById(R.id.nome_id);
        txtEmail = (EditText) findViewById(R.id.email_id);
        txtSenha = (EditText) findViewById(R.id.senha_id);

        responsavelTecnica = findViewById(R.id.responsavel_id);


        //Redes Sociais
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
       // txtSenha = (EditText) findViewById(R.id.rg_senha);
        //txtSenhaRepetida = (EditText) findViewById(R.id.rg_contrasenha);
        btnRegistrar = (Button) findViewById(R.id.btn_registrar);

        rg = (RadioGroup) findViewById(R.id.radioGroup);







        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int childCount = group.getChildCount();
                for (int x = 0; x < childCount; x++) {
                    RadioButton btn = (RadioButton) group.getChildAt(x);
                    if (btn.getId() == checkedId) {
                        LeiFavorita = btn.getText().toString();
                        Toast.makeText(Registro.this, LeiFavorita, Toast.LENGTH_SHORT).show();


                    }
                }
            }
        });


        // Instaciando o servidor




        //Carregando Banco de Dados





        // Adicionando evento ao click do botão



        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = txtEmail.getText().toString();
                final String nome = txtNome.getText().toString();
                final String responsavel = responsavelTecnica.getText().toString();
                if(isValidEmail(email) && validarContraseña() && validarNombre(nome)){
                    final String senha = txtSenha.getText().toString();
                    final Boolean status = false;
                    mAuth.createUserWithEmailAndPassword(email, senha)
                            .addOnCompleteListener(Registro.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {


                                        final FirebaseUser currentUser = mAuth.getCurrentUser();

                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(Registro.this, "Registro Confirmado. Seja-bem vindo!", Toast.LENGTH_SHORT).show();
                                        Usuario usuario = new Usuario();
                                        usuario.setEmail(email);
                                        usuario.setNomeRegulacao(nome);
                                        usuario.setResponsavelRegulacao(responsavel);
                                        usuario.setTipoDePerfil("Padrao");
                                        usuario.setId(currentUser.getUid());
                                        usuario.setImagemUsuario("https://firebasestorage.googleapis.com/v0/b/lexdjus.appspot.com/o/blank-profile-picture-973460_640.png?alt=media&token=3259e2f8-38e4-4cc2-8ca9-58770d2f88d4");
                                        DatabaseReference reference = database.getReference("Usuario/"+currentUser.getUid());
                                        final DatabaseReference reference2 = database.getReference("LeisUsuarios/"+currentUser.getUid());
                                        reference.setValue(usuario);
                                        user = FirebaseAuth.getInstance().getCurrentUser();
                                        id = user.getUid();

                                        Toast.makeText(Registro.this, "Cadastro Criado", Toast.LENGTH_SHORT).show();



                                        Intent intent = new Intent(Registro.this, Chat.class);
                                        startActivity(intent);
                                        finish();

//                                        startActivity(intent);




                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(Registro.this, "Erro ao fazer o registro", Toast.LENGTH_SHORT).show();
                                        Log.i("CreateUser", "Erro ao cadastrar usuário! ", task.getException());
                                    }
                                }
                            });
                }else{
                    Toast.makeText(Registro.this, "Algum erro foi detectado! Está com internet ?", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void setLeis() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        id = user.getUid();

        refe1 = database.getReference("LeiParadaPeloUsuario").child(id).child("Constituição Federal").child("lei");
        refe1.setValue(1);
        refe2 = database.getReference("LeiParadaPeloUsuario").child(id).child("Código Civil").child("lei");
        refe2.setValue(1);
        refe3 = database.getReference("LeiParadaPeloUsuario").child(id).child("Código Penal").child("lei");
        refe3.setValue(1);
        refe4 = database.getReference("LeiParadaPeloUsuario").child(id).child("Código de Processo Civil").child("lei");
        refe4.setValue(1);
        refe5 = database.getReference("LeiParadaPeloUsuario").child(id).child("Código de Processo Penal").child("lei");
        refe5.setValue(1);
        refe6 = database.getReference("LeiParadaPeloUsuario").child(id).child("Súmulas do STF e STJ").child("lei");
        refe6.setValue(1);
    }

    private void verificarUsuarioLogado(){
        mAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if(mAuth.getCurrentUser()!= null ){
            updateUI();
        }
    }
    //método para validar e-mail
    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    private  void updateUI(){
        // Toast.makeText(login.this, "login Realizado", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Registro.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    //método para validar senha
    public boolean validarContraseña(){




        String contraseña;
        String validar;
        contraseña = txtSenha.getText().toString();
        validar = contraseña;
        if(contraseña.equals(validar)){
            if(contraseña.length()>=6 && contraseña.length()<=16){
                return true;
            }else return false;
        }else return false;



    }
    //método para validar nome
    public boolean validarNombre(String nombre){
        return !nombre.isEmpty();
    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Registro.this, Login.class);
        startActivity(intent);
        finish();


    }


    }
