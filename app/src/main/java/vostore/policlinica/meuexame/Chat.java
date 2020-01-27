package vostore.policlinica.meuexame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.webkit.WebSettings;
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
import com.google.firebase.database.FirebaseDatabase;
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
import vostore.policlinica.meuexame.Adapter.AdapterExames;
import vostore.policlinica.meuexame.Adapter.AdapterRegulacao;
import vostore.policlinica.meuexame.Firebase.ConfiguracaoFirebase;
import vostore.policlinica.meuexame.Model.ClasseExame;
import vostore.policlinica.meuexame.menuComentarios.ComentarioUsuario;


public class Chat extends AppCompatActivity    {
    private RecyclerView recyclerView,recyclerView2;
    private DatabaseReference  reference1, reference2, reference4, referenceAprendido, referenceSite, referenceSite1, referenceSite2, referenceNaoSei;
    private DatabaseReference refe1, refe2, refe3, refe4, refe5, refe6;
    ArrayList<ComentarioUsuario> list;
    AdapterChat adapter;
    private TextToSpeech mTTS;
    MultiFormatWriter multi = new MultiFormatWriter();
    ComentarioUsuario pp;
    private LottieAnimationView enviarMensagem;
    private EditText txt_enviar;

    private ImageView logoExit;
    private FirebaseDatabase database;

    Context ctx;
    private AdView adView;

    private FirebaseAuth mAuth;



    ImageView qr;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.activity_main_chat);


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


        database = FirebaseDatabase.getInstance();

        MobileAds.initialize(this, "ca-app-pub-9690901635129561~5610127516");
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        if (null != getIntent()) {
            /** Pegamos o VALOR_1**/
            final String nomeRegulacao = getIntent().getStringExtra("site");

            final FirebaseUser currentUser = mAuth.getCurrentUser();


            SharedPreferences.Editor editor = getSharedPreferences("pref", MODE_PRIVATE).edit();
            editor.putString("pref", nomeRegulacao);
            editor.commit();
            String idUsuario = currentUser.getUid();
            reference2 = ConfiguracaoFirebase.getFirebase().child("Chat").child(nomeRegulacao);
            final Query query = reference2;

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//                Toast.makeText(MainActivity.this, "true", Toast.LENGTH_SHORT).show();
                    if (dataSnapshot.exists()) {
                        //here means the value exist
                        //do whatever you want to do


                        list = new ArrayList<ComentarioUsuario>();


                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            pp = dataSnapshot1.getValue(ComentarioUsuario.class);
                            list.add(pp);

                        }

                        adapter = new AdapterChat(Chat.this, list);
                        recyclerView.setAdapter(adapter);


                    } else {


//                                                 Toast.makeText(MainActivity.this, "Você já viu tudo sobre "+leipreferida+".Está na hora de selecionar outra categoria!", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

                }






        enviarMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enviarMensagem.playAnimation();
            enviarMensagem();



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


       voltarPoliclinica2();

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
//                    Toast.makeText(ctx, ""+nomeRegula, Toast.LENGTH_SHORT).show();


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
    public void enviarMensagem (){
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        final String idUsuario = currentUser.getUid();

        reference4 = ConfiguracaoFirebase.getFirebase().child("Usuario").child(""+idUsuario);
        reference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                if (txt_enviar.getText().length() > 0){

                    final Usuario usuario = dataSnapshot.getValue(Usuario.class);
                    reference4.setValue(usuario);


                        if (null != getIntent()) {

                            final String nomeRegulacao = getIntent().getStringExtra("site");

                            final FirebaseUser currentUser = mAuth.getCurrentUser();

                            String idUsuario = currentUser.getUid();


                            SimpleDateFormat formataData2 = new SimpleDateFormat("ddMMyyyy");
                            Date data2 = new Date();
                            String dataFormatada2 = formataData2.format(data2);
                            SimpleDateFormat sdf1 = new SimpleDateFormat("HHmmss");
                            Date hora1 = Calendar.getInstance().getTime(); // Ou qualquer outra forma que tem
                            String dataFormatada11 = sdf1.format(hora1);
                            String idObjeto = dataFormatada2 + dataFormatada11;
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                            Date hora = Calendar.getInstance().getTime(); // Ou qualquer outra forma que tem
                            final String dataFormatada1 = sdf.format(hora);
                            //Dia Mes e Ano
                            SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
                            Date data = new Date();
                            String dataFormatada = formataData.format(data);

                            reference4 = ConfiguracaoFirebase.getFirebase().child("Chat").child(""+nomeRegulacao).child(idObjeto);
                            NovaMensagem novaMensagem = new NovaMensagem();

                            final String ultimaMsg = txt_enviar.getText().toString();


                            novaMensagem.setDataHora(dataFormatada + " " + "às " + dataFormatada1);
                            novaMensagem.setComentarioUsuario(txt_enviar.getText().toString());
                            novaMensagem.setEmail(usuario.getEmail());
                            novaMensagem.setId(usuario.getId());
                            novaMensagem.setNomeRegulacao(usuario.getNomeRegulacao());
                            novaMensagem.setResponsavelRegulacao(usuario.getResponsavelRegulacao());
                            novaMensagem.setUrlImagem(usuario.getImagemUsuario());
                            novaMensagem.setIdChat(""+idObjeto);
                            novaMensagem.setSolucionado(false);
                            reference4.setValue(novaMensagem);

                            txt_enviar.setText("");

                            Toast.makeText(Chat.this, "Mensagem enviada!", Toast.LENGTH_SHORT).show();






                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    public void dialogExit(){
        //Criando Dialog personalizado para a inserção do numero de autorizacãp
        final FlatDialog flatDialog = new FlatDialog(Chat.this);
        flatDialog.setTitle("Sair do Chat!");
        flatDialog.setSubtitle("Caso confirme, você será desconectado do chat e no próximo acesso deverá efetuar o login novamente.");
        flatDialog.setFirstButtonText("Desconectar");
        flatDialog.setSecondButtonText("Apenas Voltar");
        flatDialog.withFirstButtonListner(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
voltarPoliclinica();
            }
        });

        flatDialog.withSecondButtonListner(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Dissmis = fechar caixa de dialogo
                Intent intent = new Intent(Chat.this, MainActivityMenu.class);
                startActivity(intent);
                finish();
            }
        });
        flatDialog.show();
    }

    public void voltarPoliclinica2 (){
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        final String idUsuario = currentUser.getUid();

        reference4 = ConfiguracaoFirebase.getFirebase().child("Usuario").child(""+idUsuario);
        reference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    Usuario usuario = dataSnapshot.getValue(Usuario.class);

                    if (usuario.getPerfilAdministrador() == true ){

                        Intent intent = new Intent(Chat.this, ReguladoresChat.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Intent intent = new Intent(Chat.this, MainActivityMenu.class);
                        startActivity(intent);
                        finish();
                    }




                }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
    public void voltarPoliclinica (){
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        final String idUsuario = currentUser.getUid();

        reference4 = ConfiguracaoFirebase.getFirebase().child("Usuario").child(""+idUsuario);
        reference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    Usuario usuario = dataSnapshot.getValue(Usuario.class);

                    if (usuario.getNomeRegulacao() == "Policlínica Regional de Alagoinhas"){

                        Intent intent = new Intent(Chat.this, ReguladoresChat.class);
                        startActivity(intent);
                        mAuth.signOut();
                        FirebaseAuth.getInstance().signOut();;
                        finish();
                    }
                    else{
                        Intent intent = new Intent(Chat.this, MainActivityMenu.class);
                        startActivity(intent);
                        mAuth.signOut();
                        FirebaseAuth.getInstance().signOut();
                        finish();
                    }




                }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

}





