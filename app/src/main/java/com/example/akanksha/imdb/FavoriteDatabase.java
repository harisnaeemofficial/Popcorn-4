package com.example.akanksha.imdb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.RoomDatabase;

import com.example.akanksha.imdb.AddtoWatchlist.WatchEntity;


@Database(entities = {FavoriteEntity.class, WatchEntity.class},version = 2)
public abstract class FavoriteDatabase extends RoomDatabase {

    abstract FavoriteDao getFavDao();
}
