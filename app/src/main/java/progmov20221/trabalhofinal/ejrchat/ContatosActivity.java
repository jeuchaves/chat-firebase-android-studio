package progmov20221.trabalhofinal.ejrchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
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

import progmov20221.trabalhofinal.ejrchat.model.Usuario;

public class ContatosActivity extends AppCompatActivity {

    private GroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatos);

        RecyclerView rv = findViewById(R.id.recycler);
        adapter = new GroupAdapter();
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                Intent intent = new Intent(ContatosActivity.this, ChatActivity.class);

                UsuarioRecycler usuarioRecycler = (UsuarioRecycler) item;
                intent.putExtra("usuario", usuarioRecycler.usuario);
                startActivity(intent);
            }
        });

        buscarUsuarios();
    }

    private void buscarUsuarios() {
        FirebaseFirestore.getInstance().collection("/usuarios")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null) {
                            Log.e("Teste", error.getMessage(), error);
                            return;
                        }

                        List<DocumentSnapshot> docs = value.getDocuments();
                        for(DocumentSnapshot doc : docs) {
                            Usuario usuario = doc.toObject(Usuario.class);
                            if(!usuario.getUuid().equals(FirebaseAuth.getInstance().getUid()))
                                adapter.add(new UsuarioRecycler(usuario));
                            Log.d("Teste", usuario.getNome());
                        }
                    }
                });
    }

    private class UsuarioRecycler extends Item {

        private final Usuario usuario;

        private UsuarioRecycler(Usuario usuario) {
            this.usuario = usuario;
        }

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
            TextView txt_nome_usuario = viewHolder.itemView.findViewById(R.id.txt_nome_usuario);
            txt_nome_usuario.setText(usuario.getNome());
        }

        @Override
        public int getLayout() {
            return R.layout.item_usuario;
        }
    }
}