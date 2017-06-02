package com.example.magda.movieapp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private String[] mMovieData;


    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder {
        public final TextView mMovieTextView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mMovieTextView = (TextView) view.findViewById(R.id.tv_movie_data);

        }

    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        String movieForThisEntry = mMovieData[position];
        holder.mMovieTextView.setText(movieForThisEntry);
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new MovieAdapterViewHolder(view);
    }


    @Override
    public int getItemCount() {
        if (mMovieData == null) {
            return 0;
        }
        return mMovieData.length;
    }

    public void setmMovieData(String[] movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }
}
