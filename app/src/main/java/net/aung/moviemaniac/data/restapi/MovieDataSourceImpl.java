package net.aung.moviemaniac.data.restapi;

import net.aung.moviemaniac.BuildConfig;
import net.aung.moviemaniac.data.restapi.responses.GenreListResponse;
import net.aung.moviemaniac.data.restapi.responses.MovieListResponse;
import net.aung.moviemaniac.data.restapi.responses.MovieReviewResponse;
import net.aung.moviemaniac.data.restapi.responses.MovieTrailerResponse;
import net.aung.moviemaniac.data.restapi.responses.TVSeriesListResponse;
import net.aung.moviemaniac.data.vos.MovieVO;
import net.aung.moviemaniac.events.DataEvent;
import net.aung.moviemaniac.utils.CommonInstances;

import de.greenrobot.event.EventBus;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by aung on 12/15/15.
 */
public class MovieDataSourceImpl implements MovieDataSource {

    private static MovieDataSource objInstance;
    private final TheMovieApi theMovieApi;

    private MovieDataSourceImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(CommonInstances.getGsonInstance()))
                .build();

        theMovieApi = retrofit.create(TheMovieApi.class);
    }

    public static MovieDataSource getInstance() {
        if (objInstance == null) {
            objInstance = new MovieDataSourceImpl();
        }

        return objInstance;
    }

    @Override
    public void loadDiscoverMovieList(int pageNumber, String sortBy, final boolean isForce) {
        Call<MovieListResponse> movieDiscoverResponseCall = theMovieApi.discoverMovieList(
                BuildConfig.THE_MOVIE_API_KEY,
                pageNumber,
                sortBy
        );

        movieDiscoverResponseCall.enqueue(new MovieApiCallback<MovieListResponse>() {
            @Override
            public void onResponse(Response<MovieListResponse> response, Retrofit retrofit) {
                super.onResponse(response, retrofit);
                MovieListResponse movieListResponse = response.body();
                if (movieListResponse != null) {
                    DataEvent.LoadedMovieListEvent event = new DataEvent.LoadedMovieListEvent(movieListResponse, isForce);
                    EventBus.getDefault().post(event);
                }
            }
        });
    }

    @Override
    public void loadNowPlayingMovies(int pageNumber, final boolean isForce) {
        Call<MovieListResponse> nowPlayingMovieResponseCall = theMovieApi.getNowPlayingMovies(
                BuildConfig.THE_MOVIE_API_KEY,
                pageNumber
        );

        nowPlayingMovieResponseCall.enqueue(new MovieApiCallback<MovieListResponse>() {
            @Override
            public void onResponse(Response<MovieListResponse> response, Retrofit retrofit) {
                super.onResponse(response, retrofit);
                MovieListResponse movieListResponse = response.body();
                if (movieListResponse != null) {
                    DataEvent.LoadedNowPlayingMovieListEvent event = new DataEvent.LoadedNowPlayingMovieListEvent(movieListResponse, isForce);
                    EventBus.getDefault().post(event);
                }
            }
        });
    }

    @Override
    public void loadUpcomingMovies(int pageNumber, final boolean isForce) {
        Call<MovieListResponse> upcomingMovieResponseCall = theMovieApi.getUpcomingMovies(
                BuildConfig.THE_MOVIE_API_KEY,
                pageNumber
        );

        upcomingMovieResponseCall.enqueue(new MovieApiCallback<MovieListResponse>() {
            @Override
            public void onResponse(Response<MovieListResponse> response, Retrofit retrofit) {
                super.onResponse(response, retrofit);
                MovieListResponse movieListResponse = response.body();
                if (movieListResponse != null) {
                    DataEvent.LoadedUpcomingMovieListEvent event = new DataEvent.LoadedUpcomingMovieListEvent(movieListResponse, isForce);
                    EventBus.getDefault().post(event);
                }
            }
        });
    }

    @Override
    public void loadPopularMovies(int pageNumber, final boolean isForce) {
        Call<MovieListResponse> popularMovieResponseCall = theMovieApi.getPopularMovies(
                BuildConfig.THE_MOVIE_API_KEY,
                pageNumber
        );

        popularMovieResponseCall.enqueue(new MovieApiCallback<MovieListResponse>() {
            @Override
            public void onResponse(Response<MovieListResponse> response, Retrofit retrofit) {
                super.onResponse(response, retrofit);
                MovieListResponse movieListResponse = response.body();
                if (movieListResponse != null) {
                    DataEvent.LoadedMostPopularMovieListEvent event = new DataEvent.LoadedMostPopularMovieListEvent(movieListResponse, isForce);
                    EventBus.getDefault().post(event);
                }
            }
        });
    }

    @Override
    public void loadTopRatedMovies(int pageNumber, final boolean isForce) {
        Call<MovieListResponse> popularMovieResponseCall = theMovieApi.getTopRatedMovies(
                BuildConfig.THE_MOVIE_API_KEY,
                pageNumber
        );

        popularMovieResponseCall.enqueue(new MovieApiCallback<MovieListResponse>() {
            @Override
            public void onResponse(Response<MovieListResponse> response, Retrofit retrofit) {
                super.onResponse(response, retrofit);
                MovieListResponse movieListResponse = response.body();
                if (movieListResponse != null) {
                    DataEvent.LoadedTopRatedMovieListEvent event = new DataEvent.LoadedTopRatedMovieListEvent(movieListResponse, isForce);
                    EventBus.getDefault().post(event);
                }
            }
        });
    }

    @Override
    public void loadMovieTrailers(final int movieId) {
        Call<MovieTrailerResponse> movieTrailerResponseCall = theMovieApi.getTrailersByMovieId(movieId, BuildConfig.THE_MOVIE_API_KEY);

        movieTrailerResponseCall.enqueue(new MovieApiCallback<MovieTrailerResponse>() {
            @Override
            public void onResponse(Response<MovieTrailerResponse> response, Retrofit retrofit) {
                super.onResponse(response, retrofit);
                MovieTrailerResponse movieTrailerResponse = response.body();
                if (movieTrailerResponse != null) {
                    DataEvent.LoadedMovieTrailerEvent event = new DataEvent.LoadedMovieTrailerEvent(movieTrailerResponse, movieId);
                    EventBus.getDefault().post(event);
                }
            }
        });
    }

    @Override
    public void loadMovieDetail(final MovieVO movie) {
        Call<MovieVO> movieDetailCall = theMovieApi.getMovieDetailByMovieId(movie.getId(), BuildConfig.THE_MOVIE_API_KEY);
        movieDetailCall.enqueue(new MovieApiCallback<MovieVO>() {
            @Override
            public void onResponse(Response<MovieVO> response, Retrofit retrofit) {
                super.onResponse(response, retrofit);
                MovieVO movieDetailResponse = response.body();
                if (movieDetailResponse != null) {
                    movieDetailResponse.merge(movie);
                    DataEvent.LoadedMovieDetailEvent event = new DataEvent.LoadedMovieDetailEvent(movieDetailResponse);
                    EventBus.getDefault().post(event);
                }
            }
        });
    }

    @Override
    public void loadGenreList() {
        Call<GenreListResponse> genreListResponseCall = theMovieApi.getGenreList(BuildConfig.THE_MOVIE_API_KEY);
        genreListResponseCall.enqueue(new MovieApiCallback<GenreListResponse>() {
            @Override
            public void onResponse(Response<GenreListResponse> response, Retrofit retrofit) {
                super.onResponse(response, retrofit);
                GenreListResponse genreListResponse = response.body();
                if (genreListResponse != null) {
                    DataEvent.LoadedGenreListEvent event = new DataEvent.LoadedGenreListEvent(genreListResponse);
                    EventBus.getDefault().post(event);
                }
            }
        });
    }

    @Override
    public void loadMovieReviews(int movieId) {
        Call<MovieReviewResponse> reviewListResponseCall = theMovieApi.getReviewsByMovieId(movieId, BuildConfig.THE_MOVIE_API_KEY);
        reviewListResponseCall.enqueue(new MovieApiCallback<MovieReviewResponse>() {
            @Override
            public void onResponse(Response<MovieReviewResponse> response, Retrofit retrofit) {
                super.onResponse(response, retrofit);
                MovieReviewResponse movieReviewResponse = response.body();
                if (movieReviewResponse != null) {
                    DataEvent.LoadedMovieReviewEvent event = new DataEvent.LoadedMovieReviewEvent(movieReviewResponse);
                    EventBus.getDefault().post(event);
                }
            }
        });
    }

    @Override
    public void loadPopularTVSeries(int pageNumber, final boolean isForce) {
        Call<TVSeriesListResponse> popularTVSeriesResponseCall = theMovieApi.getPopularTVSeries(
                BuildConfig.THE_MOVIE_API_KEY,
                pageNumber
        );

        popularTVSeriesResponseCall.enqueue(new MovieApiCallback<TVSeriesListResponse>() {
            @Override
            public void onResponse(Response<TVSeriesListResponse> response, Retrofit retrofit) {
                super.onResponse(response, retrofit);
                TVSeriesListResponse tvSeriesListResponse = response.body();
                if (tvSeriesListResponse != null) {
                    DataEvent.LoadedPopularTVSeriesListEvent event = new DataEvent.LoadedPopularTVSeriesListEvent(tvSeriesListResponse, isForce);
                    EventBus.getDefault().post(event);
                }
            }
        });
    }
}
