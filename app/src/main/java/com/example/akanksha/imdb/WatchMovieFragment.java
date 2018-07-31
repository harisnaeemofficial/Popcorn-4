package com.example.akanksha.imdb;


import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.akanksha.imdb.AddtoWatchlist.WatchAdapter;
import com.example.akanksha.imdb.AddtoWatchlist.WatchEntity;
import com.example.akanksha.imdb.FavoriteDao;
import com.example.akanksha.imdb.FavoriteDatabase;
import com.example.akanksha.imdb.FavoriteEntity;
import com.example.akanksha.imdb.FavoriteMovieAdapter;
import com.example.akanksha.imdb.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WatchMovieFragment extends Fragment {

    ProgressBar progressBar;
    RecyclerView recyclerView;

    WatchAdapter adapter;
    FavoriteDao favoriteDao;
    List<WatchEntity> watchmovies = new ArrayList<>();



    public WatchMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View output = inflater.inflate(R.layout.fragment_watch_movie, container, false);


        progressBar = output.findViewById(R.id.progress);
        recyclerView= (RecyclerView) output.findViewById(R.id.recycleview);

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        FavoriteDatabase database = Room.databaseBuilder(getContext(),FavoriteDatabase.class,"expenses_db").allowMainThreadQueries().build();
        favoriteDao = database.getFavDao();
        watchmovies = favoriteDao.getWatchMovies("Movie");


        adapter = new WatchAdapter(getContext(), watchmovies);


        recyclerView.setAdapter(adapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());


        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager2);
        Log.d("Fragment","setr2");

        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);



        return  output;
    }

}
