package com.example.akanksha.imdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.akanksha.imdb.detailsofCast.AllCastAdapter;
import com.example.akanksha.imdb.detailsofCast.Cast;
import com.example.akanksha.imdb.detailsofCast.CastRoot;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewAllCastActivity extends AppCompatActivity {

    ArrayList<Cast> casts= new ArrayList<>();
    AllCastAdapter adapter;
    RecyclerView recyclerViewCast;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_cast);


        recyclerViewCast = findViewById(R.id.recycleviewallcast);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);

        adapter = new AllCastAdapter( casts,this);

        //Toast.makeText(this,"oncreatefrag", Toast.LENGTH_LONG).show();
        Log.d("Fragment","oncretefrag");


        recyclerViewCast.setAdapter(adapter);

        recyclerViewCast.setItemAnimator(new DefaultItemAnimator());

        recyclerViewCast.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewCast.setLayoutManager(layoutManager1);


        Toast.makeText(this,"afterSet", Toast.LENGTH_LONG).show();
        Log.d("Fragment","afterset");

        castfetch(id,casts,adapter);

    }

    void castfetch(int id, final ArrayList<Cast> list, final AllCastAdapter adapter) {
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

}
