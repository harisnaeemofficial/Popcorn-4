package com.example.akanksha.imdb;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        textView = findViewById(R.id.link);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                String githubLink = "https://github.com/iam-akankshaa/Popcorn";
                Intent githubIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(githubLink));
                startActivity(githubIntent);
            }
        });

    }


}
