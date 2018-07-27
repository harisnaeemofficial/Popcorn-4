package com.example.akanksha.imdb;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class TVFragment extends Fragment implements TextView.OnClickListener {

    ArrayList<TV> shows = new ArrayList<>();
    ArrayList<TV> shows2 = new ArrayList<>();
    ArrayList<TV> shows3 = new ArrayList<>();
    ArrayList<TV> shows4 = new ArrayList<>();

    TVLandscapeAdapter adapter1;
    TVPotraitAdapter adapter2;
    TVLandscapeAdapter adapter3;
    TVPotraitAdapter adapter4;

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

    public TVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View output = inflater.inflate(R.layout.fragment_tv, container, false);

        progressBar = output.findViewById(R.id.progress);
        linearLayout =output.findViewById(R.id.linear);

        progressBar.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);

        textView1= output.findViewById(R.id.viewtoday);
        textView2= output.findViewById(R.id.viewonair);
        textView3= output.findViewById(R.id.viewpopular);
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

        adapter1 = new TVLandscapeAdapter(getContext(), shows, new MovieItemClickListener() {
            @Override
            public void favButtonClicked(MoviePortrait item, int position) {


               /* FavoriteEntity movie= new FavoriteEntity();
                favoriteDao.addFav(movie);

                favmovies.add(movie);
*/
            }


        });

        //Toast.makeText(getContext(),"oncreatefrag", Toast.LENGTH_LONG).show();
        Log.d("Fragment","oncretefrag");


        recyclerView1.setAdapter(adapter1);

        recyclerView1.setItemAnimator(new DefaultItemAnimator());

        recyclerView1.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView1.setLayoutManager(layoutManager);


        Toast.makeText(getContext(),"afterSet", Toast.LENGTH_LONG).show();
        Log.d("Fragment","afterset");

        seelandscape("airing_today",shows,adapter1);


        recyclerView2= (RecyclerView) output.findViewById(R.id.recycleview2);

        adapter2 = new TVPotraitAdapter(getContext(), shows2, new MovieItemClickListener() {
            @Override
            public void favButtonClicked(MoviePortrait item, int position) {

                //kh;

            }


        });


        recyclerView2.setAdapter(adapter2);

        recyclerView2.setItemAnimator(new DefaultItemAnimator());

        recyclerView2.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView2.setLayoutManager(layoutManager2);
        Log.d("Fragment","setr2");

        seeportrait("on_the_air",shows2,adapter2);


        recyclerView3= (RecyclerView) output.findViewById(R.id.recycleview3);

        adapter3 = new TVLandscapeAdapter(getContext(), shows3, new MovieItemClickListener() {
            @Override
            public void favButtonClicked(MoviePortrait item, int position) {

                //kh;

            }


        });


        recyclerView3.setAdapter(adapter3);

        recyclerView3.setItemAnimator(new DefaultItemAnimator());

        recyclerView3.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView3.setLayoutManager(layoutManager3);
        Log.d("Fragment","setr2");

        seelandscape("popular",shows3,adapter3);


        recyclerView4= (RecyclerView) output.findViewById(R.id.recycleview4);

        adapter4 = new TVPotraitAdapter(getContext(), shows4, new MovieItemClickListener() {
            @Override
            public void favButtonClicked(MoviePortrait item, int position) {

                //kh;

            }


        });


        recyclerView4.setAdapter(adapter4);

        recyclerView4.setItemAnimator(new DefaultItemAnimator());

        recyclerView4.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        LinearLayoutManager layoutManager4 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView4.setLayoutManager(layoutManager4);
        Log.d("Fragment","setr2");

        seeportrait("top_rated",shows4,adapter4);

        return output;

    }

    void seelandscape(String cat,final ArrayList<TV> list,final TVLandscapeAdapter adapter) {
        Log.d("Fragment", "seefunc");

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/tv/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        MovieSevice service = retrofit.create(MovieSevice.class);

        Call<TVRoot> call = service.getTVDetails(cat, "7e00b48b59b417dfc865afa6de61f2aa", 1);

        call.enqueue(new Callback<TVRoot>() {
            @Override
            public void onResponse(Call<TVRoot> call, Response<TVRoot> response) {

                TVRoot show = response.body();
                ArrayList<TV> shows1 = show.results;
                //ArrayList<Album> courses = a.getData().courses;

                list.clear();
                for (int i = 0; i < shows1.size(); i++) {

                    list.add(shows1.get(i));


                }

                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);


                Log.d("Fragment", "sucess");
                //progressBar.setVisibility(View.GONE);
                //recyclerView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<TVRoot> call, Throwable t) {

                Log.d("Fragment", t.getMessage());
            }
        });

    }


    void seeportrait(String cat,final ArrayList<TV> list,final TVPotraitAdapter adapter)
    {
        Log.d("Fragment","seefunc");

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/tv/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        MovieSevice service = retrofit.create(MovieSevice.class);

        Call<TVRoot> call = service.getTVDetails(cat,"7e00b48b59b417dfc865afa6de61f2aa",1);

        call.enqueue(new Callback<TVRoot>() {
            @Override
            public void onResponse(Call<TVRoot> call, Response<TVRoot> response) {

                TVRoot show = response.body();
                ArrayList<TV> shows1 = show.results;
                //ArrayList<Album> courses = a.getData().courses;

                list.clear();
                for(int i = 0;i<shows1.size();i++){

                    list.add(shows1.get(i));


                }

                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);

                Log.d("Fragment","sucess");

            }

            @Override
            public void onFailure(Call<TVRoot> call, Throwable t) {

                Log.d("Fragment",t.getMessage());
            }
        });



    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        Intent intent= new Intent(getContext(),ViewAllTVActivity.class);

        if(id == R.id.viewtoday)
        {
            intent.putExtra("category","airing_today");
            intent.putExtra("title","Airing Today Shows");
        }
        else if(id == R.id.viewonair)
        {
            intent.putExtra("category","on_the_air");
            intent.putExtra("title","On The Air Shows");
        }
        else if(id == R.id.viewpopular)
        {
            intent.putExtra("category","popular");
            intent.putExtra("title","Popular Shows");
        }

        else if(id == R.id.viewtoprated)
        {
            intent.putExtra("category","top_rated");
            intent.putExtra("title","Top Rated Shows");
        }


        startActivity(intent);

    }



}



