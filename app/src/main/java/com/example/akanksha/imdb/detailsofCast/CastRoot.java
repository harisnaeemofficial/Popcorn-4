package com.example.akanksha.imdb.detailsofCast;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class CastRoot {

    private  String id;

    @Expose
    private ArrayList<Crew> crew;

    @Expose
    private ArrayList<Cast> cast;

    public ArrayList<Cast> getCast() {
        return cast;
    }

    public ArrayList<Crew> getCrew() {
        return crew;
    }

    public String getId() {
        return id;
    }

}
