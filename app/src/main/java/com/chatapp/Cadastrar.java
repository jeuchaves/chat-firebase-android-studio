package com.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Cadastrar extends AppCompatActivity {

    private EditText usuario;
    private EditText senha;
    private EditText confirmaSenha;
    private Button cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        // Recebendo os dados da tela
        usuario = findViewById(R.id.edtCadastrarUsuario);
        senha = findViewById(R.id.edtCadastrarSenha);
        confirmaSenha = findViewById(R.id.edtCadastrarConfirmaSenha);
        cadastrar = findViewById(R.id.btnFinalizarCadastro);

        // Redirecionando o botão
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarUsuario();
            }
        });

    }

    /**
     * Realizar validação de entrada e caso tenha sucesso
     * cadastrar usuário no banco de dados e redirecionar para tela de contatos
     */
    private void cadastrarUsuario() {
        // TODO
        Intent it = new Intent(Cadastrar.this, Contatos.class);
        startActivity(it);
    }
}