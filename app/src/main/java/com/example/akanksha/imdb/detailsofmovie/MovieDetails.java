package com.example.akanksha.imdb.detailsofmovie;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieDetails {

    private  int id;

    @SerializedName("vote_average")
    private Double voteAverage;

    @SerializedName("poster_path")
    private String posterPath;

    private String backdrop_path;

    private String title;

    private String overview;

    private String release_date;

    private int runtime;

    private String tagline;

    ArrayList<Language> spoken_languages;

    ArrayList<Production> production_countries;

    ArrayList<Genre> genres;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public ArrayList<Language> getSpoken_languages() {
        return spoken_languages;
    }

    public void setSpoken_languages(ArrayList<Language> spoken_languages) {
        this.spoken_languages = spoken_languages;
    }

    public ArrayList<Production> getProduction_countries() {
        return production_countries;
    }

    public void setProduction_countries(ArrayList<Production> production_countries) {
        this.production_countries = production_countries;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }
}
