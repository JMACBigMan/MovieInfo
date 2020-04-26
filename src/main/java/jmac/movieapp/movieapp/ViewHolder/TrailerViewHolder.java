package jmac.movieapp.movieapp.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import Model.MovieTrailer;
import butterknife.BindView;
import butterknife.ButterKnife;
import jmac.movieapp.movieapp.R;
import jmac.movieapp.movieapp.utils.TrailerClickListener; public class TrailerViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_movie_trailer_name)
    TextView mMovieTrailerName;
    @BindView(R.id.cv_movie_trailer_cards)
    CardView mMovieCard;

    public TrailerViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final MovieTrailer mMovieTrailer, final TrailerClickListener mTrailerClickListener) {
        mMovieTrailerName.setText(mMovieTrailer.getName());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTrailerClickListener.onMovieTrailerClick(mMovieTrailer);
            }
        });
    }
}
