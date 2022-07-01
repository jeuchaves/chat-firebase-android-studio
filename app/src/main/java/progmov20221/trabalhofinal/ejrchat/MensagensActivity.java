package progmov20221.trabalhofinal.ejrchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

public class MensagensActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensagens);

        verificarAutenticacao();
    }

    private void verificarAutenticacao() {
        if(FirebaseAuth.getInstance().getUid() == null) {
            Intent it = new Intent(MensagensActivity.this, LoginActivity.class);
            it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(it);
        }
    }
}