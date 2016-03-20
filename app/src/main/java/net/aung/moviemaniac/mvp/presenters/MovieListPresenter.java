package net.aung.moviemaniac.mvp.presenters;

import android.support.annotation.NonNull;

import net.aung.moviemaniac.data.model.MovieModel;
import net.aung.moviemaniac.events.DataEvent;
import net.aung.moviemaniac.mvp.views.MovieListView;
import net.aung.moviemaniac.utils.MovieManiacConstants;
import net.aung.moviemaniac.utils.SettingsUtils;

/**
 * Created by aung on 12/12/15.
 */
public class MovieListPresenter extends BasePresenter {

    private MovieListView movieListView;
    private int movieCategory;

    private int pageNumber;

    public MovieListPresenter(@NonNull MovieListView movieListView, int movieCategory) {
        this.movieListView = movieListView;
        this.movieCategory = movieCategory;
        pageNumber = SettingsUtils.retrievePageNumber(movieCategory);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    public void loadMovieListFromNetwork() {
        loadNewMovieList(false);
    }

    public void onEventMainThread(DataEvent.ShowMostPopularMovieListEvent event) {
        if (movieCategory == MovieManiacConstants.CATEGORY_MOST_POPULAR_MOVIES) {
            pageNumber = event.getPageNumber() + 1;
            SettingsUtils.saveMoviePageNumber(movieCategory, pageNumber);
        }
    }

    public void onEventMainThread(DataEvent.ShowTopRatedMovieListEvent event) {
        if (movieCategory == MovieManiacConstants.CATEGORY_TOP_RATED_MOVIES) {
            pageNumber = event.getPageNumber() + 1;
            SettingsUtils.saveMoviePageNumber(movieCategory, pageNumber);
        }
    }

    public void onEventMainThread(DataEvent.ShowNowPlayingMovieListEvent event) {
        if (movieCategory == MovieManiacConstants.CATEGORY_NOW_PLAYING_MOVIES) {
            pageNumber = event.getPageNumber() + 1;
            SettingsUtils.saveMoviePageNumber(movieCategory, pageNumber);
        }
    }

    public void onEventMainThread(DataEvent.ShowUpcomingMovieListEvent event) {
        if (movieCategory == MovieManiacConstants.CATEGORY_UPCOMING_MOVIES) {
            pageNumber = event.getPageNumber() + 1;
            SettingsUtils.saveMoviePageNumber(movieCategory, pageNumber);
        }
    }

    public void onEventMainThread(DataEvent.FailedToLoadDataEvent event) {
        movieListView.displayFailToLoadData(event.getMessage());
    }

    private void loadNewMovieList(boolean isForce) {
        if (movieCategory == MovieManiacConstants.CATEGORY_MOST_POPULAR_MOVIES) {
            MovieModel.getInstance().loadMostPopularMovieList(pageNumber, isForce);
        } else if (movieCategory == MovieManiacConstants.CATEGORY_TOP_RATED_MOVIES) {
            MovieModel.getInstance().loadTopRatedMovieList(pageNumber, isForce);
        } else if (movieCategory == MovieManiacConstants.CATEGORY_NOW_PLAYING_MOVIES) {
            MovieModel.getInstance().loadNowPlayingMovieList(pageNumber, isForce);
        } else if (movieCategory == MovieManiacConstants.CATEGORY_UPCOMING_MOVIES) {
            MovieModel.getInstance().loadUpcomingMovieList(pageNumber, isForce);
        }
    }

    public void forceRefresh() {
        pageNumber = MovieModel.INITIAL_PAGE_NUMBER;
        SettingsUtils.resetPageNumber(movieCategory);
        loadNewMovieList(true);
    }

    public void loadMoreData() {
        if (pageNumber != MovieModel.INITIAL_PAGE_NUMBER) {
            loadNewMovieList(false);
        }
    }
}
