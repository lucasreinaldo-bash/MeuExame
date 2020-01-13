package vostore.policlinica.meuexame.Adapter;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.abdularis.civ.AvatarImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vostore.policlinica.meuexame.R;
import vostore.policlinica.meuexame.Usuario;
import vostore.policlinica.meuexame.menuComentarios.ComentarioUsuario;


public class AdapterChat extends RecyclerView.Adapter<AdapterChat.MyViewHolder> {

    Context context;
    ArrayList<ComentarioUsuario> Experts;


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
        Picasso.get().load(Experts.get(position).getUrlImagem()).into(holder.profilePic);
        mAuth = FirebaseAuth.getInstance();
        String tipoComentario = Experts.get(position).getTipoComentario();


        if (tipoComentario == "padrao"){
holder.imgUsuarioVip.setVisibility(View.VISIBLE);
        }
        else {
            holder.imgUsuarioVip.setVisibility(View.GONE);


        }

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
        private TextView profileNome,profileData;
        private AvatarImageView profilePic;
        private ImageView imgUsuarioVip;

        public MyViewHolder(View itemView) {
            super(itemView);




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
