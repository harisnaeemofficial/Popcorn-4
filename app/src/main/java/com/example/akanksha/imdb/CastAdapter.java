package com.example.akanksha.imdb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akanksha.imdb.detailsofCast.Cast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CastAdapter extends RecyclerView.Adapter<CastHolder>{


    LayoutInflater inflater;
    ArrayList<Cast> items;
    Context context;

    public CastAdapter(ArrayList items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public CastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View output= inflater.inflate(R.layout.cast_row,null,false);

        return new CastHolder(output);
    }

    @Override
    public void onBindViewHolder(@NonNull CastHolder holder, int position) {

        final  Cast cast = items.get(position);

        holder.realname.setText(cast.getName());
        holder.charname.setText(cast.getCharacter());

        String url = "https://image.tmdb.org/t/p/w185/" + cast.getProfilePath();

        Picasso.get().load(url).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });



    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
