package com.example.akanksha.imdb.detailsofCast;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akanksha.imdb.CastHolder;
import com.example.akanksha.imdb.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllCastAdapter extends RecyclerView.Adapter<AllCastHolder>{

    LayoutInflater inflater;
    ArrayList<Cast> items;
    Context context;

    public AllCastAdapter(ArrayList<Cast> items, Context context) {

        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public AllCastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View output= inflater.inflate(R.layout.view_all_cast_row,null,false);

        return new AllCastHolder(output);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCastHolder holder, int position) {

        final  Cast cast = items.get(position);

        holder.realname.setText(cast.getName());
        holder.charname.setText(cast.getCharacter());

        String url = "https://image.tmdb.org/t/p/h632/" + cast.getProfilePath();

        Picasso.get().load(url).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
