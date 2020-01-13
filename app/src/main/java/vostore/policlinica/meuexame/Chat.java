package vostore.policlinica.meuexame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
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

import java.util.ArrayList;

import vostore.policlinica.meuexame.Adapter.AdapterChat;
import vostore.policlinica.meuexame.Adapter.AdapterExames;
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


    Context ctx;
    private AdView adView;




    ImageView qr;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.activity_main_chat);


        //Instanciando elementos do layout
        qr = findViewById(R.id.id_qr_confirmado);
        recyclerView = (RecyclerView) findViewById(R.id.id_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recuperarExame();

        MobileAds.initialize(this, "ca-app-pub-9690901635129561~5610127516");
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);




    }


    @Override
    public void onBackPressed() {


        Intent intent = new Intent(Chat.this, MainActivityMenu.class);
        startActivity(intent);
        finish();

        super.onBackPressed();

    }




    //Métodos e Funções
    public void recuperarExame() {

        //Recuperando preferencia do usuario ( numero de autorizacao inserida na tela do menu)
        SharedPreferences settingss = getSharedPreferences("numLei", MODE_PRIVATE);
        int  numAutorizacao = 1;



        //Gerandando QRCODE com o numero de autorizacao impresso
        gerarQR(numAutorizacao);

        reference1 = ConfiguracaoFirebase.getFirebase().child("Chat");
        final Query query = reference1;
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
//                                                    int i =
//                                                            Toast.makeText(MainActivity.this, "Numero foi ", Toast.LENGTH_SHORT).show();

//                                                        Log.println(1,"msg",""+leipreferida);


//                                                Toast.makeText(MainActivity.this, testando, Toast.LENGTH_SHORT).show();

                    }

                    adapter = new AdapterChat(Chat.this, list);
                    recyclerView.setAdapter(adapter);


                } else {


//                                                 Toast.makeText(MainActivity.this, "Você já viu tudo sobre "+leipreferida+".Está na hora de selecionar outra categoria!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Chat.this, "Nao tem exame pronto!", Toast.LENGTH_SHORT).show();

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
}







