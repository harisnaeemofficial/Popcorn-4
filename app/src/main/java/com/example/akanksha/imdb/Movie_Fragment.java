package com.example.akanksha.imdb;


import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
public class Movie_Fragment extends Fragment implements TextView.OnClickListener{

    ArrayList<MoviePortrait> movies = new ArrayList<>();
    ArrayList<MoviePortrait> movies2 = new ArrayList<>();
    ArrayList<MoviePortrait> movies3 = new ArrayList<>();
    ArrayList<MoviePortrait> movies4 = new ArrayList<>();

    MovieLandscapeAdapter adapter1;
    MoviePortraitAdapter adapter2;
    MovieLandscapeAdapter adapter3;
    MoviePortraitAdapter adapter4;

    RecyclerView recyclerView1;
    RecyclerView recyclerView2;
    RecyclerView recyclerView3;
    RecyclerView recyclerView4;

    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;

    Bundle bundle = new Bundle();

    ProgressBar progressBar;
    LinearLayout linearLayout;

    FavoriteDao favoriteDao;
    List<FavoriteEntity> favmovies = new ArrayList<>();

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

        progressBar = output.findViewById(R.id.progress);
        linearLayout =output.findViewById(R.id.linear);

        progressBar.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);

        textView1= output.findViewById(R.id.viewpopular);
        textView2= output.findViewById(R.id.viewnowplaying);
        textView3= output.findViewById(R.id.viewcomingsoon);
        textView4= output.findViewById(R.id.viewtoprated);

        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
        textView4.setOnClickListener(this);


       /* FavoriteDatabase database = Room.databaseBuilder(getContext(),FavoriteDatabase.class,"expenses_db").allowMainThreadQueries().build();
        favoriteDao = database.getFavDao();
        favmovies = favoriteDao.getMovies();
*/


        recyclerView1= (RecyclerView) output.findViewById(R.id.recycleview1);

        adapter1 = new MovieLandscapeAdapter(getContext(), movies, new MovieItemClickListener() {
            @Override
            public void favButtonClicked(MoviePortrait item, int position) {


               /* FavoriteEntity movie= new FavoriteEntity();
                favoriteDao.addFav(movie);

                favmovies.add(movie);
*/
            }


        });

        Log.d("Fragment","oncretefrag");


        recyclerView1.setAdapter(adapter1);

        recyclerView1.setItemAnimator(new DefaultItemAnimator());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView1.setLayoutManager(layoutManager);


        Log.d("Fragment","afterset");

        seelandscape("popular",movies,adapter1);


        recyclerView2= (RecyclerView) output.findViewById(R.id.recycleview2);

        adapter2 = new MoviePortraitAdapter(getContext(), movies2, new MovieItemClickListener() {
            @Override
            public void favButtonClicked(MoviePortrait item, int position) {

                //kh;

            }


        });


        recyclerView2.setAdapter(adapter2);

        recyclerView2.setItemAnimator(new DefaultItemAnimator());

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView2.setLayoutManager(layoutManager2);
        Log.d("Fragment","setr2");

        seeportrait("now_playing",movies2,adapter2);


        recyclerView3= (RecyclerView) output.findViewById(R.id.recycleview3);

        adapter3 = new MovieLandscapeAdapter(getContext(), movies3, new MovieItemClickListener() {
            @Override
            public void favButtonClicked(MoviePortrait item, int position) {

                //kh;

            }


        });


        recyclerView3.setAdapter(adapter3);

        recyclerView3.setItemAnimator(new DefaultItemAnimator());

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView3.setLayoutManager(layoutManager3);
        Log.d("Fragment","setr2");

        seelandscape("upcoming",movies3,adapter3);


        recyclerView4= (RecyclerView) output.findViewById(R.id.recycleview4);

        adapter4 = new MoviePortraitAdapter(getContext(), movies4, new MovieItemClickListener() {
            @Override
            public void favButtonClicked(MoviePortrait item, int position) {

                //kh;

            }


        });


        recyclerView4.setAdapter(adapter4);

        recyclerView4.setItemAnimator(new DefaultItemAnimator());

        LinearLayoutManager layoutManager4 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView4.setLayoutManager(layoutManager4);
        Log.d("Fragment","setr2");

        seeportrait("top_rated",movies4,adapter4);



        return output;

    }

    void seelandscape(String cat,final ArrayList<MoviePortrait> list,final MovieLandscapeAdapter adapter) {
        Log.d("Fragment", "seefunc");

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        MovieSevice service = retrofit.create(MovieSevice.class);

        Call<Movie> call = service.getDetails(cat, "7e00b48b59b417dfc865afa6de61f2aa", 1);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {

                Movie movie = response.body();
                ArrayList<MoviePortrait> movies1 = movie.results;
                //ArrayList<Album> courses = a.getData().courses;

                list.clear();
                for (int i = 0; i < movies1.size(); i++) {

                    list.add(movies1.get(i));


                }

                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);


                Log.d("Fragment", "sucess");
                //progressBar.setVisibility(View.GONE);
                //recyclerView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

                Log.d("Fragment", t.getMessage());
            }
        });

    }


        void seeportrait(String cat,final ArrayList<MoviePortrait> list,final MoviePortraitAdapter adapter)
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
                    progressBar.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);


                    Log.d("Fragment","sucess");

                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {

                    Log.d("Fragment",t.getMessage());
                }
            });



        }


    @Override
    public void onClick(View v) {

        int id = v.getId();
        Intent intent= new Intent(getContext(),ViewActivity.class);

        if(id == R.id.viewpopular)
        {
            intent.putExtra("category","popular");
            intent.putExtra("title","PopularMovies");
        }
        else if(id == R.id.viewnowplaying)
        {
            intent.putExtra("category","now_playing");
            intent.putExtra("title","NowPlayingMovies");
        }
        else if(id == R.id.viewcomingsoon)
        {
            intent.putExtra("category","upcoming");
            intent.putExtra("title","UpComingMovies");
        }

        else if(id == R.id.viewtoprated)
        {
            intent.putExtra("category","top_rated");
            intent.putExtra("title","TopRatedMovies");
        }


        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

        adapter1.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
        adapter3.notifyDataSetChanged();
        adapter4.notifyDataSetChanged();

    }
}
