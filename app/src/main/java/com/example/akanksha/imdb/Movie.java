
package com.example.akanksha.imdb;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

//@Generated("net.hexar.json2pojo")
//@SuppressWarnings("unused")
public class Movie {

    @Expose
    private ArrayList<MoviePortrait> results;

    public ArrayList<MoviePortrait> getResults() {
        return results;
    }

    public void setResults(ArrayList<MoviePortrait> results) {
        this.results = results;
    }

}
