package com.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText usuario;
    private EditText senha;
    private Button login;
    private Button cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recebendo os dados da tela
        usuario = findViewById(R.id.edtUsuario);
        senha = findViewById(R.id.edtSenha);
        login = findViewById(R.id.btnLogin);
        cadastrar = findViewById(R.id.btnCadastrar);

        // Redirecionando botões
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fazerLogin();
            }
        });

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });
    }

    /**
     * Realizar validação de entrada e redirecionando caso correto
     * para página de contatos
     */
    private void fazerLogin() {
        // TODO
        Intent it = new Intent(MainActivity.this, Contatos.class);
        startActivity(it);
    }

    /**
     * Enviar usuário para tela de cadastro
     */
    private void cadastrar() {
        Intent it = new Intent(MainActivity.this, Cadastrar.class);
        startActivity(it);
    }
}