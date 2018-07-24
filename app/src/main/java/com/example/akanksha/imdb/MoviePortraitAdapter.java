package com.example.akanksha.imdb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviePortraitAdapter extends RecyclerView.Adapter<MoviePortraitHolder> {

    LayoutInflater inflater;
    ArrayList<MoviePortrait> items;
    MovieItemClickListener listener;
    Context context;

    public MoviePortraitAdapter(@NonNull Context context, ArrayList<MoviePortrait> items,MovieItemClickListener listener) {

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context=context;
        this.items = items;
        this.listener=listener;

    }

    @NonNull
    @Override
    public MoviePortraitHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View output= inflater.inflate(R.layout.vertical_movie_row,viewGroup,false);

        return new MoviePortraitHolder(output);
    }

    @Override
    public void onBindViewHolder(@NonNull final MoviePortraitHolder moviePortraitHolder, int i) {

        final MoviePortrait movie = items.get(i);

        moviePortraitHolder.rating.setText(movie.getVoteAverage().toString());
        moviePortraitHolder.star.setBackground(context.getResources().getDrawable(R.drawable.ic_star_yellow_600_24dp));
        moviePortraitHolder.favorite.setBackground(context.getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));

        String url = movie.getPosterPath();

        Picasso.get().load(url).into(moviePortraitHolder.poster);


        moviePortraitHolder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.favButtonClicked(movie,moviePortraitHolder.getAdapterPosition());

            }
        });


    }

    @Override
    public long getItemId(int position) {
        return  position;

    }

    @Override
    public int getItemCount() {

        return items.size();
    }




}
