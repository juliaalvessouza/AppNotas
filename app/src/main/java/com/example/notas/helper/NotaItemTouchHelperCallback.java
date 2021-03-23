package com.example.notas.helper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notas.adapter.ListaNotaAdapter;
import com.example.notas.dao.NotaDAO;

public class NotaItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ListaNotaAdapter adapter;

    public NotaItemTouchHelperCallback(ListaNotaAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int marcacoesDeslize = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int marcacoesArrastar = ItemTouchHelper.DOWN | ItemTouchHelper.UP
                                | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
         return makeMovementFlags(marcacoesArrastar, marcacoesDeslize);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int viewHolderPosicaoInicial = viewHolder.getAdapterPosition();
        int targetPosicaoFinal = target.getAdapterPosition();
        trocaNotas(viewHolderPosicaoInicial, targetPosicaoFinal);
        return true;
    }

    private void trocaNotas(int viewHolderPosicaoInicial, int targetPosicaoFinal) {
        new NotaDAO().troca(viewHolderPosicaoInicial, targetPosicaoFinal);
        adapter.troca(viewHolderPosicaoInicial, targetPosicaoFinal);
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int posicaoNotaDeslizada = viewHolder.getAdapterPosition();
        removeNota(posicaoNotaDeslizada);
    }

    private void removeNota(int posicao) {
        new NotaDAO().remove(posicao);
        adapter.remove(posicao);
    }
}
