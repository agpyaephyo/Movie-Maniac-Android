package net.aung.moviemaniac.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;

import net.aung.moviemaniac.controllers.MovieItemController;
import net.aung.moviemaniac.fragments.MovieListFragment;
import net.aung.moviemaniac.R;
import net.aung.moviemaniac.data.vos.MovieVO;
import net.aung.moviemaniac.menus.LeftMenuFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieListActivity extends BaseActivity
        implements MovieItemController {

    private LeftMenuFragment mLeftMenu;
    private CallbackManager mCallbackManager;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this, this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.title_yote_shin));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Filter the type of movies you like most", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mLeftMenu = (LeftMenuFragment) getSupportFragmentManager().findFragmentById(R.id.left_meu);
        mLeftMenu.setUp(R.id.left_meu, mDrawerLayout, mCallbackManager);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeftMenu.openMenu();
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, MovieListFragment.newInstance())
                    .commit();
        }

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
        if (id == R.id.action_about) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNavigateToDetail(MovieVO movie) {
        MovieDetailActivity.startActivity(this, movie.getId());

        /*
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.fl_container, MovieDetailFragment.newInstance(movie.getId()))
                .addToBackStack(null)
                .commit();
                */
    }
}
