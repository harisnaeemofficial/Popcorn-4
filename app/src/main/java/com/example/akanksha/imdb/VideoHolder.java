package com.example.akanksha.imdb;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class VideoHolder extends RecyclerView.ViewHolder{

    ImageView videoView;
    TextView description;
    View itemView;

    public VideoHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;

        videoView = itemView.findViewById(R.id.video);
        description = itemView.findViewById(R.id.description);


    }
}
