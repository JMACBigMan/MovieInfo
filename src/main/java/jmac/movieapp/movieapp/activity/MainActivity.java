package jmac.movieapp.movieapp.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Model.Movie;
import Model.MoviePageResult;
import butterknife.OnClick;
import jmac.movieapp.movieapp.Adapter.MovieAdapter;
import jmac.movieapp.movieapp.Network.GetMovieDataService;
import jmac.movieapp.movieapp.Network.RetrofitInstance;
import jmac.movieapp.movieapp.R;
import jmac.movieapp.movieapp.data.FavoriteContract;
import jmac.movieapp.movieapp.utils.EndlessRecyclerViewScrollListener;
import jmac.movieapp.movieapp.utils.MovieClickListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final String API_KEY = "ac46563e77026ef303b9a2cfd883f9a1";
    private static int totalPages;
    private static int currentSortMode = 1;
    private Call<MoviePageResult> call;
    private RecyclerView recyclerView;
    private List<Movie> movieResults;
    private MovieAdapter movieAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv_movies);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        recyclerView.setLayoutManager(manager);

        loadPage(1);

        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if ((page + 1) <= totalPages) {
                    loadPage(page + 1);
                }
            }
        };

        recyclerView.addOnScrollListener(scrollListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //SortID 1 -> Popularity
        //SortID 2 -> Top rated
        switch (item.getItemId()) {
            case R.id.sort_by_popularity:
                currentSortMode = 1;
                break;
            case R.id.sort_by_top:
                currentSortMode = 2;
                break;
        }
        loadPage(1);
        return super.onOptionsItemSelected(item);

    }

    private void loadPage(final int page) {
        GetMovieDataService movieDataService = RetrofitInstance.getRetrofitInstance().create(GetMovieDataService.class);

        switch(currentSortMode){
            case 1:
                call = movieDataService.getPopularMovies(page, API_KEY);
                break;
            case 2:
                call = movieDataService.getTopRatedMovies(page, API_KEY);
                break;
        }


        call.enqueue(new Callback<MoviePageResult>() {
            @Override
            public void onResponse(Call<MoviePageResult> call, Response<MoviePageResult> response) {

                if(page == 1) {
                    movieResults = response.body().getMovieResult();
                    totalPages = response.body().getTotalPages();

                    movieAdapter = new MovieAdapter(movieResults, new MovieClickListener() {

                        @Override
                        public void onMovieClick(Movie movie) {
                            Intent intent = new Intent(MainActivity.this, MovieActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("movie", movie);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                    recyclerView.setAdapter(movieAdapter);
                } else {
                    List<Movie> movies = response.body().getMovieResult();
                    for(Movie movie : movies){
                        movieResults.add(movie);
                        movieAdapter.notifyItemInserted(movieResults.size() - 1);
                    }
                }

            }

            @Override
            public void onFailure(Call<MoviePageResult> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static String movieImagePathBuilder(String imagePath) {
        return "https://image.tmdb.org/t/p/" +
                "w500" +
                imagePath;
    }


    private ArrayList<Movie> getFavoriteMovies(){
        ArrayList<Movie> movieList = new ArrayList<>();
        Cursor cursor = getContentResolver()
                .query(FavoriteContract.FavoriteEntry.CONTENT_URI,null,null,null,null);

        assert cursor != null;
        if (cursor.moveToFirst()){
            do{
                Movie movie = new Movie();

                int id = cursor.getInt(cursor.getColumnIndex("movie_id"));
                String movieTitle = cursor.getString(cursor.getColumnIndex("movie_title"));
                String movieOverview = cursor.getString(cursor.getColumnIndex("movie_overview"));
                double movieVoteAverage = cursor.getDouble(cursor.getColumnIndex("movie_vote_average"));
                String movieReleaseDate = cursor.getString(cursor.getColumnIndex("movie_release_date"));
                String moviePosterPath = cursor.getString(cursor.getColumnIndex("movie_poster_path"));

                movie.setId(id);
                movie.setTitle(movieTitle);
                movie.setOverview(movieOverview);
                movie.setVoteAverage(movieVoteAverage);
                movie.setReleaseDate(movieReleaseDate);
                movie.setPosterPath(moviePosterPath);

                movieList.add(movie);

            }while(cursor.moveToNext());
        }
        cursor.close();

        return movieList;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @OnClick(R.id.tv_no_internet_error_refreshs)
    public void refreshActivity(){
        finish();
        startActivity(getIntent());
    }

}