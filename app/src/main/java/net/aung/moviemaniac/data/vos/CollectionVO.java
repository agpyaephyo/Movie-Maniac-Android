package net.aung.moviemaniac.data.vos;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.data.persistence.MovieContract;

/**
 * Created by aung on 12/16/15.
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = MovieVO.class, parentColumns = "id", childColumns = "movieId"),
}, tableName = "collection")
public class CollectionVO {

    @SerializedName("id")
    @PrimaryKey
    private int collectionId;

    private int movieId;

    @SerializedName("name")
    private String name;

    @SerializedName("poster_path")
    @ColumnInfo(name = "collection_poster_path")
    private String posterPath;

    @SerializedName("backdrop_path")
    @ColumnInfo(name = "collection_backdrop_path")
    private String backdropPath;

    public int getCollectionId() {
        return collectionId;
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
        cv.put(MovieContract.CollectionEntry.COLUMN_COLLECTION_ID, collectionId);
        cv.put(MovieContract.CollectionEntry.COLUMN_NAME, name);
        cv.put(MovieContract.CollectionEntry.COLUMN_POSTER_PATH, posterPath);
        cv.put(MovieContract.CollectionEntry.COLUMN_BACKDROP_PATH, backdropPath);

        return cv;
    }

    public static CollectionVO parseFromCursor(Cursor data) {
        CollectionVO collection = new CollectionVO();
        collection.collectionId = data.getInt(data.getColumnIndex(MovieContract.CollectionEntry.COLUMN_COLLECTION_ID));
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

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }
}
