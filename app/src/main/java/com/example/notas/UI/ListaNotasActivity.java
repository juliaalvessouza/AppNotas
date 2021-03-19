package com.example.notas.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notas.R;
import com.example.notas.adapter.ListaNotaAdapter;
import com.example.notas.dao.NotaDAO;
import com.example.notas.model.Nota;

import java.util.List;

public class ListaNotasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
        List<Nota> todasNotas = notasDeExemplo();
        configuraRecyclerView(todasNotas);
        TextView insereNota = findViewById(R.id.lista_notas_insere_nota);
        insereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<Nota> notasDeExemplo() {
        NotaDAO dao = new NotaDAO();
        dao.insere(new Nota("Título", "Descrição"));
        return dao.todos();
    }

    private void configuraRecyclerView(List<Nota> todasNotas) {
        RecyclerView listaNotas = findViewById(R.id.recyclerView);
        listaNotas.setAdapter(new ListaNotaAdapter(this, todasNotas));
    }

    @Override
    protected void onResume() {
        NotaDAO dao = new NotaDAO();
        List<Nota> todasNotas = dao.todos();
        super.onResume();
    }
}