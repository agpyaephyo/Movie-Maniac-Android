package net.aung.moviemaniac.data.vos;

import com.google.gson.annotations.SerializedName;

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
        return airDateText;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }
}
