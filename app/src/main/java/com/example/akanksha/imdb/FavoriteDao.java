package com.example.akanksha.imdb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.widget.FrameLayout;

import com.example.akanksha.imdb.AddtoWatchlist.WatchEntity;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Insert
    void addFav(FavoriteEntity movie);

    @Delete
    void deleteFav(FavoriteEntity movie);


    @Query("select * from favorite where type = :Type")
    List<FavoriteEntity> getMovies(String Type);

    @Query("select * from favorite where type = :Type")
    List<FavoriteEntity> getShows(String Type);


    @Query("select movid from favorite where posterPath = :poster")
    int getmovid(String poster);


    @Insert
    void addWatch(WatchEntity movie);

    @Delete
    void deleteWatch(WatchEntity movie);


    @Query("select * from watchlist where type = :Type")
    List<WatchEntity> getWatchMovies(String Type);


    @Query("select movid from watchlist where posterPath = :poster")
    int getWatchmovid(String poster);





}
