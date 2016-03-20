package net.aung.moviemaniac.data.model;

import android.util.Log;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.data.restapi.MovieDataSource;
import net.aung.moviemaniac.data.restapi.MovieDataSourceImpl;
import net.aung.moviemaniac.data.restapi.responses.MovieListResponse;
import net.aung.moviemaniac.data.restapi.responses.TVSeriesListResponse;
import net.aung.moviemaniac.data.vos.GenreVO;
import net.aung.moviemaniac.data.vos.MovieReviewVO;
import net.aung.moviemaniac.data.vos.MovieVO;
import net.aung.moviemaniac.data.vos.TVSeriesVO;
import net.aung.moviemaniac.events.DataEvent;
import net.aung.moviemaniac.utils.MovieManiacConstants;
import net.aung.moviemaniac.utils.SettingsUtils;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by aung on 12/12/15.
 */
public class MovieModel {

    public static final int INITIAL_PAGE_NUMBER = 1;

    private static MovieModel objInstance = getInstance(); //active initiating.

    private MovieDataSource movieDataSource;

    public static MovieModel getInstance() {
        if (objInstance == null) {
            objInstance = new MovieModel();
        }

        return objInstance;
    }

    private MovieModel() {
        movieDataSource = MovieDataSourceImpl.getInstance();

        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }

        if (!SettingsUtils.isGenreListLoaded()) {
            movieDataSource.loadGenreList();
        }
    }

    public void loadMostPopularMovieList(int pageNumber, boolean isForce) {
        Log.d(MovieManiacApp.TAG, "Loading popular movies for pageNumber : " + pageNumber);
        movieDataSource.loadPopularMovies(pageNumber, isForce);
    }

    public void loadTopRatedMovieList(int pageNumber, boolean isForce) {
        Log.d(MovieManiacApp.TAG, "Loading top-rated movies for pageNumber : " + pageNumber);
        movieDataSource.loadTopRatedMovies(pageNumber, isForce);
    }

    public void loadNowPlayingMovieList(int pageNumber, boolean isForce) {
        Log.d(MovieManiacApp.TAG, "Loading now playing movies for pageNumber : " + pageNumber);
        movieDataSource.loadNowPlayingMovies(pageNumber, isForce);
    }

    public void loadUpcomingMovieList(int pageNumber, boolean isForce) {
        Log.d(MovieManiacApp.TAG, "Loading upcoming movies for pageNumber : " + pageNumber);
        movieDataSource.loadUpcomingMovies(pageNumber, isForce);
    }

    public void loadMovieDetailByMovieId(MovieVO movie) {
        Log.d(MovieManiacApp.TAG, "Loading movie detail by movieId " + movie.getId());
        movieDataSource.loadMovieDetail(movie);
    }

    public void loadTrailerListByMovieId(int movieId) {
        Log.d(MovieManiacApp.TAG, "loading trailer list for movieId " + movieId);
        movieDataSource.loadMovieTrailers(movieId);
    }

    public void loadMovieReviewByMovieId(int movieId) {
        Log.d(MovieManiacApp.TAG, "loading movie review list for movieId " + movieId);
        movieDataSource.loadMovieReviews(movieId);
    }

    public void loadMostPopularTVSeriesList(int pageNumber, boolean isForce) {
        Log.d(MovieManiacApp.TAG, "Loading popular tv series for pageNumber : " + pageNumber);
        movieDataSource.loadPopularTVSeries(pageNumber, isForce);
    }

    public void loadTopRatedTVSeriesList(int pageNumber, boolean isForce) {
        Log.d(MovieManiacApp.TAG, "Loading top rated tv series for pageNumber : " + pageNumber);
        movieDataSource.loadTopRatedTVSeries(pageNumber, isForce);
    }

    public void loadTVSeriesDetailByTVSeriesId(TVSeriesVO tvSeries) {
        Log.d(MovieManiacApp.TAG, "Loading tv series detail by tv series id " + tvSeries.getTvSerieId());
        movieDataSource.loadTVSeriesDetail(tvSeries);
    }

    public void loadTrailerListByTVSeriesId(int tvSeriesId) {
        Log.d(MovieManiacApp.TAG, "loading trailer list for tv series id " + tvSeriesId);
        movieDataSource.loadTvSeriesTrailers(tvSeriesId);
    }

    public void onEventMainThread(DataEvent.LoadedMostPopularMovieListEvent event) {
        MovieListResponse response = event.getResponse();

        ArrayList<MovieVO> loadedMovieList = response.getResults();
        //Persistent into DB.
        MovieVO.saveMovieFromList(loadedMovieList, MovieManiacConstants.MOVIE_TYPE_MOST_POPULAR);

        DataEvent.ShowMovieListEvent showDataEvent = new DataEvent.ShowMostPopularMovieListEvent(loadedMovieList, event.isForce(), event.getResponse().getPage());
        EventBus.getDefault().post(showDataEvent);
    }

    public void onEventMainThread(DataEvent.LoadedUpcomingMovieListEvent event) {
        MovieListResponse response = event.getResponse();

        ArrayList<MovieVO> loadedMovieList = response.getResults();
        //Persistent into DB.
        MovieVO.saveMovieFromList(loadedMovieList, MovieManiacConstants.MOVIE_TYPE_UPCOMING);

        DataEvent.ShowMovieListEvent showDataEvent = new DataEvent.ShowUpcomingMovieListEvent(loadedMovieList, event.isForce(), event.getResponse().getPage());
        EventBus.getDefault().post(showDataEvent);
    }

    public void onEventMainThread(DataEvent.LoadedTopRatedMovieListEvent event) {
        MovieListResponse response = event.getResponse();

        ArrayList<MovieVO> loadedMovieList = response.getResults();
        //Persistent into DB.
        MovieVO.saveMovieFromList(loadedMovieList, MovieManiacConstants.MOVIE_TYPE_TOP_RATED);

        DataEvent.ShowMovieListEvent showDataEvent = new DataEvent.ShowTopRatedMovieListEvent(loadedMovieList, event.isForce(), event.getResponse().getPage());
        EventBus.getDefault().post(showDataEvent);
    }

    public void onEventMainThread(DataEvent.LoadedNowPlayingMovieListEvent event) {
        MovieListResponse response = event.getResponse();

        ArrayList<MovieVO> loadedMovieList = response.getResults();
        //Persistent into DB.
        MovieVO.saveMovieFromList(loadedMovieList, MovieManiacConstants.MOVIE_TYPE_NOW_PLAYING);

        DataEvent.ShowMovieListEvent showDataEvent = new DataEvent.ShowNowPlayingMovieListEvent(loadedMovieList, event.isForce(), event.getResponse().getPage());
        EventBus.getDefault().post(showDataEvent);
    }

    public void onEventMainThread(DataEvent.LoadedMovieTrailerEvent event) {
        //Persistent into DB.
        MovieVO.saveTrailerList(event.getMovieId(), event.getResponse().getTrailerList());
    }

    public void onEventMainThread(DataEvent.LoadedMovieDetailEvent event) {
        MovieVO movieWithDetail = event.getMovie();
        movieWithDetail.setIsDetailLoaded(true);

        //Persistent into DB.
        movieWithDetail.updateMovieFromDetail();
    }

    public void onEventMainThread(DataEvent.LoadedGenreListEvent event) {
        ArrayList<GenreVO> genreList = event.getResponse().getGenreList();

        //Persistent into DB.
        GenreVO.saveGenreFromList(genreList);

        //Update on Setting Flag.
        SettingsUtils.setGenreListLoaded(true);
    }

    public void onEventMainThread(DataEvent.LoadedMovieReviewEvent event) {
        int movieId = event.getResponse().getMovieId();
        List<MovieReviewVO> movieReviewList = event.getResponse().getReviewList();

        //Persistent into DB.
        MovieReviewVO.saveReviewsFromList(movieReviewList, movieId);
    }

    public void onEventMainThread(DataEvent.LoadedPopularTVSeriesListEvent event) {
        TVSeriesListResponse response = event.getResponse();

        ArrayList<TVSeriesVO> loadedTVSeriesList = response.getTvSeriesList();
        //Persistent into DB.
        TVSeriesVO.saveTVSeriesFromList(loadedTVSeriesList, MovieManiacConstants.TV_SERIES_TYPE_MOST_POPULAR);

        DataEvent.ShowTVSeriesListEvent showDataEvent = new DataEvent.ShowPopularTVSeriesListEvent(loadedTVSeriesList, event.isForce(), event.getResponse().getPage());
        EventBus.getDefault().post(showDataEvent);
    }

    public void onEventMainThread(DataEvent.LoadedTopRatedTVSeriesListEvent event) {
        TVSeriesListResponse response = event.getResponse();

        ArrayList<TVSeriesVO> loadedTVSeriesList = response.getTvSeriesList();
        //Persistent into DB.
        TVSeriesVO.saveTVSeriesFromList(loadedTVSeriesList, MovieManiacConstants.TV_SERIES_TYPE_TOP_RATED);

        DataEvent.ShowTVSeriesListEvent showDataEvent = new DataEvent.ShowTopRatedTVSeriesListEvent(loadedTVSeriesList, event.isForce(), event.getResponse().getPage());
        EventBus.getDefault().post(showDataEvent);
    }

    public void onEventMainThread(DataEvent.LoadedTVSeriesDetailEvent event) {
        TVSeriesVO tvSeriesWithDetail = event.getTvSeries();
        tvSeriesWithDetail.setDetailLoaded(true);

        //Persistent into DB.
        tvSeriesWithDetail.updateTVSeriesFromDetail();
    }

    public void onEventMainThread(DataEvent.LoadedTVSeriesTrailerEvent event) {
        //Persistent into DB.
        TVSeriesVO.saveTrailerList(event.getTvSeriesId(), event.getResponse().getTrailerList());
    }
}
