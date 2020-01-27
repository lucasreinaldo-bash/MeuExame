package vostore.policlinica.meuexame.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.github.abdularis.civ.AvatarImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import vostore.policlinica.meuexame.Chat;
import vostore.policlinica.meuexame.Firebase.ConfiguracaoFirebase;
import vostore.policlinica.meuexame.Login;
import vostore.policlinica.meuexame.NovaMensagem;
import vostore.policlinica.meuexame.R;
import vostore.policlinica.meuexame.ReguladoresChat;
import vostore.policlinica.meuexame.Usuario;
import vostore.policlinica.meuexame.menuComentarios.ComentarioUsuario;


public class AdapterRegulacao extends RecyclerView.Adapter<AdapterRegulacao.MyViewHolder> {

    Context context;
    ArrayList<Usuario> Experts;

    Boolean solucionado;

    private TextToSpeech mTTS;
    private FirebaseAuth mAuth;
    private DatabaseReference reference,reference1,reference2,reference4,referenceAprendido,referenceSite,referenceSite1,referenceSite2,referenceNaoSei;
    private FirebaseUser user;
    final Usuario usuario = new Usuario();
    private boolean ultrapassou = false;
    String teste,leiAtualAtualizar;
    ArrayList<Usuario> list3;
    ComentarioUsuario ppp;
    String id = "1";
    AdapterRegulacao adapter2;
//    AdapterLeis adapter;






    String idDoUsuario = "";
    public AdapterRegulacao(Context c, ArrayList<Usuario> p) {
        context = c;
        Experts = p;
    }

    @Override
    public AdapterRegulacao.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.nomes_regulacao,parent,false));
    }

    @Override
    public void onBindViewHolder(final AdapterRegulacao.MyViewHolder holder, final int position) {

        user = FirebaseAuth.getInstance().getCurrentUser();
        idDoUsuario = user.getUid();



        // O Holder é responsável por instanciar os elementos do layout.

        holder.profileNome.setText(Experts.get(position).getNomeRegulacao());
        holder.profileComentario.setText(Experts.get(position).getResponsavelRegulacao());
        holder.profileData.setText(Experts.get(position).getEmail());
        Picasso.get().load(Experts.get(position).getImagemUsuario()).into(holder.profilePic);
        mAuth = FirebaseAuth.getInstance();
        String tipoComentario = Experts.get(position).getTipoDePerfil();

//        Toast.makeText(context, ""+Experts.get(position).getNomeRegulacao(), Toast.LENGTH_SHORT).show();


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Chat.class);
                intent.putExtra("site", Experts.get(position).getNomeRegulacao());
                context.startActivity(intent);
            }
        });

        holder.profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Chat.class);
                intent.putExtra("site", Experts.get(position).getNomeRegulacao());
                context.startActivity(intent);
            }
        });

        if (Experts.get(position).getPerfilAdministrador() == true){
            holder.linear.setVisibility(View.VISIBLE);
        }
        else{
            holder.linear.setVisibility(View.VISIBLE);
        }







//                        reference2 = ConfiguracaoFirebase.getFirebase().child("Chat").child(Experts.get(position).getNomeRegulacao());
//                        final Query query = reference2.orderByChild("solucionado").equalTo(false).limitToFirst(1);
//
//                        query.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                                if (dataSnapshot.exists()) {
//
//                                    NovaMensagem novaMensagem = dataSnapshot.getValue(NovaMensagem.class);
//
////                                    Toast.makeText(context, ""+ novaMensagem.getDataHora(), Toast.LENGTH_SHORT).show();
//                                } else {
//
//
////                                                 Toast.makeText(MainActivity.this, "Você já viu tudo sobre "+leipreferida+".Está na hora de selecionar outra categoria!", Toast.LENGTH_SHORT).show();
//
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });








    }

    @Override
    public int getItemCount() {
        return Experts.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder
    {



        private TextView profileComentario;
        private TextView txtProblemaSituacao;
        private TextView profileNome,ultimaMensagem,profileData;
        private ImageView profilePic;
        private ImageView imgUsuarioVip;
        private CardView cardView;
        private LinearLayout linear;

        public MyViewHolder(View itemView) {
            super(itemView);




            txtProblemaSituacao = itemView.findViewById(R.id.txt_situacao_problema);
            profileNome = itemView.findViewById(R.id.profile_nome);
            profileComentario = itemView.findViewById(R.id.profile_comentario);
            profileData = itemView.findViewById(R.id.profile_data);
            cardView = itemView.findViewById(R.id.card);
            linear = itemView.findViewById(R.id.linear);
            ultimaMensagem = itemView.findViewById(R.id.profile_ultima_mensagem);
            profilePic = itemView.findViewById(R.id.profile_image);
            imgUsuarioVip = itemView.findViewById(R.id.logo_professor);
            // btn = (Button) itemView.findViewById(R.id.checkDetails);
        }

    }
    private void speak() {
        String text = "lucas santos reinaldo";
        float pitch = 1;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = 1;
        if (speed < 0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void ocultarComentario (){


    }

public void alterarStatus(){

}
}
