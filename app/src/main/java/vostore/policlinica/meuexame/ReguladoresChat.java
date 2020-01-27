package vostore.policlinica.meuexame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import vostore.policlinica.meuexame.Adapter.AdapterChat;
import vostore.policlinica.meuexame.Adapter.AdapterRegulacao;
import vostore.policlinica.meuexame.Firebase.ConfiguracaoFirebase;
import vostore.policlinica.meuexame.menuComentarios.ComentarioUsuario;


public class ReguladoresChat extends AppCompatActivity    {
    private RecyclerView recyclerView,recyclerView2;
    private DatabaseReference  reference1, reference2, reference4, referenceAprendido, referenceSite, referenceSite1, referenceSite2, referenceNaoSei;
    private DatabaseReference refe1, refe2, refe3, refe4, refe5, refe6;
    ArrayList<Usuario> list;
    AdapterRegulacao adapter;
    private TextToSpeech mTTS;
    MultiFormatWriter multi = new MultiFormatWriter();
    Usuario pp;
    private LottieAnimationView enviarMensagem;
    private EditText txt_enviar;

    private ImageView logoExit;

    Context ctx;
    private AdView adView;

    private FirebaseAuth mAuth;



    ImageView qr;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.activity_main_regulacao);


        //Instanciando elementos para o envio de msg
        enviarMensagem = findViewById(R.id.btn_enviar);
        txt_enviar = findViewById(R.id.texto_enviar);
        logoExit = findViewById(R.id.logoid);


        //Instanciando elementos do layout
        qr = findViewById(R.id.id_qr_confirmado);
        recyclerView = (RecyclerView) findViewById(R.id.id_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAuth = FirebaseAuth.getInstance();

//        chat();

        MobileAds.initialize(this, "ca-app-pub-9690901635129561~5610127516");
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


            reference2 = ConfiguracaoFirebase.getFirebase().child("Usuario");
            final Query query = reference2;

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//                Toast.makeText(MainActivity.this, "true", Toast.LENGTH_SHORT).show();
                    if (dataSnapshot.exists()) {
                        //here means the value exist
                        //do whatever you want to do


                        list = new ArrayList<>();


                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            pp = dataSnapshot1.getValue(Usuario.class);
                            list.add(pp);

                        }

                        adapter = new AdapterRegulacao(ReguladoresChat.this, list);
                        recyclerView.setAdapter(adapter);








                    } else {

                        Toast.makeText(ctx, "tem", Toast.LENGTH_SHORT).show();

//                                                 Toast.makeText(MainActivity.this, "Você já viu tudo sobre "+leipreferida+".Está na hora de selecionar outra categoria!", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });









        logoExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              dialogExit();
            }
        });
    }


    @Override
    public void onBackPressed() {


        Intent intent = new Intent(ReguladoresChat.this, MainActivityMenu.class);
        startActivity(intent);
        finish();

        super.onBackPressed();

    }




    //Métodos e Funções
    public void chat() {

        //Recuperando preferencia do usuario ( numero de autorizacao inserida na tela do menu)
        SharedPreferences settingss = getSharedPreferences("numLei", MODE_PRIVATE);
        int  numAutorizacao = 1;



        //Gerandando QRCODE com o numero de autorizacao impresso
        gerarQR(numAutorizacao);



        final FirebaseUser currentUser = mAuth.getCurrentUser();


        String idUsuario = currentUser.getUid();

        refe1 = ConfiguracaoFirebase.getFirebase().child("Usuario").child(""+idUsuario);
        refe1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (txt_enviar.getText().length() > 0){

                    Usuario usuario = dataSnapshot.getValue(Usuario.class);



                    String nomeRegula = usuario.getNomeRegulacao();
                    Toast.makeText(ctx, ""+nomeRegula, Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
    public int gerarQR(int numAutorizacao) {
        try {
            BitMatrix bitMatrix = multi.encode("" + numAutorizacao, BarcodeFormat.QR_CODE, 300, 300);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(bitMatrix);
            qr.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return numAutorizacao;
    }

    public void dialogExit(){
        //Criando Dialog personalizado para a inserção do numero de autorizacãp
        final FlatDialog flatDialog = new FlatDialog(ReguladoresChat.this);
        flatDialog.setTitle("Sair do Chat!");
        flatDialog.setSubtitle("Caso confirme, você será desconectado do chat e no próximo acesso deverá efetuar o login novamente.");
        flatDialog.setFirstButtonText("Desconectar");
        flatDialog.setSecondButtonText("Apenas Voltar");
        flatDialog.withFirstButtonListner(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ReguladoresChat.this, Login.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        flatDialog.withSecondButtonListner(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Dissmis = fechar caixa de dialogo
                Intent intent = new Intent(ReguladoresChat.this, MainActivityMenu.class);
                startActivity(intent);
                finish();
            }
        });
        flatDialog.show();
    }

}







