package com.example.akanksha.imdb.detailsofreviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.akanksha.imdb.R;
import com.example.akanksha.imdb.detailsofCast.AllCastHolder;
import com.example.akanksha.imdb.detailsofCast.Cast;
import com.example.akanksha.imdb.detailsofvideo.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReviewAdapter  extends RecyclerView.Adapter<ReviewHolder>{

    LayoutInflater inflater;
    ArrayList<Review> items;
    Context context;

    public ReviewAdapter(ArrayList<Review> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View output= inflater.inflate(R.layout.review_row,null,false);

        return new ReviewHolder(output);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {

        final  Review review = items.get(position);

        holder.author.setText(review.getAuthor());
        String s = review.getContent();
        int length = review.getContent().length();
        Log.d("Detailtextlength",Integer.toString(length));

        SpannableString text = new SpannableString("....");

        if(length > 120 && (Math.abs(length - 120)) > 10){

            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(s,0,100);
            spannableStringBuilder.append(text);

            holder.content.setText(spannableStringBuilder.toString());

        }

        else{

            holder.content.setText(s);
        }


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final View output = inflater.inflate(R.layout.alert_review, null, false);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                TextView author = output.findViewById(R.id.author);
                TextView content = output.findViewById(R.id.content);
                author.setText(review.getAuthor());
                content.setText(review.getContent());

                builder.setView(output);
                AlertDialog dialog = builder.create(); //show the dialog
                dialog.show();


            }
        });


    }

    @Override
    public int getItemCount() {

        return items.size();
    }


}
