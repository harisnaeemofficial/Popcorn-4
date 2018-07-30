package com.example.akanksha.imdb;

import android.support.v4.app.Fragment ;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FavoritePagerAdapter extends FragmentPagerAdapter {


    public FavoritePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0){

            return new FavoriteMovieFragment();
        }
        else if(position == 1){

            return new FavoriteTVFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
