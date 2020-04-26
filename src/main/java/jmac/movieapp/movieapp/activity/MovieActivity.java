package jmac.movieapp.movieapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import Model.Movie;
import Model.MovieTrailerResult;
import butterknife.BindView;
import butterknife.ButterKnife;
import jmac.movieapp.movieapp.Network.GetMovieTrailerService;
import jmac.movieapp.movieapp.Network.RetrofitInstance;
import jmac.movieapp.movieapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static jmac.movieapp.movieapp.activity.MainActivity.API_KEY;
import static jmac.movieapp.movieapp.activity.MainActivity.movieImagePathBuilder;

public class MovieActivity extends AppCompatActivity {
    @BindView(R.id.movie_activity_title)
    TextView mMovieTitle;
    @BindView(R.id.movie_activity_poster)
    ImageView mMoviePoster;
    @BindView(R.id.movie_activity_overview) TextView mMovieOverview;
    @BindView(R.id.movie_activity_release_date) TextView mMovieReleaseDate;
    @BindView(R.id.movie_activity_rating) TextView mMovieRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Movie mMovie = (Movie) bundle.getSerializable("movie");

        getTrailer(mMovie.getId());
        populateActivity(mMovie);

    }

    private void populateActivity(Movie mMovie){
        Picasso.with(this).load( movieImagePathBuilder(mMovie.getPosterPath())).into(mMoviePoster);
        mMovieTitle.setText(mMovie.getTitle());
        mMovieOverview.setText(mMovie.getOverview());
        mMovieReleaseDate.setText(mMovie.getReleaseDate());

        String userRatingText = String.valueOf(mMovie.getVoteAverage()) + "/10";
        mMovieRating.setText(userRatingText);
    }

    private void getTrailer(int movieId) {
        GetMovieTrailerService movieTrailerService = RetrofitInstance.getRetrofitInstance().create(GetMovieTrailerService.class);
        Call<MovieTrailerResult> call = movieTrailerService.getTrailers(movieId, API_KEY);


        call.enqueue(new Callback<MovieTrailerResult>() {
            @Override
            public void onResponse(Call<MovieTrailerResult> call, Response<MovieTrailerResult> response) {
                Log.wtf("MovieActivity", "https://youtube.com/watch?v=" + response.body().getTrailerResult().get(0).getKey());

            }

            @Override
            public void onFailure(Call<MovieTrailerResult> call, Throwable t) {
                Toast.makeText(MovieActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }


}