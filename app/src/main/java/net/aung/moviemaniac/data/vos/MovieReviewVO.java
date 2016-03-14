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
 * Created by aung on 3/14/16.
 */
public class MovieReviewVO {

    @SerializedName("id")
    private String id;

    @SerializedName("author")
    private String author;

    @SerializedName("content")
    private String content;

    @SerializedName("url")
    private String url;

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    public static void saveReviewsFromList(List<MovieReviewVO> movieReviewList, int movieId) {
        ContentValues[] reviewsListCVs = parseToContentValuesArray(movieReviewList, movieId);

        //Bulk Insert reviews.
        Context context = MovieManiacApp.getContext();
        int insertedCount = context.getContentResolver().bulkInsert(MovieContract.ReviewEntry.CONTENT_URI, reviewsListCVs);

        Log.d(MovieManiacApp.TAG, "Bulk inserted into reviews table : " + insertedCount);
    }

    private static ContentValues[] parseToContentValuesArray(List<MovieReviewVO> movieReviewList, int movieId) {
        ContentValues[] reviewListCVs = new ContentValues[movieReviewList.size()];

        for (int index = 0; index < reviewListCVs.length; index++) {
            reviewListCVs[index] = movieReviewList.get(index).parseToContentValues(movieId);
        }

        return reviewListCVs;
    }

    private ContentValues parseToContentValues(int movieId) {
        ContentValues reviewCV = new ContentValues();
        reviewCV.put(MovieContract.ReviewEntry.COLUMN_REVIEW_ID, id);
        reviewCV.put(MovieContract.ReviewEntry.COLUMN_MOVIE_ID, movieId);
        reviewCV.put(MovieContract.ReviewEntry.COLUMN_AUTHOR, author);
        reviewCV.put(MovieContract.ReviewEntry.COLUMN_CONTENT, content);
        reviewCV.put(MovieContract.ReviewEntry.COLUMN_URL, url);
        return reviewCV;
    }

    public static List<MovieReviewVO> loadReviewListByMovieId(int movieId) {
        List<MovieReviewVO> reviewList = new ArrayList<>();

        Context context = MovieManiacApp.getContext();
        Cursor cursorReviewList = context.getContentResolver().query(MovieContract.ReviewEntry.buildReviewUriWithMovieId(movieId),
                null, null, null, null);

        if (cursorReviewList != null && cursorReviewList.moveToFirst()) {
            do {
                reviewList.add(MovieReviewVO.parseToContentValues(cursorReviewList));
            } while (cursorReviewList.moveToNext());
        }

        return reviewList;
    }

    private static MovieReviewVO parseToContentValues(Cursor cursorReview) {
        MovieReviewVO review = new MovieReviewVO();
        review.id = cursorReview.getString(cursorReview.getColumnIndex(MovieContract.ReviewEntry.COLUMN_REVIEW_ID));
        review.author = cursorReview.getString(cursorReview.getColumnIndex(MovieContract.ReviewEntry.COLUMN_AUTHOR));
        review.content = cursorReview.getString(cursorReview.getColumnIndex(MovieContract.ReviewEntry.COLUMN_CONTENT));
        review.url = cursorReview.getString(cursorReview.getColumnIndex(MovieContract.ReviewEntry.COLUMN_URL));
        return review;
    }
}
