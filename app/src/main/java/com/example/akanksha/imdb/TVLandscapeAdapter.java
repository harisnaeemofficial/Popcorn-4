package com.example.akanksha.imdb;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static java.sql.Types.NULL;

public class TVLandscapeAdapter extends RecyclerView.Adapter<MovieLandscapeHolder>{

    LayoutInflater inflater;
    ArrayList<TV> items;
    MovieItemClickListener listener;
    Context context;
    FavoriteDao favoriteDao;

    public TVLandscapeAdapter(Context context, ArrayList<TV> items, MovieItemClickListener listener) {

        this.items = items;
        this.listener = listener;
        this.context = context;
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

        final TV shows = items.get(position);

        FavoriteDatabase database = Room.databaseBuilder(context,FavoriteDatabase.class,"expenses_db").allowMainThreadQueries().build();
        favoriteDao = database.getFavDao();

        holder.rating.setText(shows.getVoteAverage().toString());
        holder.star.setBackground(context.getResources().getDrawable(R.drawable.ic_star_yellow_600_24dp));
        int mov = favoriteDao.getmovid(shows.getPosterPath());

        if(mov == NULL) {
            holder.favorite.setBackground(context.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
        }

        else
            holder.favorite.setBackground(context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp));

        holder.title.setText(shows.getName());

        String url = "https://image.tmdb.org/t/p/w780/" + shows.getBackdrop_path();

        Picasso.get().load(url).into(holder.poster);

        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //listener.favButtonClicked(movie,holder.getAdapterPosition());
                FavoriteEntity fmovie= new FavoriteEntity(shows.getId(),shows.getVoteAverage(),shows.getPosterPath(),"TVshow");
                favoriteDao.addFav(fmovie);

                holder.favorite.setBackground(context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                holder.favorite.setEnabled(false);

            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, TVDetailActivity.class);
                intent.putExtra("id",shows.getId());
                context.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {

        return items.size();
    }

}
