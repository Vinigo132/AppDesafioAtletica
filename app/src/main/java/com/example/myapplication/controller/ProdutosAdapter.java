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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.CardKits;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ProdutosAdapter extends RecyclerView.Adapter<ProdutosAdapter.ViewHolder> {

    private List<CardKits> lista;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //        private final TextView descricao;
        private final ImageView imagemProduto;
        private final ImageView btnExcluir;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
//            descricao = view.findViewById(R.id.descricaoPostagem);
            imagemProduto = view.findViewById(R.id.imgProduto);
            btnExcluir = view.findViewById(R.id.btnExcluirProduto);
        }
    }

    public ProdutosAdapter(List<CardKits> lista) {
        this.lista = lista;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ProdutosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View produtosView = inflater.inflate(R.layout.produtos_lista, parent, false);
        ProdutosAdapter.ViewHolder viewHolder = new ProdutosAdapter.ViewHolder(produtosView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProdutosAdapter.ViewHolder viewHolder, final int position) {
        CardKits kits = lista.get(position);
//       viewHolder.descricao.setText(evento.getDescricao());
        byte[] imageBytes;
        Log.d("Alo", "Documdsaento: " + kits.getImg());
        imageBytes = Base64.decode(kits.getImg(), Base64.DEFAULT);
        Bitmap imagemDecodificada = BitmapFactory.decodeByteArray(imageBytes, 0,imageBytes.length);
        viewHolder.imagemProduto.setImageBitmap(imagemDecodificada);
        viewHolder.btnExcluir.setOnClickListener(view -> excluirItem(position));

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public boolean adicionarItem(CardKits produto){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference colecao = db.collection("Produtos");
        String id = UUID.randomUUID().toString();
        produto.setId(id);
        // Cria um novo documento com os dados desejados
        Map<String, Object> dadosDocumento = new HashMap<>();
        dadosDocumento.put("ID", produto.getId());
        dadosDocumento.put("nome",produto.getNome());
        dadosDocumento.put("Descricao",produto.getDescricao());
        dadosDocumento.put("Valor", produto.getValor());
        dadosDocumento.put("Tamanho",produto.getTamanho());
        dadosDocumento.put("Quantidade",produto.getQuantidade());
        dadosDocumento.put("imagem", produto.getImg());


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