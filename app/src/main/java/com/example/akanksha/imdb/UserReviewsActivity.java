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
import android.widget.Toast;

import com.example.akanksha.imdb.detailsofCast.AllCastAdapter;
import com.example.akanksha.imdb.detailsofCast.Cast;
import com.example.akanksha.imdb.detailsofreviews.Review;
import com.example.akanksha.imdb.detailsofreviews.ReviewAdapter;
import com.example.akanksha.imdb.detailsofreviews.ReviewRoot;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserReviewsActivity extends AppCompatActivity {

    ArrayList<Review> reviews= new ArrayList<>();
    ReviewAdapter adapter;
    RecyclerView recyclerViewReview;
    int id;
    String cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reviews);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitle("User Reviews");

        recyclerViewReview = findViewById(R.id.recycleviewreviews);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        cat = intent.getStringExtra("category");
        adapter = new ReviewAdapter( reviews,this);

        //Toast.makeText(this,"oncreatefrag", Toast.LENGTH_LONG).show();
        Log.d("Fragment","oncretefrag");


        recyclerViewReview.setAdapter(adapter);

        recyclerViewReview.setItemAnimator(new DefaultItemAnimator());


        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewReview.setLayoutManager(layoutManager1);

        Log.d("Fragment","afterset");

       reviewfetch(id,reviews,adapter,cat);



    }



    public  void reviewfetch(int id,final ArrayList<Review> list, final ReviewAdapter adapter,String cat)
    {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/" + cat + "/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        MovieSevice service = retrofit.create(MovieSevice.class);

        Call<ReviewRoot> call = service.getReviewDetails(id,"7e00b48b59b417dfc865afa6de61f2aa");

        call.enqueue(new Callback<ReviewRoot>() {
            @Override
            public void onResponse(Call<ReviewRoot> call, Response<ReviewRoot> response) {

                ReviewRoot root = response.body();

                if(root != null) {

                    ArrayList<Review> reviews = root.getResults();

                    list.clear();
                    for (int i = 0; i < reviews.size(); i++) {

                        list.add(reviews.get(i));

                    }

                    adapter.notifyDataSetChanged();
                }

                Log.d("Fragment","sucess");

            }

            @Override
            public void onFailure(Call<ReviewRoot> call, Throwable t) {

                Log.d("Fragment",t.getMessage());
            }
        });


    }

}
