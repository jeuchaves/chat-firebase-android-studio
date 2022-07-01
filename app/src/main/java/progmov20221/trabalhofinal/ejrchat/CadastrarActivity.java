package progmov20221.trabalhofinal.ejrchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import progmov20221.trabalhofinal.ejrchat.model.Usuario;

public class CadastrarActivity extends AppCompatActivity {

    private EditText edt_usuario;
    private EditText edt_email;
    private EditText edt_senha;
    private Button btn_cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        edt_usuario = findViewById(R.id.edt_usuario_cadastro);
        edt_email = findViewById(R.id.edt_email_cadastro);
        edt_senha = findViewById(R.id.edt_senha_cadastro);
        btn_cadastrar = findViewById(R.id.btn_cadastrar);

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
            Log.i("Teste", "Nome, email e senha devem ser preenchidos");
            return;
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.i("Teste", task.getResult().getUser().getUid());
                            salvarUsuarioFireBase();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Teste", e.getMessage());
                    }
                });
    }

    private void salvarUsuarioFireBase() {
        String uuid = FirebaseAuth.getInstance().getUid();
        String nome = edt_usuario.getText().toString();
        Usuario usuario = new Usuario(uuid, nome);

        FirebaseFirestore.getInstance().collection("usuarios")
                .add(usuario)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i("Teste", documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Teste", e.getMessage());
                    }
                });
    }
}