package com.example.akanksha.imdb;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
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

import com.example.akanksha.imdb.detailsofmovie.MovieDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static java.sql.Types.NULL;

public class MoviePortraitAdapter extends RecyclerView.Adapter<MoviePortraitHolder> {

    LayoutInflater inflater;
    ArrayList<MoviePortrait> items;
    MovieItemClickListener listener;
    Context context;
    FavoriteDao favoriteDao;

    public MoviePortraitAdapter(@NonNull Context context, ArrayList<MoviePortrait> items,MovieItemClickListener listener) {

        this.context=context;
        this.items = items;
        this.listener=listener;

    }

    @NonNull
    @Override
    public MoviePortraitHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View output= inflater.inflate(R.layout.vertical_movie_row,null,false);

        return new MoviePortraitHolder(output);
    }

    @Override
    public void onBindViewHolder(@NonNull final MoviePortraitHolder moviePortraitHolder, int i) {

        FavoriteDatabase database = Room.databaseBuilder(context,FavoriteDatabase.class,"expenses_db").allowMainThreadQueries().build();
        favoriteDao = database.getFavDao();

        final MoviePortrait movie = items.get(i);

        moviePortraitHolder.rating.setText(movie.getVoteAverage().toString());
        moviePortraitHolder.star.setBackground(context.getResources().getDrawable(R.drawable.ic_star_yellow_600_24dp));
        int mov = favoriteDao.getmovid(movie.getPosterPath());

        if(mov == NULL) {
            moviePortraitHolder.favorite.setBackground(context.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
        }

        else
            moviePortraitHolder.favorite.setBackground(context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp));

        String url = "https://image.tmdb.org/t/p/w500/" + movie.getPosterPath();

        Picasso.get().load(url).into(moviePortraitHolder.poster);


        moviePortraitHolder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //listener.favButtonClicked(movie,moviePortraitHolder.getAdapterPosition());
                FavoriteEntity fmovie= new FavoriteEntity(movie.getId(),movie.getVoteAverage(),movie.getPosterPath(),"Movie");
                favoriteDao.addFav(fmovie);

                moviePortraitHolder.favorite.setBackground(context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                moviePortraitHolder.favorite.setEnabled(false);


            }
        });

        moviePortraitHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra("id",movie.getId());
                context.startActivity(intent);


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
