package com.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class Chat extends AppCompatActivity {

    private Button voltar;
    private Button enviar;
    private EditText mensagem;
    private ListView conversa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Recebendo os dados da tela
        voltar = findViewById(R.id.btnVoltar);
        enviar = findViewById(R.id.btnEnviar);
        mensagem = findViewById(R.id.edtMensagem);
        conversa = findViewById(R.id.listConversa);

        // Redirecionando botões
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Chat.this, Contatos.class);
                startActivity(it);
            }
        });
    }
}