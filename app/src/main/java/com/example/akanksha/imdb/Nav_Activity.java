package com.example.akanksha.imdb;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class Nav_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toolbar.setTitle("Movies");

        Movie_Fragment fragment = new Movie_Fragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.container,fragment);
        transaction.commit();

       // Toast.makeText(this,"lallal",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.movies) {
            // Handle the camera action

            toolbar.setTitle("Movies");
            Movie_Fragment fragment = new Movie_Fragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            transaction.replace(R.id.container,fragment);
            transaction.commit();
/*
            Intent intent = new Intent(this,Nav_Activity.class);

            //intent.putExtras(bundle);
            startActivity(intent);*/

        } else if (id == R.id.tvshows) {

            toolbar.setTitle("TV Shows");
           TVFragment fragment = new TVFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            transaction.replace(R.id.container,fragment);
            transaction.commit();

        } else if (id == R.id.favorites) {


            Intent intent = new Intent(this,FavoriteActivity.class);

            startActivity(intent);


        }

        else if (id == R.id.watchlist) {


            Intent intent = new Intent(this,WatchListActivity.class);

            startActivity(intent);


        }


        else if (id == R.id.share) {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String extraText = "Hey! Want To Contribute! Fork It .\n";
            extraText += "https://github.com/iam-akankshaa/Popcorn";
            intent.putExtra(Intent.EXTRA_TEXT, extraText);
            startActivity(intent);

        } else if (id == R.id.about) {


            Intent intent = new Intent(this,AboutActivity.class);
            startActivity(intent);

        }

        else if(id == R.id.feedback)
        {

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SENDTO); //Only For Mail

            Uri u =Uri.parse("mailto:akankshajain0209@gmail.com");

            intent.setData(u);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
