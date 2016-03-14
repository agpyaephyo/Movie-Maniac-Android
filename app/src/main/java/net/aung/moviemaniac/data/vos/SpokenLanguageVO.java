package net.aung.moviemaniac.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.data.persistence.MovieContract;

import java.util.ArrayList;

/**
 * Created by aung on 12/16/15.
 */
public class SpokenLanguageVO {

    @SerializedName("iso_639_1")
    private String iso_639_1;

    @SerializedName("name")
    private String name;

    public String getIso_639_1() {
        return iso_639_1;
    }

    public String getName() {
        return name;
    }

    private ContentValues parseToContentValue() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.SpokenLanguageEntry.COLUMN_ISO_639_1, iso_639_1);
        contentValues.put(MovieContract.SpokenLanguageEntry.COLUMN_NAME, name);
        return contentValues;
    }

    /**
     * For spoken_language table.
     * @param spokenLanguageList
     * @return
     */
    public static ContentValues[] parseToContentValueArray(ArrayList<SpokenLanguageVO> spokenLanguageList) {
        ContentValues[] contentValuesArray = new ContentValues[spokenLanguageList.size()];
        for (int index = 0; index < contentValuesArray.length; index++) {
            SpokenLanguageVO spokenLanguage = spokenLanguageList.get(index);
            contentValuesArray [index] = spokenLanguage.parseToContentValue();
        }

        return contentValuesArray;
    }

    /**
     * For movie_spoken_language table.
     * @param spokenLanguageList
     * @param movieId
     * @return
     */
    public static ContentValues[] parseToContentValueArray(ArrayList<SpokenLanguageVO> spokenLanguageList, int movieId) {
        ContentValues[] contentValuesArray = new ContentValues[spokenLanguageList.size()];
        for (int index = 0; index < contentValuesArray.length; index++) {
            SpokenLanguageVO spokenLanguage = spokenLanguageList.get(index);

            ContentValues contentValues = new ContentValues();
            contentValues.put(MovieContract.MovieSpokenLanguageEntry.COLUMN_ISO_639_1, spokenLanguage.getIso_639_1());
            contentValues.put(MovieContract.MovieSpokenLanguageEntry.COLUMN_MOVIE_ID, movieId);

            contentValuesArray [index] = contentValues;
        }

        return contentValuesArray;
    }

    public static SpokenLanguageVO parseFromCursor(Cursor spokenLanguageCursor) {
        SpokenLanguageVO spokenLanguage = new SpokenLanguageVO();
        spokenLanguage.iso_639_1 = spokenLanguageCursor.getString(spokenLanguageCursor.getColumnIndex(MovieContract.SpokenLanguageEntry.COLUMN_ISO_639_1));
        spokenLanguage.name = spokenLanguageCursor.getString(spokenLanguageCursor.getColumnIndex(MovieContract.SpokenLanguageEntry.COLUMN_NAME));
        return spokenLanguage;
    }

    public static ArrayList<SpokenLanguageVO> loadSpokenLanguageListByMovieId(int movieId) {
        Context context = MovieManiacApp.getContext();
        ArrayList<SpokenLanguageVO> spokenLanguageList = new ArrayList<>();
        Cursor spokenLanguageCursor = context.getContentResolver().query(MovieContract.MovieSpokenLanguageEntry.CONTENT_URI,
                null,
                MovieContract.MovieSpokenLanguageEntry.COLUMN_MOVIE_ID + " = ?",
                new String[]{String.valueOf(movieId)},
                null);

        if (spokenLanguageCursor != null && spokenLanguageCursor.moveToFirst()) {
            do {
                spokenLanguageList.add(SpokenLanguageVO.parseFromCursor(spokenLanguageCursor));

            } while (spokenLanguageCursor.moveToNext());
            spokenLanguageCursor.close();
        }

        return spokenLanguageList;
    }
}
