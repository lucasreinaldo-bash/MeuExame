package vostore.policlinica.meuexame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import vostore.policlinica.meuexame.Adapter.AdapterExames;
import vostore.policlinica.meuexame.Firebase.ConfiguracaoFirebase;
import vostore.policlinica.meuexame.Model.ClasseExame;


public class MainActivityMenu extends AppCompatActivity    {
    private DatabaseReference reference1;
    Context ctx;
    private CardView btnCardLocalizar;





    private CardView btnCardConsultar,btnCardChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.activity_main_menu);

        //Instanciando elementos do layout
        btnCardConsultar = findViewById(R.id.btn_card_resultado);
        btnCardLocalizar = findViewById(R.id.btn_card_localizacao);
        btnCardChat = findViewById(R.id.btn_card_fale_conosco);



        btnCardChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FlatDialog flatDialog = new FlatDialog(MainActivityMenu.this);
                flatDialog.setTitle("ARÉA RESTRITA!");
                flatDialog.setSubtitle("Esse espaço é reservado apenas para que os reguladores dos municípios se comuniquem diretamente com a Policlínica Regional de Alagoinhas.");


                flatDialog.setFirstButtonText("Sou Regulador");
                flatDialog.setSecondButtonText("Sou Paciente");
                flatDialog.setSecondButtonColor(R.color.fundo);

                flatDialog.withSecondButtonListner(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flatDialog.dismiss();
                    }
                });
                flatDialog.withFirstButtonListner(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivityMenu.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                });
                flatDialog.show();


            }
        });
        btnCardLocalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityMenu.this, Maps.class);
                startActivity(intent);
                finish();
            }
        });



        btnCardConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dialogConsultar();
            }
        });
    }
    //Métodos
    public void dialogConsultar (){
        //Criando Dialog personalizado para a inserção do numero de autorizacãp
        final FlatDialog flatDialog = new FlatDialog(MainActivityMenu.this);
        flatDialog.setTitle("Digite o número de autorização do seu exame.");

        flatDialog.setSecondTextFieldHint("somente números.");
        flatDialog.setSecondTextFieldInputType(2);

        flatDialog.setFirstButtonText("Continuar");
        flatDialog.setSecondButtonText("Cancelar");
        flatDialog.withFirstButtonListner(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numeroAutorizacao = flatDialog.getSecondTextField();

                if (numeroAutorizacao.length() == 0){
                    Toast.makeText(ctx, "Por favor, preencha o campo com o seu número de autorização.", Toast.LENGTH_LONG).show();
                }
                else {
                    final int numAutorizacao = Integer.parseInt(numeroAutorizacao);

                    if (numAutorizacao >= 1) {


                        reference1 = ConfiguracaoFirebase.getFirebase().child("Procedimentos");
                        final Query query = reference1.orderByChild("numeroAutorizacao").equalTo(numAutorizacao);
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    SharedPreferences settings = getSharedPreferences("numLei", MODE_PRIVATE);
                                    SharedPreferences.Editor prefEditor = settings.edit();
                                    prefEditor.putInt("num", numAutorizacao);
                                    prefEditor.commit();
                                    Toast.makeText(MainActivityMenu.this, "O seu exame já está pronto!", Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(MainActivityMenu.this, MainActivity.class);
                                    intent.putExtra("num", numAutorizacao);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(MainActivityMenu.this, "Desculpe, o seu exame ainda não está pronto!", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(MainActivityMenu.this, "Desculpe, houve um erro de comunicação com os nossos servidores!", Toast.LENGTH_SHORT).show();

                            }
                        });


                    } else {
                    }
                }
            }
        });

        flatDialog.withSecondButtonListner(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Dissmis = fechar caixa de dialogo
                flatDialog.dismiss();
            }
        });
        flatDialog.show();
    }
}







