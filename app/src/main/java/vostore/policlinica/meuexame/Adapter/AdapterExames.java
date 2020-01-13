package vostore.policlinica.meuexame.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.abdularis.civ.AvatarImageView;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;


import vostore.policlinica.meuexame.Model.ClasseExame;
import vostore.policlinica.meuexame.R;

import static android.content.Context.MODE_PRIVATE;


public class AdapterExames extends RecyclerView.Adapter<AdapterExames.MyViewHolder> {

    Context context;
    ArrayList<ClasseExame> Experts;


    private TextToSpeech mTTS;
    private DatabaseReference reference,reference1,reference2,referenceAprendido,referenceSite,referenceSite1,referenceSite2,referenceNaoSei;
//    final Usuario usuario = new Usuario();
    String teste,leiAtualAtualizar;



    String idDoUsuario = "";
    public AdapterExames(Context c, ArrayList<ClasseExame> p) {
        context = c;
        Experts = p;
    }

    @Override
    public AdapterExames.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.exame_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(final AdapterExames.MyViewHolder holder, int position) {




        // O Holder é responsável por instanciar os elementos do layout.

        holder.profileData.setText(Experts.get(position).getData());
        holder.nomePaciente.setText(Experts.get(position).getNome());
        holder.procedimento.setText(Experts.get(position).getProcedimento());
        holder.profile_cidade.setText("Paciente de "+Experts.get(position).getCidade());
        holder.prontuario.setText("Prontuário número "+ Experts.get(position).getProntuario());




//        holder.profile_autorizacao.setText(Experts.get(position).getNumeroAutorizacao());
//        Picasso.get().load(Experts.get(position).getImagem()).into(holder.profilePic);


        //Como no Android não há uma forma nativa de justificar um TextView, usei um WebView e manipulei a string usando o HTML
        String text = "<html><body>"
                + "<p align=\"justify\">"
                + Experts.get(position).getResultado()
                + "</p> "
                + "</body></html>";


        holder.mWebView.loadData(text,"text/html","iso-8859-1");






        holder.textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR){
                    float velocidade = 0.8f;
                    holder.textToSpeech.setLanguage(Locale.getDefault());
                    holder.textToSpeech.setSpeechRate(velocidade);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return Experts.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder
    {


        TextView autorizacao,profile_cidade,prontuario,mensagem,nomePaciente,procedimento,profileData;
        AvatarImageView profilePic;
        ImageView qr;
        Button btn;
        LinearLayout linearLayoutComentario;
        private WebView mWebView;
        Context context;
        TextToSpeech textToSpeech;
        AvatarImageView btnSpeak;
        public CheckBox checkBoxAprendidoViewHolder, checkBoxNaoSeiViewHolder;
        public MyViewHolder(View itemView) {
            super(itemView);




            //Fazendo cast dos elementos do layout

            btnSpeak = itemView.findViewById(R.id.profile_image);
            mWebView = (WebView) itemView.findViewById(R.id.webview);
            nomePaciente = (TextView) itemView.findViewById(R.id.profile_nome_paciente);
//          mensagem = (TextView) itemView.findViewById(R.id.profile_mensagem);
            procedimento = (TextView) itemView.findViewById(R.id.profile_procedimento);
            profile_cidade = (TextView) itemView.findViewById(R.id.profile_cidade);
            prontuario = (TextView) itemView.findViewById(R.id.profile_prontuario);

            autorizacao = (TextView) itemView.findViewById(R.id.profile_autorizacao);
            profileData = (TextView) itemView.findViewById(R.id.profile_data);
            profilePic =(AvatarImageView) itemView.findViewById(R.id.profile_image);
            // btn = (Button) itemView.findViewById(R.id.checkDetails);
        }

    }

}
