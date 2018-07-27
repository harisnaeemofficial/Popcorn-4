package com.example.akanksha.imdb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<MoviePortraitHolder>{


    LayoutInflater inflater;
    List<FavoriteEntity> items;
    Context context;

    public FavoriteMovieAdapter(Context context , List<FavoriteEntity> items) {

        this.context=context;
        this.items = items;
    }

    @NonNull
    @Override
    public MoviePortraitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View output= inflater.inflate(R.layout.vertical_movie_row,null,false);

        return new MoviePortraitHolder(output);

    }

    @Override
    public void onBindViewHolder(@NonNull MoviePortraitHolder holder, int position) {

        final FavoriteEntity movie = items.get(position);

        holder.rating.setText(movie.getVoteAverage().toString());
        holder.star.setBackground(context.getResources().getDrawable(R.drawable.ic_star_yellow_600_24dp));
        holder.favorite.setBackground(context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp));

        String url = "https://image.tmdb.org/t/p/w500/" + movie.getPosterPath();

        Picasso.get().load(url).into(holder.poster);

    }

    @Override
    public int getItemCount() {

        return items.size();
    }
}
