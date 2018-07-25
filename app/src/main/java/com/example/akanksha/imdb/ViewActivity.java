package com.example.akanksha.imdb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<MoviePortrait> movies = new ArrayList<>();
    MoviePortraitAdapter adapter;
    String cat;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        cat = intent.getStringExtra("category") ;
        title = intent.getStringExtra("title") ;

        recyclerView = findViewById(R.id.recycleview);

        adapter = new MoviePortraitAdapter(this, movies, new MovieItemClickListener() {
            @Override
            public void favButtonClicked(MoviePortrait item, int position) {

                //kh;

            }


        });

       // Toast.makeText(getContext(),"oncreatefrag", Toast.LENGTH_LONG).show();
        Log.d("Fragment","oncretefrag");


        recyclerView.setAdapter(adapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        GridLayoutManager layoutManager1 = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager1);

       // Toast.makeText(getContext(),"afterSet", Toast.LENGTH_LONG).show();
        Log.d("Fragment","afterset");

        see(cat,movies,adapter);

    }

    void see(String cat,final ArrayList<MoviePortrait> list,final MoviePortraitAdapter adapter)
    {
        Log.d("Fragment","seefunc");

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        MovieSevice service = retrofit.create(MovieSevice.class);

        Call<Movie> call = service.getDetails(cat,"7e00b48b59b417dfc865afa6de61f2aa",1);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {

                Movie movie= response.body();
                ArrayList<MoviePortrait> movies1 = movie.results;
                //ArrayList<Album> courses = a.getData().courses;

                list.clear();
                for(int i = 0;i<movies1.size();i++){

                    list.add(movies1.get(i));


                }

                adapter.notifyDataSetChanged();

                Log.d("Fragment","sucess");
                //progressBar.setVisibility(View.GONE);
                //recyclerView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

                Log.d("Fragment",t.getMessage());
            }
        });


    }


}
