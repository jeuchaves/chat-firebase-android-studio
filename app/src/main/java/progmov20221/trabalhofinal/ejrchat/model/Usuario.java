package progmov20221.trabalhofinal.ejrchat.model;

public class Usuario {

    private final String uuid;
    private final String nome;

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
