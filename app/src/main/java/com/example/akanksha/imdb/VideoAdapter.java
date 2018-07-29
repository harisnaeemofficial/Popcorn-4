package com.example.akanksha.imdb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akanksha.imdb.detailsofCast.Cast;
import com.example.akanksha.imdb.detailsofvideo.Result;
import com.example.akanksha.imdb.detailsofvideo.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoHolder> {

    LayoutInflater inflater;
    ArrayList<Result> items;
    Context context;


    public VideoAdapter(ArrayList<Result> items, Context context) {

        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View output= inflater.inflate(R.layout.video_row,null,false);

        return new VideoHolder(output);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {

        final Result result = items.get(position);


        Picasso.get().load("https://www.youtube.com/watch?v=" + result.getKey() + "/hqdefault.jpg")
                .into(holder.videoView);

        if (result.getName() != null)
            holder.description.setText(result.getName());
        else
            holder.description.setText("");




    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}