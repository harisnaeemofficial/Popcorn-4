package com.example.akanksha.imdb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.RoomDatabase;


@Database(entities = {FavoriteEntity.class},version = 1)
public abstract class FavoriteDatabase extends RoomDatabase {

    abstract FavoriteDao getFavDao();
}
