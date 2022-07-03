package progmov20221.trabalhofinal.ejrchat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import progmov20221.trabalhofinal.ejrchat.model.Usuario;

public class CadastrarActivity extends AppCompatActivity {

    private EditText edt_usuario;
    private EditText edt_email;
    private EditText edt_senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        edt_usuario = findViewById(R.id.edt_usuario_cadastro);
        edt_email = findViewById(R.id.edt_email_cadastro);
        edt_senha = findViewById(R.id.edt_senha_cadastro);
        Button btn_cadastrar = findViewById(R.id.btn_cadastrar);

        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                criarUsuario();
            }
        });
    }

    private void criarUsuario() {
        String nome = edt_usuario.getText().toString();
        String email = edt_email.getText().toString();
        String senha = edt_senha.getText().toString();

        if(nome == null || email == null || senha == null || nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Nome, email e senha devem ser preenchidos", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            salvarUsuarioFireBase();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Teste", "criarUsuario(): " + e.getMessage());
                        Toast.makeText(CadastrarActivity.this, "Verifique os dados e tente novamente", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void salvarUsuarioFireBase() {
        String uuid = FirebaseAuth.getInstance().getUid();
        String nome = edt_usuario.getText().toString();
        Usuario usuario = new Usuario(uuid, nome);

        FirebaseFirestore.getInstance().collection("usuarios")
                .document(uuid)
                .set(usuario)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Intent intent = new Intent(CadastrarActivity.this, MensagensActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Teste", "salvarUsuario(): " + e.getMessage());
                        Toast.makeText(CadastrarActivity.this, "Erro ao se conectar ao Firebase, tente novamente mais tarde", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}