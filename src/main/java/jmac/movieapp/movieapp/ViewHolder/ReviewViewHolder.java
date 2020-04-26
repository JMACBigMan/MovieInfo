package jmac.movieapp.movieapp.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import Model.MovieReview;
import butterknife.BindView;
import butterknife.ButterKnife;
import jmac.movieapp.movieapp.R;

public class ReviewViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.movie_review_username)
    TextView mMovieReviewAuthor;
    @BindView(R.id.movie_review_content)
    TextView mMovieReviewContent;

    public ReviewViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final MovieReview mMovieReview) {
        mMovieReviewAuthor.setText(mMovieReview.getAuthor());
        mMovieReviewContent.setText(mMovieReview.getContent());
    }
}
