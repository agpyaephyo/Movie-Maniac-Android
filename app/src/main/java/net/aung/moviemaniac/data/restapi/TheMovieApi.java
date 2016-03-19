package net.aung.moviemaniac.data.restapi;

import net.aung.moviemaniac.data.restapi.responses.MovieReviewResponse;
import net.aung.moviemaniac.data.vos.MovieVO;
import net.aung.moviemaniac.data.restapi.responses.MovieListResponse;
import net.aung.moviemaniac.data.restapi.responses.MovieTrailerResponse;
import net.aung.moviemaniac.data.restapi.responses.GenreListResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by aung on 12/15/15.
 */
public interface TheMovieApi {

    @GET("discover/movie")
    Call<MovieListResponse> discoverMovieList(
            @Query("api_key") String apiKey,
            @Query("page") int pageNumber,
            @Query("sort_by") String sortBy
    );

    @GET("movie/now_playing")
    Call<MovieListResponse> getNowPlayingMovies(
            @Query("api_key") String apiKey,
            @Query("page") int pageNumber
    );

    @GET("movie/upcoming")
    Call<MovieListResponse> getUpcomingMovies(
            @Query("api_key") String apiKey,
            @Query("page") int pageNumber
    );

    @GET("movie/popular")
    Call<MovieListResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("page") int pageNumber
    );

    @GET("movie/top_rated")
    Call<MovieListResponse> getTopRatedMovies(
            @Query("api_key") String apiKey,
            @Query("page") int pageNumber
    );

    @GET("movie/{movieId}/videos")
    Call<MovieTrailerResponse> getTrailersByMovieId(
            @Path("movieId") int movieId,
            @Query("api_key") String apiKey
    );

    @GET("movie/{movieId}")
    Call<MovieVO> getMovieDetailByMovieId(
            @Path("movieId") int movieId,
            @Query("api_key") String apiKey
    );

    @GET("genre/movie/list")
    Call<GenreListResponse> getGenreList(
            @Query("api_key") String apiKey
    );

    @GET("movie/{movieId}/reviews")
    Call<MovieReviewResponse> getReviewsByMovieId(
            @Path("movieId") int movieId,
            @Query("api_key") String apiKey
    );
}
