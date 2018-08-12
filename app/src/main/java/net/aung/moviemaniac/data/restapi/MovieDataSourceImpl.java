package net.aung.moviemaniac.data.restapi;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import net.aung.moviemaniac.BuildConfig;
import net.aung.moviemaniac.data.restapi.responses.GenreListResponse;
import net.aung.moviemaniac.data.restapi.responses.MovieListResponse;
import net.aung.moviemaniac.data.restapi.responses.MovieReviewResponse;
import net.aung.moviemaniac.data.restapi.responses.TVSeriesListResponse;
import net.aung.moviemaniac.data.restapi.responses.TrailerResponse;
import net.aung.moviemaniac.data.vos.MovieVO;
import net.aung.moviemaniac.data.vos.TVSeriesVO;
import net.aung.moviemaniac.events.DataEvent;
import net.aung.moviemaniac.utils.CommonInstances;

import java.util.concurrent.TimeUnit;

import de.greenrobot.event.EventBus;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aung on 12/15/15.
 */
public class MovieDataSourceImpl implements MovieDataSource {

    private static MovieDataSource objInstance;
    private final TheMovieApi theMovieApi;

    private MovieDataSourceImpl() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addNetworkInterceptor(new StethoInterceptor()) //TODO
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(CommonInstances.getGsonInstance()))
                .client(okHttpClient)
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
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                super.onResponse(call, response);
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
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                super.onResponse(call, response);
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
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                super.onResponse(call, response);
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
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                super.onResponse(call, response);
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
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                super.onResponse(call, response);
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
        Call<TrailerResponse> movieTrailerResponseCall = theMovieApi.getTrailersByMovieId(movieId, BuildConfig.THE_MOVIE_API_KEY);

        movieTrailerResponseCall.enqueue(new MovieApiCallback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                super.onResponse(call, response);
                TrailerResponse trailerResponse = response.body();
                if (trailerResponse != null) {
                    DataEvent.LoadedMovieTrailerEvent event = new DataEvent.LoadedMovieTrailerEvent(trailerResponse, movieId);
                    EventBus.getDefault().post(event);
                }
            }
        });
    }

    @Override
    public void loadMovieDetail(final MovieVO movie) {
        Call<MovieVO> movieDetailCall = theMovieApi.getMovieDetailByMovieId(movie.getMovieId(), BuildConfig.THE_MOVIE_API_KEY);
        movieDetailCall.enqueue(new MovieApiCallback<MovieVO>() {
            @Override
            public void onResponse(Call<MovieVO> call, Response<MovieVO> response) {
                super.onResponse(call, response);
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
    public void loadMovieDetail(int movieId) {
        Call<MovieVO> movieDetailCall = theMovieApi.getMovieDetailByMovieId(movieId, BuildConfig.THE_MOVIE_API_KEY);
        movieDetailCall.enqueue(new MovieApiCallback<MovieVO>() {
            @Override
            public void onResponse(Call<MovieVO> call, Response<MovieVO> response) {
                super.onResponse(call, response);
                MovieVO movieDetailResponse = response.body();
                if (movieDetailResponse != null) {
                    DataEvent.LoadedMovieDetailEvent event = new DataEvent.LoadedMovieDetailEvent(movieDetailResponse);
                    EventBus.getDefault().post(event);
                }
            }
        });
    }

    @Override
    public void loadGenreList(final Context context) {
        Call<GenreListResponse> genreListResponseCall = theMovieApi.getGenreList(BuildConfig.THE_MOVIE_API_KEY);
        genreListResponseCall.enqueue(new MovieApiCallback<GenreListResponse>() {
            @Override
            public void onResponse(Call<GenreListResponse> call, Response<GenreListResponse> response) {
                super.onResponse(call, response);
                GenreListResponse genreListResponse = response.body();
                if (genreListResponse != null) {
                    DataEvent.LoadedGenreListEvent event = new DataEvent.LoadedGenreListEvent(context, genreListResponse);
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
            public void onResponse(Call<MovieReviewResponse> call, Response<MovieReviewResponse> response) {
                super.onResponse(call, response);
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
            public void onResponse(Call<TVSeriesListResponse> call, Response<TVSeriesListResponse> response) {
                super.onResponse(call, response);
                TVSeriesListResponse tvSeriesListResponse = response.body();
                if (tvSeriesListResponse != null) {
                    DataEvent.LoadedPopularTVSeriesListEvent event = new DataEvent.LoadedPopularTVSeriesListEvent(tvSeriesListResponse, isForce);
                    EventBus.getDefault().post(event);
                }
            }
        });
    }

    @Override
    public void loadTopRatedTVSeries(int pageNumber, final boolean isForce) {
        Call<TVSeriesListResponse> popularTVSeriesResponseCall = theMovieApi.getTopRatedTVSeries(
                BuildConfig.THE_MOVIE_API_KEY,
                pageNumber
        );

        popularTVSeriesResponseCall.enqueue(new MovieApiCallback<TVSeriesListResponse>() {
            @Override
            public void onResponse(Call<TVSeriesListResponse> call, Response<TVSeriesListResponse> response) {
                super.onResponse(call, response);
                TVSeriesListResponse tvSeriesListResponse = response.body();
                if (tvSeriesListResponse != null) {
                    DataEvent.LoadedTopRatedTVSeriesListEvent event = new DataEvent.LoadedTopRatedTVSeriesListEvent(tvSeriesListResponse, isForce);
                    EventBus.getDefault().post(event);
                }
            }
        });
    }

    @Override
    public void loadTVSeriesDetail(final TVSeriesVO tvSeries) {
        Call<TVSeriesVO> tvSeriesDetailCall = theMovieApi.getTVSeriesDetailByTVSeriesId(tvSeries.getTvSerieId(), BuildConfig.THE_MOVIE_API_KEY);
        tvSeriesDetailCall.enqueue(new MovieApiCallback<TVSeriesVO>() {
            @Override
            public void onResponse(Call<TVSeriesVO> call, Response<TVSeriesVO> response) {
                super.onResponse(call, response);
                TVSeriesVO tvSeriesDetailResponse = response.body();
                if (tvSeriesDetailResponse != null) {
                    tvSeriesDetailResponse.merge(tvSeries);
                    DataEvent.LoadedTVSeriesDetailEvent event = new DataEvent.LoadedTVSeriesDetailEvent(tvSeriesDetailResponse);
                    EventBus.getDefault().post(event);
                }
            }
        });
    }

    @Override
    public void loadTVSeriesDetail(int tvSeriesId) {
        Call<TVSeriesVO> tvSeriesDetailCall = theMovieApi.getTVSeriesDetailByTVSeriesId(tvSeriesId, BuildConfig.THE_MOVIE_API_KEY);
        tvSeriesDetailCall.enqueue(new MovieApiCallback<TVSeriesVO>() {
            @Override
            public void onResponse(Call<TVSeriesVO> call, Response<TVSeriesVO> response) {
                super.onResponse(call, response);
                TVSeriesVO tvSeriesDetailResponse = response.body();
                if (tvSeriesDetailResponse != null) {
                    DataEvent.LoadedTVSeriesDetailEvent event = new DataEvent.LoadedTVSeriesDetailEvent(tvSeriesDetailResponse);
                    EventBus.getDefault().post(event);
                }
            }
        });
    }

    @Override
    public void loadTvSeriesTrailers(final int tvSeriesId) {
        Call<TrailerResponse> movieTrailerResponseCall = theMovieApi.getTrailersByTVSeriesId(tvSeriesId, BuildConfig.THE_MOVIE_API_KEY);

        movieTrailerResponseCall.enqueue(new MovieApiCallback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                super.onResponse(call, response);
                TrailerResponse trailerResponse = response.body();
                if (trailerResponse != null) {
                    DataEvent.LoadedTVSeriesTrailerEvent event = new DataEvent.LoadedTVSeriesTrailerEvent(trailerResponse, tvSeriesId);
                    EventBus.getDefault().post(event);
                }
            }
        });
    }

    @Override
    public void searchOnMovie(final String query) {
        Call<MovieListResponse> movieSearchCall = theMovieApi.searchOnMovie(query, BuildConfig.THE_MOVIE_API_KEY);

        movieSearchCall.enqueue(new MovieApiCallback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                super.onResponse(call, response);
                MovieListResponse movieListResponse = response.body();
                if (movieListResponse != null) {
                    DataEvent.SearchedMovieEvent event = new DataEvent.SearchedMovieEvent(movieListResponse, query);
                    EventBus.getDefault().post(event);
                }
            }
        });
    }

    @Override
    public void searchOnTVSeries(final String query) {
        Call<TVSeriesListResponse> tvSeriesSearchCall = theMovieApi.searchOnTVSeries(query, BuildConfig.THE_MOVIE_API_KEY);

        tvSeriesSearchCall.enqueue(new MovieApiCallback<TVSeriesListResponse>() {
            @Override
            public void onResponse(Call<TVSeriesListResponse> call, Response<TVSeriesListResponse> response) {
                super.onResponse(call, response);
                TVSeriesListResponse tvSeriesListResponse = response.body();
                if (tvSeriesListResponse != null) {
                    DataEvent.SearchedTVSeriesEvent event = new DataEvent.SearchedTVSeriesEvent(tvSeriesListResponse, query);
                    EventBus.getDefault().post(event);
                }
            }
        });
    }
}
