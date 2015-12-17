package net.aung.moviemaniac.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import net.aung.moviemaniac.adapters.MovieListAdapter;
import net.aung.moviemaniac.controllers.MovieItemController;
import net.aung.moviemaniac.data.vos.MovieVO;
import net.aung.moviemaniac.mvp.presenters.MovieListPresenter;
import net.aung.moviemaniac.mvp.views.MovieListView;
import net.aung.moviemaniac.views.components.recyclerview.SmartScrollListener;
import net.aung.moviemaniac.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieListFragment extends BaseFragment
        implements MovieListView,
        SwipeRefreshLayout.OnRefreshListener,
        SmartScrollListener.ControllerSmartScroll {

    @Bind(R.id.rv_movies)
    RecyclerView rvMovies;

    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;

    private View rootView;

    private MovieListAdapter movieListAdapter;
    private MovieListPresenter movieListPresenter;
    private SmartScrollListener smartScrollListener;
    private MovieItemController controller;

    public static MovieListFragment newInstance() {
        MovieListFragment fragment = new MovieListFragment();
        return fragment;
    }

    public MovieListFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        controller = (MovieItemController) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieListAdapter = MovieListAdapter.newInstance(controller);

        movieListPresenter = new MovieListPresenter(this);
        movieListPresenter.onCreate();

        smartScrollListener = new SmartScrollListener(this);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ButterKnife.bind(this, rootView);

        rvMovies.setAdapter(movieListAdapter);
        rvMovies.addOnScrollListener(smartScrollListener);

        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_red_dark);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movie_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            Snackbar.make(rootView, "Search your favourite movies", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        movieListPresenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        movieListPresenter.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        movieListPresenter.onDestroy();
    }

    @Override
    public boolean isMovieListEmpty() {
        return movieListAdapter == null || movieListAdapter.getItemCount() == 0;
    }

    @Override
    public void displayMovieList(List<MovieVO> movieList, boolean isToAppend) {
        if (swipeContainer.isRefreshing()) {
            swipeContainer.setRefreshing(false);
        }

        if(isToAppend){
            movieListAdapter.appendMovieList(movieList);
        } else {
            movieListAdapter.setMovieList(movieList);
        }
    }

    @Override
    public void displayFailToLoadData(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Action", null).show();
    }


    @Override
    public void onRefresh() {
        movieListPresenter.forceRefresh();
    }

    @Override
    public void onListEndReached() {
        movieListPresenter.loadMoreData();
    }
}
