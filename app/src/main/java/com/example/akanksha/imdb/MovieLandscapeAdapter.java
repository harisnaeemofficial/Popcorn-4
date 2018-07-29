package com.example.akanksha.imdb;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akanksha.imdb.detailsofmovie.MovieDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static java.sql.Types.NULL;


public class MovieLandscapeAdapter extends RecyclerView.Adapter<MovieLandscapeHolder>{


    LayoutInflater inflater;
    ArrayList<MoviePortrait> items;
    MovieItemClickListener listener;
    Context context;
    FavoriteDao favoriteDao;

    public MovieLandscapeAdapter(Context context, ArrayList<MoviePortrait> items, MovieItemClickListener listener) {


        this.context=context;
        this.items = items;
        this.listener=listener;

    }

    @NonNull
    @Override
    public MovieLandscapeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View output= inflater.inflate(R.layout.horizontal_movie_row,null,false);

        return new MovieLandscapeHolder(output);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieLandscapeHolder holder, int position) {

        final MoviePortrait movie = items.get(position);

        FavoriteDatabase database = Room.databaseBuilder(context,FavoriteDatabase.class,"expenses_db").allowMainThreadQueries().build();
        favoriteDao = database.getFavDao();

        holder.rating.setText(movie.getVoteAverage().toString());
        holder.star.setBackground(context.getResources().getDrawable(R.drawable.ic_star_yellow_600_24dp));
        int mov = favoriteDao.getmovid(movie.getPosterPath());

        if(mov == NULL) {
            holder.favorite.setBackground(context.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
        }

        else
           holder.favorite.setBackground(context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp));

        holder.title.setText(movie.getTitle());

        String url = "https://image.tmdb.org/t/p/w1280/" + movie.getBackdrop_path();

        Picasso.get().load(url).into(holder.poster);
        
       holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //listener.favButtonClicked(movie,holder.getAdapterPosition());
                FavoriteEntity fmovie= new FavoriteEntity(movie.getId(),movie.getVoteAverage(),movie.getPosterPath(),"Movie");
                favoriteDao.addFav(fmovie);

               holder.favorite.setBackground(context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
               holder.favorite.setEnabled(false);

            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra("id",movie.getId());
                context.startActivity(intent);


            }
        });



    }

    @Override
    public int getItemCount() {

        return items.size();
    }



}
