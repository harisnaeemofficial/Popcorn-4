package com.example.akanksha.imdb;

import com.example.akanksha.imdb.detailsofCast.CastRoot;
import com.example.akanksha.imdb.detailsofmovie.MovieDetails;
import com.example.akanksha.imdb.detailsofperson.Person;
import com.example.akanksha.imdb.detailsofreviews.ReviewRoot;
import com.example.akanksha.imdb.detailsoftv.TVDetails;
import com.example.akanksha.imdb.detailsofvideo.Video;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieSevice {

@GET("{category}")
Call<Movie> getDetails(@Path("category") String cat, @Query("api_key") String api , @Query("page") int pno);

@GET("{category}")
Call<TVRoot> getTVDetails(@Path("category") String cat, @Query("api_key") String api , @Query("page") int pno);

@GET("{id}")
Call<MovieDetails> getMovieDetails(@Path("id") int id, @Query("api_key") String api);

@GET("{id}")
Call<TVDetails> getTVDetails(@Path("id") int id, @Query("api_key") String api);




@GET("{id}/credits")
Call<CastRoot> getCastDetails(@Path("id") int id, @Query("api_key") String api);

@GET("{id}/videos")
Call<Video> getVideoDetails(@Path("id") int id, @Query("api_key") String api);


@GET("{id}/similar")
Call<Movie> getSimilarDetails(@Path("id") int id, @Query("api_key") String api);

@GET("{id}/similar")
Call<TVRoot> getSimilarTVDetails(@Path("id") int id, @Query("api_key") String api);

@GET("{id}/reviews")
Call<ReviewRoot> getReviewDetails(@Path("id") int id, @Query("api_key") String api);

@GET("{id}")
Call<Person> getPersonDetails(@Path("id") long id, @Query("api_key") String api);

@GET("{id}/movie_credits")
Call<MovieCast> getMovieCastDetails(@Path("id") long id, @Query("api_key") String api);

@GET("{id}/tv_credits")
Call<MovieCast> getTVCastDetails(@Path("id") long id, @Query("api_key") String api);




}
