package com.example.akanksha.imdb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewAllTVActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    ArrayList<TV> shows = new ArrayList<>();
    TVPotraitAdapter adapter;
    String cat;
    String title;
    int totalpages;

    private  static final int PAGE_START = 1;
    private  boolean isLoading = false;
    private  boolean isLastPage= false;
    private  int currentPage = PAGE_START;
    private  static int flag;

    int currentItems;
    int scrolledItems;
    int totalItems;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_tv);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        Intent intent = getIntent();
        cat = intent.getStringExtra("category") ;
        title = intent.getStringExtra("title") ;
        toolbar.setTitle(title);

        recyclerView = findViewById(R.id.recycleview);

        adapter = new TVPotraitAdapter(this, shows, new MovieItemClickListener() {
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

        final GridLayoutManager layoutManager1 = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager1);


        // final ProgressBar progressBar= new ProgressBar(this);
        //recyclerView.addFooterView(progressBar,null,false);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView view, int scrollState) {

            }

            @Override
            public void onScrolled(RecyclerView view, int dx, int dy) {

                super.onScrolled(view,dx,dy);

                currentItems = layoutManager1.getChildCount();
                totalItems = layoutManager1.getItemCount();
                scrolledItems = layoutManager1.findFirstVisibleItemPosition();


                if( currentPage < totalpages && !isLoading &&  currentItems + scrolledItems == totalItems){

                    currentPage+=1;
                    see(cat,shows,adapter);

                }


            }
        });

        // Toast.makeText(getContext(),"afterSet", Toast.LENGTH_LONG).show();
        Log.d("Fragment","afterset");

        see(cat,shows,adapter);


    }

    void see(String cat,final ArrayList<TV> list,final TVPotraitAdapter adapter)
    {

        isLoading=true;

        Log.d("Fragment","seefunc");

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/tv/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        MovieSevice service = retrofit.create(MovieSevice.class);

        Call<TVRoot> call = service.getTVDetails(cat,"7e00b48b59b417dfc865afa6de61f2aa",currentPage);

        call.enqueue(new Callback<TVRoot>() {
            @Override
            public void onResponse(Call<TVRoot> call, Response<TVRoot> response) {

                TVRoot show = response.body();
                ArrayList<TV> shows1 = show.results;

                totalpages = show.total_pages;

                //list.clear();

                if (show != null) {
                    for(int i = 0;i<shows1.size();i++){

                        list.add(shows1.get(i));


                    }

                    adapter.notifyDataSetChanged();

                    isLoading = false;

                } else {

                    isLastPage = true;
                }


                Log.d("Fragment","sucess");
                //progressBar.setVisibility(View.GONE);
                //recyclerView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<TVRoot> call, Throwable t) {

                Log.d("Fragment",t.getMessage());
            }
        });


    }


}
