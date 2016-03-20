package net.aung.moviemaniac.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.data.persistence.MovieContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aung on 12/16/15.
 */
public class GenreVO {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private ContentValues parseToContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(MovieContract.GenreEntry.COLUMN_GENRE_ID, id);
        cv.put(MovieContract.GenreEntry.COLUMN_NAME, name);
        return cv;
    }

    /**
     * For GenreEntry Table.
     * @param genreList
     * @return
     */
    public static ContentValues[] parseToContentValueArray(ArrayList<GenreVO> genreList) {
        ContentValues[] contentValuesArray = new ContentValues[genreList.size()];

        for (int index = 0; index < contentValuesArray.length; index++) {
            GenreVO genre = genreList.get(index);
            contentValuesArray[index] = genre.parseToContentValues();
        }

        return contentValuesArray;
    }

    /**
     * For MovieGenreEntry Table.
     * @param genreList
     * @param movieId
     * @return
     */
    public static ContentValues[] parseToContentValueArray(ArrayList<GenreVO> genreList, int movieId) {
        ContentValues[] contentValuesArray = new ContentValues[genreList.size()];
        for (int index = 0; index < contentValuesArray.length; index++) {
            GenreVO genre = genreList.get(index);

            ContentValues cv = new ContentValues();
            cv.put(MovieContract.MovieGenreEntry.COLUMN_MOVIE_ID, movieId);
            cv.put(MovieContract.MovieGenreEntry.COLUMN_GENRE_ID, genre.getId());

            contentValuesArray[index] = cv;
        }
        return contentValuesArray;
    }

    public static void saveGenreFromList(ArrayList<GenreVO> genreList) {
        ContentValues [] genreListCVs = parseToContentValueArray(genreList);

        //Bulk Insert movie.
        Context context = MovieManiacApp.getContext();
        int insertedCount = context.getContentResolver().bulkInsert(MovieContract.GenreEntry.CONTENT_URI, genreListCVs);

        Log.d(MovieManiacApp.TAG, "Bulk inserted into genre table : " + insertedCount);
    }

    public static ArrayList<GenreVO> loadGenreListByMovieId(int movieId) {
        Context context = MovieManiacApp.getContext();
        ArrayList<GenreVO> genreList = new ArrayList<>();
        Cursor genreCursor = context.getContentResolver().query(MovieContract.MovieGenreEntry.buildMovieGenreUriWithMovieId(movieId),
                null, null, null, null);

        if (genreCursor != null && genreCursor.moveToFirst()) {
            do {
                genreList.add(GenreVO.parseFromCursor(genreCursor));
            } while (genreCursor.moveToNext());
            genreCursor.close();
        }

        return genreList;
    }

    public static ArrayList<GenreVO> loadGenreListByTVSeriesId(int tvSeriesId) {
        Context context = MovieManiacApp.getContext();
        ArrayList<GenreVO> genreList = new ArrayList<>();
        Cursor genreCursor = context.getContentResolver().query(MovieContract.TVSeriesGenreEntry.buildTVSeriesGenreUriWithTVSeriesId(tvSeriesId),
                null, null, null, null);

        if (genreCursor != null && genreCursor.moveToFirst()) {
            do {
                genreList.add(GenreVO.parseFromCursor(genreCursor));
            } while (genreCursor.moveToNext());
            genreCursor.close();
        }

        return genreList;
    }

    private static GenreVO parseFromCursor(Cursor genreCursor) {
        GenreVO genre = new GenreVO();
        genre.id = genreCursor.getInt(genreCursor.getColumnIndex(MovieContract.GenreEntry.COLUMN_GENRE_ID));
        genre.name = genreCursor.getString(genreCursor.getColumnIndex(MovieContract.GenreEntry.COLUMN_NAME));
        return genre;
    }
}
