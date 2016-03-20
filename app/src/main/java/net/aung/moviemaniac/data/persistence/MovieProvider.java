package net.aung.moviemaniac.data.persistence;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by aung on 3/12/16.
 */
public class MovieProvider extends ContentProvider {

    public static final int MOVIE = 100;
    public static final int TRAILER = 200;

    public static final int GENRE = 300;
    public static final int COLLECTION = 400;
    public static final int PRODUCTION_COMPANY = 500;
    public static final int PRODUCTION_COUNTRY = 600;
    public static final int SPOKEN_LANGUAGE = 700;

    public static final int MOVIE_SPOKEN_LANGUAGE = 800;
    public static final int MOVIE_PRODUCTION_COMPANY = 900;
    public static final int MOVIE_PRODUCTION_COUNTRY = 1000;
    public static final int MOVIE_GENRE = 1100;

    public static final int REVIEW = 1200;

    public static final int TV_SERIES = 1300;
    public static final int TV_SERIES_GENRE = 1400;
    public static final int TV_SERIES_PRODUCTION_COMPANY = 1500;
    public static final int NETWORKS = 1600;
    public static final int TV_SERIES_NETWORKS = 1700;
    public static final int TV_SEASONS = 1800;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDBHelper mMovieDBHelper;

    private static final SQLiteQueryBuilder sMovieCollectionIJ, sMovieGenre_GenreIJ,
            sMovieProductionCompany_ProductionCompanyIJ, sMovieProductionCountry_ProductionCountryIJ,
            sMovieSpokenLanguage_SpokenLanguage, sTVSeriesGenre_GenreIJ,
            sTVSeriesProductionCompany_ProductionCompanyIJ, sTVSeriesNetworks_NetworksIJ;

    static {
        sMovieCollectionIJ = new SQLiteQueryBuilder();
        //movie INNER JOIN collection ON movie.collection_id = collection.collection_id
        sMovieCollectionIJ.setTables(
                MovieContract.MovieEntry.TABLE_NAME + " INNER JOIN " +
                        MovieContract.CollectionEntry.TABLE_NAME + " ON " +
                        MovieContract.MovieEntry.TABLE_NAME + "." + MovieContract.MovieEntry.COLUMN_COLLECTION_ID + " = " +
                        MovieContract.CollectionEntry.TABLE_NAME + "." + MovieContract.CollectionEntry.COLUMN_COLLECTION_ID);

        sMovieGenre_GenreIJ = new SQLiteQueryBuilder();
        //movie_genre INNER JOIN genre ON movie_genre.genre_id = genre.genre_id
        sMovieGenre_GenreIJ.setTables(
                MovieContract.MovieGenreEntry.TABLE_NAME + " INNER JOIN " +
                        MovieContract.GenreEntry.TABLE_NAME + " ON " +
                        MovieContract.MovieGenreEntry.TABLE_NAME + "." + MovieContract.MovieGenreEntry.COLUMN_GENRE_ID + " = " +
                        MovieContract.GenreEntry.TABLE_NAME + "." + MovieContract.GenreEntry.COLUMN_GENRE_ID);

        sMovieProductionCompany_ProductionCompanyIJ = new SQLiteQueryBuilder();
        //movie_production_company INNER JOIN production_company ON movie_production_company.production_company_id = production_company.production_company_id
        sMovieProductionCompany_ProductionCompanyIJ.setTables(
                MovieContract.MovieProductionCompanyEntry.TABLE_NAME + " INNER JOIN " +
                        MovieContract.ProductionCompanyEntry.TABLE_NAME + " ON " +
                        MovieContract.MovieProductionCompanyEntry.TABLE_NAME + "." + MovieContract.MovieProductionCompanyEntry.COLUMN_PRODUCTION_COMPANY_ID + " = " +
                        MovieContract.ProductionCompanyEntry.TABLE_NAME + "." + MovieContract.ProductionCompanyEntry.COLUMN_PRODUCTION_COMPANY_ID);

        sMovieProductionCountry_ProductionCountryIJ = new SQLiteQueryBuilder();
        //movie_production_country INNER JOIN production_country ON movie_production_country.iso_3166_1 = production_country.iso_3166_1
        sMovieProductionCountry_ProductionCountryIJ.setTables(
                MovieContract.MovieProductionCountryEntry.TABLE_NAME + " INNER JOIN " +
                        MovieContract.ProductionCountryEntry.TABLE_NAME + " ON " +
                        MovieContract.MovieProductionCountryEntry.TABLE_NAME + "." + MovieContract.MovieProductionCountryEntry.COLUMN_ISO_3166_1 + " = " +
                        MovieContract.ProductionCountryEntry.TABLE_NAME + "." + MovieContract.ProductionCountryEntry.COLUMN_ISO_3166_1);

        sMovieSpokenLanguage_SpokenLanguage = new SQLiteQueryBuilder();
        //movie_spoken_language INNER JOIN spoken_language ON movie_spoken_language.iso_639_1 = spoken_language.iso_639_1
        sMovieSpokenLanguage_SpokenLanguage.setTables(
                MovieContract.MovieSpokenLanguageEntry.TABLE_NAME + " INNER JOIN " +
                        MovieContract.SpokenLanguageEntry.TABLE_NAME + " ON " +
                        MovieContract.MovieSpokenLanguageEntry.TABLE_NAME + "." + MovieContract.MovieSpokenLanguageEntry.COLUMN_ISO_639_1 + " = " +
                        MovieContract.SpokenLanguageEntry.TABLE_NAME + "." + MovieContract.SpokenLanguageEntry.COLUMN_ISO_639_1);

        sTVSeriesGenre_GenreIJ = new SQLiteQueryBuilder();
        //tv_series_genre INNER JOIN genre ON tv_series_genre.genre_id = genre.genre_id
        sTVSeriesGenre_GenreIJ.setTables(
                MovieContract.TVSeriesGenreEntry.TABLE_NAME + " INNER JOIN " +
                        MovieContract.GenreEntry.TABLE_NAME + " ON " +
                        MovieContract.TVSeriesGenreEntry.TABLE_NAME + "." + MovieContract.TVSeriesGenreEntry.COLUMN_GENRE_ID + " = " +
                        MovieContract.GenreEntry.TABLE_NAME + "." + MovieContract.GenreEntry.COLUMN_GENRE_ID);

        sTVSeriesProductionCompany_ProductionCompanyIJ = new SQLiteQueryBuilder();
        //tv_series_production_company INNER JOIN production_company ON tv_series_production_company.production_company_id = production_company.production_company_id
        sTVSeriesProductionCompany_ProductionCompanyIJ.setTables(
                MovieContract.TVSeriesProductionCompanyEntry.TABLE_NAME + " INNER JOIN " +
                        MovieContract.ProductionCompanyEntry.TABLE_NAME + " ON " +
                        MovieContract.TVSeriesProductionCompanyEntry.TABLE_NAME + "." + MovieContract.TVSeriesProductionCompanyEntry.COLUMN_PRODUCTION_COMPANY_ID + " = " +
                        MovieContract.ProductionCompanyEntry.TABLE_NAME + "." + MovieContract.ProductionCompanyEntry.COLUMN_PRODUCTION_COMPANY_ID);

        sTVSeriesNetworks_NetworksIJ = new SQLiteQueryBuilder();
        //tv_series_networks INNER JOIN networks ON tv_series_networks.network_id = networks.network_id
        sTVSeriesNetworks_NetworksIJ.setTables(
                MovieContract.TVSeriesNetworkEntry.TABLE_NAME + " INNER JOIN " +
                        MovieContract.NetworkEntry.TABLE_NAME + " ON " +
                        MovieContract.TVSeriesNetworkEntry.TABLE_NAME + "." + MovieContract.TVSeriesNetworkEntry.COLUMN_NETWORK_ID + " = " +
                        MovieContract.NetworkEntry.TABLE_NAME + "." + MovieContract.NetworkEntry.COLUMN_NETWORK_ID);
    }

    private static final String sMovieMovieIdSelection = MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?";
    private static final String sTVSeriesTVSeriesIdSelection = MovieContract.TVSeriesEntry.COLUMN_TV_SERIES_ID + " = ?";
    private static final String sTrailerMovieIdSelection = MovieContract.TrailerEntry.COLUMN_MOVIE_ID + " = ?";
    private static final String sMovieGenreMovieIdSelection = MovieContract.MovieGenreEntry.COLUMN_MOVIE_ID + " = ?";
    private static final String sTVSeriesGenreTVSeriesIdSelection = MovieContract.TVSeriesGenreEntry.COLUMN_TV_SERIES_ID + " = ?";
    private static final String sMovieProductionCompanyMovieIdSelection = MovieContract.MovieProductionCompanyEntry.COLUMN_MOVIE_ID + " = ?";
    private static final String sTVSeriesProductionCompanyTVSeriesIdSelection = MovieContract.TVSeriesProductionCompanyEntry.COLUMN_TV_SERIES_ID + " = ?";
    private static final String sTVSeriesNetworksTVSeriesIdSelection = MovieContract.TVSeriesNetworkEntry.COLUMN_TV_SERIES_ID + " = ?";
    private static final String sReviewMovieIdSelection = MovieContract.ReviewEntry.COLUMN_MOVIE_ID + " = ?";
    private static final String sTVSeasonTVSeriesIdSelection = MovieContract.TVSeasonEntry.COLUMN_TV_SERIES_ID + " = ?";

    @Override
    public boolean onCreate() {
        mMovieDBHelper = new MovieDBHelper(getContext());
        return true;
    }


    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor queryCursor;

        int matchUri = sUriMatcher.match(uri);
        switch (matchUri) {
            case MOVIE: {
                //Uri for retrieving movies with desc popularity value.
                //Uri for retrieving movies with desc voteAverage value.
                long movieId = MovieContract.MovieEntry.getMovieIdFromParam(uri);
                if (movieId > -1) {
                    selection = sMovieMovieIdSelection;
                    selectionArgs = new String[]{String.valueOf(movieId)};
                }
                queryCursor = mMovieDBHelper.getReadableDatabase().query(MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, //group_by
                        null, //having
                        sortOrder);
                break;
            }
            case TRAILER: {
                //retrieving trailers for a movie id.
                long movieId = MovieContract.TrailerEntry.getMovieIdFromParam(uri);
                if (movieId > -1) {
                    selection = sTrailerMovieIdSelection;
                    selectionArgs = new String[]{String.valueOf(movieId)};
                }
                queryCursor = mMovieDBHelper.getReadableDatabase().query(MovieContract.TrailerEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, //group_by
                        null, //having
                        sortOrder);
                break;
            }
            case REVIEW: {
                //retrieving reviews for a movie id.
                long movieId = MovieContract.ReviewEntry.getMovieIdFromParam(uri);
                if (movieId > -1) {
                    selection = sReviewMovieIdSelection;
                    selectionArgs = new String[]{String.valueOf(movieId)};
                }
                queryCursor = mMovieDBHelper.getReadableDatabase().query(MovieContract.ReviewEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, //group_by
                        null, //having
                        sortOrder);
                break;
            }
            case COLLECTION: {
                queryCursor = mMovieDBHelper.getReadableDatabase().query(MovieContract.CollectionEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, //group_by
                        null, //having
                        sortOrder);
                break;
            }
            case MOVIE_GENRE: {
                //retrieving genres for a movie id.
                long movieId = MovieContract.MovieGenreEntry.getMovieIdFromParam(uri);
                if (movieId > -1) {
                    selection = sMovieGenreMovieIdSelection;
                    selectionArgs = new String[]{String.valueOf(movieId)};
                }

                queryCursor = sMovieGenre_GenreIJ.query(mMovieDBHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case MOVIE_PRODUCTION_COMPANY: {
                //retrieving production_companies for a movie id.
                long movieId = MovieContract.MovieProductionCompanyEntry.getMovieIdFromParam(uri);
                if (movieId > -1) {
                    selection = sMovieProductionCompanyMovieIdSelection;
                    selectionArgs = new String[]{String.valueOf(movieId)};
                }

                queryCursor = sMovieProductionCompany_ProductionCompanyIJ.query(mMovieDBHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case MOVIE_PRODUCTION_COUNTRY: {
                //retrieving production_countries for a movie id.
                long movieId = MovieContract.MovieProductionCountryEntry.getMovieIdFromParam(uri);
                if (movieId > -1) {
                    selection = sMovieProductionCompanyMovieIdSelection;
                    selectionArgs = new String[]{String.valueOf(movieId)};
                }

                queryCursor = sMovieProductionCountry_ProductionCountryIJ.query(mMovieDBHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case MOVIE_SPOKEN_LANGUAGE: {
                //retrieving spoken_language for a movie id.
                long movieId = MovieContract.MovieSpokenLanguageEntry.getMovieIdFromParam(uri);
                if (movieId > -1) {
                    selection = sMovieProductionCompanyMovieIdSelection;
                    selectionArgs = new String[]{String.valueOf(movieId)};
                }

                queryCursor = sMovieSpokenLanguage_SpokenLanguage.query(mMovieDBHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case TV_SERIES: {
                long tvSeriesId = MovieContract.TVSeriesEntry.getTVSeriesIdFromParam(uri);
                if (tvSeriesId > -1) {
                    selection = sTVSeriesTVSeriesIdSelection;
                    selectionArgs = new String[]{String.valueOf(tvSeriesId)};
                }
                queryCursor = mMovieDBHelper.getReadableDatabase().query(MovieContract.TVSeriesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, //group_by
                        null, //having
                        sortOrder);
                break;
            }
            case TV_SERIES_GENRE: {
                long tvSeriesId = MovieContract.TVSeriesGenreEntry.getTVSeriesIdFromParam(uri);
                if (tvSeriesId > -1) {
                    selection = sTVSeriesGenreTVSeriesIdSelection;
                    selectionArgs = new String[]{String.valueOf(tvSeriesId)};
                }

                queryCursor = sTVSeriesGenre_GenreIJ.query(mMovieDBHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case TV_SERIES_PRODUCTION_COMPANY: {
                //retrieving production_companies for a tv_series id.
                long tvSeriesId = MovieContract.TVSeriesProductionCompanyEntry.getTVSeriesIdFromParam(uri);
                if (tvSeriesId > -1) {
                    selection = sTVSeriesProductionCompanyTVSeriesIdSelection;
                    selectionArgs = new String[]{String.valueOf(tvSeriesId)};
                }

                queryCursor = sMovieProductionCompany_ProductionCompanyIJ.query(mMovieDBHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case TV_SERIES_NETWORKS: {
                //retrieving production_companies for a tv_series id.
                long tvSeriesId = MovieContract.TVSeriesNetworkEntry.getTVSeriesIdFromParam(uri);
                if (tvSeriesId > -1) {
                    selection = sTVSeriesNetworksTVSeriesIdSelection;
                    selectionArgs = new String[]{String.valueOf(tvSeriesId)};
                }

                queryCursor = sTVSeriesNetworks_NetworksIJ.query(mMovieDBHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case TV_SEASONS: {
                //retrieving trailers for a movie id.
                long tvSeriesId = MovieContract.TVSeasonEntry.getTVSeriesIdFromParam(uri);
                if (tvSeriesId > -1) {
                    selection = sTVSeasonTVSeriesIdSelection;
                    selectionArgs = new String[]{String.valueOf(tvSeriesId)};
                }
                queryCursor = mMovieDBHelper.getReadableDatabase().query(MovieContract.TVSeasonEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, //group_by
                        null, //having
                        sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }

        Context context = getContext();
        if (context != null) {
            queryCursor.setNotificationUri(context.getContentResolver(), uri);
        }

        return queryCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int matchUri = sUriMatcher.match(uri);
        switch (matchUri) {
            case MOVIE:
                return MovieContract.MovieEntry.DIR_TYPE;
            case TRAILER:
                return MovieContract.TrailerEntry.DIR_TYPE;
            case COLLECTION:
                return MovieContract.CollectionEntry.DIR_TYPE;
            case MOVIE_GENRE:
                return MovieContract.GenreEntry.DIR_TYPE;
            case MOVIE_SPOKEN_LANGUAGE:
                return MovieContract.MovieSpokenLanguageEntry.DIR_TYPE;
            case MOVIE_PRODUCTION_COMPANY:
                return MovieContract.MovieProductionCompanyEntry.DIR_TYPE;
            case MOVIE_PRODUCTION_COUNTRY:
                return MovieContract.MovieProductionCountryEntry.DIR_TYPE;
            case REVIEW:
                return MovieContract.ReviewEntry.DIR_TYPE;
            case TV_SERIES: {
                return MovieContract.TVSeriesEntry.DIR_TYPE;
            }
            case TV_SERIES_GENRE: {
                return MovieContract.TVSeriesGenreEntry.DIR_TYPE;
            }
            case TV_SERIES_PRODUCTION_COMPANY: {
                return MovieContract.TVSeriesProductionCompanyEntry.DIR_TYPE;
            }
            case NETWORKS: {
                return MovieContract.NetworkEntry.DIR_TYPE;
            }
            case TV_SERIES_NETWORKS: {
                return MovieContract.TVSeriesNetworkEntry.DIR_TYPE;
            }
            case TV_SEASONS: {
                return MovieContract.TVSeasonEntry.DIR_TYPE;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mMovieDBHelper.getWritableDatabase();
        final int matchUri = sUriMatcher.match(uri);
        Uri insertedUri;

        switch (matchUri) {
            case MOVIE: {
                long _id = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    insertedUri = MovieContract.MovieEntry.buildMovieUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case TRAILER: {
                long _id = db.insert(MovieContract.TrailerEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    insertedUri = MovieContract.TrailerEntry.buildTrailerUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case GENRE: {
                long _id = db.insert(MovieContract.GenreEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    insertedUri = MovieContract.GenreEntry.buildGenreUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case COLLECTION: {
                long _id = db.insert(MovieContract.CollectionEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    insertedUri = MovieContract.CollectionEntry.buildCollectionUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case PRODUCTION_COMPANY: {
                long _id = db.insert(MovieContract.ProductionCompanyEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    insertedUri = MovieContract.ProductionCompanyEntry.buildProductionCompanyUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case PRODUCTION_COUNTRY: {
                long _id = db.insert(MovieContract.ProductionCountryEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    insertedUri = MovieContract.ProductionCountryEntry.buildMovieProductionCountryUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case SPOKEN_LANGUAGE: {
                long _id = db.insert(MovieContract.SpokenLanguageEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    insertedUri = MovieContract.SpokenLanguageEntry.buildSpokenLanguageUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case MOVIE_GENRE: {
                long _id = db.insert(MovieContract.MovieGenreEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    insertedUri = MovieContract.MovieGenreEntry.buildMovieGenreUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case MOVIE_PRODUCTION_COMPANY: {
                long _id = db.insert(MovieContract.MovieProductionCompanyEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    insertedUri = MovieContract.MovieProductionCompanyEntry.buildMovieProductionCompanyUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case MOVIE_PRODUCTION_COUNTRY: {
                long _id = db.insert(MovieContract.MovieProductionCountryEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    insertedUri = MovieContract.MovieProductionCountryEntry.buildMovieProductionCountryUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case MOVIE_SPOKEN_LANGUAGE: {
                long _id = db.insert(MovieContract.MovieSpokenLanguageEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    insertedUri = MovieContract.SpokenLanguageEntry.buildSpokenLanguageUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case REVIEW: {
                long _id = db.insert(MovieContract.ReviewEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    insertedUri = MovieContract.ReviewEntry.buildReviewUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case TV_SERIES: {
                long _id = db.insert(MovieContract.TVSeriesEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    insertedUri = MovieContract.TVSeriesEntry.buildTVSeriesUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case TV_SERIES_GENRE: {
                long _id = db.insert(MovieContract.TVSeriesGenreEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    insertedUri = MovieContract.TVSeriesGenreEntry.buildTVSeriesGenreUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case TV_SERIES_PRODUCTION_COMPANY: {
                long _id = db.insert(MovieContract.TVSeriesProductionCompanyEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    insertedUri = MovieContract.TVSeriesProductionCompanyEntry.buildMovieProductionCompanyUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case NETWORKS: {
                long _id = db.insert(MovieContract.NetworkEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    insertedUri = MovieContract.NetworkEntry.buildNetworkUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case TV_SERIES_NETWORKS: {
                long _id = db.insert(MovieContract.TVSeriesNetworkEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    insertedUri = MovieContract.TVSeriesNetworkEntry.buildTVSeriesNetworkUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case TV_SEASONS: {
                long _id = db.insert(MovieContract.TVSeasonEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    insertedUri = MovieContract.TVSeasonEntry.buildTVSeasonUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }

        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null); //notify any registered observers.
        }
        db.close();

        return insertedUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mMovieDBHelper.getWritableDatabase();
        int rowDeleted;
        String tableName = getTableName(uri);

        rowDeleted = db.delete(tableName, selection, selectionArgs);
        Context context = getContext();
        if (context != null && rowDeleted > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        db.close();
        return rowDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mMovieDBHelper.getWritableDatabase();
        int rowUpdated;
        String tableName = getTableName(uri);

        rowUpdated = db.update(tableName, values, selection, selectionArgs);
        Context context = getContext();
        if (context != null && rowUpdated > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        db.close();
        return rowUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mMovieDBHelper.getWritableDatabase();
        String tableName = getTableName(uri);
        int insertedCount = 0;

        try {
            db.beginTransaction();
            for (ContentValues cv : values) {
                long _id = db.insert(tableName, null, cv);
                if (_id > 0) {
                    insertedCount++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);
        }

        return insertedCount;
    }

    public static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE, MOVIE);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_TRAILER, TRAILER);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_GENRE, GENRE);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_COLLECTION, COLLECTION);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_PRODUCTION_COMPANY, PRODUCTION_COMPANY);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_PRODUCTION_COUNTRY, PRODUCTION_COUNTRY);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_SPOKEN_LANGUAGE, SPOKEN_LANGUAGE);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE_SPOKEN_LANGUAGE, MOVIE_SPOKEN_LANGUAGE);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE_PRODUCTION_COMPANY, MOVIE_PRODUCTION_COMPANY);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE_PRODUCTION_COUNTRY, MOVIE_PRODUCTION_COUNTRY);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE_GENRE, MOVIE_GENRE);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_REVIEWS, REVIEW);

        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_TV_SERIES, TV_SERIES);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_TV_SERIES_GENRE, TV_SERIES_GENRE);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_TV_SERIES_PRODUCTION_COMPANY, TV_SERIES_PRODUCTION_COMPANY);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_NETWORKS, NETWORKS);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_TV_SERIES_NETWORKS, TV_SERIES_NETWORKS);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_TV_SEASONS, TV_SEASONS);

        return uriMatcher;
    }

    private String getTableName(Uri uri) {
        final int matchUri = sUriMatcher.match(uri);

        switch (matchUri) {
            case MOVIE:
                return MovieContract.MovieEntry.TABLE_NAME;

            case TRAILER:
                return MovieContract.TrailerEntry.TABLE_NAME;

            case GENRE:
                return MovieContract.GenreEntry.TABLE_NAME;

            case COLLECTION:
                return MovieContract.CollectionEntry.TABLE_NAME;

            case PRODUCTION_COMPANY:
                return MovieContract.ProductionCompanyEntry.TABLE_NAME;

            case PRODUCTION_COUNTRY:
                return MovieContract.ProductionCountryEntry.TABLE_NAME;

            case SPOKEN_LANGUAGE:
                return MovieContract.SpokenLanguageEntry.TABLE_NAME;

            case MOVIE_GENRE:
                return MovieContract.MovieGenreEntry.TABLE_NAME;

            case MOVIE_PRODUCTION_COMPANY:
                return MovieContract.MovieProductionCompanyEntry.TABLE_NAME;

            case MOVIE_PRODUCTION_COUNTRY:
                return MovieContract.MovieProductionCountryEntry.TABLE_NAME;

            case MOVIE_SPOKEN_LANGUAGE:
                return MovieContract.MovieSpokenLanguageEntry.TABLE_NAME;

            case REVIEW:
                return MovieContract.ReviewEntry.TABLE_NAME;
            case TV_SERIES: {
                return MovieContract.TVSeriesEntry.TABLE_NAME;
            }
            case TV_SERIES_GENRE: {
                return MovieContract.TVSeriesGenreEntry.TABLE_NAME;
            }
            case TV_SERIES_PRODUCTION_COMPANY: {
                return MovieContract.TVSeriesProductionCompanyEntry.TABLE_NAME;
            }
            case NETWORKS: {
                return MovieContract.NetworkEntry.TABLE_NAME;
            }
            case TV_SERIES_NETWORKS: {
                return MovieContract.TVSeriesNetworkEntry.TABLE_NAME;
            }
            case TV_SEASONS: {
                return MovieContract.TVSeasonEntry.TABLE_NAME;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }
    }
}
