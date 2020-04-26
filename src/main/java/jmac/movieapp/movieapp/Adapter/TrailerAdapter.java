package jmac.movieapp.movieapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Model.MovieTrailer;
import jmac.movieapp.movieapp.R;
import jmac.movieapp.movieapp.ViewHolder.TrailerViewHolder;
import jmac.movieapp.movieapp.utils.TrailerClickListener;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerViewHolder> {

    private final TrailerClickListener mTrailerClickListener;
    private final ArrayList<MovieTrailer> mMovieTrailers;

    public TrailerAdapter(ArrayList<MovieTrailer> mMovieTrailers, TrailerClickListener mTrailerClickListener) {
        this.mMovieTrailers = mMovieTrailers;
        this.mTrailerClickListener = mTrailerClickListener;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_trailer_card_review, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        MovieTrailer mMovieTrailer = this.mMovieTrailers.get(position);
        holder.bind(mMovieTrailer, mTrailerClickListener);
    }

    @Override
    public int getItemCount() {
        return this.mMovieTrailers.size();
    }

    @Override
    public void onViewRecycled(@NonNull TrailerViewHolder holder) {
        super.onViewRecycled(holder);
    }
}
