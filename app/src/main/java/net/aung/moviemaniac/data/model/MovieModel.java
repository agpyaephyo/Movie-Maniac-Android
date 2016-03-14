package net.aung.moviemaniac.data.model;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.data.vos.GenreVO;
import net.aung.moviemaniac.data.vos.MovieVO;
import net.aung.moviemaniac.data.vos.TrailerVO;
import net.aung.moviemaniac.events.DataEvent;
import net.aung.moviemaniac.data.restapi.MovieDataSource;
import net.aung.moviemaniac.data.restapi.MovieDataSourceImpl;
import net.aung.moviemaniac.data.restapi.responses.MovieListResponse;
import net.aung.moviemaniac.utils.MovieManiacConstants;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by aung on 12/12/15.
 */
public class MovieModel {

    public static final int INITIAL_PAGE_NUMBER = 1;

    private static MovieModel objInstance = getInstance(); //active initiating.

    private ArrayMap<Integer, MovieVO> movieArrayMap;
    private ArrayMap<Integer, GenreVO> genreArrayMap;
    private MovieDataSource movieDataSource;

    public static MovieModel getInstance() {
        if (objInstance == null) {
            objInstance = new MovieModel();
        }

        return objInstance;
    }

    private MovieModel() {
        movieArrayMap = new ArrayMap<>();
        genreArrayMap = new ArrayMap<>();
        movieDataSource = MovieDataSourceImpl.getInstance();

        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }

        movieDataSource.loadGenreList();
    }

    public void loadMostPopularMovieList(int pageNumber, boolean isForce) {
        Log.d(MovieManiacApp.TAG, "Loading popular movies for pageNumber : "+pageNumber);
        movieDataSource.loadPopularMovies(pageNumber, isForce);
    }

    public void loadTopRatedMovieList(int pageNumber, boolean isForce) {
        Log.d(MovieManiacApp.TAG, "Loading top-rated movies for pageNumber : "+pageNumber);
        movieDataSource.loadTopRatedMovies(pageNumber, isForce);
    }

    public MovieVO loadMovieDetailByMovieId(int movieId) {
        MovieVO movie = movieArrayMap.get(movieId);
        if (!movie.isDetailLoaded()) {
            Log.d(MovieManiacApp.TAG, "loadMovieDetailByMovieId " + movieId);
            movieDataSource.loadMovieDetail(movieId);
        }

        return movie;
    }

    public List<TrailerVO> loadTrailerListByMovieId(int movieId) {
        MovieVO movie = movieArrayMap.get(movieId);
        List<TrailerVO> trailerList = movie.getTrailerList();

        if (trailerList == null) {
            Log.d(MovieManiacApp.TAG, "loading trailer list for movieId " + movieId);
            movieDataSource.loadMovieTrailers(movieId);
        }

        return trailerList;
    }

    public void onEventMainThread(DataEvent.LoadedMostPopularMovieListEvent event) {
        MovieListResponse response = event.getResponse();

        ArrayList<MovieVO> loadedMovieList = response.getResults();
        storeMoviesInArrayMap(loadedMovieList);

        //Persistent into DB.
        MovieVO.saveMovieFromList(loadedMovieList, MovieManiacConstants.MOVIE_TYPE_MOST_POPULAR);

        DataEvent.ShowMovieListEvent showDataEvent = new DataEvent.ShowMostPopularMovieListEvent(loadedMovieList, event.isForce(), event.getResponse().getPage());
        EventBus.getDefault().post(showDataEvent);
    }

    public void onEventMainThread(DataEvent.LoadedTopRatedMovieListEvent event) {
        MovieListResponse response = event.getResponse();

        ArrayList<MovieVO> loadedMovieList = response.getResults();
        storeMoviesInArrayMap(loadedMovieList);

        //Persistent into DB.
        MovieVO.saveMovieFromList(loadedMovieList, MovieManiacConstants.MOVIE_TYPE_TOP_RATED);

        DataEvent.ShowMovieListEvent showDataEvent = new DataEvent.ShowTopRatedMovieListEvent(loadedMovieList, event.isForce(), event.getResponse().getPage());
        EventBus.getDefault().post(showDataEvent);
    }

    public void onEventMainThread(DataEvent.LoadedMovieTrailerEvent event) {
        MovieVO movie = movieArrayMap.get(event.getMovieId());
        movie.setTrailerList(event.getResponse().getTrailerList());

        //Persistent into DB.
        movie.saveTrailerList();
    }

    public void onEventMainThread(DataEvent.LoadedMovieDetailEvent event) {
        MovieVO movieWithDetail = event.getMovie();
        movieWithDetail.setIsDetailLoaded(true);
        movieArrayMap.put(event.getMovieId(), movieWithDetail);

        //Persistent into DB.
        movieWithDetail.updateMovieFromDetail();
    }

    public void onEventMainThread(DataEvent.LoadedGenreListEvent event) {
        ArrayList<GenreVO> genreList = event.getResponse().getGenreList();
        storeGenreInArrayMap(genreList);

        //Persistent into DB.
        GenreVO.saveGenreFromList(genreList);
    }

    private void storeGenreInArrayMap(ArrayList<GenreVO> genreList) {
        for (GenreVO genre : genreList) {
            genreArrayMap.put(genre.getId(), genre);
        }
    }

    private void storeMoviesInArrayMap(ArrayList<MovieVO> loadedMovieList) {
        for (MovieVO movie : loadedMovieList) {
            int[] genreIds = movie.getGenreIds();
            ArrayList<GenreVO> genreList = new ArrayList<>(genreIds.length);
            for (int genreId : genreIds) {
                genreList.add(genreArrayMap.get(genreId));
            }
            movie.setGenreList(genreList);
            movieArrayMap.put(movie.getId(), movie);
        }
    }
}
