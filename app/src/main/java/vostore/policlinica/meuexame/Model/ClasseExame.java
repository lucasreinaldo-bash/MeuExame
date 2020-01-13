package vostore.policlinica.meuexame.Model;

public class ClasseExame {
    public String nome,procedimento,cidade,resultado,data_ficha,data_laudo,data;
    public int id,numeroAutorizacao,prontuario;


    public ClasseExame() {
    }

    public ClasseExame(String nome,String cidade,int prontuario,String data_ficha,String data_laudo, String procedimento, String data, int numeroAutorizacao, String resultado) {
        this.nome = nome;
        this.procedimento = procedimento;
        this.data = data;
        this.numeroAutorizacao = numeroAutorizacao;
        this.resultado = resultado;
        this.cidade = cidade;
        this.data_ficha = data_ficha;
        this.data_laudo = data_laudo;
        this.prontuario = prontuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(String procedimento) {
        this.procedimento = procedimento;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getData_ficha() {
        return data_ficha;
    }

    public void setData_ficha(String data_ficha) {
        this.data_ficha = data_ficha;
    }

    public String getData_laudo() {
        return data_laudo;
    }

    public void setData_laudo(String data_laudo) {
        this.data_laudo = data_laudo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroAutorizacao() {
        return numeroAutorizacao;
    }

    public void setNumeroAutorizacao(int numeroAutorizacao) {
        this.numeroAutorizacao = numeroAutorizacao;
    }

    public int getProntuario() {
        return prontuario;
    }

    public void setProntuario(int prontuario) {
        this.prontuario = prontuario;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
