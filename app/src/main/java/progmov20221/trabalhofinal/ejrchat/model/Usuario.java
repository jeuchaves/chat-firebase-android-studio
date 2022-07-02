package progmov20221.trabalhofinal.ejrchat.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario implements Parcelable {

    private String uuid;
    private String nome;

    public Usuario() {}

    public Usuario(String uuid, String nome) {
        this.uuid = uuid;
        this.nome = nome;
    }

    protected Usuario(Parcel in) {
        uuid = in.readString();
        nome = in.readString();
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    public String getUuid() {
        return uuid;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uuid);
        parcel.writeString(nome);
    }
}
