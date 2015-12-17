package net.aung.moviemaniac.mvp.presenters;

import android.support.annotation.NonNull;

import net.aung.moviemaniac.data.model.MovieModel;
import net.aung.moviemaniac.events.DataEvent;
import net.aung.moviemaniac.mvp.views.MovieListView;

/**
 * Created by aung on 12/12/15.
 */
public class MovieListPresenter extends BasePresenter {

    private MovieListView movieListView;
    private int currentPageNumber = MovieModel.INITIAL_PAGE_NUMBER;

    public MovieListPresenter(@NonNull MovieListView movieListView) {
        this.movieListView = movieListView;
    }

    @Override
    public void onStart() {
        if (movieListView.isMovieListEmpty()) {
            loadNewMovieList();
        }
    }

    @Override
    public void onStop() {

    }

    public void onEventMainThread(DataEvent.ShowMovieListEvent event) {
        currentPageNumber = event.getPageNumber() + 1;
        movieListView.displayMovieList(event.getMovieList(), !event.isForce());
    }

    public void onEventMainThread(DataEvent.FailedToLoadDataEvent event) {
        movieListView.displayFailToLoadData(event.getMessage());
    }

    private void loadNewMovieList() {
       MovieModel.getInstance().loadMovieListByPage(currentPageNumber, false);
    }

    public void forceRefresh() {
        currentPageNumber = MovieModel.INITIAL_PAGE_NUMBER;
        MovieModel.getInstance().loadMovieListByPage(currentPageNumber, true);
    }

    public void loadMoreData() {
        if (currentPageNumber != MovieModel.INITIAL_PAGE_NUMBER) {
            loadNewMovieList();
        }
    }
}
