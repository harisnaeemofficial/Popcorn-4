package com.example.akanksha.imdb.detailsofreviews;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.akanksha.imdb.R;

public class ReviewHolder extends RecyclerView.ViewHolder {

    TextView author;
    TextView content;
    CardView cardView;
    View itemView;

    public ReviewHolder(View itemView) {

        super(itemView);
        author = itemView.findViewById(R.id.author);
        content = itemView.findViewById(R.id.content);
        cardView = itemView.findViewById(R.id.cardreview);


    }


}
