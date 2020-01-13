package vostore.policlinica.meuexame;

import com.google.firebase.database.DatabaseReference;

import vostore.policlinica.meuexame.Firebase.ConfiguracaoFirebase;

public class Usuario {

    private String nome;
    private String tipoUsuario;
    private String imageUsuario;
    private String email;
    private String numeroLei;
    private String senha;
    private String contrasenha;
    private String LeiFavorita;
    private String fotoPerfilChat;
    private String id;


    public String getFotoPerfilChat() {
        return fotoPerfilChat;
    }

    public Usuario() {

    }

    public Usuario(String id, String nome, String imageUsuario, String LeiFavorita, String numeroLei, String tipoUsuario, String email, String senha, String contrasenha, String fotoPerfilChat) {
        this.nome = nome;
        this.email = email;
        this.tipoUsuario = tipoUsuario;
        this.senha = senha;
        this.LeiFavorita = LeiFavorita;
        this.numeroLei = numeroLei;
        this.contrasenha = contrasenha;
        this.fotoPerfilChat = fotoPerfilChat;
        this.id = id;
        this.imageUsuario = imageUsuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeiFavorita() {
        return LeiFavorita;
    }

    public String getImageUsuario() {
        return imageUsuario;
    }

    public void setImageUsuario(String imageUsuario) {
        this.imageUsuario = imageUsuario;
    }

    public void setLeiFavorita(String leiFavorita) {
        LeiFavorita = leiFavorita;
    }

    public String getNumeroLei() {
        return numeroLei;
    }

    public void setNumeroLei(String numeroLei) {
        this.numeroLei = numeroLei;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public void setFotoPerfilChat(String fotoPerfilChat) {
        this.fotoPerfilChat = fotoPerfilChat;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void saveUser() {
        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebase();
        databaseReference.child("Usuario").child(getId()).setValue(this);
    }
}