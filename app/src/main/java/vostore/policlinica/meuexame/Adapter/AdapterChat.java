package vostore.policlinica.meuexame.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
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

import vostore.policlinica.meuexame.Chat;
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
    public void onBindViewHolder(final AdapterChat.MyViewHolder holder, final int position) {

        user = FirebaseAuth.getInstance().getCurrentUser();
        idDoUsuario = user.getUid();



        // O Holder é responsável por instanciar os elementos do layout.

        holder.profileNome.setText(Experts.get(position).getNomeRegulacao()+ "\n disse:");
        holder.profileComentario.setText(Experts.get(position).getComentarioUsuario());
        holder.profileData.setText(Experts.get(position).getDataHora());
        solucionado = (Experts.get(position).getSolucionado());
        Picasso.get().load(Experts.get(position).getUrlImagem()).into(holder.profilePic);
        mAuth = FirebaseAuth.getInstance();
        final Boolean problemaSolucao = Experts.get(position).getSolucionado();


//        if (tipoComentario == "padrao"){
//        holder.imgUsuarioVip.setVisibility(View.VISIBLE);
//        }
//        else {
//            holder.imgUsuarioVip.setVisibility(View.GONE);
//
//
//        }

        holder.profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, ""+Experts.get(position).getIdChat(), Toast.LENGTH_SHORT).show();
            }
        });



//        if (Experts.get(position).getNomeRegulacao() == "Policlinica Regional de Alagoinhas"){
//            holder.btnSolucionado.setVisibility(View.GONE);
//
//        }
        if (solucionado == true ){
            holder.btnSolucionado.setFrame(13);
            holder.txtProblemaSituacao.setText("Problema já resolvido!");

        }else{
            holder.btnSolucionado.setFrame(52);
            holder.txtProblemaSituacao.setText("Problema não solucionado!");

        }
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        String idUsuario = currentUser.getUid();
        reference4 = ConfiguracaoFirebase.getFirebase().child("Usuario").child(""+idUsuario);
        reference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                boolean perfil = usuario.getPerfilAdministrador();

                String nomePoli = "Policlínica Regional de Alagoinhas";

//                Toast.makeText(context, ""+perfil, Toast.LENGTH_SHORT).show();
                if (perfil == true) {


                    SharedPreferences settingss = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
                    final String nomeRegula = settingss.getString("pref", "Policlinica Regional de Alagoinhas");

                    if (solucionado == false) {


                        holder.btnSolucionado.setFrame(52);

                        holder.txtProblemaSituacao.setText("Problema solucionado!");


//                        Toast.makeText(context, "" + nomeRegula, Toast.LENGTH_SHORT).show();

                    }


                    holder.btnSolucionado.setFrame(13);

                }else {
//                    Toast.makeText(context, "Apenas a Policlínica poderá mudar o status do problema!", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


            holder.btnSolucionado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    final FirebaseUser currentUser = mAuth.getCurrentUser();
                    String idUsuario = currentUser.getUid();
                    reference4 = ConfiguracaoFirebase.getFirebase().child("Usuario").child(""+idUsuario);
                    reference4.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                            Usuario usuario = dataSnapshot.getValue(Usuario.class);
                            boolean perfil = usuario.getPerfilAdministrador();

                            String nomePoli = "Policlínica Regional de Alagoinhas";

//                            Toast.makeText(context, ""+perfil, Toast.LENGTH_SHORT).show();
                            if (perfil == true) {


                                SharedPreferences settingss = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
                                final String nomeRegula = settingss.getString("pref", "Policlinica Regional de Alagoinhas");

                                if (solucionado == false) {


                                    holder.btnSolucionado.setFrame(52);

                                    holder.txtProblemaSituacao.setText("Problema solucionado!");


                                    Toast.makeText(context, "" + nomeRegula, Toast.LENGTH_SHORT).show();
                                    reference2 = ConfiguracaoFirebase.getFirebase().child("Chat").child(nomeRegula);
                                    final Query query = reference2.orderByChild("idChat").equalTo(Experts.get(position).getIdChat()).limitToFirst(1);

                                    query.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            if (dataSnapshot.exists()) {

                                                Experts.get(position).setSolucionado(true);
                                                reference2 = ConfiguracaoFirebase.getFirebase().child("Chat").child(nomeRegula).child(Experts.get(position).getIdChat());
                                                reference2.setValue(Experts.get(position));


                                            } else {


//                                                 Toast.makeText(MainActivity.this, "Você já viu tudo sobre "+leipreferida+".Está na hora de selecionar outra categoria!", Toast.LENGTH_SHORT).show();

                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }


                                holder.btnSolucionado.setFrame(13);

                            }else {
                                Toast.makeText(context, "Apenas a Policlínica poderá mudar o status do problema!", Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });



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

    private void ocultarEmoticoin (){

    }
}
