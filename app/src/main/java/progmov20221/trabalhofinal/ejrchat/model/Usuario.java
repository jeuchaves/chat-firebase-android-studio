package progmov20221.trabalhofinal.ejrchat.model;

public class Usuario {

    private String uuid;
    private String nome;

    public Usuario() {}

    public Usuario(String uuid, String nome) {
        this.uuid = uuid;
        this.nome = nome;
    }

    public String getUuid() {
        return uuid;
    }

    public String getNome() {
        return nome;
    }
}
