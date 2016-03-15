package net.aung.moviemaniac.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.R;
import net.aung.moviemaniac.adapters.MoviePagerAdapter;
import net.aung.moviemaniac.controllers.MovieItemController;
import net.aung.moviemaniac.data.vos.MovieVO;
import net.aung.moviemaniac.fragments.MovieDetailFragment;
import net.aung.moviemaniac.fragments.MovieListFragment;
import net.aung.moviemaniac.menus.LeftMenuFragment;
import net.aung.moviemaniac.utils.MovieManiacConstants;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieListActivity extends BaseActivity
        implements MovieItemController {

    private LeftMenuFragment mLeftMenu;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.pager_movies)
    ViewPager pagerMovies;

    @Bind(R.id.tl_movies)
    TabLayout tlMovies;

    private MoviePagerAdapter mMoviePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this, this);

        ((MovieManiacApp)getApplication()).startTracking();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.section_movie));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        }

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SortMovieDialog dialog = new SortMovieDialog(MovieListActivity.this);
                dialog.show();
            }
        });
        */

        mLeftMenu = (LeftMenuFragment) getSupportFragmentManager().findFragmentById(R.id.left_meu);
        mLeftMenu.setUp(R.id.left_meu, mDrawerLayout, mCallbackManager);

        final FrameLayout flTabletMovieDetail = (FrameLayout) findViewById(R.id.fl_tablet_movie_detail);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeftMenu.openMenu();
            }
        });

        mMoviePagerAdapter = new MoviePagerAdapter(getSupportFragmentManager());
        mMoviePagerAdapter.addTab(MovieListFragment.newInstance(MovieManiacConstants.CATEGORY_MOST_POPULAR_MOVIES), getString(R.string.most_popular_movies));
        mMoviePagerAdapter.addTab(MovieListFragment.newInstance(MovieManiacConstants.CATEGORY_TOP_RATED_MOVIES), getString(R.string.top_rated_movies));
        mMoviePagerAdapter.addTab(MovieListFragment.newInstance(MovieManiacConstants.CATEGORY_MY_FAVOURITES), getString(R.string.my_favourites));

        pagerMovies.setAdapter(mMoviePagerAdapter);
        tlMovies.setupWithViewPager(pagerMovies);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*
        if (id == R.id.action_about) {
            return true;
        }
        */

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNavigateToDetail(MovieVO movie) {
        if (getResources().getBoolean(R.bool.isTablet)) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_tablet_movie_detail, MovieDetailFragment.newInstance(movie.getId()))
                    .commit();

            mDrawerLayout.openDrawer(Gravity.RIGHT);
        } else {
            Intent intentToDetail = MovieDetailActivity.createNewIntent(movie.getId());
            startActivity(intentToDetail);
        }

        /*
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.fl_container, MovieDetailFragment.newInstance(movie.getId()))
                .addToBackStack(null)
                .commit();
                */
    }
}
