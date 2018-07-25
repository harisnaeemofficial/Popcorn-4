package com.example.akanksha.imdb;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieLandscapeHolder extends RecyclerView.ViewHolder{


    ImageView poster ;
    TextView title;
    ImageView star ;
    TextView rating ;
    Button favorite ;
    View itemView;

    public MovieLandscapeHolder(View itemView) {

        super(itemView);
        this.itemView = itemView;

        poster = itemView.findViewById(R.id.poster);
        title = itemView.findViewById(R.id.title);
        star = itemView.findViewById(R.id.star);
        rating = itemView.findViewById(R.id.rating);
        favorite = itemView.findViewById(R.id.fav);

    }


}
