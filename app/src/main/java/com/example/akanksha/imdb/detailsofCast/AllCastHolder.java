package com.example.akanksha.imdb.detailsofCast;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.akanksha.imdb.R;

public class AllCastHolder extends RecyclerView.ViewHolder{



    public AllCastHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;
        imageView = itemView.findViewById(R.id.poster);
        realname = itemView.findViewById(R.id.name);
        charname = itemView.findViewById(R.id.charactername);

    }

    ImageView imageView;
    TextView realname;
    TextView charname;
    View itemView;


}
