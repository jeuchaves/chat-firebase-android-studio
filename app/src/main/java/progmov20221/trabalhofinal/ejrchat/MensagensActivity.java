package progmov20221.trabalhofinal.ejrchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

import java.util.List;

import progmov20221.trabalhofinal.ejrchat.model.Contato;
import progmov20221.trabalhofinal.ejrchat.model.Usuario;

public class MensagensActivity extends AppCompatActivity {

    private GroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensagens);

        RecyclerView rv = findViewById(R.id.recycler_ultimas_mensagens);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new GroupAdapter();
        rv.setAdapter(adapter);

        verificarAutenticacao();
        buscarUltimaMensagem();

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                Log.i("Teste", item.toString());
                ItemContato itemContato = (ItemContato) item;
                FirebaseFirestore.getInstance().collection("/usuarios")
                        .document(itemContato.contato.getUuid())
                        .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                Intent intent = new Intent(MensagensActivity.this, ChatActivity.class);
                                intent.putExtra("usuario", value.toObject(Usuario.class));
                                startActivity(intent);
                            }
                        });
            }
        });
    }

    private void buscarUltimaMensagem() {
        String uuid = FirebaseAuth.getInstance().getUid();
        if(uuid == null) return;
        FirebaseFirestore.getInstance().collection("/ultimas-mensagens")
                .document(uuid)
                .collection("contatos")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        List<DocumentChange> documentChanges = value.getDocumentChanges();
                        if(documentChanges != null) {
                            for(DocumentChange doc : documentChanges) {
                                if(doc.getType() == DocumentChange.Type.ADDED) {
                                    Contato contato = doc.getDocument().toObject(Contato.class);
                                    adapter.add(new ItemContato(contato));
                                }
                            }
                        }
                    }
                });
    }

    private void verificarAutenticacao() {
        if(FirebaseAuth.getInstance().getUid() == null) {
            Intent it = new Intent(MensagensActivity.this, LoginActivity.class);
            it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(it);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.contatos:
                Intent it = new Intent(MensagensActivity.this, ContatosActivity.class);
                startActivity(it);
                break;
            case R.id.sair:
                FirebaseAuth.getInstance().signOut();
                verificarAutenticacao();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ItemContato extends Item {

        private final Contato contato;

        private ItemContato(Contato contato) {
            this.contato = contato;
        }

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
            TextView txt_ultima_mensagem_nome = viewHolder.itemView.findViewById(R.id.txt_ultima_mensagem_nome);
            TextView txt_ultima_mensagem = viewHolder.itemView.findViewById(R.id.txt_ultima_mensagem);
            txt_ultima_mensagem_nome.setText(contato.getNome());
            txt_ultima_mensagem.setText(contato.getUltimaMensagem());
        }

        @Override
        public int getLayout() {
            return R.layout.item_ultimas_mensagens;
        }
    }
}