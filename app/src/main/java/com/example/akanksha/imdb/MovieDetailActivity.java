package com.example.akanksha.imdb;

import android.arch.persistence.room.Room;
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
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.akanksha.imdb.AddtoWatchlist.WatchEntity;
import com.example.akanksha.imdb.detailsofCast.Cast;
import com.example.akanksha.imdb.detailsofCast.CastRoot;
import com.example.akanksha.imdb.detailsofCast.Crew;
import com.example.akanksha.imdb.detailsofmovie.Genre;
import com.example.akanksha.imdb.detailsofmovie.Language;
import com.example.akanksha.imdb.detailsofmovie.MovieDetails;
import com.example.akanksha.imdb.detailsofmovie.Production;
import com.example.akanksha.imdb.detailsofreviews.Review;
import com.example.akanksha.imdb.detailsofreviews.ReviewRoot;
import com.example.akanksha.imdb.detailsofvideo.Result;
import com.example.akanksha.imdb.detailsofvideo.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.sql.Types.NULL;

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

    TextView castViewAll;
    TextView reviewViewAll;

    TextView authorView;
    TextView contentView;

    Button fav;
    Button add;
    FavoriteDao favoriteDao;

    FrameLayout frameLayout;

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

    LottieAnimationView animationView;
    LottieAnimationView animationViewFav;
    LottieAnimationView animationViewLoad;
    LottieAnimationView animationViewLoad1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);


        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        //id=353081;

        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                    Log.d("lalala","if");

                    collapsingToolbarLayout.setTitleEnabled(false);

                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;

                    Log.d("lalala","if 2");
                    collapsingToolbarLayout.setTitleEnabled(true);

                } else if (isShow) {
                    isShow = false;

                    Log.d("lalala","else if ");
                    collapsingToolbarLayout.setTitleEnabled(false);


                }
            }
        });

        animationView= findViewById(R.id.animation_view);
        animationViewFav = findViewById(R.id.animation_fav);
        animationViewLoad = findViewById(R.id.animation_load);
        animationViewLoad1 = findViewById(R.id.animation_loadd);

        //frameLayout = findViewById(R.id.reviewframe);
        fav= findViewById(R.id.fav);
        add = findViewById(R.id.add);

        castViewAll = findViewById(R.id.viewcast);
        reviewViewAll = findViewById(R.id.viewreviews);

        authorView = findViewById(R.id.author);
        contentView = findViewById(R.id.content);

        castViewAll.setOnClickListener(this);
        reviewViewAll.setOnClickListener(this);

        authorView.setOnClickListener(this);
        contentView.setOnClickListener(this);

        titleView = findViewById(R.id.title);
        toolGenreView = findViewById(R.id.toolgenre);
        landscapeView = findViewById(R.id.expandedImage);
        posterView = findViewById(R.id.poster);
        //genreView = findViewById(R.id.genre);
        overView = findViewById(R.id.overview);
        ratingView = findViewById(R.id.rating);

        fetch(id);

        recyclerViewCast = findViewById(R.id.recycleviewcast);

        adapter1 = new CastAdapter( casts,this);

        Log.d("Fragment","oncretefrag");


        recyclerViewCast.setAdapter(adapter1);

        recyclerViewCast.setItemAnimator(new DefaultItemAnimator());


        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCast.setLayoutManager(layoutManager1);


        Log.d("Fragment","afterset");

        castfetch(id,casts,adapter1);

        recyclerViewVideo = findViewById(R.id.recycleviewvideos);

        adapter2 = new VideoAdapter( videos,this);

        //Toast.makeText(this,"oncreatefrag", Toast.LENGTH_LONG).show();
        Log.d("Fragment","oncretefrag");


        recyclerViewVideo.setAdapter(adapter2);

        recyclerViewVideo.setItemAnimator(new DefaultItemAnimator());


        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewVideo.setLayoutManager(layoutManager2);

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

        reviewfetch(id);


    }

    public  void fetch(int id)
    {
        FavoriteDatabase database = Room.databaseBuilder(this,FavoriteDatabase.class,"expenses_db").allowMainThreadQueries().build();
        favoriteDao = database.getFavDao();


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        MovieSevice service = retrofit.create(MovieSevice.class);

        Call<MovieDetails> call = service.getMovieDetails(id,"7e00b48b59b417dfc865afa6de61f2aa");

        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {

                final MovieDetails details = response.body();


                String url = "https://image.tmdb.org/t/p/w500/" + details.getPosterPath();

                Picasso.get().load(url).into(posterView);

                String url1 = "https://image.tmdb.org/t/p/w1280/" + details.getBackdrop_path();

                Picasso.get().load(url1).into(landscapeView);

                overView.setText(details.getOverview());
                ratingView.setText(details.getVoteAverage().toString() + "/10");
                titleView.setText(details.getTitle());

//                getSupportActionBar().setTitle(details.getTitle());

                collapsingToolbarLayout.setTitle(details.getTitle());

                animationViewLoad.setVisibility(View.GONE);
                landscapeView.setVisibility(View.VISIBLE);

                animationViewLoad1.setVisibility(View.GONE);
                posterView.setVisibility(View.VISIBLE);

                ArrayList<Genre> genres = details.getGenres();


                    for (int i = 0; i < genres.size(); i++) {
                        toolGenreView.append(genres.get(i).getName() + ",");

                    }

                int mov = favoriteDao.getmovid(details.getPosterPath());
                int watchmov = favoriteDao.getWatchmovid(details.getPosterPath());

                if(mov == NULL) {
                   fav.setBackground(MovieDetailActivity.this.getResources().getDrawable(R.drawable.ic_favorite_border_yellow_600_24dp));
                }

                else {

                    fav.setEnabled(false);
                    //fav.setBackground(MovieDetailActivity.this.getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                    animationViewFav.setVisibility(View.VISIBLE);
                    fav.setVisibility(View.INVISIBLE);
                }

                if(watchmov == NULL) {
                    add.setBackground(MovieDetailActivity.this.getResources().getDrawable(R.drawable.ic_add_circle_outline_yellow_600_24dp));
                }

                else {

                    add.setEnabled(false);
                    add.setBackground(MovieDetailActivity.this.getResources().getDrawable(R.drawable.ic_add_circle_red_700_24dp));
                }

                fav.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            FavoriteEntity fmovie= new FavoriteEntity(details.getId(),details.getVoteAverage(),details.getPosterPath(),"Movie");
                            favoriteDao.addFav(fmovie);

                            //fav.setBackground(MovieDetailActivity.this.getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                            animationViewFav.setVisibility(View.VISIBLE);
                            fav.setVisibility(View.INVISIBLE);
                            fav.setEnabled(false);

                        }
                    });

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        WatchEntity wmovie= new WatchEntity(details.getId(),details.getVoteAverage(),details.getPosterPath(),"Movie");
                        favoriteDao.addWatch(wmovie);

                        add.setBackground(MovieDetailActivity.this.getResources().getDrawable(R.drawable.ic_add_circle_red_700_24dp));
                        add.setEnabled(false);

                    }
                });


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

    public  void reviewfetch(int id)
    {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        MovieSevice service = retrofit.create(MovieSevice.class);

        Call<ReviewRoot> call = service.getReviewDetails(id,"7e00b48b59b417dfc865afa6de61f2aa");

        call.enqueue(new Callback<ReviewRoot>() {
            @Override
            public void onResponse(Call<ReviewRoot> call, Response<ReviewRoot> response) {

                ReviewRoot root = response.body();


                ArrayList<Review> reviews = root.getResults();

                if( !reviews.isEmpty()) {

                    authorView.setText(reviews.get(0).getAuthor());
                    String s = reviews.get(0).getContent();
                    int length = reviews.get(0).getContent().length();
                    Log.d("Detailtextlength", Integer.toString(length));

                    SpannableString text = new SpannableString("....");

                    if (length > 120 && (Math.abs(length - 120)) > 10) {

                        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(s, 0, 100);
                        spannableStringBuilder.append(text);

                        contentView.setText(spannableStringBuilder.toString());

                    } else {

                        contentView.setText(s);
                    }

                }

                else
                {
                   /* authorView.setVisibility(View.GONE);
                    contentView.setVisibility(View.GONE);
                    LottieAnimationView animationView = new LottieAnimationView(MovieDetailActivity.this);
                    animationView.setAnimation(R.raw.not_found);
                    frameLayout.addView(animationView);
                    animationView.playAnimation();*/
                   animationView.setVisibility(View.VISIBLE);

                }
                Log.d("Fragment","sucess");

            }

            @Override
            public void onFailure(Call<ReviewRoot> call, Throwable t) {

                Log.d("Fragment",t.getMessage());
            }
        });


    }



    @Override
    public void onClick(View v) {

        int ide = v.getId();

        if(ide == R.id.viewcast)
        {
            Intent intent= new Intent(this,ViewAllCastActivity.class);
            intent.putExtra("id",id);
            intent.putExtra("category","movie");
            startActivity(intent);
        }

        else if( ide == R.id.viewreviews  || ide == R.id.content || ide == R.id.author)
        {
            Intent intent= new Intent(this,UserReviewsActivity.class);
            intent.putExtra("id",id);
            intent.putExtra("category","movie");
            startActivity(intent);

        }


    }

}
