package net.aung.moviemaniac.data.model;

import android.os.AsyncTask;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import net.aung.moviemaniac.data.vos.GenreVO;
import net.aung.moviemaniac.data.vos.MovieVO;
import net.aung.moviemaniac.restapi.MovieDataSource;
import net.aung.moviemaniac.restapi.responses.MovieDiscoverResponse;
import net.aung.moviemaniac.PopularMoviesApplication;
import net.aung.moviemaniac.data.vos.TrailerVO;
import net.aung.moviemaniac.events.DataEvent;
import net.aung.moviemaniac.restapi.MovieDataSourceImpl;
import net.aung.moviemaniac.restapi.RestApiConstants;
import net.aung.moviemaniac.utils.CommonInstances;
import net.aung.moviemaniac.utils.JsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by aung on 12/12/15.
 */
public class MovieModel {

    public static final int INITIAL_PAGE_NUMBER = 1;

    private static final String DUMMY_MOVIE_LIST_FILE_NAME = "movie_discover.json";

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

        movieDataSource.getGenreList();
    }

    //Async
    public void loadMovieListByPage(int pageNumber, boolean isForce) {
        Log.d(PopularMoviesApplication.TAG, "loading new movie list for page " + pageNumber);
        movieDataSource.discoverMovieList(pageNumber, RestApiConstants.DEFAULT_SORT_BY, isForce);
    }

    public MovieVO loadMovieDetailByMovieId(int movieId) {
        MovieVO movie = movieArrayMap.get(movieId);
        if(!movie.isDetailLoaded()){
            Log.d(PopularMoviesApplication.TAG, "loadMovieDetailByMovieId " + movieId);
            movieDataSource.getMovieDetail(movieId);
        }

        return movie;
    }

    public List<TrailerVO> loadTrailerListByMovieId(int movieId) {
        MovieVO movie = movieArrayMap.get(movieId);
        List<TrailerVO> trailerList = movie.getTrailerList();

        if (trailerList == null) {
            Log.d(PopularMoviesApplication.TAG, "loading trailer list for movieId " + movieId);
            movieDataSource.getMovieTrailers(movieId);
        }

        return trailerList;
    }

    public void onEventMainThread(DataEvent.LoadedMovieDiscoverEvent event) {
        boolean isForce = event.isForce();
        MovieDiscoverResponse response = event.getResponse();
        int currentPageNumber = response.getPage();

        ArrayList<MovieVO> loadedMovieList = response.getResults();
        if (isForce) {
            movieArrayMap.clear();
        }
        storeMoviesInArrayMap(loadedMovieList);

        DataEvent.ShowMovieListEvent showDataEvent = new DataEvent.ShowMovieListEvent(loadedMovieList, event.isForce(), event.getResponse().getPage());
        EventBus.getDefault().post(showDataEvent);
    }

    public void onEventMainThread(DataEvent.LoadedMovieTrailerEvent event) {
        MovieVO movie = movieArrayMap.get(event.getMovieId());
        movie.setTrailerList(event.getResponse().getTrailerList());
    }

    public void onEventMainThread(DataEvent.LoadedMovieDetailEvent event) {
        MovieVO movieWithDetail = event.getMovie();
        movieWithDetail.setIsDetailLoaded(true);
        movieArrayMap.put(event.getMovieId(), movieWithDetail);
    }

    public void onEventMainThread(DataEvent.LoadedGenreListEvent event) {
        storeGenreInArrayMap(event.getResponse().getGenreList());
    }

    private void storeGenreInArrayMap(ArrayList<GenreVO> genreList) {
        for (GenreVO genre : genreList) {
            genreArrayMap.put(genre.getId(), genre);
        }
    }

    private void storeMoviesInArrayMap(ArrayList<MovieVO> loadedMovieList) {
        for (MovieVO movie : loadedMovieList) {
            int [] genreIds = movie.getGenreIds();
            ArrayList<GenreVO> genreList = new ArrayList<>(genreIds.length);
            for(int genreId:genreIds) {
                genreList.add(genreArrayMap.get(genreId));
            }
            movie.setGenreList(genreList);
            movieArrayMap.put(movie.getId(), movie);
        }
    }

    private class MovieLoaderTask extends AsyncTask<Integer, Void, MovieDiscoverResponse> {

        private int pageNumber;
        private boolean isForce;

        public MovieLoaderTask(boolean isForce) {
            this.isForce = isForce;
        }

        @Override
        protected MovieDiscoverResponse doInBackground(Integer... params) {
            pageNumber = params[0];
            MovieDiscoverResponse response = null;
            try {
                String dummyMovieList = JsonUtils.getInstance().loadDummyData(DUMMY_MOVIE_LIST_FILE_NAME);
                response = CommonInstances.getGsonInstance().fromJson(dummyMovieList, MovieDiscoverResponse.class);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(MovieDiscoverResponse response) {
            super.onPostExecute(response);
            storeMoviesInArrayMap(response.getResults());

            DataEvent.LoadedMovieDiscoverEvent event = new DataEvent.LoadedMovieDiscoverEvent(response, isForce);
            EventBus.getDefault().post(event);
        }
    }
}
