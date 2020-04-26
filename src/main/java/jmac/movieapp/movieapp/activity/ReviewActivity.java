package jmac.movieapp.movieapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Model.MovieReview;
import butterknife.BindView;
import butterknife.ButterKnife;
import jmac.movieapp.movieapp.Adapter.ReviewAdapter;
import jmac.movieapp.movieapp.R;

public class ReviewActivity extends AppCompatActivity {

    @BindView(R.id.rv_movie_reviews)
    RecyclerView mReviewsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        ArrayList<MovieReview> mMovieReviews = (ArrayList<MovieReview>) bundle.getSerializable("reviews");
        String mMovieTitle = bundle.getString("movie_title");

        setTitle(mMovieTitle + getString(R.string.review_activity_title));

        ReviewAdapter mReviewsAdapter = new ReviewAdapter(mMovieReviews);
        mReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mReviewsRecyclerView.setAdapter(mReviewsAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
