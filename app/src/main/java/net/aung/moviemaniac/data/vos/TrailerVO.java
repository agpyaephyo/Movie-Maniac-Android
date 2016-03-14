package net.aung.moviemaniac.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.data.persistence.MovieContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aung on 12/16/15.
 */
public class TrailerVO {

    private static final String YOUTUBE_IMAGE_PREVIEW_PATH_FORMAT = "http://img.youtube.com/vi/%s/0.jpg";

    @SerializedName("id")
    private String id;

    @SerializedName("iso_639_1")
    private String iso639_1;

    @SerializedName("key")
    private String key;

    @SerializedName("name")
    private String name;

    @SerializedName("site")
    private String site;

    @SerializedName("size")
    private int size;

    @SerializedName("type")
    private String type;

    public String getId() {
        return id;
    }

    public String getIso639_1() {
        return iso639_1;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public String getTrailerPath() {
        return String.format(YOUTUBE_IMAGE_PREVIEW_PATH_FORMAT, key);
    }

    private ContentValues parseToContentValues(int movieId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.TrailerEntry.COLUMN_TRAILER_ID, id);
        contentValues.put(MovieContract.TrailerEntry.COLUMN_ISO_639_1, iso639_1);
        contentValues.put(MovieContract.TrailerEntry.COLUMN_KEY, key);
        contentValues.put(MovieContract.TrailerEntry.COLUMN_NAME, name);
        contentValues.put(MovieContract.TrailerEntry.COLUMN_SITE, site);
        contentValues.put(MovieContract.TrailerEntry.COLUMN_SIZE, size);
        contentValues.put(MovieContract.TrailerEntry.COLUMN_TYPE, type);
        contentValues.put(MovieContract.TrailerEntry.COLUMN_MOVIE_ID, movieId);
        return contentValues;
    }

    /**
     * for trailer table.
     *
     * @param trailerList
     * @return
     */
    public static ContentValues[] parseToContentValueArray(List<TrailerVO> trailerList, int movieId) {
        ContentValues[] contentValuesArray = new ContentValues[trailerList.size()];
        for (int index = 0; index < contentValuesArray.length; index++) {
            TrailerVO trailer = trailerList.get(index);
            contentValuesArray[index] = trailer.parseToContentValues(movieId);
        }

        return contentValuesArray;
    }

    public static TrailerVO parseFromCursor(Cursor trailerCursor) {
        TrailerVO trailer = new TrailerVO();
        trailer.id = trailerCursor.getString(trailerCursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_TRAILER_ID));
        trailer.iso639_1 = trailerCursor.getString(trailerCursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_ISO_639_1));
        trailer.key = trailerCursor.getString(trailerCursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_KEY));
        trailer.name = trailerCursor.getString(trailerCursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_NAME));
        trailer.site = trailerCursor.getString(trailerCursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_SITE));
        trailer.size = trailerCursor.getInt(trailerCursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_SIZE));
        trailer.type = trailerCursor.getString(trailerCursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_TYPE));
        return trailer;
    }

    public static List<TrailerVO> loadTrailerListByMovieId(int movieId) {
        Context context = MovieManiacApp.getContext();
        List<TrailerVO> trailerList = new ArrayList<>();
        Cursor trailerCursor = context.getContentResolver().query(MovieContract.TrailerEntry.CONTENT_URI,
                null,
                MovieContract.TrailerEntry.COLUMN_MOVIE_ID + " = ?",
                new String[]{String.valueOf(movieId)},
                null);

        if (trailerCursor != null && trailerCursor.moveToFirst()) {
            do {
                trailerList.add(TrailerVO.parseFromCursor(trailerCursor));

            } while (trailerCursor.moveToNext());
            trailerCursor.close();
        }

        return trailerList;
    }
}
