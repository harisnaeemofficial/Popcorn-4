package com.example.akanksha.imdb;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class WatchPagerAdapter  extends FragmentPagerAdapter{


    public WatchPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0){
            return new WatchMovieFragment();
        }
        else if(position == 1){

            return new WatchTVFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
