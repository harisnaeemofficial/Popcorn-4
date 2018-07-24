package com.example.akanksha.imdb;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class Movie_Fragment extends Fragment {

    ArrayList<MoviePortrait> movies = new ArrayList<>();
    MoviePortraitAdapter adapter;
   // ExpenseDAO expenseDAO;
    RecyclerView recyclerView1;

    public Movie_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View output= inflater.inflate(R.layout.fragment_movie_, container, false);

        recyclerView1= (RecyclerView) output.findViewById(R.id.recycleview1);

        adapter = new MoviePortraitAdapter(getContext(), movies, new MovieItemClickListener() {
            @Override
            public void favButtonClicked(MoviePortrait item, int position) {

                //kh;

            }


        });


        recyclerView1.setAdapter(adapter);

        recyclerView1.setItemAnimator(new DefaultItemAnimator());

        recyclerView1.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView1.setLayoutManager(layoutManager);


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://developers.themoviedb.org/3/movies/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        MovieSevice service = retrofit.create(MovieSevice.class);

        Call<Movie> call = service.getDetails("get-popular-movies","7e00b48b59b417dfc865afa6de61f2aa",1);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {

                Movie movie= response.body();
                ArrayList<MoviePortrait> movies1 = movie.getResults();
                //ArrayList<Album> courses = a.getData().courses;

                movies.clear();
                for(int i = 0;i<movies1.size();i++){

                    movies.add(movies1.get(i));
                    adapter.notifyDataSetChanged();

                }

               //progressBar.setVisibility(View.GONE);
                //recyclerView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });


        return output;

    }



}
