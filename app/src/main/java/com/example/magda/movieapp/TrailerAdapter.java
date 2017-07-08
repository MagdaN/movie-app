package com.example.magda.movieapp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {

    private MovieDBTrailer[] mTrailerData;
    private final TrailerAdapterOnClickHandler mClickHandler;
    private Context mContext;

    public TrailerAdapter(TrailerAdapterOnClickHandler clickHandler, Context context) {
        mClickHandler = clickHandler;
        mContext = context;
    }

    public interface TrailerAdapterOnClickHandler {
        void onClick(MovieDBTrailer trailer);
    }

    @Override
    public int getItemCount() {
        if (mTrailerData == null) {
            return 0;
        }
        return mTrailerData.length;
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.TrailerAdapterViewHolder holder, int position) {
        MovieDBTrailer trailerOnThisPosition = mTrailerData[position];
        String name = trailerOnThisPosition.getmName();
        String type = trailerOnThisPosition.getmType();

        holder.mTrailerName.setText(name);
        holder.mTrailerType.setText(type);
    }

    @Override
    public TrailerAdapter.TrailerAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.trailer_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new TrailerAdapter.TrailerAdapterViewHolder(view);
    }

    public void setmReviewData(MovieDBTrailer[] dbTrailers) {
        mTrailerData = dbTrailers;
        notifyDataSetChanged();
    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mTrailerName;
        public final TextView mTrailerType;

        public TrailerAdapterViewHolder(View view) {
            super(view);
            mTrailerName = (TextView) view.findViewById(R.id.tv_trailer_name);
            mTrailerType = (TextView) view.findViewById(R.id.tv_trailer_type);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            MovieDBTrailer trailer = mTrailerData[adapterPosition];
            mClickHandler.onClick(trailer);
        }
    }
}
