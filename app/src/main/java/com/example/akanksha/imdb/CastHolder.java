package com.example.akanksha.imdb;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CastHolder extends RecyclerView.ViewHolder{


    ImageView imageView;
    TextView realname;
    TextView charname;
    View itemView;

    public CastHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;
        imageView = itemView.findViewById(R.id.castimage);
        realname = itemView.findViewById(R.id.name);
        charname = itemView.findViewById(R.id.charactername);


    }
}
