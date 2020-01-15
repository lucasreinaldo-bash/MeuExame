package vostore.policlinica.meuexame.menuComentarios;

public class ComentarioUsuario {
    private String categoriaUsuario;
    private int idLei;
    private String uidUsuario;
    private String nomeRegulacao;
    private String comentarioUsuario;
    private String dataHora;
    private String image;
    private String urlImagem ;
    private String tipoComentario = "padrao";
    private Boolean solucionado = false;

    public Boolean getSolucionado() {
        return solucionado;
    }

    public void setSolucionado(Boolean solucionado) {
        this.solucionado = solucionado;
    }

    public ComentarioUsuario() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ComentarioUsuario(String categoriaUsuario,Boolean solucionado, String tipoComentario, int idLei, String uidUsuario, String nomeRegulacao, String comentarioUsuario, String dataHora, String image, String urlImagem) {
        this.categoriaUsuario = categoriaUsuario;
        this.idLei = idLei;
        this.solucionado = solucionado;
        this.uidUsuario = uidUsuario;
        this.nomeRegulacao = nomeRegulacao;
        this.comentarioUsuario = comentarioUsuario;
        this.dataHora = dataHora;
        this.image = image;
        this.urlImagem = urlImagem;
        this.tipoComentario = tipoComentario;
    }

    public String getCategoriaUsuario() {
        return categoriaUsuario;
    }

    public void setCategoriaUsuario(String categoriaUsuario) {
        this.categoriaUsuario = categoriaUsuario;
    }

    public int getIdLei() {
        return idLei;
    }

    public void setIdLei(int idLei) {
        this.idLei = idLei;
    }

    public String getTipoComentario() {
        return tipoComentario;
    }

    public void setTipoComentario(String tipoComentario) {
        this.tipoComentario = tipoComentario;
    }

    public String getUidUsuario() {
        return uidUsuario;
    }
    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getDataHora() {
        return dataHora;
    }
    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUidUsuario(String uidUsuario) {
        this.uidUsuario = uidUsuario;
    }

    public String getNomeRegulacao() {
        return nomeRegulacao;
    }

    public void setNomeRegulacao(String nomeUsuario) {
        this.nomeRegulacao = nomeUsuario;
    }

    public String getComentarioUsuario() {
        return comentarioUsuario;
    }

    public void setComentarioUsuario(String comentarioUsuario) {
        this.comentarioUsuario = comentarioUsuario;
    }
}
