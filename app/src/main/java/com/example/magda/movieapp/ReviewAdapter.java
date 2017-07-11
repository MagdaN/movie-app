package com.example.magda.movieapp;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {

    private MovieDBReview[] mReviewData;
    private final Context mContext;

    public ReviewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getItemCount() {
        if (mReviewData == null) {
            return 0;
        }
        return mReviewData.length;
    }

    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {

        public final TextView mReviewAuthor;
        public final TextView mReviewContent;

        public ReviewAdapterViewHolder(View view) {
            super(view);
            mReviewAuthor = (TextView) view.findViewById(R.id.tv_review_author);
            mReviewContent = (TextView) view.findViewById(R.id.tv_review_content);
        }

    }

    @Override
    public void onBindViewHolder(ReviewAdapterViewHolder holder, int position) {
        MovieDBReview reviewOnThisPosition = mReviewData[position];
        String author = reviewOnThisPosition.getmAuthor();
        String content = reviewOnThisPosition.getmContent();

        Resources res = mContext.getResources();
        holder.mReviewAuthor.setText(String.format(res.getString(R.string.author_wrote), author));
        holder.mReviewContent.setText(content);
    }

    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.review_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new ReviewAdapter.ReviewAdapterViewHolder(view);
    }

    public MovieDBReview[] getValues() {
        return mReviewData;
    }

    public void setmReviewData(MovieDBReview[] reviewData) {
        mReviewData = reviewData;
        notifyDataSetChanged();
    }
}
