package progmov20221.trabalhofinal.ejrchat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;

import java.util.List;

import progmov20221.trabalhofinal.ejrchat.model.Contato;
import progmov20221.trabalhofinal.ejrchat.model.Mensagem;
import progmov20221.trabalhofinal.ejrchat.model.Usuario;

public class ChatActivity extends AppCompatActivity {

    private GroupAdapter adapter;
    private EditText edt_mensagem;
    private Usuario usuario;
    private Usuario eu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        usuario = getIntent().getExtras().getParcelable("usuario");
        getSupportActionBar().setTitle(usuario.getNome());

        RecyclerView rv = findViewById(R.id.recycler_chat);
        edt_mensagem = findViewById(R.id.edt_mensagem);
        Button btn_enviar_mensagem = findViewById(R.id.btn_enviar_mensagem);
        btn_enviar_mensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarMensagem();
            }
        });

        adapter = new GroupAdapter();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        FirebaseFirestore.getInstance().collection("/usuarios")
                .document(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        eu = documentSnapshot.toObject(Usuario.class);
                        buscarMensagens();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("teste", e.getMessage());
                    }
                });
    }

    private void buscarMensagens() {
        if(eu != null) {
            String idEnviado = eu.getUuid();
            String idRecebido = usuario.getUuid();
            FirebaseFirestore.getInstance().collection("/conversas")
                    .document(idEnviado)
                    .collection(idRecebido)
                    .orderBy("timestamp", Query.Direction.ASCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            List<DocumentChange> documentChanges = value.getDocumentChanges();
                            if(documentChanges != null) {
                                for (DocumentChange doc : documentChanges) {
                                    if (doc.getType() == DocumentChange.Type.ADDED) {
                                        Mensagem mensagem = doc.getDocument().toObject(Mensagem.class);
                                        adapter.add(new ItemMensagem(mensagem));
                                    }
                                }
                            }
                        }
                    });
        }
    }

    private void enviarMensagem() {
        String texto = edt_mensagem.getText().toString();
        edt_mensagem.setText("");
        String idEnviado = FirebaseAuth.getInstance().getUid();
        String idRecebido = usuario.getUuid();
        long timestamp = System.currentTimeMillis();

        Mensagem mensagem = new Mensagem();
        mensagem.setIdEnviado(idEnviado);
        mensagem.setIdRecebido(idRecebido);
        mensagem.setTimestamp(timestamp);
        mensagem.setTexto(texto);

        if(!mensagem.getTexto().isEmpty()) {
            // Criar coleção para o idEnviado
            FirebaseFirestore.getInstance().collection("/conversas")
                    .document(idEnviado)
                    .collection(idRecebido)
                    .add(mensagem)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("teste", documentReference.getId());
                          
                            Contato contato = new Contato();
                            contato.setNome(usuario.getNome());
                            contato.setUuid(idRecebido);
                            contato.setTimestamp(mensagem.getTimestamp());
                            contato.setUltimaMensagem(mensagem.getTexto());

                            FirebaseFirestore.getInstance().collection("/ultimas-mensagens")
                                    .document(idEnviado)
                                    .collection("contatos")
                                    .document(idRecebido)
                                    .set(contato);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("teste", e.getMessage());
                        }
                    });

            // Criar coleção [duplicada] para o idRecebido
            FirebaseFirestore.getInstance().collection("/conversas")
                    .document(idRecebido)
                    .collection(idEnviado)
                    .add(mensagem)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("teste", documentReference.getId());

                            Contato contato = new Contato();
                            contato.setNome(usuario.getNome());
                            contato.setUuid(idRecebido);
                            contato.setTimestamp(mensagem.getTimestamp());
                            contato.setUltimaMensagem(mensagem.getTexto());

                            FirebaseFirestore.getInstance().collection("/ultimas-mensagens")
                                    .document(idRecebido)
                                    .collection("contatos")
                                    .document(idEnviado)
                                    .set(contato);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("teste", e.getMessage());
                        }
                    });
        }
    }

    private class ItemMensagem extends Item {

        private final Mensagem mensagem;

        private ItemMensagem(Mensagem mensagem) {
            this.mensagem = mensagem;
        }

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
            TextView txt_mensagem = viewHolder.itemView.findViewById(R.id.txt_mensagem);
            if(!mensagem.getIdEnviado().equals(eu.getUuid())) {
                TextView txt_mensagem_nome = viewHolder.itemView.findViewById(R.id.txt_mensagem_nome);
                txt_mensagem_nome.setText(usuario.getNome() + " disse:");
            }
            txt_mensagem.setText(mensagem.getTexto());
        }

        @Override
        public int getLayout() {
            return mensagem.getIdEnviado().equals(FirebaseAuth.getInstance().getUid())
                    ? R.layout.item_mensagem_enviada
                    : R.layout.item_mensagem_recebida;
        }
    }
}