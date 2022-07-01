package progmov20221.trabalhofinal.ejrchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText edt_email;
    private EditText edt_senha;
    private Button btn_entrar;
    private TextView txt_cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_email = findViewById(R.id.edt_email);
        edt_senha = findViewById(R.id.edt_senha);
        btn_entrar = findViewById(R.id.btn_entrar);
        txt_cadastrar = findViewById(R.id.txt_cadastrar);

        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        txt_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(LoginActivity.this, CadastrarActivity.class);
                startActivity(it);
            }
        });
    }
}