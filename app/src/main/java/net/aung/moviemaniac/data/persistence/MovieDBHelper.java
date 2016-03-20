package net.aung.moviemaniac.data.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.aung.moviemaniac.data.persistence.MovieContract.CollectionEntry;
import net.aung.moviemaniac.data.persistence.MovieContract.GenreEntry;
import net.aung.moviemaniac.data.persistence.MovieContract.MovieEntry;
import net.aung.moviemaniac.data.persistence.MovieContract.MovieGenreEntry;
import net.aung.moviemaniac.data.persistence.MovieContract.MovieProductionCompanyEntry;
import net.aung.moviemaniac.data.persistence.MovieContract.MovieProductionCountryEntry;
import net.aung.moviemaniac.data.persistence.MovieContract.MovieSpokenLanguageEntry;
import net.aung.moviemaniac.data.persistence.MovieContract.ProductionCompanyEntry;
import net.aung.moviemaniac.data.persistence.MovieContract.ProductionCountryEntry;
import net.aung.moviemaniac.data.persistence.MovieContract.SpokenLanguageEntry;
import net.aung.moviemaniac.data.persistence.MovieContract.TrailerEntry;
import net.aung.moviemaniac.data.persistence.MovieContract.ReviewEntry;
import net.aung.moviemaniac.data.persistence.MovieContract.TVSeriesEntry;
import net.aung.moviemaniac.data.persistence.MovieContract.TVSeriesGenreEntry;
import net.aung.moviemaniac.data.persistence.MovieContract.TVSeriesProductionCompanyEntry;
import net.aung.moviemaniac.data.persistence.MovieContract.NetworkEntry;
import net.aung.moviemaniac.data.persistence.MovieContract.TVSeriesNetworkEntry;
import net.aung.moviemaniac.data.persistence.MovieContract.TVSeasonEntry;

/**
 * Created by aung on 3/8/16.
 */
public class MovieDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 11;
    public static final String DATABASE_NAME = "movie.db";

    private static final String SQL_CREATE_SPOKEN_LANGUAGE_TABLE = "CREATE TABLE " + SpokenLanguageEntry.TABLE_NAME + " (" +
            SpokenLanguageEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SpokenLanguageEntry.COLUMN_ISO_639_1 + " TEXT NOT NULL, " +
            SpokenLanguageEntry.COLUMN_NAME + " TEXT NOT NULL," +

            " UNIQUE (" + SpokenLanguageEntry.COLUMN_ISO_639_1 +") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_PRODUCTION_COUNTRY_TABLE = "CREATE TABLE " + ProductionCountryEntry.TABLE_NAME + " (" +
            ProductionCountryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ProductionCountryEntry.COLUMN_ISO_3166_1 + " TEXT NOT NULL, " +
            ProductionCountryEntry.COLUMN_NAME + " TEXT NOT NULL," +

            " UNIQUE (" + ProductionCountryEntry.COLUMN_ISO_3166_1 +") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_PRODUCTION_COMPANY_TABLE = "CREATE TABLE " + ProductionCompanyEntry.TABLE_NAME + " (" +
            ProductionCompanyEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ProductionCompanyEntry.COLUMN_PRODUCTION_COMPANY_ID + " INTEGER NOT NULL, " +
            ProductionCompanyEntry.COLUMN_NAME + " TEXT NOT NULL," +

            " UNIQUE (" + ProductionCompanyEntry.COLUMN_PRODUCTION_COMPANY_ID +") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_COLLECTION_TABLE = "CREATE TABLE " + CollectionEntry.TABLE_NAME + " (" +
            CollectionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CollectionEntry.COLUMN_COLLECTION_ID + " INTEGER NOT NULL, " +
            CollectionEntry.COLUMN_NAME + " TEXT NOT NULL, " +
            CollectionEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
            CollectionEntry.COLUMN_BACKDROP_PATH + " TEXT NOT NULL," +

            " UNIQUE (" + CollectionEntry.COLUMN_COLLECTION_ID +") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_GENRE_TABLE = "CREATE TABLE " + GenreEntry.TABLE_NAME + " (" +
            GenreEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            GenreEntry.COLUMN_GENRE_ID + " INTEGER NOT NULL, " +
            GenreEntry.COLUMN_NAME + " TEXT NOT NULL," +

            " UNIQUE (" + GenreEntry.COLUMN_GENRE_ID +") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
            MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
            MovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
            MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
            MovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
            MovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, " +
            MovieEntry.COLUMN_ORIGINAL_LANGUAGE + " TEXT NOT NULL, " +
            MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
            MovieEntry.COLUMN_BACKDROP_PATH + " TEXT, " +
            MovieEntry.COLUMN_POPULARITY + " REAL NOT NULL, " +
            MovieEntry.COLUMN_VOTE_COUNT + " INTEGER NOT NULL, " +
            MovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +
            MovieEntry.COLUMN_IS_ADULT + " INTEGER DEFAULT 0, " +
            MovieEntry.COLUMN_IS_VIDEO + " INTEGER DEFAULT 0, " +

            MovieEntry.COLUMN_BUDGET + " INTEGER, " +
            MovieEntry.COLUMN_HOMEPAGE + " TEXT, " +
            MovieEntry.COLUMN_IMDB_ID + " TEXT, " +
            MovieEntry.COLUMN_REVENUE + " INTEGER, " +
            MovieEntry.COLUMN_RUNTIME + " INTEGER, " +
            MovieEntry.COLUMN_STATUS + " TEXT, " +
            MovieEntry.COLUMN_TAGLINE + " TEXT, " +
            MovieEntry.COLUMN_COLLECTION_ID + " INTEGER, " +

            MovieEntry.COLUMN_MOVIE_TYPE + " INTEGER NOT NULL, " +
            MovieEntry.COLUMN_IS_DETAIL_LOADED + " INTEGER DEFAULT 0, " +
            MovieEntry.COLUMN_IS_STAR + " INTEGER DEFAULT 0, " +

            /* make reference for FK */
            " FOREIGN KEY (" + MovieEntry.COLUMN_COLLECTION_ID + ") REFERENCES " +
            CollectionEntry.TABLE_NAME + " (" + CollectionEntry.COLUMN_COLLECTION_ID + ")," +

            " UNIQUE (" + MovieEntry.COLUMN_MOVIE_ID +", "+
            MovieEntry.COLUMN_MOVIE_TYPE+") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_TRAILER_TABLE = "CREATE TABLE " + TrailerEntry.TABLE_NAME + " (" +
            TrailerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TrailerEntry.COLUMN_TRAILER_ID + " TEXT NOT NULL, " +
            TrailerEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
            TrailerEntry.COLUMN_ISO_639_1 + " TEXT NOT NULL, " +
            TrailerEntry.COLUMN_KEY + " TEXT NOT NULL, " +
            TrailerEntry.COLUMN_NAME + " TEXT NOT NULL, " +
            TrailerEntry.COLUMN_SITE + " TEXT NOT NULL, " +
            TrailerEntry.COLUMN_SIZE + " INTEGER NOT NULL, " +
            TrailerEntry.COLUMN_TYPE + " TEXT NOT NULL, " +

            /* make reference for FK */
            " FOREIGN KEY (" + TrailerEntry.COLUMN_MOVIE_ID + ") REFERENCES " +
            MovieEntry.TABLE_NAME + " (" + MovieEntry.COLUMN_MOVIE_ID + "), " +

            " UNIQUE (" + TrailerEntry.COLUMN_TRAILER_ID +") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_REVIEW_TABLE = "CREATE TABLE " + ReviewEntry.TABLE_NAME + " (" +
            ReviewEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ReviewEntry.COLUMN_REVIEW_ID + " TEXT NOT NULL, " +
            ReviewEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
            ReviewEntry.COLUMN_AUTHOR + " TEXT NOT NULL, " +
            ReviewEntry.COLUMN_CONTENT + " TEXT NOT NULL, " +
            ReviewEntry.COLUMN_URL + " TEXT NOT NULL, " +

            /* make reference for FK */
            " FOREIGN KEY (" + ReviewEntry.COLUMN_MOVIE_ID + ") REFERENCES " +
            MovieEntry.TABLE_NAME + " (" + MovieEntry.COLUMN_MOVIE_ID + "), " +

            " UNIQUE (" + ReviewEntry.COLUMN_REVIEW_ID +") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_MOVIE_GENRE_TABLE = "CREATE TABLE " + MovieGenreEntry.TABLE_NAME + " (" +
            MovieGenreEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MovieGenreEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
            MovieGenreEntry.COLUMN_GENRE_ID + " INTEGER NOT NULL, " +

            /* make reference for FK */
            " FOREIGN KEY (" + MovieGenreEntry.COLUMN_MOVIE_ID + ") REFERENCES " +
            MovieEntry.TABLE_NAME + " (" + MovieEntry.COLUMN_MOVIE_ID + "), " +

            /* make reference for FK */
            " FOREIGN KEY (" + MovieGenreEntry.COLUMN_GENRE_ID + ") REFERENCES " +
            GenreEntry.TABLE_NAME + " (" + GenreEntry.COLUMN_GENRE_ID + "), " +

            " UNIQUE (" + MovieGenreEntry.COLUMN_MOVIE_ID + ", " +
            MovieGenreEntry.COLUMN_GENRE_ID + ") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_MOVIE_PRODUCTION_COMPANY_TABLE = "CREATE TABLE " + MovieProductionCompanyEntry.TABLE_NAME + " (" +
            MovieProductionCompanyEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MovieProductionCompanyEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
            MovieProductionCompanyEntry.COLUMN_PRODUCTION_COMPANY_ID + " INTEGER NOT NULL, " +

            /* make reference for FK */
            " FOREIGN KEY (" + MovieProductionCompanyEntry.COLUMN_MOVIE_ID + ") REFERENCES " +
            MovieEntry.TABLE_NAME + " (" + MovieEntry.COLUMN_MOVIE_ID + "), " +

            /* make reference for FK */
            " FOREIGN KEY (" + MovieProductionCompanyEntry.COLUMN_PRODUCTION_COMPANY_ID + ") REFERENCES " +
            ProductionCompanyEntry.TABLE_NAME + " (" + ProductionCompanyEntry.COLUMN_PRODUCTION_COMPANY_ID + "), " +

            " UNIQUE (" + MovieProductionCompanyEntry.COLUMN_MOVIE_ID + ", " +
            MovieProductionCompanyEntry.COLUMN_PRODUCTION_COMPANY_ID + ") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_MOVIE_PRODUCTION_COUNTRY_TABLE = "CREATE TABLE " + MovieProductionCountryEntry.TABLE_NAME + " (" +
            MovieProductionCountryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MovieProductionCountryEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
            MovieProductionCountryEntry.COLUMN_ISO_3166_1 + " TEXT NOT NULL, " +

            /* make reference for FK */
            " FOREIGN KEY (" + MovieProductionCountryEntry.COLUMN_MOVIE_ID + ") REFERENCES " +
            MovieEntry.TABLE_NAME + " (" + MovieEntry.COLUMN_MOVIE_ID + "), " +

            /* make reference for FK */
            " FOREIGN KEY (" + MovieProductionCountryEntry.COLUMN_ISO_3166_1 + ") REFERENCES " +
            ProductionCountryEntry.TABLE_NAME + " (" + ProductionCountryEntry.COLUMN_ISO_3166_1 + "), " +

            " UNIQUE (" + MovieProductionCountryEntry.COLUMN_MOVIE_ID + ", " +
            MovieProductionCountryEntry.COLUMN_ISO_3166_1 + ") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_MOVIE_SPOKEN_LANGUAGE_TABLE = "CREATE TABLE " + MovieSpokenLanguageEntry.TABLE_NAME + " (" +
            MovieSpokenLanguageEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MovieSpokenLanguageEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
            MovieSpokenLanguageEntry.COLUMN_ISO_639_1 + " TEXT NOT NULL, " +

            /* make reference for FK */
            " FOREIGN KEY (" + MovieSpokenLanguageEntry.COLUMN_MOVIE_ID + ") REFERENCES " +
            MovieEntry.TABLE_NAME + " (" + MovieEntry.COLUMN_MOVIE_ID + "), " +

            /* make reference for FK */
            " FOREIGN KEY (" + MovieSpokenLanguageEntry.COLUMN_ISO_639_1 + ") REFERENCES " +
            SpokenLanguageEntry.TABLE_NAME + " (" + SpokenLanguageEntry.COLUMN_ISO_639_1 + "), " +

            " UNIQUE (" + MovieSpokenLanguageEntry.COLUMN_MOVIE_ID + ", " +
            MovieSpokenLanguageEntry.COLUMN_ISO_639_1 + ") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_TV_SERIES_TABLE = "CREATE TABLE " + TVSeriesEntry.TABLE_NAME + " (" +
            TVSeriesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TVSeriesEntry.COLUMN_TV_SERIES_ID + " INTEGER NOT NULL, " +
            TVSeriesEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
            TVSeriesEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
            TVSeriesEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
            TVSeriesEntry.COLUMN_ORIGINAL_NAME + " TEXT NOT NULL, " +
            TVSeriesEntry.COLUMN_ORIGINAL_LANGUAGE + " TEXT NOT NULL, " +
            TVSeriesEntry.COLUMN_NAME + " TEXT NOT NULL, " +
            TVSeriesEntry.COLUMN_BACKDROP_PATH + " TEXT, " +
            TVSeriesEntry.COLUMN_POPULARITY + " REAL NOT NULL, " +
            TVSeriesEntry.COLUMN_VOTE_COUNT + " INTEGER NOT NULL, " +
            TVSeriesEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +

            TVSeriesEntry.COLUMN_TV_SERIES_TYPE + " INTEGER NOT NULL, " +
            TVSeriesEntry.COLUMN_IS_DETAIL_LOADED + " INTEGER DEFAULT 0, " +
            TVSeriesEntry.COLUMN_IS_STAR + " INTEGER DEFAULT 0, " +

            TVSeriesEntry.COLUMN_HOMEPAGE + " TEXT, " +
            TVSeriesEntry.COLUMN_LAST_AIR_DATE + " TEXT, " +
            TVSeriesEntry.COLUMN_NUMBER_OF_EPISODES + " INTEGER, " +
            TVSeriesEntry.COLUMN_NUMBER_OF_SEASON + " INTEGER, " +
            TVSeriesEntry.COLUMN_STATUS + " TEXT," +

            " UNIQUE (" + TVSeriesEntry.COLUMN_TV_SERIES_ID +", "+
            TVSeriesEntry.COLUMN_TV_SERIES_TYPE+") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_TV_SERIES_GENRE_TABLE = "CREATE TABLE " + TVSeriesGenreEntry.TABLE_NAME + " (" +
            TVSeriesGenreEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TVSeriesGenreEntry.COLUMN_TV_SERIES_ID + " INTEGER NOT NULL, " +
            TVSeriesGenreEntry.COLUMN_GENRE_ID + " INTEGER NOT NULL, " +

            /* make reference for FK */
            " FOREIGN KEY (" + TVSeriesGenreEntry.COLUMN_TV_SERIES_ID + ") REFERENCES " +
            TVSeriesEntry.TABLE_NAME + " (" + TVSeriesEntry.COLUMN_TV_SERIES_ID + "), " +

            /* make reference for FK */
            " FOREIGN KEY (" + TVSeriesGenreEntry.COLUMN_GENRE_ID + ") REFERENCES " +
            GenreEntry.TABLE_NAME + " (" + GenreEntry.COLUMN_GENRE_ID + "), " +

            " UNIQUE (" + TVSeriesGenreEntry.COLUMN_TV_SERIES_ID + ", " +
            TVSeriesGenreEntry.COLUMN_GENRE_ID + ") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_TV_SERIES_PRODUCTION_COMPANY_TABLE = "CREATE TABLE " + TVSeriesProductionCompanyEntry.TABLE_NAME + " (" +
            TVSeriesProductionCompanyEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TVSeriesProductionCompanyEntry.COLUMN_TV_SERIES_ID + " INTEGER NOT NULL, " +
            TVSeriesProductionCompanyEntry.COLUMN_PRODUCTION_COMPANY_ID + " INTEGER NOT NULL, " +

            /* make reference for FK */
            " FOREIGN KEY (" + TVSeriesProductionCompanyEntry.COLUMN_TV_SERIES_ID + ") REFERENCES " +
            TVSeriesEntry.TABLE_NAME + " (" + TVSeriesEntry.COLUMN_TV_SERIES_ID + "), " +

            /* make reference for FK */
            " FOREIGN KEY (" + TVSeriesProductionCompanyEntry.COLUMN_PRODUCTION_COMPANY_ID + ") REFERENCES " +
            ProductionCompanyEntry.TABLE_NAME + " (" + ProductionCompanyEntry.COLUMN_PRODUCTION_COMPANY_ID + "), " +

            " UNIQUE (" + TVSeriesProductionCompanyEntry.COLUMN_TV_SERIES_ID + ", " +
            TVSeriesProductionCompanyEntry.COLUMN_PRODUCTION_COMPANY_ID + ") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_NETWORKS_TABLE = "CREATE TABLE " + NetworkEntry.TABLE_NAME + " (" +
            NetworkEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NetworkEntry.COLUMN_NETWORK_ID + " INTEGER NOT NULL, " +
            NetworkEntry.COLUMN_NAME + " TEXT NOT NULL," +

            " UNIQUE (" + NetworkEntry.COLUMN_NETWORK_ID +") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_TV_SERIES_NETWORK_TABLE = "CREATE TABLE " + TVSeriesNetworkEntry.TABLE_NAME + " (" +
            TVSeriesNetworkEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TVSeriesNetworkEntry.COLUMN_TV_SERIES_ID + " INTEGER NOT NULL, " +
            TVSeriesNetworkEntry.COLUMN_NETWORK_ID + " INTEGER NOT NULL, " +

            /* make reference for FK */
            " FOREIGN KEY (" + TVSeriesNetworkEntry.COLUMN_TV_SERIES_ID + ") REFERENCES " +
            TVSeriesEntry.TABLE_NAME + " (" + TVSeriesEntry.COLUMN_TV_SERIES_ID + "), " +

            /* make reference for FK */
            " FOREIGN KEY (" + TVSeriesNetworkEntry.COLUMN_NETWORK_ID + ") REFERENCES " +
            NetworkEntry.TABLE_NAME + " (" + NetworkEntry.COLUMN_NETWORK_ID + "), " +

            " UNIQUE (" + TVSeriesNetworkEntry.COLUMN_TV_SERIES_ID + ", " +
            TVSeriesNetworkEntry.COLUMN_TV_SERIES_ID + ") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_TV_SEASONS_TABLE = "CREATE TABLE " + TVSeasonEntry.TABLE_NAME + " (" +
            TVSeasonEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TVSeasonEntry.COLUMN_TV_SEASON_ID + " TEXT NOT NULL, " +
            TVSeasonEntry.COLUMN_AIR_DATE + " TEXT NOT NULL, " +
            TVSeasonEntry.COLUMN_EPISODE_COUNT + " INTEGER NOT NULL, " +
            TVSeasonEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
            TVSeasonEntry.COLUMN_SEASON_NUMBER + " INTEGER NOT NULL, " +
            TVSeasonEntry.COLUMN_TV_SERIES_ID + " INTEGER NOT NULL, " +

            /* make reference for FK */
            " FOREIGN KEY (" + TVSeasonEntry.COLUMN_TV_SERIES_ID + ") REFERENCES " +
            TVSeriesEntry.TABLE_NAME + " (" + TVSeriesEntry.COLUMN_TV_SERIES_ID + "), " +

            " UNIQUE (" + TVSeasonEntry.COLUMN_TV_SEASON_ID +") ON CONFLICT REPLACE" +
            " );";

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_SPOKEN_LANGUAGE_TABLE);
        db.execSQL(SQL_CREATE_PRODUCTION_COUNTRY_TABLE);
        db.execSQL(SQL_CREATE_PRODUCTION_COMPANY_TABLE);
        db.execSQL(SQL_CREATE_COLLECTION_TABLE);
        db.execSQL(SQL_CREATE_GENRE_TABLE);
        db.execSQL(SQL_CREATE_MOVIE_TABLE);
        db.execSQL(SQL_CREATE_TRAILER_TABLE);
        db.execSQL(SQL_CREATE_MOVIE_GENRE_TABLE);
        db.execSQL(SQL_CREATE_MOVIE_PRODUCTION_COMPANY_TABLE);
        db.execSQL(SQL_CREATE_MOVIE_PRODUCTION_COUNTRY_TABLE);
        db.execSQL(SQL_CREATE_MOVIE_SPOKEN_LANGUAGE_TABLE);
        db.execSQL(SQL_CREATE_REVIEW_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieSpokenLanguageEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieProductionCountryEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieProductionCompanyEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieGenreEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TrailerEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GenreEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CollectionEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ProductionCompanyEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ProductionCountryEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SpokenLanguageEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ReviewEntry.TABLE_NAME);

        onCreate(db);
    }
}
