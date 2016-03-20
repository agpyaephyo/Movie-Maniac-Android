package net.aung.moviemaniac.data.persistence;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import net.aung.moviemaniac.MovieManiacApp;

/**
 * Created by aung on 3/8/16.
 */
public class MovieContract {

    public static final String CONTENT_AUTHORITY = MovieManiacApp.class.getPackage().getName();
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIE = "movie";
    public static final String PATH_TRAILER = "trailer";
    public static final String PATH_GENRE = "genre";
    public static final String PATH_COLLECTION = "collection";
    public static final String PATH_PRODUCTION_COMPANY = "production_company";
    public static final String PATH_PRODUCTION_COUNTRY = "production_country";
    public static final String PATH_SPOKEN_LANGUAGE = "spoken_language";
    public static final String PATH_MOVIE_SPOKEN_LANGUAGE = "movie_spoken_language";
    public static final String PATH_MOVIE_PRODUCTION_COMPANY = "movie_production_company";
    public static final String PATH_MOVIE_PRODUCTION_COUNTRY = "movie_production_country";
    public static final String PATH_MOVIE_GENRE = "movie_genre";
    public static final String PATH_REVIEWS = "reviews";

    public static final String PATH_TV_SERIES = "tv_series";
    public static final String PATH_TV_SERIES_GENRE = "tv_series_genre";
    public static final String PATH_TV_SERIES_PRODUCTION_COMPANY = "tv_series_production_company";
    public static final String PATH_NETWORKS = "networks";
    public static final String PATH_TV_SERIES_NETWORKS = "tv_series_networks";
    public static final String PATH_TV_SEASONS = "tv_seasons";

    //public static final String PATH_USER = "user";
    //public static final String PATH_FAVOURITE = "favourite";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_ORIGINAL_LANGUAGE = "original_language";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_VOTE_COUNT = "vote_count";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_IS_ADULT = "is_adult";
        public static final String COLUMN_BUDGET = "budget";
        public static final String COLUMN_HOMEPAGE = "homepage";
        public static final String COLUMN_IMDB_ID = "imdb_id";
        public static final String COLUMN_REVENUE = "revenue";
        public static final String COLUMN_RUNTIME = "runtime";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_TAGLINE = "tagline";
        public static final String COLUMN_IS_VIDEO = "is_video";
        public static final String COLUMN_COLLECTION_ID = "collection_id";
        public static final String COLUMN_MOVIE_TYPE = "movie_type";
        public static final String COLUMN_IS_DETAIL_LOADED = "is_detail_loaded";
        public static final String COLUMN_IS_STAR = "is_star";

        public static Uri buildMovieUri(long id) {
            //content://net.aung.moviemaniac/movie/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildMovieUriWithMovieId(int movieId) {
            //content://net.aung.moviemaniac/movie?movie_id=24
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_MOVIE_ID, Integer.toString(movieId))
                    .build();
        }

        public static long getMovieIdFromParam(Uri uri) {
            String movieIdString = uri.getQueryParameter(COLUMN_MOVIE_ID);
            if(movieIdString != null && movieIdString.length() > 0) {
                return Long.parseLong(movieIdString);
            } else {
                return -1;
            }
        }

        //TODO Uri for retrieving movies with desc popularity value.

        //TODO Uri for retrieving movies with desc voteAverage value.
    }

    public static final class TrailerEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILER).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILER;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILER;

        public static final String TABLE_NAME = "trailer";

        public static final String COLUMN_TRAILER_ID = "trailer_id";
        public static final String COLUMN_ISO_639_1 = "iso_639_1";
        public static final String COLUMN_KEY = "key";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SITE = "site";
        public static final String COLUMN_SIZE = "size";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_MOVIE_ID = "movie_id";

        public static Uri buildTrailerUri(long id) {
            //content://net.aung.moviemaniac/trailer/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //Uri for retrieving trailers for a movie id.
        public static Uri buildTrailerUriWithMovieId(long movieId) {
            //content://net.aung.moviemaniac/trailer?movie_id=24
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_MOVIE_ID, Long.toString(movieId))
                    .build();
        }

        public static long getMovieIdFromParam(Uri uri) {
            String movieIdString = uri.getQueryParameter(COLUMN_MOVIE_ID);
            if(movieIdString != null && movieIdString.length() > 0) {
                return Long.parseLong(movieIdString);
            } else {
                return -1;
            }
        }
    }

    public static final class GenreEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_GENRE).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GENRE;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GENRE;

        public static final String TABLE_NAME = "genre";

        public static final String COLUMN_GENRE_ID = "genre_id";
        public static final String COLUMN_NAME = "name";

        public static Uri buildGenreUri(long id) {
            //content://net.aung.moviemaniac/genre/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class MovieGenreEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE_GENRE).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_GENRE;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_GENRE;

        public static final String TABLE_NAME = "movie_genre";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_GENRE_ID = "genre_id";

        public static Uri buildMovieGenreUri(long id) {
            //content://net.aung.moviemaniac/movie_genre/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildMovieGenreUriWithMovieId(long movieId) {
            //content://net.aung.moviemaniac/movie_genre?movie_id=24
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_MOVIE_ID, Long.toString(movieId))
                    .build();
        }

        public static Uri buildMovieGenreUriWithGenreId(long genreId) {
            //content://net.aung.moviemaniac/movie_genre?genre_id=24
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_GENRE_ID, Long.toString(genreId))
                    .build();
        }

        public static long getMovieIdFromParam(Uri uri) {
            String movieIdString = uri.getQueryParameter(COLUMN_MOVIE_ID);
            if(movieIdString != null && movieIdString.length() > 0) {
                return Long.parseLong(movieIdString);
            } else {
                return -1;
            }
        }
    }

    public static final class CollectionEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_COLLECTION).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COLLECTION;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COLLECTION;

        public static final String TABLE_NAME = "collection";

        public static final String COLUMN_COLLECTION_ID = "collection_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";

        public static Uri buildCollectionUri(long id) {
            //content://net.aung.moviemaniac/collection/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class ProductionCompanyEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PRODUCTION_COMPANY).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTION_COMPANY;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTION_COMPANY;

        public static final String TABLE_NAME = "production_company";

        public static final String COLUMN_PRODUCTION_COMPANY_ID = "production_company_id";
        public static final String COLUMN_NAME = "name";

        public static Uri buildProductionCompanyUri(long id) {
            //content://net.aung.moviemaniac/production_company/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class MovieProductionCompanyEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE_PRODUCTION_COMPANY).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_PRODUCTION_COMPANY;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_PRODUCTION_COMPANY;

        public static final String TABLE_NAME = "movie_production_company";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_PRODUCTION_COMPANY_ID = "production_company_id";

        public static Uri buildMovieProductionCompanyUri(long id) {
            //content://net.aung.moviemaniac/movie_production_company/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildMovieProductionCompanyUriWithMovieId(long movieId) {
            //content://net.aung.moviemaniac/movie_production_company?movie_id=24
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_MOVIE_ID, Long.toString(movieId))
                    .build();
        }

        public static Uri buildMovieProductionCompanyUriWithCompanyId(long companyId) {
            //content://net.aung.moviemaniac/movie_production_company?production_company_id=24
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_PRODUCTION_COMPANY_ID, Long.toString(companyId))
                    .build();
        }

        public static long getMovieIdFromParam(Uri uri) {
            String movieIdString = uri.getQueryParameter(COLUMN_MOVIE_ID);
            if(movieIdString != null && movieIdString.length() > 0) {
                return Long.parseLong(movieIdString);
            } else {
                return -1;
            }
        }
    }

    public static final class ProductionCountryEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PRODUCTION_COUNTRY).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTION_COUNTRY;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTION_COUNTRY;

        public static final String TABLE_NAME = "production_country";

        public static final String COLUMN_ISO_3166_1 = "iso_3166_1";
        public static final String COLUMN_NAME = "name";

        public static Uri buildMovieProductionCountryUri(long id) {
            //content://net.aung.moviemaniac/movie_production_country/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class MovieProductionCountryEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE_PRODUCTION_COUNTRY).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_PRODUCTION_COUNTRY;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_PRODUCTION_COUNTRY;

        public static final String TABLE_NAME = "movie_production_country";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_ISO_3166_1 = "iso_3166_1";

        public static Uri buildMovieProductionCountryUri(long id) {
            //content://net.aung.moviemaniac/movie_production_country/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildMovieProductionCountryUriWithMovieId(long movieId) {
            //content://net.aung.moviemaniac/movie_production_country?movie_id=24
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_MOVIE_ID, Long.toString(movieId))
                    .build();
        }

        public static Uri buildMovieProductionCountryUriWithISOCode(String iso_3166_1) {
            //content://net.aung.moviemaniac/movie_production_country?iso_3166_1=Myanmar
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_ISO_3166_1, iso_3166_1)
                    .build();
        }

        public static long getMovieIdFromParam(Uri uri) {
            String movieIdString = uri.getQueryParameter(COLUMN_MOVIE_ID);
            if(movieIdString != null && movieIdString.length() > 0) {
                return Long.parseLong(movieIdString);
            } else {
                return -1;
            }
        }
    }

    public static final class SpokenLanguageEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SPOKEN_LANGUAGE).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SPOKEN_LANGUAGE;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SPOKEN_LANGUAGE;

        public static final String TABLE_NAME = "spoken_language";

        public static final String COLUMN_ISO_639_1 = "iso_639_1";
        public static final String COLUMN_NAME = "name";

        public static Uri buildSpokenLanguageUri(long id) {
            //content://net.aung.moviemaniac/spoken_language/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class MovieSpokenLanguageEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE_SPOKEN_LANGUAGE).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_SPOKEN_LANGUAGE;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_SPOKEN_LANGUAGE;

        public static final String TABLE_NAME = "movie_spoken_language";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_ISO_639_1 = "iso_639_1";

        public static Uri buildMovieSpokenLanguageUri(long id) {
            //content://net.aung.moviemaniac/movie_spoken_language/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildMovieSpokenLanguageUriWithMovieId(long movieId) {
            //content://net.aung.moviemaniac/movie_spoken_language?movie_id=24
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_MOVIE_ID, Long.toString(movieId))
                    .build();
        }

        public static Uri buildMovieSpokenLanguageUriWithISOCode(String iso_639_1) {
            //content://net.aung.moviemaniac/movie_production_country?iso_639_1=Burma
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_ISO_639_1, iso_639_1)
                    .build();
        }

        public static long getMovieIdFromParam(Uri uri) {
            String movieIdString = uri.getQueryParameter(COLUMN_MOVIE_ID);
            if(movieIdString != null && movieIdString.length() > 0) {
                return Long.parseLong(movieIdString);
            } else {
                return -1;
            }
        }
    }

    public static final class ReviewEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEWS).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEWS;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEWS;

        public static final String TABLE_NAME = "review";

        public static final String COLUMN_REVIEW_ID = "review_id";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_URL = "url";

        public static Uri buildReviewUri(long id) {
            //content://net.aung.moviemaniac/review/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //Uri for retrieving reviews for a movie id.
        public static Uri buildReviewUriWithMovieId(long movieId) {
            //content://net.aung.moviemaniac/review?movie_id=24
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_MOVIE_ID, Long.toString(movieId))
                    .build();
        }

        public static long getMovieIdFromParam(Uri uri) {
            String movieIdString = uri.getQueryParameter(COLUMN_MOVIE_ID);
            if(movieIdString != null && movieIdString.length() > 0) {
                return Long.parseLong(movieIdString);
            } else {
                return -1;
            }
        }
    }

    public static final class TVSeriesEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TV_SERIES).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_SERIES;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_SERIES;

        public static final String TABLE_NAME = "tv_series";

        public static final String COLUMN_TV_SERIES_ID = "tv_series_id";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ORIGINAL_LANGUAGE = "original_language";
        public static final String COLUMN_ORIGINAL_NAME = "original_name";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_VOTE_COUNT = "vote_count";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";

        public static final String COLUMN_TV_SERIES_TYPE = "tv_series_type";
        public static final String COLUMN_IS_DETAIL_LOADED = "is_detail_loaded";
        public static final String COLUMN_IS_STAR = "is_star";

        public static final String COLUMN_HOMEPAGE = "homepage";
        public static final String COLUMN_LAST_AIR_DATE = "last_air_date";
        public static final String COLUMN_NUMBER_OF_EPISODES = "number_of_episodes";
        public static final String COLUMN_NUMBER_OF_SEASON = "number_of_seasons";
        public static final String COLUMN_STATUS = "status";

        public static Uri buildTVSeriesUri(long id) {
            //content://net.aung.moviemaniac/tv_series/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildTVSeriesUriWithTVSeriesId(int tvSeriesId) {
            //content://net.aung.moviemaniac/tv_series?tv_series_id=24
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_TV_SERIES_ID, Integer.toString(tvSeriesId))
                    .build();
        }

        public static long getTVSeriesIdFromParam(Uri uri) {
            String movieIdString = uri.getQueryParameter(COLUMN_TV_SERIES_ID);
            if(movieIdString != null && movieIdString.length() > 0) {
                return Long.parseLong(movieIdString);
            } else {
                return -1;
            }
        }
    }

    public static final class TVSeriesGenreEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TV_SERIES_GENRE).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_SERIES_GENRE;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_SERIES_GENRE;

        public static final String TABLE_NAME = "tv_series_genre";

        public static final String COLUMN_TV_SERIES_ID = "tv_series_id";
        public static final String COLUMN_GENRE_ID = "genre_id";

        public static Uri buildTVSeriesGenreUri(long id) {
            //content://net.aung.moviemaniac/tv_series_genre/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildTVSeriesGenreUriWithTVSeriesId(long tvSeriesId) {
            //content://net.aung.moviemaniac/tv_series_genre?tv_series_id=24
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_TV_SERIES_ID, Long.toString(tvSeriesId))
                    .build();
        }

        public static Uri buildTVSeriesGenreUriWithGenreId(long genreId) {
            //content://net.aung.moviemaniac/tv_series_genre?genre_id=24
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_GENRE_ID, Long.toString(genreId))
                    .build();
        }

        public static long getTVSeriesIdFromParam(Uri uri) {
            String movieIdString = uri.getQueryParameter(COLUMN_GENRE_ID);
            if(movieIdString != null && movieIdString.length() > 0) {
                return Long.parseLong(movieIdString);
            } else {
                return -1;
            }
        }
    }

    public static final class TVSeriesProductionCompanyEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TV_SERIES_PRODUCTION_COMPANY).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_SERIES_PRODUCTION_COMPANY;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_SERIES_PRODUCTION_COMPANY;

        public static final String TABLE_NAME = "tv_series_production_company";

        public static final String COLUMN_TV_SERIES_ID = "tv_series_id";
        public static final String COLUMN_PRODUCTION_COMPANY_ID = "production_company_id";

        public static Uri buildMovieProductionCompanyUri(long id) {
            //content://net.aung.moviemaniac/tv_series_production_company/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildTVSeriesProductionCompanyUriWithTVSeriesId(long tvSeriesId) {
            //content://net.aung.moviemaniac/tv_series_production_company?tv_series_id=24
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_TV_SERIES_ID, Long.toString(tvSeriesId))
                    .build();
        }

        public static Uri buildTVSeriesProductionCompanyUriWithCompanyId(long companyId) {
            //content://net.aung.moviemaniac/tv_series_production_company?production_company_id=24
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_PRODUCTION_COMPANY_ID, Long.toString(companyId))
                    .build();
        }

        public static long getTVSeriesIdFromParam(Uri uri) {
            String movieIdString = uri.getQueryParameter(COLUMN_TV_SERIES_ID);
            if(movieIdString != null && movieIdString.length() > 0) {
                return Long.parseLong(movieIdString);
            } else {
                return -1;
            }
        }
    }

    public static final class NetworkEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_NETWORKS).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NETWORKS;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NETWORKS;

        public static final String TABLE_NAME = "networks";

        public static final String COLUMN_NETWORK_ID = "network_id";
        public static final String COLUMN_NAME = "name";

        public static Uri buildNetworkUri(long id) {
            //content://net.aung.moviemaniac/networks/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class TVSeriesNetworkEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TV_SERIES_NETWORKS).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_SERIES_NETWORKS;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_SERIES_NETWORKS;

        public static final String TABLE_NAME = "tv_series_networks";

        public static final String COLUMN_TV_SERIES_ID = "tv_series_id";
        public static final String COLUMN_NETWORK_ID = "network_id";

        public static Uri buildTVSeriesNetworkUri(long id) {
            //content://net.aung.moviemaniac/tv_series_networks/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildTVSeriesNetworkUriWithTVSeriesId(long tvSeriesId) {
            //content://net.aung.moviemaniac/tv_series_networks?tv_series_id=24
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_TV_SERIES_ID, Long.toString(tvSeriesId))
                    .build();
        }

        public static Uri buildTVSeriesNetworkUriWithNetworkId(long networkId) {
            //content://net.aung.moviemaniac/tv_series_networks?network_id=24
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_NETWORK_ID, Long.toString(networkId))
                    .build();
        }

        public static long getTVSeriesIdFromParam(Uri uri) {
            String movieIdString = uri.getQueryParameter(COLUMN_TV_SERIES_ID);
            if(movieIdString != null && movieIdString.length() > 0) {
                return Long.parseLong(movieIdString);
            } else {
                return -1;
            }
        }
    }

    public static final class TVSeasonEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TV_SEASONS).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_SEASONS;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV_SEASONS;

        public static final String TABLE_NAME = "tv_seasons";

        public static final String COLUMN_TV_SEASON_ID = "tv_season_id";
        public static final String COLUMN_AIR_DATE = "air_date";
        public static final String COLUMN_EPISODE_COUNT = "episode_count";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_SEASON_NUMBER = "season_number";
        public static final String COLUMN_TV_SERIES_ID = "tv_series_id";

        public static Uri buildTVSeasonUri(long id) {
            //content://net.aung.moviemaniac/tv_seasons/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildTVSeasonUriWithTVSeriesId(long tvSeriesId) {
            //content://net.aung.moviemaniac/tv_seasons?tv_series_id=24
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_TV_SERIES_ID, Long.toString(tvSeriesId))
                    .build();
        }

        public static long getTVSeriesIdFromParam(Uri uri) {
            String movieIdString = uri.getQueryParameter(COLUMN_TV_SERIES_ID);
            if(movieIdString != null && movieIdString.length() > 0) {
                return Long.parseLong(movieIdString);
            } else {
                return -1;
            }
        }
    }
}
