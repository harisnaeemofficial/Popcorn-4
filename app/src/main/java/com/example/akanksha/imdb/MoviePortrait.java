package com.example.akanksha.imdb;

import com.google.gson.annotations.SerializedName;

public class MoviePortrait {

    @SerializedName("vote_average")
    private Double voteAverage;

    @SerializedName("poster_path")
    private String posterPath;

    public MoviePortrait(Double voteAverage, String posterPath) {

        this.voteAverage = voteAverage;
        this.posterPath = posterPath;

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
}
