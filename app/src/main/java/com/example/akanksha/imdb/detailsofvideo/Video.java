
package com.example.akanksha.imdb.detailsofvideo;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;


@SuppressWarnings("unused")
public class Video {

    @Expose
    private Long id;
    @Expose
    private ArrayList<Result> results;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArrayList<Result> getResults() {
        return results;
    }



}
