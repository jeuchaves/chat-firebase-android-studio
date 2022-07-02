package progmov20221.trabalhofinal.ejrchat.model;

public class Mensagem {

    private String texto;
    private long timestamp;
    private String idRecebido;
    private String idEnviado;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getIdRecebido() {
        return idRecebido;
    }

    public void setIdRecebido(String idRecebido) {
        this.idRecebido = idRecebido;
    }

    public String getIdEnviado() {
        return idEnviado;
    }

    public void setIdEnviado(String idEnviado) {
        this.idEnviado = idEnviado;
    }
}
