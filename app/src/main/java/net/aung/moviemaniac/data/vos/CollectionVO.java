package net.aung.moviemaniac.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.data.persistence.MovieContract;

/**
 * Created by aung on 12/16/15.
 */
public class CollectionVO {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("backdrop_path")
    private String backdropPath;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public ContentValues parseToContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(MovieContract.CollectionEntry.COLUMN_COLLECTION_ID, id);
        cv.put(MovieContract.CollectionEntry.COLUMN_NAME, name);
        cv.put(MovieContract.CollectionEntry.COLUMN_POSTER_PATH, posterPath);
        cv.put(MovieContract.CollectionEntry.COLUMN_BACKDROP_PATH, backdropPath);

        return cv;
    }

    public static CollectionVO parseFromCursor(Cursor data) {
        CollectionVO collection = new CollectionVO();
        collection.id = data.getInt(data.getColumnIndex(MovieContract.CollectionEntry.COLUMN_COLLECTION_ID));
        collection.name = data.getString(data.getColumnIndex(MovieContract.CollectionEntry.COLUMN_NAME));
        collection.posterPath = data.getString(data.getColumnIndex(MovieContract.CollectionEntry.COLUMN_POSTER_PATH));
        collection.backdropPath = data.getString(data.getColumnIndex(MovieContract.CollectionEntry.COLUMN_BACKDROP_PATH));
        return collection;
    }

    public static CollectionVO loadCollectionById(int collectionId) {
        CollectionVO collection = null;
        Context context = MovieManiacApp.getContext();
        Cursor collectionCursor = context.getContentResolver().query(MovieContract.CollectionEntry.CONTENT_URI,
                null,
                MovieContract.CollectionEntry.COLUMN_COLLECTION_ID + " = ?",
                new String[]{String.valueOf(collectionId)},
                null);

        if (collectionCursor != null && collectionCursor.moveToFirst()) {
            collection = CollectionVO.parseFromCursor(collectionCursor);
            collectionCursor.close();
        }

        return collection;
    }
}
