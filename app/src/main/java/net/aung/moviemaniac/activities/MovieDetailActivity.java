package net.aung.moviemaniac.activities;

import android.content.Intent;
import android.os.Bundle;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.R;
import net.aung.moviemaniac.fragments.MovieDetailFragment;
import net.aung.moviemaniac.utils.ScreenUtils;

/**
 * Created by aung on 12/16/15.
 */
public class MovieDetailActivity extends BaseActivity {

    private static final String INTENT_EXTRA_MOVIE_ID = "INTENT_EXTRA_MOVIE_ID";

    public static Intent createNewIntent(int movieId) {
        Intent intent = new Intent(MovieManiacApp.getContext(), MovieDetailActivity.class);
        intent.putExtra(INTENT_EXTRA_MOVIE_ID, movieId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ScreenUtils.setStatusbarTranslucent(true, this);

        int movieId = getIntent().getIntExtra(INTENT_EXTRA_MOVIE_ID, 0);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_container, MovieDetailFragment.newInstance(movieId))
                    .commit();
        }
    }
}
