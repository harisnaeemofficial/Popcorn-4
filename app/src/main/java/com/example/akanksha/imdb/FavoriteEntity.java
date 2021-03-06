package com.example.akanksha.imdb;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favorite")
public class FavoriteEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private  int movid;
    private Double voteAverage;
    private String posterPath;
    private String type;


    public FavoriteEntity( int movid, Double voteAverage, String posterPath, String type) {

        this.movid = movid;
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
        this.type = type;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovid() {
        return movid;
    }

    public void setMovid(int movid) {
        this.movid = movid;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }
}
