package vostore.policlinica.meuexame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import vostore.policlinica.meuexame.MainActivity;
import vostore.policlinica.meuexame.R;

/**
 * Essa classe é utilizada como tela inicial. Possui uma animação e faz transição após 3 segundos para a ValidarCPF
 */
public class SplashBoasVindas extends AppCompatActivity implements Runnable {


    private Button btnInsta, btnFace,btnLinke;

    ImageView top;
    Animation fromlogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash2);

        //fAZENDO cAST

        //Determinando o tempo de 3 segundos para entrar na próxima activity

        Handler handler = new Handler();
        handler.postDelayed(this, 3000);

        //Ações dos buttons
/*
        btnInsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(vostore.policlinica.meuexame.Splash.this, Site.class);
                intent.putExtra("site","https://www.instagram.com/hospital_inc/");
                startActivity(intent);
                finish();
            }
        });
        btnFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(vostore.policlinica.meuexame.Splash.this, Site.class);
                intent.putExtra("site","https://www.facebook.com/InstitutoDeNeurologiaDeCuritiba/?ref=br_rs");
                startActivity(intent);
                finish();
            }
        });
        btnLinke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(vostore.policlinica.meuexame.Splash.this, Site.class);
                intent.putExtra("site","https://br.linkedin.com/company/hospitalinc");
                startActivity(intent);
                finish();
            }
        });
        */
    }



    //Usando intent no método run
    public void run(){
        startActivity(new Intent(this, MainActivityMenu.class));
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}