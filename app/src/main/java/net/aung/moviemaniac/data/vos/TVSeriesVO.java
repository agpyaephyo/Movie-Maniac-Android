package net.aung.moviemaniac.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.R;
import net.aung.moviemaniac.data.persistence.MovieContract;
import net.aung.moviemaniac.utils.MovieManiacConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aung on 3/19/16.
 */
public class TVSeriesVO {

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("popularity")
    private float popularity;

    @SerializedName("id")
    private int tvSerieId;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("overview")
    private String overview;

    @SerializedName("first_air_date")
    private String firstAirDateText;

    @SerializedName("origin_country")
    private String[] originCountries;

    @SerializedName("genre_ids")
    private int[] genreIds;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("name")
    private String name;

    @SerializedName("original_name")
    private String originalName;

    private int tvSeriesType;
    private boolean isStar;
    private boolean isDetailLoaded;
    private List<TrailerVO> trailerList;

    @SerializedName("genres")
    private ArrayList<GenreVO> genreList; //detail

    @SerializedName("homepage")
    private String homepage; //detail

    @SerializedName("last_air_date")
    private String lastAirDateText;

    @SerializedName("networks")
    private ArrayList<TVNetworkVO> networkList;

    @SerializedName("number_of_episodes")
    private int numberOfEpisodes;

    @SerializedName("number_of_seasons")
    private int numberOfSeasons;

    @SerializedName("production_companies")
    private ArrayList<ProductionCompanyVO> productionCompanyList; //detail

    @SerializedName("seasons")
    private ArrayList<TVSeasonVO> seasonList;

    @SerializedName("status")
    private String status;

    @SerializedName("episode_run_time")
    private int[] episodeRuntime;

    public String getPosterPath() {
        return MovieManiacConstants.IMAGE_BASE_PATH + MovieManiacConstants.IMAGE_SIZE_W500 + posterPath;
    }

    public float getPopularity() {
        return popularity;
    }

    public int getTvSerieId() {
        return tvSerieId;
    }

    public String getBackdropPath() {
        return MovieManiacConstants.IMAGE_BASE_PATH + MovieManiacConstants.IMAGE_SIZE_W500 + backdropPath;
    }

    public String getVoteAverage() {
        return String.format("%.1f", voteAverage);
    }

    public String getOverview() {
        return overview;
    }

    public String getFirstAirDateText() {
        return MovieManiacApp.getContext().getString(R.string.format_first_air_date, firstAirDateText);
    }

    public String[] getOriginCountries() {
        return originCountries;
    }

    public int[] getGenreIds() {
        return genreIds;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getName() {
        return name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public int getTvSeriesType() {
        return tvSeriesType;
    }

    public void setTvSeriesType(int tvSeriesType) {
        this.tvSeriesType = tvSeriesType;
    }

    public ArrayList<GenreVO> getGenreList() {
        return genreList;
    }

    public void setGenreList(ArrayList<GenreVO> genreList) {
        this.genreList = genreList;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getLastAirDateText() {
        return lastAirDateText;
    }

    public ArrayList<TVNetworkVO> getNetworkList() {
        return networkList;
    }

    public void setNetworkList(ArrayList<TVNetworkVO> networkList) {
        this.networkList = networkList;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public ArrayList<ProductionCompanyVO> getProductionCompanyList() {
        return productionCompanyList;
    }

    public void setProductionCompanyList(ArrayList<ProductionCompanyVO> productionCompanyList) {
        this.productionCompanyList = productionCompanyList;
    }

    public ArrayList<TVSeasonVO> getSeasonList() {
        return seasonList;
    }

    public void setSeasonList(ArrayList<TVSeasonVO> seasonList) {
        this.seasonList = seasonList;
    }

    public String getStatus() {
        return status;
    }

    public boolean isStar() {
        return isStar;
    }

    public void setStar(boolean isStar) {
        this.isStar = isStar;
    }

    public boolean isDetailLoaded() {
        return isDetailLoaded;
    }

    public void setDetailLoaded(boolean isDetailLoaded) {
        this.isDetailLoaded = isDetailLoaded;
    }

    public String getEpisodeRuntime() {
        if (episodeRuntime != null && episodeRuntime.length > 0) {
            return MovieManiacApp.getContext().getString(R.string.format_episode_runtime, episodeRuntime[0]);
        }

        return null;
    }

    public List<TrailerVO> getTrailerList() {
        return trailerList;
    }

    public void setTrailerList(List<TrailerVO> trailerList) {
        this.trailerList = trailerList;
    }

    public static void saveTVSeriesFromList(ArrayList<TVSeriesVO> loadedTVSeriesList, @MovieManiacConstants.TVSeriesType int tvSeriesType) {
        ContentValues[] tvSeriesCVs = new ContentValues[loadedTVSeriesList.size()];
        for (int index = 0; index < tvSeriesCVs.length; index++) {
            TVSeriesVO tvSeries = loadedTVSeriesList.get(index);
            tvSeries.setTvSeriesType(tvSeriesType);

            tvSeriesCVs[index] = tvSeries.parseToContentValues();

            if (tvSeries.genreIds != null && tvSeries.genreIds.length > 0) {
                ContentValues[] tvSeriesGenreListCVs = new ContentValues[tvSeries.genreIds.length];
                for (int index_two = 0; index_two < tvSeriesGenreListCVs.length; index_two++) {
                    ContentValues tvSeriesGenreCV = new ContentValues();
                    tvSeriesGenreCV.put(MovieContract.TVSeriesGenreEntry.COLUMN_GENRE_ID, tvSeries.genreIds[index_two]);
                    tvSeriesGenreCV.put(MovieContract.TVSeriesGenreEntry.COLUMN_TV_SERIES_ID, tvSeries.getTvSerieId());

                    tvSeriesGenreListCVs[index_two] = tvSeriesGenreCV;
                }

                Context context = MovieManiacApp.getContext();
                int insertedCount = context.getContentResolver().bulkInsert(MovieContract.TVSeriesGenreEntry.CONTENT_URI, tvSeriesGenreListCVs);
            }
        }

        //Bulk Insert movie.
        Context context = MovieManiacApp.getContext();
        int insertedCount = context.getContentResolver().bulkInsert(MovieContract.TVSeriesEntry.CONTENT_URI, tvSeriesCVs);

        Log.d(MovieManiacApp.TAG, "Bulk inserted into tv_series table with tv series type - " + tvSeriesType + " : " + insertedCount);
    }

    private ContentValues parseToContentValues() {
        ContentValues tvSeriesCV = new ContentValues();
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_TV_SERIES_ID, tvSerieId);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_POSTER_PATH, posterPath);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_OVERVIEW, overview);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_FIRST_AIR_DATE, firstAirDateText);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_NAME, name);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_ORIGINAL_LANGUAGE, originalLanguage);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_ORIGINAL_NAME, originalName);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_BACKDROP_PATH, backdropPath);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_POPULARITY, popularity);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_VOTE_COUNT, voteCount);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_VOTE_AVERAGE, voteAverage);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_TV_SERIES_TYPE, tvSeriesType);

        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_HOMEPAGE, homepage);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_LAST_AIR_DATE, lastAirDateText);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_NUMBER_OF_EPISODES, numberOfEpisodes);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_NUMBER_OF_SEASON, numberOfSeasons);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_STATUS, status);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_IS_DETAIL_LOADED, isDetailLoaded ? 1 : 0);
        tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_IS_STAR, isStar ? 1 : 0);

        if (episodeRuntime != null && episodeRuntime.length > 0) {
            tvSeriesCV.put(MovieContract.TVSeriesEntry.COLUMN_EPISODE_RUNTIME, episodeRuntime[0]);
        }

        return tvSeriesCV;
    }

    public static TVSeriesVO parseFromListCursor(Cursor data) {
        TVSeriesVO tvSeries = new TVSeriesVO();

        tvSeries.tvSerieId = data.getInt(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_TV_SERIES_ID));
        tvSeries.posterPath = data.getString(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_POSTER_PATH));
        tvSeries.overview = data.getString(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_OVERVIEW));
        tvSeries.popularity = data.getFloat(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_POPULARITY));
        tvSeries.backdropPath = data.getString(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_BACKDROP_PATH));
        tvSeries.voteAverage = data.getDouble(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_VOTE_AVERAGE));
        tvSeries.firstAirDateText = data.getString(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_FIRST_AIR_DATE));
        tvSeries.originalLanguage = data.getString(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_ORIGINAL_LANGUAGE));
        tvSeries.voteCount = data.getInt(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_VOTE_COUNT));
        tvSeries.name = data.getString(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_NAME));
        tvSeries.originalName = data.getString(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_ORIGINAL_NAME));
        tvSeries.isStar = data.getInt(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_IS_STAR)) == 1;
        tvSeries.tvSeriesType = data.getInt(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_TV_SERIES_TYPE));

        return tvSeries;
    }

    public void merge(TVSeriesVO tvSeries) {
        tvSeriesType = tvSeries.tvSeriesType;
        isStar = tvSeries.isStar;
    }

    public void updateTVSeriesFromDetail() {
        //Update tv series.
        Context context = MovieManiacApp.getContext();

        if (networkList != null) {
            ContentValues[] networkListCVs = TVNetworkVO.parseToContentValueArray(networkList);
            //Bulk insert to NetworkEntry.
            int insertedNetworkCount = context.getContentResolver().bulkInsert(MovieContract.NetworkEntry.CONTENT_URI, networkListCVs);
            Log.d(MovieManiacApp.TAG, "Bulk inserted into networks table : " + insertedNetworkCount);

            ContentValues[] tvSeriesNetworkListCVs = TVNetworkVO.parseToContentValueArray(networkList, tvSerieId);
            //Bulk insert to TVSeriesNetworkEntry.
            int insertedTVSeriesNetworkCount = context.getContentResolver().bulkInsert(MovieContract.TVSeriesNetworkEntry.CONTENT_URI, tvSeriesNetworkListCVs);
            Log.d(MovieManiacApp.TAG, "Bulk inserted into tv_series_networks table : " + insertedTVSeriesNetworkCount);
        }

        if (productionCompanyList != null) {
            ContentValues[] productionCompanyListCVs = ProductionCompanyVO.parseToContentValueArray(productionCompanyList);
            //Bulk insert to ProductionCompanyEntry.
            int insertedProductionCompanyCount = context.getContentResolver().bulkInsert(MovieContract.ProductionCompanyEntry.CONTENT_URI, productionCompanyListCVs);
            Log.d(MovieManiacApp.TAG, "Bulk inserted into production_company table : " + insertedProductionCompanyCount);

            ContentValues[] tvSeriesProductionCompanyListCVs = ProductionCompanyVO.parseToContentValueArrayTVSeries(productionCompanyList, tvSerieId);
            //Bulk insert to MovieProductionCompanyEntry.
            int insertedMovieProductionCompanyCount = context.getContentResolver().bulkInsert(MovieContract.TVSeriesProductionCompanyEntry.CONTENT_URI, tvSeriesProductionCompanyListCVs);
            Log.d(MovieManiacApp.TAG, "Bulk inserted into tv_series_production_company table : " + insertedMovieProductionCompanyCount);
        }

        if (seasonList != null) {
            ContentValues[] seasonListCVs = TVSeasonVO.parseToContentValueArray(seasonList, tvSerieId);
            //Bulk insert to TVSeasonEntry.
            int insertedTVSeasonListCount = context.getContentResolver().bulkInsert(MovieContract.TVSeasonEntry.CONTENT_URI, seasonListCVs);
            Log.d(MovieManiacApp.TAG, "Bulk inserted into tv_season table : " + insertedTVSeasonListCount);
        }

        ContentValues tvSeriesCV = parseToContentValues();
        int updateCount = context.getContentResolver().update(MovieContract.TVSeriesEntry.CONTENT_URI, tvSeriesCV,
                MovieContract.TVSeriesEntry.COLUMN_TV_SERIES_ID + " = ? AND " + MovieContract.TVSeriesEntry.COLUMN_TV_SERIES_TYPE + " = ?",
                new String[]{String.valueOf(tvSerieId), String.valueOf(tvSeriesType)});

        if(updateCount <= 0) {
            Uri insertedUri = context.getContentResolver().insert(MovieContract.TVSeriesEntry.CONTENT_URI, tvSeriesCV);
        }
    }

    public static TVSeriesVO parseFromDetailCursor(Cursor data) {
        TVSeriesVO tvSeries = parseFromListCursor(data);

        tvSeries.homepage = data.getString(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_HOMEPAGE));
        tvSeries.lastAirDateText = data.getString(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_LAST_AIR_DATE));
        tvSeries.numberOfEpisodes = data.getInt(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_NUMBER_OF_EPISODES));
        tvSeries.numberOfSeasons = data.getInt(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_NUMBER_OF_SEASON));
        tvSeries.status = data.getString(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_STATUS));
        tvSeries.episodeRuntime = new int[]{data.getInt(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_EPISODE_RUNTIME))};
        tvSeries.isDetailLoaded = data.getInt(data.getColumnIndex(MovieContract.TVSeriesEntry.COLUMN_IS_DETAIL_LOADED)) == 1;

        return tvSeries;
    }

    public static void saveTrailerList(int tvSeriesId, ArrayList<TrailerVO> trailerList) {
        if (trailerList != null) {
            Context context = MovieManiacApp.getContext();
            ContentValues[] trailerListCVs = TrailerVO.parseToContentValueArrayForTVSeries(trailerList, tvSeriesId);
            //Bulk insert to TrailerEntry.
            int insertedTrailerListCount = context.getContentResolver().bulkInsert(MovieContract.TrailerEntry.CONTENT_URI, trailerListCVs);
            Log.d(MovieManiacApp.TAG, "Bulk inserted into trailer table : " + insertedTrailerListCount);

            Uri movieUri = MovieContract.TVSeriesEntry.buildTVSeriesUri(tvSeriesId);
            context.getContentResolver().notifyChange(movieUri, null);
        }
    }

    public void updateMovieStarStatus() {
        ContentValues cv = new ContentValues();
        cv.put(MovieContract.TVSeriesEntry.COLUMN_IS_STAR, isStar ? 1 : 0);

        Context context = MovieManiacApp.getContext();
        int updateCount = context.getContentResolver().update(MovieContract.TVSeriesEntry.CONTENT_URI, cv,
                MovieContract.TVSeriesEntry.COLUMN_TV_SERIES_ID + " = ? AND " + MovieContract.TVSeriesEntry.COLUMN_TV_SERIES_TYPE + " = ?",
                new String[]{String.valueOf(tvSerieId), String.valueOf(tvSeriesType)});

        if (updateCount > 0) {
            Log.d(MovieManiacApp.TAG, "The star status for tv series " + name + " has updated to " + isStar);
        }
    }
}
