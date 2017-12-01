package com.example.alex.supermercato;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by utente4.academy on 01/12/2017.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder>{
    public static class CardViewHolder extends RecyclerView.ViewHolder{
        private TextView cardTextView;
        private ImageView cardImageView;
        public CardViewHolder(View v, Context context){
            super(v);
            cardImageView = v.findViewById(R.id.imgCardId);
            cardTextView = v.findViewById(R.id.textCardId);
        }
    }
    private List<Prodotto> listaProdotti;
    private Context context;

    public CardAdapter(List<Prodotto> listaProdotti, Context context){
        this.listaProdotti = listaProdotti;
        this.context = context;
    }

    @Override
    public CardAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout,parent,false);
        CardViewHolder cvh = new CardViewHolder(v,parent.getContext());
        return cvh;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Prodotto p = listaProdotti.get(position);
        String str = "Marca: "+p.getMarca() +"\nPrezzo: " + p.getPrezzo();
        holder.cardTextView.setText(str);
        Bitmap bitmap;
        if(p instanceof Carne) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.carne);
            holder.cardImageView.setImageBitmap(bitmap);
        }
        if(p instanceof Latte) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.latte);
            holder.cardImageView.setImageBitmap(bitmap);
        }
        if(p instanceof Pesce) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pesce);
            holder.cardImageView.setImageBitmap(bitmap);
        }
    }
    @Override
    public int getItemCount() {
        return listaProdotti.size();
    }
}
