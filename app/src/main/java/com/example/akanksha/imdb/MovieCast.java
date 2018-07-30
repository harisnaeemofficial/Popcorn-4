package com.example.akanksha.imdb;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class MovieCast {

    @Expose
    ArrayList<MoviePortrait> cast;

    public ArrayList<MoviePortrait> getCast() {
        return cast;
    }
}
