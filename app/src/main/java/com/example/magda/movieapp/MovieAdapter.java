/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.magda.movieapp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * The code is based on the code from udacities sunshine app.
 * (https://github.com/udacity/ud851-Sunshine).
 */


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private MovieDBEntry[] mMovieData;

    private final MovieAdapterOnClickHandler mClickHandler;
    private final Context mContext;

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler, Context context)
    {
        mClickHandler = clickHandler;
        mContext = context;
    }

    public interface MovieAdapterOnClickHandler {
        void onClick(MovieDBEntry movie);
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mMovieImageView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mMovieImageView = (ImageView) view.findViewById(R.id.iv_movie_list_item_poster);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            MovieDBEntry movie = mMovieData[adapterPosition];
            mClickHandler.onClick(movie);
        }
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        MovieDBEntry movieForThisEntry = mMovieData[position];
        String url = movieForThisEntry.getmPoster();
        Picasso.with(mContext)
                .load(url)
                .into(holder.mMovieImageView);
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

    public void setmMovieData(MovieDBEntry[] movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }

    public MovieDBEntry[] getValues() {
        return mMovieData;
    }
}
