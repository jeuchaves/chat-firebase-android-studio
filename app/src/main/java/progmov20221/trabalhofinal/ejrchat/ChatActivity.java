package progmov20221.trabalhofinal.ejrchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;

import progmov20221.trabalhofinal.ejrchat.model.Usuario;

public class ChatActivity extends AppCompatActivity {

    private GroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Usuario usuario = getIntent().getExtras().getParcelable("usuario");
        getSupportActionBar().setTitle(usuario.getNome());

        RecyclerView rv = findViewById(R.id.recycler_chat);

        adapter = new GroupAdapter();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        adapter.add(new ItemMensagem(true));
        adapter.add(new ItemMensagem(true));
        adapter.add(new ItemMensagem(false));
        adapter.add(new ItemMensagem(true));
        adapter.add(new ItemMensagem(false));
        adapter.add(new ItemMensagem(true));
        adapter.add(new ItemMensagem(true));
        adapter.add(new ItemMensagem(false));
        adapter.add(new ItemMensagem(true));
        adapter.add(new ItemMensagem(false));
    }

    private class ItemMensagem extends Item {

        private final boolean foiEnviado;

        private ItemMensagem(boolean foiEnviado) {
            this.foiEnviado = foiEnviado;
        }

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) { }

        @Override
        public int getLayout() {
            return foiEnviado ? R.layout.item_mensagem_enviada : R.layout.item_mensagem_recebida;
        }
    }
}