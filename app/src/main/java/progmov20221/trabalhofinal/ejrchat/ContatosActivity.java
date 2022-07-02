package progmov20221.trabalhofinal.ejrchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;

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
            return R.layout.usuario_recycler;
        }
    }
}