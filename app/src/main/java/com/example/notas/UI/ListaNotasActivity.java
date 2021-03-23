package com.example.notas.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notas.R;
import com.example.notas.adapter.ListaNotaAdapter;
import com.example.notas.adapter.listener.OnItemClickListener;
import com.example.notas.dao.NotaDAO;
import com.example.notas.helper.NotaItemTouchHelperCallback;
import com.example.notas.model.Nota;

import java.util.List;

import static com.example.notas.UI.NotaActivityConstantes.CHAVE_NOTA;
import static com.example.notas.UI.NotaActivityConstantes.CHAVE_POSITION;
import static com.example.notas.UI.NotaActivityConstantes.POSITION_INVALIDA;
import static com.example.notas.UI.NotaActivityConstantes.REQUEST_CODE_ALTERA;
import static com.example.notas.UI.NotaActivityConstantes.REQUEST_CODE_INSERE_NOTA;


public class ListaNotasActivity extends AppCompatActivity {

    public static final String TITULO_NOTA_APP_BAR = "Notas";
    private ListaNotaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
        setTitle(TITULO_NOTA_APP_BAR);
        List<Nota> todasNotas = pegaTodasNotas();
        configuraRecyclerView(todasNotas);
        configuraClickInsereNota();
    }

    private void configuraClickInsereNota() {
        TextView insereNota = findViewById(R.id.lista_notas_insere_nota);
        insereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vaiParaFormularioInsere();
            }
        });
    }

    private void vaiParaFormularioInsere() {
        Intent intent = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
        startActivityForResult(intent, REQUEST_CODE_INSERE_NOTA);
    }

    private List<Nota> pegaTodasNotas() {
        NotaDAO dao = new NotaDAO();
        return dao.todos();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(ehResultadoInserNota(requestCode, resultCode, data)){
            Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
            adicionaNota(notaRecebida);
        }
        if(ehResultadoAlteraNota(requestCode, data)){
            Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
            int posicaoRecebida = data.getIntExtra(CHAVE_POSITION, POSITION_INVALIDA);
            if(ehPosicaoValida(posicaoRecebida)){
                altera(notaRecebida, posicaoRecebida);
            } else{
                Toast.makeText(this, "Ocorreu um problema na alteração da nota",
                        Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void altera(Nota nota, int posicao) {
        new NotaDAO().altera(posicao, nota);
        adapter.altera(posicao, nota);
    }

    private boolean ehPosicaoValida(int posicaoRecebida) {
        return posicaoRecebida > POSITION_INVALIDA;
    }

    private boolean ehResultadoAlteraNota(int requestCode, @Nullable Intent data) {
        return ehRequisicaoAlteraNota(requestCode) && ehRequisicaoAlteraNota(requestCode)
                && temNota(data);
    }

    private boolean ehRequisicaoAlteraNota(int requestCode) {
        return requestCode ==REQUEST_CODE_ALTERA;
    }

    private void adicionaNota(Nota nota) {
        new NotaDAO().insere(nota);
        adapter.adiciona(nota);
    }

    private boolean ehResultadoInserNota(int requestCode, int resultCode, @Nullable Intent data) {
        return ehRequisicaoInsereNota(requestCode) &&
                result_OK(resultCode, 2) && temNota(data);
    }

    private boolean temNota(@Nullable Intent data) {
        return data.hasExtra("nota");
    }

    private boolean result_OK(int resultCode, int i) {
        return resultCode == Activity.RESULT_OK;
    }

    private boolean ehRequisicaoInsereNota(int requestCode) {
        return requestCode ==REQUEST_CODE_INSERE_NOTA;
    }

    private void configuraRecyclerView(List<Nota> todasNotas) {
        RecyclerView listaNotas = findViewById(R.id.recyclerView);
        configuraAdapter(todasNotas, listaNotas);
        configuraItemTouchHelper(listaNotas);
    }

    private void configuraItemTouchHelper(RecyclerView listaNotas) {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new NotaItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(listaNotas);
    }

    private void configuraAdapter(List<Nota> todasNotas, RecyclerView listaNotas) {
        adapter = new ListaNotaAdapter(this,todasNotas);
        listaNotas.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Nota nota, int position) {
                vaiParaFormularioAltera(nota, position);
            }
        });
    }

    private void vaiParaFormularioAltera(Nota nota, int position) {
        Intent intent = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
        intent.putExtra(CHAVE_NOTA, nota);
        intent.putExtra(CHAVE_POSITION, position);
        startActivityForResult(intent, REQUEST_CODE_ALTERA);
    }
}