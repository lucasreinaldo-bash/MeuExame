package vostore.policlinica.meuexame;

import com.google.firebase.database.DatabaseReference;

import vostore.policlinica.meuexame.Firebase.ConfiguracaoFirebase;

public class Usuario {

    private String nomeRegulacao;
    private String responsavelRegulacao;
    private String email;
    private String senha;
    private String imagemUsuario;
    private String id;
    private String tipoDePerfil;
    private Boolean perfilAdministrador = false;
    private String ultimaMensagem;



    public void setPerfilAdministrador(Boolean perfilAdministrador) {
        this.perfilAdministrador = perfilAdministrador;
    }

    public Boolean getPerfilAdministrador() {
        return perfilAdministrador;
    }

    public Usuario(Boolean perfilAdministrador) {
        this.perfilAdministrador = perfilAdministrador;
    }

    public String getNomeRegulacao() {
        return nomeRegulacao;
    }

    public void setNomeRegulacao(String nomeRegulacao) {
        this.nomeRegulacao = nomeRegulacao;
    }

    public String getResponsavelRegulacao() {
        return responsavelRegulacao;
    }

    public void setResponsavelRegulacao(String responsavelRegulacao) {
        this.responsavelRegulacao = responsavelRegulacao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getImagemUsuario() {
        return imagemUsuario;
    }

    public void setImagemUsuario(String imagemUsuario) {
        this.imagemUsuario = imagemUsuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipoDePerfil() {
        return tipoDePerfil;
    }

    public void setTipoDePerfil(String tipoDePerfil) {
        this.tipoDePerfil = tipoDePerfil;
    }

    public String getUltimaMensagem() {
        return ultimaMensagem;
    }

    public void setUltimaMensagem(String ultimaMensagem) {
        this.ultimaMensagem = ultimaMensagem;
    }

    public Usuario(String nomeRegulacao, String ultimaMensagem, Boolean perfilAdministrador, String responsavelRegulacao, String email, String senha, String imagemUsuario, String id, String tipoDePerfil) {
        this.nomeRegulacao = nomeRegulacao;
        this.responsavelRegulacao = responsavelRegulacao;
        this.email = email;
        this.senha = senha;
        this.ultimaMensagem = ultimaMensagem;
        this.imagemUsuario = imagemUsuario;
        this.id = id;
        this.tipoDePerfil = tipoDePerfil;
        this.perfilAdministrador = perfilAdministrador;
    }

    public Usuario() {

    }


}