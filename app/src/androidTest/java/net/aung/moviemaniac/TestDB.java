package net.aung.moviemaniac;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

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
import net.aung.moviemaniac.data.persistence.MovieDBHelper;

import java.util.HashSet;

/**
 * Created by aung on 2/9/16.
 */
public class TestDB extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mContext.deleteDatabase(MovieDBHelper.DATABASE_NAME);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test DB basic operations.
     * - is operational ?
     * - all the tables are created properly ?
     * - if columns in location table are correct ?
     * - if columns in weather table are correct ?
     *
     * @throws Throwable
     */
    public void testDatabase() throws Throwable {
        Log.d(MovieManiacApp.TAG, "#### Start testCreateDB");
        //Create DB.
        SQLiteDatabase db = new MovieDBHelper(mContext).getWritableDatabase(); //the db should be "opened" by default.
        Log.d(MovieManiacApp.TAG, "Database is created");

        //TODO Is db operational ?
        assertTrue("Database can't be opened", db.isOpen());
        Log.d(MovieManiacApp.TAG, "Database is operational");

        //Create HashSet for table name we would like to create.
        final HashSet<String> tableNames = new HashSet<>();
        tableNames.add(CollectionEntry.TABLE_NAME);
        tableNames.add(GenreEntry.TABLE_NAME);
        tableNames.add(MovieEntry.TABLE_NAME);
        tableNames.add(MovieGenreEntry.TABLE_NAME);
        tableNames.add(MovieProductionCompanyEntry.TABLE_NAME);
        tableNames.add(MovieProductionCountryEntry.TABLE_NAME);
        tableNames.add(MovieSpokenLanguageEntry.TABLE_NAME);
        tableNames.add(ProductionCompanyEntry.TABLE_NAME);
        tableNames.add(ProductionCountryEntry.TABLE_NAME);
        tableNames.add(SpokenLanguageEntry.TABLE_NAME);
        tableNames.add(TrailerEntry.TABLE_NAME);

        //TODO Are tables created correctly ?
        //Get table names from created db.
        Cursor cursorTables = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        //** Check if cursor is operational. Maybe because db hasn't been created correctly.
        assertTrue("Table cursor can't move to first", cursorTables.moveToFirst());
        Log.d(MovieManiacApp.TAG, "Table cursor is operational");

        do {
            tableNames.remove(cursorTables.getString(0)); //table name is being stored in column index 0.
        } while (cursorTables.moveToNext());

        //** Check if all the desired tables are being created.
        if (!tableNames.isEmpty()) {
            StringBuilder notCreatedTables = new StringBuilder();
            for (String tableName : tableNames) {
                notCreatedTables.append(tableName + " - ");
            }
            fail("One of the db tables is not being created. " + notCreatedTables.toString());
        } else {
            Log.d(MovieManiacApp.TAG, "All the tables are being created properly.");
        }

        collectionColumnsTest(db);
        genreColumnsTest(db);
        movieColumnsTest(db);
        movieGenreColumnsTest(db);
        movieProductionCompanyColumnsTest(db);
        movieProductionCountryColumnsTest(db);
        movieSpokenLanguageColumnsTest(db);
        productionCompanyColumnsTest(db);
        productionCountryColumnsTest(db);
        spokenLanguageColumnsTest(db);
        trailerColumnsTest(db);

        db.close();
    }

    /**
     * Test if columns in Location table are correct.
     */
    private void collectionColumnsTest(SQLiteDatabase db) {
        Cursor cursorCollectionColumns = db.rawQuery("PRAGMA table_info(" + CollectionEntry.TABLE_NAME + ")", null);

        //** Check if cursor is operational.
        assertTrue("Collection column cursor can't move to first", cursorCollectionColumns.moveToFirst());
        Log.d(MovieManiacApp.TAG, "Collection column cursor is operational");

        //Create HashSet for column names in Location Table.
        final HashSet<String> collectionColumns = new HashSet<String>();
        collectionColumns.add(CollectionEntry._ID);
        collectionColumns.add(CollectionEntry.COLUMN_BACKDROP_PATH);
        collectionColumns.add(CollectionEntry.COLUMN_COLLECTION_ID);
        collectionColumns.add(CollectionEntry.COLUMN_NAME);
        collectionColumns.add(CollectionEntry.COLUMN_POSTER_PATH);

        int indexForColumnName = cursorCollectionColumns.getColumnIndex("name");
        do {
            String columnName = cursorCollectionColumns.getString(indexForColumnName);
            collectionColumns.remove(columnName);
        } while (cursorCollectionColumns.moveToNext());

        //** Check if all the columns in Location table are being created.
        if (!collectionColumns.isEmpty()) {
            StringBuilder notCreatedColumns = new StringBuilder();
            for (String locationColumn : collectionColumns) {
                notCreatedColumns.append(locationColumn + " - ");
            }
            fail("One of the columns in Collection table is not being created. " + notCreatedColumns.toString());
        } else {
            Log.d(MovieManiacApp.TAG, "All the columns in Collection table are being created properly.");
        }
    }

    /**
     * Test if columns in Weather table are correct.
     */
    private void genreColumnsTest(SQLiteDatabase db) {
        Cursor cursorGenreColumns = db.rawQuery("PRAGMA table_info(" + GenreEntry.TABLE_NAME + ")", null);

        //** Check if cursor is operation.
        assertTrue("Genre column cursor can't move to first", cursorGenreColumns.moveToFirst());
        Log.d(MovieManiacApp.TAG, "Genre column cursor is operational");

        final HashSet<String> genreColumns = new HashSet<>();
        genreColumns.add(GenreEntry._ID);
        genreColumns.add(GenreEntry.COLUMN_GENRE_ID);
        genreColumns.add(GenreEntry.COLUMN_NAME);


        int indexForColumnName = cursorGenreColumns.getColumnIndex("name");
        do {
            String columnName = cursorGenreColumns.getString(indexForColumnName);
            genreColumns.remove(columnName);
        } while (cursorGenreColumns.moveToNext());

        //** Check if all the columns in Weather table are being created.
        if (!genreColumns.isEmpty()) {
            StringBuilder notCreatedColumns = new StringBuilder();
            for (String weatherColumn : genreColumns) {
                notCreatedColumns.append(weatherColumn + " - ");
            }

            fail("One of the columns in Genre table is not being created. " + notCreatedColumns.toString());
        } else {
            Log.d(MovieManiacApp.TAG, "All the columns in Genre table are being created properly.");
        }
    }

    private void movieColumnsTest(SQLiteDatabase db) {
        Cursor cursorMovieColumns = db.rawQuery("PRAGMA table_info(" + MovieEntry.TABLE_NAME + ")", null);

        //** Check if cursor is operation.
        assertTrue("Movie column cursor can't move to first", cursorMovieColumns.moveToFirst());
        Log.d(MovieManiacApp.TAG, "Movie column cursor is operational");

        final HashSet<String> movieColumns = new HashSet<>();
        movieColumns.add(MovieEntry._ID);
        movieColumns.add(MovieEntry.COLUMN_BACKDROP_PATH);
        movieColumns.add(MovieEntry.COLUMN_BUDGET);
        movieColumns.add(MovieEntry.COLUMN_COLLECTION_ID);
        movieColumns.add(MovieEntry.COLUMN_HOMEPAGE);
        movieColumns.add(MovieEntry.COLUMN_IMDB_ID);
        movieColumns.add(MovieEntry.COLUMN_IS_ADULT);
        movieColumns.add(MovieEntry.COLUMN_IS_VIDEO);
        movieColumns.add(MovieEntry.COLUMN_MOVIE_ID);
        movieColumns.add(MovieEntry.COLUMN_ORIGINAL_LANGUAGE);
        movieColumns.add(MovieEntry.COLUMN_ORIGINAL_TITLE);
        movieColumns.add(MovieEntry.COLUMN_OVERVIEW);
        movieColumns.add(MovieEntry.COLUMN_POPULARITY);
        movieColumns.add(MovieEntry.COLUMN_POSTER_PATH);
        movieColumns.add(MovieEntry.COLUMN_RELEASE_DATE);
        movieColumns.add(MovieEntry.COLUMN_REVENUE);
        movieColumns.add(MovieEntry.COLUMN_RUNTIME);
        movieColumns.add(MovieEntry.COLUMN_STATUS);
        movieColumns.add(MovieEntry.COLUMN_TAGLINE);
        movieColumns.add(MovieEntry.COLUMN_TITLE);
        movieColumns.add(MovieEntry.COLUMN_VOTE_AVERAGE);
        movieColumns.add(MovieEntry.COLUMN_VOTE_COUNT);

        int indexForColumnName = cursorMovieColumns.getColumnIndex("name");
        do {
            String columnName = cursorMovieColumns.getString(indexForColumnName);
            movieColumns.remove(columnName);
        } while (cursorMovieColumns.moveToNext());

        //** Check if all the columns in Weather table are being created.
        if (!movieColumns.isEmpty()) {
            StringBuilder notCreatedColumns = new StringBuilder();
            for (String weatherColumn : movieColumns) {
                notCreatedColumns.append(weatherColumn + " - ");
            }

            fail("One of the columns in Movie table is not being created. " + notCreatedColumns.toString());
        } else {
            Log.d(MovieManiacApp.TAG, "All the columns in Movie table are being created properly.");
        }
    }

    private void movieGenreColumnsTest(SQLiteDatabase db) {
        Cursor cursorMovieGenreColumns = db.rawQuery("PRAGMA table_info(" + MovieGenreEntry.TABLE_NAME + ")", null);

        //** Check if cursor is operation.
        assertTrue("MovieGenre column cursor can't move to first", cursorMovieGenreColumns.moveToFirst());
        Log.d(MovieManiacApp.TAG, "MovieGenre column cursor is operational");

        final HashSet<String> movieGenreColumns = new HashSet<>();
        movieGenreColumns.add(MovieGenreEntry._ID);
        movieGenreColumns.add(MovieGenreEntry.COLUMN_GENRE_ID);
        movieGenreColumns.add(MovieGenreEntry.COLUMN_MOVIE_ID);

        int indexForColumnName = cursorMovieGenreColumns.getColumnIndex("name");
        do {
            String columnName = cursorMovieGenreColumns.getString(indexForColumnName);
            movieGenreColumns.remove(columnName);
        } while (cursorMovieGenreColumns.moveToNext());

        //** Check if all the columns in Weather table are being created.
        if (!movieGenreColumns.isEmpty()) {
            StringBuilder notCreatedColumns = new StringBuilder();
            for (String weatherColumn : movieGenreColumns) {
                notCreatedColumns.append(weatherColumn + " - ");
            }

            fail("One of the columns in MovieGenre table is not being created. " + notCreatedColumns.toString());
        } else {
            Log.d(MovieManiacApp.TAG, "All the columns in MovieGenre table are being created properly.");
        }
    }

    private void movieProductionCompanyColumnsTest(SQLiteDatabase db) {
        Cursor cursorMovieProductionCompanyColumns = db.rawQuery("PRAGMA table_info(" + MovieProductionCompanyEntry.TABLE_NAME + ")", null);

        //** Check if cursor is operation.
        assertTrue("MovieProductionCompany column cursor can't move to first", cursorMovieProductionCompanyColumns.moveToFirst());
        Log.d(MovieManiacApp.TAG, "MovieProductionCompany column cursor is operational");

        final HashSet<String> movieProductionCompanyColumns = new HashSet<>();
        movieProductionCompanyColumns.add(MovieProductionCompanyEntry._ID);
        movieProductionCompanyColumns.add(MovieProductionCompanyEntry.COLUMN_MOVIE_ID);
        movieProductionCompanyColumns.add(MovieProductionCompanyEntry.COLUMN_PRODUCTION_COMPANY_ID);

        int indexForColumnName = cursorMovieProductionCompanyColumns.getColumnIndex("name");
        do {
            String columnName = cursorMovieProductionCompanyColumns.getString(indexForColumnName);
            movieProductionCompanyColumns.remove(columnName);
        } while (cursorMovieProductionCompanyColumns.moveToNext());

        //** Check if all the columns in Weather table are being created.
        if (!movieProductionCompanyColumns.isEmpty()) {
            StringBuilder notCreatedColumns = new StringBuilder();
            for (String weatherColumn : movieProductionCompanyColumns) {
                notCreatedColumns.append(weatherColumn + " - ");
            }

            fail("One of the columns in MovieProductionCompany table is not being created. " + notCreatedColumns.toString());
        } else {
            Log.d(MovieManiacApp.TAG, "All the columns in MovieProductionCompany table are being created properly.");
        }
    }

    private void movieProductionCountryColumnsTest(SQLiteDatabase db) {
        Cursor cursorMovieProductionCountryColumns = db.rawQuery("PRAGMA table_info(" + MovieProductionCountryEntry.TABLE_NAME + ")", null);

        //** Check if cursor is operation.
        assertTrue("MovieProductionCountry column cursor can't move to first", cursorMovieProductionCountryColumns.moveToFirst());
        Log.d(MovieManiacApp.TAG, "MovieProductionCountry column cursor is operational");

        final HashSet<String> movieProductionCountryColumns = new HashSet<>();
        movieProductionCountryColumns.add(MovieProductionCountryEntry._ID);
        movieProductionCountryColumns.add(MovieProductionCountryEntry.COLUMN_ISO_3166_1);
        movieProductionCountryColumns.add(MovieProductionCountryEntry.COLUMN_MOVIE_ID);

        int indexForColumnName = cursorMovieProductionCountryColumns.getColumnIndex("name");
        do {
            String columnName = cursorMovieProductionCountryColumns.getString(indexForColumnName);
            movieProductionCountryColumns.remove(columnName);
        } while (cursorMovieProductionCountryColumns.moveToNext());

        //** Check if all the columns in Weather table are being created.
        if (!movieProductionCountryColumns.isEmpty()) {
            StringBuilder notCreatedColumns = new StringBuilder();
            for (String weatherColumn : movieProductionCountryColumns) {
                notCreatedColumns.append(weatherColumn + " - ");
            }

            fail("One of the columns in MovieProductionCountry table is not being created. " + notCreatedColumns.toString());
        } else {
            Log.d(MovieManiacApp.TAG, "All the columns in MovieProductionCountry table are being created properly.");
        }
    }

    private void movieSpokenLanguageColumnsTest(SQLiteDatabase db) {
        Cursor cursorMovieSpokenLanguageColumns = db.rawQuery("PRAGMA table_info(" + MovieSpokenLanguageEntry.TABLE_NAME + ")", null);

        //** Check if cursor is operation.
        assertTrue("MovieSpokenLanguage column cursor can't move to first", cursorMovieSpokenLanguageColumns.moveToFirst());
        Log.d(MovieManiacApp.TAG, "MovieSpokenLanguage column cursor is operational");

        final HashSet<String> movieSpokenLanguageColumns = new HashSet<>();
        movieSpokenLanguageColumns.add(MovieSpokenLanguageEntry._ID);
        movieSpokenLanguageColumns.add(MovieSpokenLanguageEntry.COLUMN_ISO_639_1);
        movieSpokenLanguageColumns.add(MovieSpokenLanguageEntry.COLUMN_MOVIE_ID);

        int indexForColumnName = cursorMovieSpokenLanguageColumns.getColumnIndex("name");
        do {
            String columnName = cursorMovieSpokenLanguageColumns.getString(indexForColumnName);
            movieSpokenLanguageColumns.remove(columnName);
        } while (cursorMovieSpokenLanguageColumns.moveToNext());

        //** Check if all the columns in Weather table are being created.
        if (!movieSpokenLanguageColumns.isEmpty()) {
            StringBuilder notCreatedColumns = new StringBuilder();
            for (String weatherColumn : movieSpokenLanguageColumns) {
                notCreatedColumns.append(weatherColumn + " - ");
            }

            fail("One of the columns in MovieSpokenLanguage table is not being created. " + notCreatedColumns.toString());
        } else {
            Log.d(MovieManiacApp.TAG, "All the columns in MovieSpokenLanguage table are being created properly.");
        }
    }

    private void productionCompanyColumnsTest(SQLiteDatabase db) {
        Cursor cursorProductionCompanyColumns = db.rawQuery("PRAGMA table_info(" + ProductionCompanyEntry.TABLE_NAME + ")", null);

        //** Check if cursor is operation.
        assertTrue("ProductionCompany column cursor can't move to first", cursorProductionCompanyColumns.moveToFirst());
        Log.d(MovieManiacApp.TAG, "ProductionCompany column cursor is operational");

        final HashSet<String> productionCompanyColumns = new HashSet<>();
        productionCompanyColumns.add(ProductionCompanyEntry._ID);
        productionCompanyColumns.add(ProductionCompanyEntry.COLUMN_NAME);
        productionCompanyColumns.add(ProductionCompanyEntry.COLUMN_PRODUCTION_COMPANY_ID);

        int indexForColumnName = cursorProductionCompanyColumns.getColumnIndex("name");
        do {
            String columnName = cursorProductionCompanyColumns.getString(indexForColumnName);
            productionCompanyColumns.remove(columnName);
        } while (cursorProductionCompanyColumns.moveToNext());

        //** Check if all the columns in Weather table are being created.
        if (!productionCompanyColumns.isEmpty()) {
            StringBuilder notCreatedColumns = new StringBuilder();
            for (String weatherColumn : productionCompanyColumns) {
                notCreatedColumns.append(weatherColumn + " - ");
            }

            fail("One of the columns in ProductionCompany table is not being created. " + notCreatedColumns.toString());
        } else {
            Log.d(MovieManiacApp.TAG, "All the columns in ProductionCompany table are being created properly.");
        }
    }

    private void productionCountryColumnsTest(SQLiteDatabase db) {
        Cursor cursorProductionCountryColumns = db.rawQuery("PRAGMA table_info(" + ProductionCountryEntry.TABLE_NAME + ")", null);

        //** Check if cursor is operation.
        assertTrue("ProductionCountry column cursor can't move to first", cursorProductionCountryColumns.moveToFirst());
        Log.d(MovieManiacApp.TAG, "ProductionCountry column cursor is operational");

        final HashSet<String> productionCountryColumns = new HashSet<>();
        productionCountryColumns.add(ProductionCountryEntry._ID);
        productionCountryColumns.add(ProductionCountryEntry.COLUMN_ISO_3166_1);
        productionCountryColumns.add(ProductionCountryEntry.COLUMN_NAME);

        int indexForColumnName = cursorProductionCountryColumns.getColumnIndex("name");
        do {
            String columnName = cursorProductionCountryColumns.getString(indexForColumnName);
            productionCountryColumns.remove(columnName);
        } while (cursorProductionCountryColumns.moveToNext());

        //** Check if all the columns in Weather table are being created.
        if (!productionCountryColumns.isEmpty()) {
            StringBuilder notCreatedColumns = new StringBuilder();
            for (String weatherColumn : productionCountryColumns) {
                notCreatedColumns.append(weatherColumn + " - ");
            }

            fail("One of the columns in ProductionCountry table is not being created. " + notCreatedColumns.toString());
        } else {
            Log.d(MovieManiacApp.TAG, "All the columns in ProductionCountry table are being created properly.");
        }
    }

    private void spokenLanguageColumnsTest(SQLiteDatabase db) {
        Cursor cursorSpokenLanguageColumns = db.rawQuery("PRAGMA table_info(" + SpokenLanguageEntry.TABLE_NAME + ")", null);

        //** Check if cursor is operation.
        assertTrue("SpokenLanguage column cursor can't move to first", cursorSpokenLanguageColumns.moveToFirst());
        Log.d(MovieManiacApp.TAG, "SpokenLanguage column cursor is operational");

        final HashSet<String> spokenLanguageColumns = new HashSet<>();
        spokenLanguageColumns.add(SpokenLanguageEntry._ID);
        spokenLanguageColumns.add(SpokenLanguageEntry.COLUMN_ISO_639_1);
        spokenLanguageColumns.add(SpokenLanguageEntry.COLUMN_NAME);

        int indexForColumnName = cursorSpokenLanguageColumns.getColumnIndex("name");
        do {
            String columnName = cursorSpokenLanguageColumns.getString(indexForColumnName);
            spokenLanguageColumns.remove(columnName);
        } while (cursorSpokenLanguageColumns.moveToNext());

        //** Check if all the columns in Weather table are being created.
        if (!spokenLanguageColumns.isEmpty()) {
            StringBuilder notCreatedColumns = new StringBuilder();
            for (String weatherColumn : spokenLanguageColumns) {
                notCreatedColumns.append(weatherColumn + " - ");
            }

            fail("One of the columns in SpokenLanguage table is not being created. " + notCreatedColumns.toString());
        } else {
            Log.d(MovieManiacApp.TAG, "All the columns in SpokenLanguage table are being created properly.");
        }
    }

    private void trailerColumnsTest(SQLiteDatabase db) {
        Cursor cursorTrailerColumns = db.rawQuery("PRAGMA table_info(" + TrailerEntry.TABLE_NAME + ")", null);

        //** Check if cursor is operation.
        assertTrue("Trailer column cursor can't move to first", cursorTrailerColumns.moveToFirst());
        Log.d(MovieManiacApp.TAG, "Trailer column cursor is operational");

        final HashSet<String> trailerColumns = new HashSet<>();
        trailerColumns.add(TrailerEntry._ID);
        trailerColumns.add(TrailerEntry.COLUMN_ISO_639_1);
        trailerColumns.add(TrailerEntry.COLUMN_KEY);
        trailerColumns.add(TrailerEntry.COLUMN_MOVIE_ID);
        trailerColumns.add(TrailerEntry.COLUMN_NAME);
        trailerColumns.add(TrailerEntry.COLUMN_SITE);
        trailerColumns.add(TrailerEntry.COLUMN_SIZE);
        trailerColumns.add(TrailerEntry.COLUMN_TRAILER_ID);
        trailerColumns.add(TrailerEntry.COLUMN_TYPE);

        int indexForColumnName = cursorTrailerColumns.getColumnIndex("name");
        do {
            String columnName = cursorTrailerColumns.getString(indexForColumnName);
            trailerColumns.remove(columnName);
        } while (cursorTrailerColumns.moveToNext());

        //** Check if all the columns in Weather table are being created.
        if (!trailerColumns.isEmpty()) {
            StringBuilder notCreatedColumns = new StringBuilder();
            for (String weatherColumn : trailerColumns) {
                notCreatedColumns.append(weatherColumn + " - ");
            }

            fail("One of the columns in Trailer table is not being created. " + notCreatedColumns.toString());
        } else {
            Log.d(MovieManiacApp.TAG, "All the columns in Trailer table are being created properly.");
        }
    }
}
