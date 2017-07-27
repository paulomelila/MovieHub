package com.gmail.paulovitormelila.moviehub;

import android.support.v4.app.Fragment;

public class WatchlistActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new WatchlistFragment();
    }

}
