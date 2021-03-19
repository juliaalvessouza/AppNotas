package com.example.notas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notas.R;
import com.example.notas.model.Nota;

import java.util.List;

public class ListaNotaAdapter extends RecyclerView.Adapter<ListaNotaAdapter.ViewHolder> {

    private final List<Nota> notas;
    private final Context context;

    public ListaNotaAdapter(Context context, List<Nota> notas) {
        this.context = context;
        this.notas = notas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_nota, parent, false);
        return new ViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Nota nota = notas.get(position);
        holder.vincula(nota);
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView titulo;
        private final TextView descricao;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.item_nota_titulo);
            descricao = itemView.findViewById(R.id.item_nota_descricao);
        }

        public void vincula(Nota nota) {
            titulo.setText(nota.getTitulo());
            descricao.setText(nota.getTitulo());
        }
    }
}
