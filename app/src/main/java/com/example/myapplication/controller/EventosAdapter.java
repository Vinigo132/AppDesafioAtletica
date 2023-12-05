package com.example.myapplication.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.CardEvento;
import com.example.myapplication.model.CardNoticias;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventosAdapter extends RecyclerView.Adapter<EventosAdapter.ViewHolder> {

    private List<CardEvento> lista;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //        private final TextView descricao;
        private final ImageView imagemEvento;
        private final ImageView btnExcluir;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
//            descricao = view.findViewById(R.id.descricaoPostagem);
            imagemEvento = view.findViewById(R.id.imgEvento);
            btnExcluir = view.findViewById(R.id.btnExcluirEevnt);
        }
    }

    public EventosAdapter(List<CardEvento> lista) {
        this.lista = lista;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EventosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View eventosView = inflater.inflate(R.layout.eventos_lista, parent, false);
        EventosAdapter.ViewHolder viewHolder = new EventosAdapter.ViewHolder(eventosView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EventosAdapter.ViewHolder viewHolder, final int position) {
        CardEvento evento = lista.get(position);
//       viewHolder.descricao.setText(evento.getDescricao());
        byte[] imageBytes;
        Log.d("Alo", "Documdsaento: " + evento.getImg());
        imageBytes = Base64.decode(evento.getImg(), Base64.DEFAULT);
        Bitmap imagemDecodificada = BitmapFactory.decodeByteArray(imageBytes, 0,imageBytes.length);
        viewHolder.imagemEvento.setImageBitmap(imagemDecodificada);
        viewHolder.btnExcluir.setOnClickListener(view -> excluirItem(position));

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public boolean adicionarItem(CardEvento evento){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference colecao = db.collection("eventos");

        // Cria um novo documento com os dados desejados
        Map<String, String> dadosDocumento = new HashMap<>();
        dadosDocumento.put("descricao", evento.getDescricao());
        dadosDocumento.put("imagem", evento.getImg());


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
        DocumentReference documentoRef = db.collection("eventos").document(String.valueOf(idDocumento));

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