package com.example.akanksha.imdb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.akanksha.imdb.detailsofperson.Person;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CastDetailActivity extends AppCompatActivity {

    ImageView imageView;
    TextView nameView;
    TextView placeView;
    TextView birthView;
    TextView bioView;

    RecyclerView recyclerViewMovie;
    RecyclerView recyclerViewTV;

    MoviePortraitAdapter adapter1;
    ArrayList<MoviePortrait> movies = new ArrayList<>();

    MoviePortraitAdapter adapter2;
    ArrayList<MoviePortrait> shows = new ArrayList<>();

    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        imageView = findViewById(R.id.expandedImage);
        nameView = findViewById(R.id.name);
        placeView = findViewById(R.id.place);
        bioView = findViewById(R.id.overview);
        birthView = findViewById(R.id.dob);

        Intent intent = getIntent();
        id = intent.getLongExtra("id",0);

        recyclerViewMovie= (RecyclerView) findViewById(R.id.recycleviewfilmography);

        adapter1 = new MoviePortraitAdapter(this, movies, new MovieItemClickListener() {
            @Override
            public void favButtonClicked(MoviePortrait item, int position) {

                //kh;

            }


        });


        recyclerViewMovie.setAdapter(adapter1);

        recyclerViewMovie.setItemAnimator(new DefaultItemAnimator());

        recyclerViewMovie.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewMovie.setLayoutManager(layoutManager3);
        Log.d("Fragment","setr2");

        movieCastfetch(id,movies,adapter1);

        recyclerViewTV= (RecyclerView) findViewById(R.id.recycleviewtvcast);

        adapter2 = new MoviePortraitAdapter(this, movies, new MovieItemClickListener() {
            @Override
            public void favButtonClicked(MoviePortrait item, int position) {

                //kh;

            }


        });


        recyclerViewTV.setAdapter(adapter2);

        recyclerViewTV.setItemAnimator(new DefaultItemAnimator());

        recyclerViewTV.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewTV.setLayoutManager(layoutManager);
        Log.d("Fragment","setr2");

        TVCastfetch(id,shows,adapter2);

        datafetch(id);

    }

    void datafetch(long id)
    {
        Log.d("Fragment","seefunc");

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/person/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        MovieSevice service = retrofit.create(MovieSevice.class);

        Call<Person> call = service.getPersonDetails(id,"7e00b48b59b417dfc865afa6de61f2aa");

        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {

                Person person= response.body();
                String url = "https://image.tmdb.org/t/p/h632/" + person.getProfilePath();

                Picasso.get().load(url).into(imageView);

                nameView.setText(person.getName());
                placeView.setText(person.getPlaceOfBirth());
                birthView.setText(person.getBirthday());
                //bioView.setText(person.getBiography());


                String s = person.getBiography();
                int length = person.getBiography().length();
                Log.d("Detailtextlength", Integer.toString(length));

                SpannableString text = new SpannableString("....");

                if (length > 220 && (Math.abs(length - 220)) > 10) {

                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(s, 0, 200);
                    spannableStringBuilder.append(text);

                    bioView.setText(spannableStringBuilder.toString());

                } else {

                    bioView.setText(s);
                }


                //progressBar.setVisibility(View.GONE);
                //linearLayout.setVisibility(View.VISIBLE);

                Log.d("Fragment","sucess");

            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {

                Log.d("Fragment",t.getMessage());
            }
        });

    }

    void movieCastfetch(long id,final ArrayList<MoviePortrait> list,final MoviePortraitAdapter adapter)
    {
        Log.d("Fragment","seefunc");

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/person/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        MovieSevice service = retrofit.create(MovieSevice.class);

        Call<MovieCast> call = service.getMovieCastDetails(id,"7e00b48b59b417dfc865afa6de61f2aa");

        call.enqueue(new Callback<MovieCast>() {
            @Override
            public void onResponse(Call<MovieCast> call, Response<MovieCast> response) {

                MovieCast movie= response.body();
                ArrayList<MoviePortrait> movies1 = movie.getCast();
                //ArrayList<Album> courses = a.getData().courses;

                list.clear();
                for(int i = 0;i<movies1.size();i++){

                    list.add(movies1.get(i));


                }

                adapter.notifyDataSetChanged();
                //progressBar.setVisibility(View.GONE);
                //linearLayout.setVisibility(View.VISIBLE);
                Log.d("Fragment","sucess");

            }

            @Override
            public void onFailure(Call<MovieCast> call, Throwable t) {

                Log.d("Fragment",t.getMessage());
            }
        });

    }


    void TVCastfetch(long id,final ArrayList<MoviePortrait> list,final MoviePortraitAdapter adapter)
    {
        Log.d("Fragment","seefunc");

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/person/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        MovieSevice service = retrofit.create(MovieSevice.class);

        Call<MovieCast> call = service.getTVCastDetails(id,"7e00b48b59b417dfc865afa6de61f2aa");

        call.enqueue(new Callback<MovieCast>() {
            @Override
            public void onResponse(Call<MovieCast> call, Response<MovieCast>response) {

                MovieCast movie= response.body();
                ArrayList<MoviePortrait> movies1 = movie.getCast();

                list.clear();
                for(int i = 0;i<movies1.size();i++){

                    list.add(movies1.get(i));


                }

                adapter.notifyDataSetChanged();
                //progressBar.setVisibility(View.GONE);
                //linearLayout.setVisibility(View.VISIBLE);
                Log.d("Fragment","sucess");

            }

            @Override
            public void onFailure(Call<MovieCast> call, Throwable t) {

                Log.d("Fragment",t.getMessage());
            }
        });

    }


}
