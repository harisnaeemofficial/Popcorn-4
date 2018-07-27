package com.example.akanksha.imdb;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class TVRoot {

    @Expose
    ArrayList<TV> results;

    int total_pages;

}
