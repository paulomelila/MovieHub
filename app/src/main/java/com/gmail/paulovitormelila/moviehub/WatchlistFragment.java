package com.gmail.paulovitormelila.moviehub;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Paulo on 18/07/2017.
 */

public class WatchlistFragment extends Fragment{
    private RecyclerView mMovieRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_watchlist, container, false);
        mMovieRecyclerView = (RecyclerView) view.findViewById(R.id.watchlist_recycler_view);
        mMovieRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }




}
