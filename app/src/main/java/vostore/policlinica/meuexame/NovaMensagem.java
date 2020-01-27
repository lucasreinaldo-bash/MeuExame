package vostore.policlinica.meuexame;

public class NovaMensagem {

    private String nomeRegulacao;
    private String responsavelRegulacao;
    private String email;
    private String imagemUsuario;
    private String id;
    private String comentarioUsuario;
    private String dataHora;
    private Boolean solucionado;
    private String urlImagem;
    private String idChat;

    public String getIdChat() {
        return idChat;
    }

    public void setIdChat(String idChat) {
        this.idChat = idChat;
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

    public String getComentarioUsuario() {
        return comentarioUsuario;
    }

    public void setComentarioUsuario(String comentarioUsuario) {
        this.comentarioUsuario = comentarioUsuario;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public Boolean getSolucionado() {
        return solucionado;
    }

    public void setSolucionado(Boolean solucionado) {
        this.solucionado = solucionado;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public NovaMensagem(String nomeRegulacao,String idChat, String responsavelRegulacao, String email, String imagemUsuario, String id, String comentarioUsuario, String dataHora, Boolean solucionado, String urlImagem) {
        this.nomeRegulacao = nomeRegulacao;
        this.responsavelRegulacao = responsavelRegulacao;
        this.email = email;
        this.imagemUsuario = imagemUsuario;
        this.id = id;
        this.comentarioUsuario = comentarioUsuario;
        this.dataHora = dataHora;
        this.solucionado = solucionado;
        this.urlImagem = urlImagem;
        this.idChat = idChat;
    }

    public NovaMensagem() {

    }


}