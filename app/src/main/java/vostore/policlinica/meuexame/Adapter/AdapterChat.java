package vostore.policlinica.meuexame.Adapter;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;

import vostore.policlinica.meuexame.Firebase.ConfiguracaoFirebase;
import vostore.policlinica.meuexame.R;
import vostore.policlinica.meuexame.Usuario;
import vostore.policlinica.meuexame.menuComentarios.ComentarioUsuario;


public class AdapterChat extends RecyclerView.Adapter<AdapterChat.MyViewHolder> {

    Context context;
    ArrayList<ComentarioUsuario> Experts;

    Boolean solucionado;

    private TextToSpeech mTTS;
    private FirebaseAuth mAuth;
    private DatabaseReference reference,reference1,reference2,reference4,referenceAprendido,referenceSite,referenceSite1,referenceSite2,referenceNaoSei;
    private FirebaseUser user;
    final Usuario usuario = new Usuario();
    private boolean ultrapassou = false;
    String teste,leiAtualAtualizar;
    ArrayList<ComentarioUsuario> list3;
    ComentarioUsuario ppp;
    String id = "1";
    AdapterChat adapter2;
//    AdapterLeis adapter;






    String idDoUsuario = "";
    public AdapterChat(Context c, ArrayList<ComentarioUsuario> p) {
        context = c;
        Experts = p;
    }

    @Override
    public AdapterChat.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.exame_layout_chat,parent,false));
    }

    @Override
    public void onBindViewHolder(final AdapterChat.MyViewHolder holder, int position) {

        user = FirebaseAuth.getInstance().getCurrentUser();
        idDoUsuario = user.getUid();



        // O Holder é responsável por instanciar os elementos do layout.

        holder.profileNome.setText(Experts.get(position).getNomeRegulacao()+ " disse:");
        holder.profileComentario.setText(Experts.get(position).getComentarioUsuario());
        holder.profileData.setText(Experts.get(position).getDataHora());
        solucionado = (Experts.get(position).getSolucionado());
        Picasso.get().load(Experts.get(position).getUrlImagem()).into(holder.profilePic);
        mAuth = FirebaseAuth.getInstance();
        String tipoComentario = Experts.get(position).getTipoComentario();


        if (tipoComentario == "padrao"){
        holder.imgUsuarioVip.setVisibility(View.VISIBLE);
        }
        else {
            holder.imgUsuarioVip.setVisibility(View.GONE);


        }

        if (Experts.get(position).getNomeRegulacao() != "Policlinica Regional de Alagoinhas"){
            holder.btnSolucionado.setVisibility(View.VISIBLE);
            Toast.makeText(context, "Hehe", Toast.LENGTH_SHORT).show();
        }else {
            holder.btnSolucionado.setVisibility(View.GONE);        }


        if (solucionado == true ){
            holder.btnSolucionado.setFrame(13);
            holder.txtProblemaSituacao.setText("Problema já resolvido!");

        }else{
            holder.btnSolucionado.setFrame(52);
            holder.txtProblemaSituacao.setText("Problema não solucionado!");

        }
        holder.btnSolucionado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (solucionado == true){

                    solucionado = false;

                    holder.btnSolucionado.setFrame(52);

                    holder.txtProblemaSituacao.setText("Problema não solucionado!");

                    reference1 = ConfiguracaoFirebase.getFirebase().child("Chat").child("Regulacao Alagoinhas");
                    final Query query = reference1.orderByChild("dataHora").equalTo("22/12/2019").limitToFirst(1);
                    query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        reference1.setValue(Experts);


                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });}
                else{
                    solucionado = true;

                    holder.btnSolucionado.setFrame(13);


                    holder.txtProblemaSituacao.setText("Problema já resolvido!");
                }




            }
        });

//        //Como no Android não há uma forma nativa de justificar um TextView, usei um WebView e manipulei a string usando o HTML
//        String text = "<html><body>"
//                + "<p align=\"justify\">"
//                + Experts.get(position).getComentarioUsuario()
//                + "</p> "
//                + "</body></html>";
//
//        holder.profileComentario.loadData(text,"text/html","iso-8859-1");





        //As linhas de codigos seguinte serve para filtras os textos que serao pronunciados, para evitar que a assistente virtual pronuncie palavras desnecessarias

//        final String formatarArtigo = finalLeiArtigo.replace("<p style=“text-align: justify;”>","");
//        final String formatarArtigo2 = formatarArtigo.replace("-","");
//        final String formatarArtigo3 = formatarArtigo2.replace("I","");
//        final String formatarArtigo4 = formatarArtigo3.replace("Art.","Artigo");
//
//

        //Ação no Firebase que ocorrerá após o usuário marcar o checkBox Aprendido


        //Exibir/Ocultar Comentarios








    }

    @Override
    public int getItemCount() {
        return Experts.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder
    {



        private TextView profileComentario;
        private TextView txtProblemaSituacao;
        private TextView profileNome,profileData;
        private AvatarImageView profilePic;
        private ImageView imgUsuarioVip;
        private LottieAnimationView btnSolucionado;

        public MyViewHolder(View itemView) {
            super(itemView);




            txtProblemaSituacao = itemView.findViewById(R.id.txt_situacao_problema);
            btnSolucionado = itemView.findViewById(R.id.btn_solucionado);
            profileNome = itemView.findViewById(R.id.profile_nome);
            profileComentario = itemView.findViewById(R.id.profile_comentario);
            profileData = itemView.findViewById(R.id.profile_data);
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
}
