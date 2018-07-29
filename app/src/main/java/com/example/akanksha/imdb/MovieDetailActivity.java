package com.example.akanksha.imdb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akanksha.imdb.detailsofCast.Cast;
import com.example.akanksha.imdb.detailsofCast.CastRoot;
import com.example.akanksha.imdb.detailsofCast.Crew;
import com.example.akanksha.imdb.detailsofmovie.Genre;
import com.example.akanksha.imdb.detailsofmovie.Language;
import com.example.akanksha.imdb.detailsofmovie.MovieDetails;
import com.example.akanksha.imdb.detailsofmovie.Production;
import com.example.akanksha.imdb.detailsofvideo.Result;
import com.example.akanksha.imdb.detailsofvideo.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailActivity extends AppCompatActivity implements TextView.OnClickListener {

    ImageView landscapeView;
    ImageView posterView;

    TextView titleView;
    TextView toolGenreView;

    TextView genreView;
    TextView overView;
    TextView ratingView;
    CollapsingToolbarLayout collapsingToolbarLayout;

    TextView plotView;
    TextView tagLineView;
    TextView storygenreView;

    TextView releaseView;
    TextView originView;
    TextView durationView;
    TextView languageView;

    RecyclerView recyclerViewCast;

    RecyclerView recyclerViewVideo;

    RecyclerView recyclerViewMore;

    CastAdapter adapter1;
    ArrayList<Cast> casts = new ArrayList<>();

    VideoAdapter adapter2;
    ArrayList<Result> videos = new ArrayList<>();

    MoviePortraitAdapter adapter3;
    ArrayList<MoviePortrait> movies = new ArrayList<>();
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

       // collapsingToolbarLayout.setCollapsedTitleGravity(0);

        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);

        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    //showOption(R.id.action_info);
                } else if (isShow) {
                    isShow = false;
                    //hideOption(R.id.action_info);
                }
            }
        });

        titleView = findViewById(R.id.title);
        toolGenreView = findViewById(R.id.toolgenre);
        landscapeView = findViewById(R.id.expandedImage);
        posterView = findViewById(R.id.poster);
        genreView = findViewById(R.id.genre);
        overView = findViewById(R.id.overview);
        ratingView = findViewById(R.id.rating);

        fetch(id);

        recyclerViewCast = findViewById(R.id.recycleviewcast);

        adapter1 = new CastAdapter( casts,this);

        //Toast.makeText(this,"oncreatefrag", Toast.LENGTH_LONG).show();
        Log.d("Fragment","oncretefrag");


        recyclerViewCast.setAdapter(adapter1);

        recyclerViewCast.setItemAnimator(new DefaultItemAnimator());

        recyclerViewCast.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCast.setLayoutManager(layoutManager1);


        Toast.makeText(this,"afterSet", Toast.LENGTH_LONG).show();
        Log.d("Fragment","afterset");

        castfetch(id,casts,adapter1);

        recyclerViewVideo = findViewById(R.id.recycleviewvideos);

        adapter2 = new VideoAdapter( videos,this);

        //Toast.makeText(this,"oncreatefrag", Toast.LENGTH_LONG).show();
        Log.d("Fragment","oncretefrag");


        recyclerViewVideo.setAdapter(adapter2);

        recyclerViewVideo.setItemAnimator(new DefaultItemAnimator());

        recyclerViewVideo.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewVideo.setLayoutManager(layoutManager2);


        Toast.makeText(this,"afterSet", Toast.LENGTH_LONG).show();
        Log.d("Fragment","afterset");

        videofetch(id,videos,adapter2);


        recyclerViewMore= (RecyclerView) findViewById(R.id.recycleviewmore);

        adapter3 = new MoviePortraitAdapter(this, movies, new MovieItemClickListener() {
            @Override
            public void favButtonClicked(MoviePortrait item, int position) {

                //kh;

            }


        });


        recyclerViewMore.setAdapter(adapter3);

        recyclerViewMore.setItemAnimator(new DefaultItemAnimator());

        recyclerViewMore.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewMore.setLayoutManager(layoutManager3);
        Log.d("Fragment","setr2");

        similarfetch(id,movies,adapter3);


        storygenreView= findViewById(R.id.storygenre);
        plotView = findViewById(R.id.plot);
        tagLineView = findViewById(R.id.tag);
        storylinefetch(id);


        releaseView = findViewById(R.id.date);
        durationView = findViewById(R.id.duration);
        originView= findViewById(R.id.origin);
        languageView = findViewById(R.id.language);
        detailsfetch(id);


    }

    public  void fetch(int id)
    {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        MovieSevice service = retrofit.create(MovieSevice.class);

        Call<MovieDetails> call = service.getMovieDetails(id,"7e00b48b59b417dfc865afa6de61f2aa");

        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {

                MovieDetails details = response.body();


                String url = "https://image.tmdb.org/t/p/w500/" + details.getPosterPath();

                Picasso.get().load(url).into(posterView);

                String url1 = "https://image.tmdb.org/t/p/w1280/" + details.getBackdrop_path();

                Picasso.get().load(url1).into(landscapeView);

                overView.setText(details.getOverview());
                ratingView.setText(details.getVoteAverage().toString() + "/10");
                titleView.setText(details.getTitle());

                ArrayList<Genre> genres = details.getGenres();


                    for (int i = 0; i < genres.size(); i++) {
                        toolGenreView.append(genres.get(i).getName() + ",");

                    }


                    Log.d("Fragment","sucess");

            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {

                Log.d("Fragment",t.getMessage());
            }
        });


    }

    void castfetch(int id,final ArrayList<Cast> list,final CastAdapter adapter) {
        Log.d("Fragment", "seefunc");

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        MovieSevice service = retrofit.create(MovieSevice.class);

        Call<CastRoot> call = service.getCastDetails(id, "7e00b48b59b417dfc865afa6de61f2aa");

        call.enqueue(new Callback<CastRoot>() {
            @Override
            public void onResponse(Call<CastRoot> call, Response<CastRoot> response) {

                CastRoot castRoot = response.body();
                ArrayList<Cast> casts1 = castRoot.getCast();
                //ArrayList<Album> courses = a.getData().courses;

                list.clear();
                for (int i = 0; i < casts1.size(); i++) {

                    list.add(casts1.get(i));

                }

                adapter.notifyDataSetChanged();

              /*  ArrayList<Crew> crews = castRoot.getCrew();

                for (int i = 0; i < crews.size(); i++) {

                    String dep = crews.getDepartment();

                    directorView.append(crew.get(i).getName() + ",");

                }*/

               // progressBar.setVisibility(View.GONE);
                //linearLayout.setVisibility(View.VISIBLE);


                Log.d("Fragment", "sucess");
            }

            @Override
            public void onFailure(Call<CastRoot> call, Throwable t) {

                Log.d("Fragment", t.getMessage());
            }
        });

    }

   void videofetch(int id, final ArrayList<Result> list, final VideoAdapter adapter) {
        Log.d("Fragment", "seefunc");

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        MovieSevice service = retrofit.create(MovieSevice.class);

        Call<Video> call = service.getVideoDetails(id, "7e00b48b59b417dfc865afa6de61f2aa");

        call.enqueue(new Callback<Video>() {
            @Override
            public void onResponse(Call<Video> call, Response<Video> response) {

                Video video = response.body();
                ArrayList<Result> videos1 = video.getResults();

                list.clear();
                for (int i = 0; i < videos1.size(); i++) {

                    list.add(videos1.get(i));


                }

                adapter.notifyDataSetChanged();
                // progressBar.setVisibility(View.GONE);
                //linearLayout.setVisibility(View.VISIBLE);


                Log.d("Fragment", "sucess");

            }

            @Override
            public void onFailure(Call<Video> call, Throwable t) {

                Log.d("Fragment", t.getMessage());
            }
        });

    }



    void similarfetch(int id,final ArrayList<MoviePortrait> list,final MoviePortraitAdapter adapter)
    {
        Log.d("Fragment","seefunc");

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        MovieSevice service = retrofit.create(MovieSevice.class);

        Call<Movie> call = service.getSimilarDetails(id,"7e00b48b59b417dfc865afa6de61f2aa");

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
                //progressBar.setVisibility(View.GONE);
                //linearLayout.setVisibility(View.VISIBLE);


                Log.d("Fragment","sucess");

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

                Log.d("Fragment",t.getMessage());
            }
        });

    }

    public  void storylinefetch(int id)
    {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        MovieSevice service = retrofit.create(MovieSevice.class);

        Call<MovieDetails> call = service.getMovieDetails(id,"7e00b48b59b417dfc865afa6de61f2aa");

        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {

                MovieDetails details = response.body();

                //ArrayList<TV> shows1 = show.results;


                plotView.setText(details.getOverview());
                tagLineView.setText(details.getTagline());

                ArrayList<Genre> genres = details.getGenres();


                for (int i = 0; i < genres.size(); i++) {
                    storygenreView.append(genres.get(i).getName() + ",");

                }


                Log.d("Fragment","sucess");

            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {

                Log.d("Fragment",t.getMessage());
            }
        });


    }


    public  void detailsfetch(int id)
    {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        MovieSevice service = retrofit.create(MovieSevice.class);

        Call<MovieDetails> call = service.getMovieDetails(id,"7e00b48b59b417dfc865afa6de61f2aa");

        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {

                MovieDetails details = response.body();

                releaseView.setText(details.getRelease_date());
                durationView.setText(Integer.toString(details.getRuntime()) + " min");

                ArrayList<Production> countries = details.getProduction_countries();


                for (int i = 0; i < countries.size(); i++) {
                    originView.append(countries.get(i).getName() + ",");

                }

                ArrayList<Language> languages = details.getSpoken_languages();


                for (int i = 0; i < languages.size(); i++) {
                    languageView.append(languages.get(i).getName() + ",");

                }



                Log.d("Fragment","sucess");

            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {

                Log.d("Fragment",t.getMessage());
            }
        });


    }


    @Override
    public void onClick(View v) {


    }


}
