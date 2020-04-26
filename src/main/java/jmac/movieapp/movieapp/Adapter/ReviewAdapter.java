package jmac.movieapp.movieapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Model.MovieReview;
import jmac.movieapp.movieapp.R;
import jmac.movieapp.movieapp.ViewHolder.ReviewViewHolder;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

    private final ArrayList<MovieReview> mMovieReviews;

    public ReviewAdapter(ArrayList<MovieReview> mMovieReviews) {
        this.mMovieReviews = mMovieReviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_review_card_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        MovieReview mMovieReview = this.mMovieReviews.get(position);
        holder.bind(mMovieReview);
    }

    @Override
    public int getItemCount() {
        return this.mMovieReviews.size();
    }

    @Override
    public void onViewRecycled(@NonNull ReviewViewHolder holder) {
        super.onViewRecycled(holder);
    }
}
