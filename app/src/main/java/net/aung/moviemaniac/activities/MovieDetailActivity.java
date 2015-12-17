package net.aung.moviemaniac.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import net.aung.moviemaniac.R;
import net.aung.moviemaniac.controllers.TrailerItemController;
import net.aung.moviemaniac.data.vos.TrailerVO;
import net.aung.moviemaniac.fragments.MovieDetailFragment;
import net.aung.moviemaniac.utils.ScreenUtils;
import net.aung.moviemaniac.utils.YoutubeUtils;

/**
 * Created by aung on 12/16/15.
 */
public class MovieDetailActivity extends BaseActivity implements TrailerItemController {

    private static final String INTENT_EXTRA_MOVIE_ID = "INTENT_EXTRA_MOVIE_ID";

    public static void startActivity(Activity hostActivity, int movieId) {
        Intent intent = new Intent(hostActivity, MovieDetailActivity.class);
        intent.putExtra(INTENT_EXTRA_MOVIE_ID, movieId);
        hostActivity.startActivity(intent);
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

    @Override
    public void onShowTrailer(TrailerVO trailer) {
        YoutubeUtils.getObjInstance().playYoutbueVideo(this, trailer.getKey());
    }
}
