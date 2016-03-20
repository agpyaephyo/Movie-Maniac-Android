package net.aung.moviemaniac.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.R;
import net.aung.moviemaniac.data.persistence.MovieContract;
import net.aung.moviemaniac.utils.MovieManiacConstants;

import java.util.ArrayList;

/**
 * Created by aung on 3/20/16.
 */
public class TVSeasonVO {

    @SerializedName("air_date")
    private String airDateText;

    @SerializedName("episode_count")
    private int episodeCount;

    @SerializedName("id")
    private int seasonId;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("season_number")
    private int seasonNumber;

    public String getAirDateText() {
        return MovieManiacApp.getContext().getString(R.string.format_season_air_date, airDateText);
    }

    public String getEpisodeCount() {
        return MovieManiacApp.getContext().getString(R.string.format_episode_count, episodeCount);
    }

    public int getSeasonId() {
        return seasonId;
    }

    public String getPosterPath() {
        return MovieManiacConstants.IMAGE_BASE_PATH + MovieManiacConstants.IMAGE_SIZE_W500 + posterPath;
    }

    public String getSeasonNumber() {
        return MovieManiacApp.getContext().getString(R.string.format_season_number, seasonNumber);
    }

    public static ContentValues[] parseToContentValueArray(ArrayList<TVSeasonVO> seasonList, int tvSeriesId) {
        ContentValues[] seasonCVs = new ContentValues[seasonList.size()];

        for (int index = 0; index < seasonCVs.length; index++) {
            seasonCVs[index] = seasonList.get(index).parseToContentValue(tvSeriesId);
        }

        return seasonCVs;
    }

    private ContentValues parseToContentValue(int tvSeriesId) {
        ContentValues seasonCV = new ContentValues();

        seasonCV.put(MovieContract.TVSeasonEntry.COLUMN_AIR_DATE, airDateText);
        seasonCV.put(MovieContract.TVSeasonEntry.COLUMN_EPISODE_COUNT, episodeCount);
        seasonCV.put(MovieContract.TVSeasonEntry.COLUMN_POSTER_PATH, posterPath);
        seasonCV.put(MovieContract.TVSeasonEntry.COLUMN_SEASON_NUMBER, seasonNumber);
        seasonCV.put(MovieContract.TVSeasonEntry.COLUMN_TV_SEASON_ID, seasonId);
        seasonCV.put(MovieContract.TVSeasonEntry.COLUMN_TV_SERIES_ID, tvSeriesId);

        return seasonCV;
    }

    public static ArrayList<TVSeasonVO> loadSeasonListByTVSeriesId(int tvSeriesId) {
        Context context = MovieManiacApp.getContext();
        ArrayList<TVSeasonVO> seasonList = new ArrayList<>();
        Cursor seasonCursor = context.getContentResolver().query(MovieContract.TVSeasonEntry.buildTVSeasonUriWithTVSeriesId(tvSeriesId),
                null, null, null, null);

        if (seasonCursor != null && seasonCursor.moveToFirst()) {
            do {
                seasonList.add(TVSeasonVO.parseFromCursor(seasonCursor));
            } while (seasonCursor.moveToNext());
            seasonCursor.close();
        }

        return seasonList;
    }

    private static TVSeasonVO parseFromCursor(Cursor seasonCursor) {
        TVSeasonVO tvSeason = new TVSeasonVO();

        tvSeason.airDateText = seasonCursor.getString(seasonCursor.getColumnIndex(MovieContract.TVSeasonEntry.COLUMN_AIR_DATE));
        tvSeason.episodeCount = seasonCursor.getInt(seasonCursor.getColumnIndex(MovieContract.TVSeasonEntry.COLUMN_EPISODE_COUNT));
        tvSeason.seasonId = seasonCursor.getInt(seasonCursor.getColumnIndex(MovieContract.TVSeasonEntry.COLUMN_TV_SEASON_ID));
        tvSeason.posterPath = seasonCursor.getString(seasonCursor.getColumnIndex(MovieContract.TVSeasonEntry.COLUMN_POSTER_PATH));
        tvSeason.seasonNumber = seasonCursor.getInt(seasonCursor.getColumnIndex(MovieContract.TVSeasonEntry.COLUMN_SEASON_NUMBER));

        return tvSeason;
    }
}
