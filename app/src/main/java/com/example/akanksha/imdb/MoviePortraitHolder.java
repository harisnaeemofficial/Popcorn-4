package com.example.akanksha.imdb;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MoviePortraitHolder extends RecyclerView.ViewHolder {

    ImageView poster ;
    ImageView star ;
    TextView rating ;
    Button favorite ;
    CardView cardView;
    View itemView;

    public MoviePortraitHolder(@NonNull View itemView) {

        super(itemView);
        this.itemView = itemView;

        poster = itemView.findViewById(R.id.poster);
        star = itemView.findViewById(R.id.star);
        rating = itemView.findViewById(R.id.rating);
        favorite = itemView.findViewById(R.id.fav);
        cardView = itemView.findViewById(R.id.cardvertical);

    }
}
