package com.example.myapplication.controller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.CardNoticias;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PublicacoesAtleticaAdapter extends RecyclerView.Adapter<PublicacoesAtleticaAdapter.ViewHolder> {

    private List<CardNoticias> lista;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nomeCurso;
        private final TextView descricao;
        private final ImageView btnExcluir;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            descricao = view.findViewById(R.id.descricaoPostagemAltetica);
            nomeCurso = view.findViewById(R.id.NomePostagemAtletica);
            btnExcluir = view.findViewById(R.id.btnExcluirAteltica);
        }
    }

    public PublicacoesAtleticaAdapter(List<CardNoticias> lista) {
        this.lista = lista;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View publiView = inflater.inflate(R.layout.publicacoes_atletica_lista, parent, false);
        ViewHolder viewHolder = new ViewHolder(publiView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, final int position) {
        CardNoticias publicacao = lista.get(position);
        viewHolder.descricao.setText(publicacao.getDescricao());
        viewHolder.nomeCurso.setText(publicacao.getCurso());
        viewHolder.btnExcluir.setOnClickListener(view -> excluirItem(position));

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public boolean adicionarItem(CardNoticias postagem){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference colecao = db.collection("publicacoesAtletica");

        // Cria um novo documento com os dados desejados
        Map<String, String> dadosDocumento = new HashMap<>();
        dadosDocumento.put("curso", postagem.getCurso());
        dadosDocumento.put("descricao", postagem.getDescricao());


        final boolean[] resultado = {false};

        // Adiciona o novo documento à coleção
        colecao.add(dadosDocumento)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // O documento foi adicionado com sucesso
                        resultado[0] = true;
                        Log.d("Firestore", "Documdsaento adicionado com ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Houve um erro ao adicionar o documento
                        resultado[0] = false;
                        Log.d("Firestore", "ERRO: " + e);
                    }
                });

        return resultado[0];
    }

    private void excluirItem(int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String idDocumento = lista.get(position).getId();

        // Obtém uma referência para o documento que deseja excluir
        DocumentReference documentoRef = db.collection("publicacoesAtletica").document(String.valueOf(idDocumento));

        // Exclui o documento
        documentoRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Documento excluído com sucesso
                        Log.e("Firestore", "Documento excluído com sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Houve um erro ao excluir o documento
                        Log.e("Firestore", "Erro ao excluir documento", e);
                    }
                });

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, lista.size());
    }


}
