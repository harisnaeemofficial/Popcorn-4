package com.example.akanksha.imdb;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieSevice {

@GET("{category}")
Call<Movie> getDetails(@Path("category") String cat, @Query("api_key") String api , @Query("page") int pno);

@GET("{category}")
Call<TVRoot> getTVDetails(@Path("category") String cat, @Query("api_key") String api , @Query("page") int pno);


}
