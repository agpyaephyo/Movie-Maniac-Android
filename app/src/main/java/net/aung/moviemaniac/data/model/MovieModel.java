package net.aung.moviemaniac.data.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.data.db.MovieManiacDB;
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
public class MovieModel extends ViewModel {

    public static final int INITIAL_PAGE_NUMBER = 1;

    private static MovieModel objInstance = getInstance(); //active initiating.

    private MovieDataSource movieDataSource;
    private MovieManiacDB movieDB;

    //private MutableLiveData<List<MovieVO>> mMostPopularMovies;
    private MutableLiveData<List<MovieVO>> mUpcomingMovies;
    private MutableLiveData<List<MovieVO>> mTopRatedMovies;
    private MutableLiveData<List<MovieVO>> mNowPlayingMovies;

    public static MovieModel getInstance() {
        return objInstance;
    }

    public static void initMovieModel(Context context) {
        objInstance = new MovieModel(context);
    }

    public MovieModel(Context context) {
        //mMostPopularMovies = new MutableLiveData<>();
        mUpcomingMovies = new MutableLiveData<>();
        mTopRatedMovies = new MutableLiveData<>();
        mNowPlayingMovies = new MutableLiveData<>();

        movieDataSource = MovieDataSourceImpl.getInstance();

        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }

        if (!SettingsUtils.isGenreListLoaded(context)) {
            movieDataSource.loadGenreList(context);
        }
    }

    public void initMovieDB(Context context) {
        movieDB = MovieManiacDB.getMovieManiacDatabase(context);
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
        Log.d(MovieManiacApp.TAG, "Loading movie detail by movieId " + movie.getMovieId());
        movieDataSource.loadMovieDetail(movie);
    }

    public void loadMovieDetailByMovieId(int movieId) {
        Log.d(MovieManiacApp.TAG, "Loading movie detail by movieId " + movieId);
        movieDataSource.loadMovieDetail(movieId);
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

    public void loadTVSeriesDetailByTVSeriesId(int tVSeriesId) {
        Log.d(MovieManiacApp.TAG, "Loading tv series detail by tv series id " + tVSeriesId);
        movieDataSource.loadTVSeriesDetail(tVSeriesId);
    }

    public void loadTrailerListByTVSeriesId(int tvSeriesId) {
        Log.d(MovieManiacApp.TAG, "loading trailer list for tv series id " + tvSeriesId);
        movieDataSource.loadTvSeriesTrailers(tvSeriesId);
    }

    public void searchOnMovies(String query) {
        Log.d(MovieManiacApp.TAG, "Search on movie with query " + query);
        movieDataSource.searchOnMovie(query);
    }

    public void searchOnTVSeries(String query) {
        Log.d(MovieManiacApp.TAG, "Search on tv series with query " + query);
        movieDataSource.searchOnTVSeries(query);
    }

    public void onEventMainThread(DataEvent.LoadedMostPopularMovieListEvent event) {
        MovieListResponse response = event.getResponse();

        //mMostPopularMovies.setValue(response.getResults());
        movieDB.movieDao().insertMovies(response.getResults().toArray(new MovieVO[1]));

        /*
        ArrayList<MovieVO> loadedMovieList = response.getResults();
        //Persistent into DB.
        MovieVO.saveMovieFromList(loadedMovieList, MovieManiacConstants.MOVIE_TYPE_MOST_POPULAR);

        DataEvent.ShowMovieListEvent showDataEvent = new DataEvent.ShowMostPopularMovieListEvent(loadedMovieList, event.isForce(), event.getResponse().getPage());
        EventBus.getDefault().post(showDataEvent);
        */
    }

    public void onEventMainThread(DataEvent.LoadedUpcomingMovieListEvent event) {
        MovieListResponse response = event.getResponse();

        mUpcomingMovies.setValue(response.getResults());
        /*
        ArrayList<MovieVO> loadedMovieList = response.getResults();
        //Persistent into DB.
        MovieVO.saveMovieFromList(loadedMovieList, MovieManiacConstants.MOVIE_TYPE_UPCOMING);

        DataEvent.ShowMovieListEvent showDataEvent = new DataEvent.ShowUpcomingMovieListEvent(loadedMovieList, event.isForce(), event.getResponse().getPage());
        EventBus.getDefault().post(showDataEvent);
        */
    }

    public void onEventMainThread(DataEvent.LoadedTopRatedMovieListEvent event) {
        MovieListResponse response = event.getResponse();

        mTopRatedMovies.setValue(response.getResults());
        /*
        ArrayList<MovieVO> loadedMovieList = response.getResults();
        //Persistent into DB.
        MovieVO.saveMovieFromList(loadedMovieList, MovieManiacConstants.MOVIE_TYPE_TOP_RATED);

        DataEvent.ShowMovieListEvent showDataEvent = new DataEvent.ShowTopRatedMovieListEvent(loadedMovieList, event.isForce(), event.getResponse().getPage());
        EventBus.getDefault().post(showDataEvent);
        */
    }

    public void onEventMainThread(DataEvent.LoadedNowPlayingMovieListEvent event) {
        MovieListResponse response = event.getResponse();

        mNowPlayingMovies.setValue(response.getResults());
        /*
        ArrayList<MovieVO> loadedMovieList = response.getResults();
        //Persistent into DB.
        MovieVO.saveMovieFromList(loadedMovieList, MovieManiacConstants.MOVIE_TYPE_NOW_PLAYING);

        DataEvent.ShowMovieListEvent showDataEvent = new DataEvent.ShowNowPlayingMovieListEvent(loadedMovieList, event.isForce(), event.getResponse().getPage());
        EventBus.getDefault().post(showDataEvent);
        */
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
        SettingsUtils.setGenreListLoaded(event.getContext(), true);
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

    public LiveData<List<MovieVO>> getmMostPopularMovies() {
        return movieDB.movieDao().getAllMovies();
    }

    public LiveData<List<MovieVO>> getmUpcomingMovies() {
        return mUpcomingMovies;
    }

    public LiveData<List<MovieVO>> getmTopRatedMovies() {
        return mTopRatedMovies;
    }

    public LiveData<List<MovieVO>> getmNowPlayingMovies() {
        return mNowPlayingMovies;
    }
}
