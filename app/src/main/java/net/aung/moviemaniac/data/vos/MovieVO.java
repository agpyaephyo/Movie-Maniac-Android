package net.aung.moviemaniac.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.data.persistence.MovieContract;
import net.aung.moviemaniac.utils.DateFormatUtils;
import net.aung.moviemaniac.utils.MovieManiacConstants;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Immutable.
 * Created by aung on 12/12/15.
 */
public class MovieVO {

    public static final String IMAGE_BASE_PATH = "http://image.tmdb.org/t/p/";
    public static final String IMAGE_SIZE_W185 = "w185";
    public static final String IMAGE_SIZE_W500 = "w500";

    private static final String RUNTIME_FORMAT = "%1$d hrs %2$d mins";

    @SerializedName("id")
    private int id; //list - d

    @SerializedName("poster_path")
    private String posterPath; //list - d

    @SerializedName("overview")
    private String overview; //list - d

    @SerializedName("release_date")
    private String releaseDateText; //list - d

    @SerializedName("genre_ids")
    private int[] genreIds; //list

    @SerializedName("original_title")
    private String originalTitle; //list - d

    @SerializedName("original_language")
    private String originalLanguage; //list - d

    @SerializedName("title")
    private String title; //list - d

    @SerializedName("backdrop_path")
    private String backdropPath; //list - d

    @SerializedName("popularity")
    private float popularity; //list - d

    @SerializedName("vote_count")
    private int voteCount; //list - d

    @SerializedName("vote_average")
    private float voteAverage; //list - d

    @SerializedName("adult")
    private boolean isAdult; //list - d

    @SerializedName("video")
    private boolean isVideo; //list

    @SerializedName("belongs_to_collection")
    private CollectionVO collection; //detail

    @SerializedName("budget")
    private long budget; //detail

    @SerializedName("genres")
    private ArrayList<GenreVO> genreList; //detail

    @SerializedName("homepage")
    private String homepage; //detail

    @SerializedName("imdb_id")
    private String imdbId; //detail

    @SerializedName("production_companies")
    private ArrayList<ProductionCompanyVO> productionCompanyList; //detail

    @SerializedName("production_countries")
    private ArrayList<ProductionCountryVO> productionCountryList; //detail

    @SerializedName("revenue")
    private long revenue; //detail

    @SerializedName("runtime")
    private int runtime; //detail

    @SerializedName("spoken_languages")
    private ArrayList<SpokenLanguageVO> spokenLanguageList; //detail

    @SerializedName("status")
    private String status; //detail

    @SerializedName("tagline")
    private String tagline; //detail

    private List<TrailerVO> trailerList;
    private boolean isDetailLoaded;
    private Date releaseDate;
    private int collectionId;
    private int movieType;

    public int getId() {
        return id;
    }

    public String getPosterPath() {
        return IMAGE_BASE_PATH + IMAGE_SIZE_W500 + posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public Date getReleaseDate() {
        if (releaseDate == null) {
            try {
                releaseDate = DateFormatUtils.sdf_yyyy_mm_dd.parse(releaseDateText);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return releaseDate;
    }

    public String getReleaseDateDisplay() {
        if (releaseDate == null) {
            try {
                releaseDate = DateFormatUtils.sdf_yyyy_mm_dd.parse(releaseDateText);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return DateFormatUtils.sdf_MMMM_yyyy.format(releaseDate);
    }

    public int[] getGenreIds() {
        return genreIds;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdropPath() {
        return IMAGE_BASE_PATH + IMAGE_SIZE_W500 + backdropPath;
    }

    public float getPopularity() {
        return popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public String getVoteAverage() {
        return String.format("%.1f", voteAverage);
    }

    public CollectionVO getCollection() {
        return collection;
    }

    public void setCollection(CollectionVO collection) {
        this.collection = collection;
    }

    public long getBudget() {
        return budget;
    }

    public void setGenreList(ArrayList<GenreVO> genreList) {
        this.genreList = genreList;
    }

    public ArrayList<GenreVO> getGenreList() {
        return genreList;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getImdbId() {
        return imdbId;
    }

    public ArrayList<ProductionCompanyVO> getProductionCompanyList() {
        return productionCompanyList;
    }

    public ArrayList<ProductionCountryVO> getProductionCountryList() {
        return productionCountryList;
    }

    public long getRevenue() {
        return revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getRuntimeDisplay() {
        int hour = runtime / 60;
        int minute = runtime % 60;
        return String.format(RUNTIME_FORMAT, hour, minute);
    }

    public ArrayList<SpokenLanguageVO> getSpokenLanguageList() {
        return spokenLanguageList;
    }

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
    }

    public List<TrailerVO> getTrailerList() {
        return trailerList;
    }

    public void setTrailerList(List<TrailerVO> trailerList) {
        this.trailerList = trailerList;
    }

    public boolean isDetailLoaded() {
        return isDetailLoaded;
    }

    public void setIsDetailLoaded(boolean isDetailLoaded) {
        this.isDetailLoaded = isDetailLoaded;
    }

    public int getCollectionId() {
        return collectionId;
    }

    public int getMovieType() {
        return movieType;
    }

    public void setMovieType(int movieType) {
        this.movieType = movieType;
    }

    public ContentValues parseToContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, id);
        cv.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, posterPath);
        cv.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, overview);
        cv.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, releaseDateText);
        cv.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE, originalTitle);
        cv.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE, originalLanguage);
        cv.put(MovieContract.MovieEntry.COLUMN_TITLE, title);
        cv.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH, backdropPath);
        cv.put(MovieContract.MovieEntry.COLUMN_POPULARITY, popularity);
        cv.put(MovieContract.MovieEntry.COLUMN_VOTE_COUNT, voteCount);
        cv.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, voteAverage);
        cv.put(MovieContract.MovieEntry.COLUMN_IS_ADULT, isAdult ? 1 : 0);
        cv.put(MovieContract.MovieEntry.COLUMN_IS_VIDEO, isVideo ? 1 : 0);
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_TYPE, movieType);

        if (budget != 0)
            cv.put(MovieContract.MovieEntry.COLUMN_BUDGET, budget);

        if (homepage != null)
            cv.put(MovieContract.MovieEntry.COLUMN_HOMEPAGE, homepage);

        if (imdbId != null)
            cv.put(MovieContract.MovieEntry.COLUMN_IMDB_ID, imdbId);

        if (revenue != 0)
            cv.put(MovieContract.MovieEntry.COLUMN_REVENUE, revenue);

        if (runtime != 0)
            cv.put(MovieContract.MovieEntry.COLUMN_RUNTIME, runtime);

        if (status != null)
            cv.put(MovieContract.MovieEntry.COLUMN_STATUS, status);

        if (tagline != null)
            cv.put(MovieContract.MovieEntry.COLUMN_TAGLINE, tagline);

        if (collection != null)
            cv.put(MovieContract.MovieEntry.COLUMN_COLLECTION_ID, collection.getId());

        return cv;
    }

    public static void saveMovieFromList(List<MovieVO> movieList, @MovieManiacConstants.MovieType int movieType) {
        ContentValues[] movieListCVs = new ContentValues[movieList.size()];
        for (int index = 0; index < movieListCVs.length; index++) {
            MovieVO movie = movieList.get(index);
            movie.setMovieType(movieType);
            movieListCVs[index] = movie.parseToContentValues();

            if (movie.genreIds != null && movie.genreIds.length > 0) {
                ContentValues[] movieGenreListCVs = new ContentValues[movie.getGenreIds().length];
                for (int index_two = 0; index_two < movieGenreListCVs.length; index_two++) {
                    ContentValues movieGenreCV = new ContentValues();
                    movieGenreCV.put(MovieContract.MovieGenreEntry.COLUMN_GENRE_ID, movie.genreIds[index_two]);
                    movieGenreCV.put(MovieContract.MovieGenreEntry.COLUMN_MOVIE_ID, movie.getId());
                    movieGenreListCVs[index_two] = movieGenreCV;
                }

                Context context = MovieManiacApp.getContext();
                int insertedCount = context.getContentResolver().bulkInsert(MovieContract.MovieGenreEntry.CONTENT_URI, movieGenreListCVs);
                Log.d(MovieManiacApp.TAG, "Bulk inserted into movie_genre table : " + insertedCount);
            }
        }

        //Bulk Insert movie.
        Context context = MovieManiacApp.getContext();
        int insertedCount = context.getContentResolver().bulkInsert(MovieContract.MovieEntry.CONTENT_URI, movieListCVs);

        Log.d(MovieManiacApp.TAG, "Bulk inserted into movie table : " + insertedCount);
    }

    public void updateMovieFromDetail() {
        ContentValues cv = parseToContentValues();

        //Update movie.
        Context context = MovieManiacApp.getContext();
        int updateCount = context.getContentResolver().update(MovieContract.MovieEntry.CONTENT_URI, cv,
                MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?",
                new String[]{String.valueOf(id)});

        if (collection != null) {
            ContentValues cvCollection = collection.parseToContentValues();
            //Insert collection.
            Uri uri = context.getContentResolver().insert(MovieContract.CollectionEntry.CONTENT_URI, cvCollection);
            Log.d(MovieManiacApp.TAG, "Inserted collection uri : " + uri);
        }

        if (genreList != null) {
            ContentValues[] genreListCVs = GenreVO.parseToContentValueArray(genreList);
            //Bulk insert to GenreEntry.
            int insertedGenreCount = context.getContentResolver().bulkInsert(MovieContract.GenreEntry.CONTENT_URI, genreListCVs);
            Log.d(MovieManiacApp.TAG, "Bulk inserted into genre table : " + insertedGenreCount);

            ContentValues[] movieGenreListCVs = GenreVO.parseToContentValueArray(genreList, id);
            //Bulk insert to MovieGenreEntry.
            int insertedMovieGenreCount = context.getContentResolver().bulkInsert(MovieContract.MovieGenreEntry.CONTENT_URI, movieGenreListCVs);
            Log.d(MovieManiacApp.TAG, "Bulk inserted into movie_genre table : " + insertedMovieGenreCount);
        }

        if (productionCompanyList != null) {
            ContentValues[] productionCompanyListCVs = ProductionCompanyVO.parseToContentValueArray(productionCompanyList);
            //Bulk insert to ProductionCompanyEntry.
            int insertedProductionCompanyCount = context.getContentResolver().bulkInsert(MovieContract.ProductionCompanyEntry.CONTENT_URI, productionCompanyListCVs);
            Log.d(MovieManiacApp.TAG, "Bulk inserted into production_company table : " + insertedProductionCompanyCount);

            ContentValues[] movieProductionCompanyListCVs = ProductionCompanyVO.parseToContentValueArray(productionCompanyList, id);
            //Bulk insert to MovieProductionCompanyEntry.
            int insertedMovieProductionCompanyCount = context.getContentResolver().bulkInsert(MovieContract.MovieProductionCompanyEntry.CONTENT_URI, movieProductionCompanyListCVs);
            Log.d(MovieManiacApp.TAG, "Bulk inserted into movie_production_company table : " + insertedMovieProductionCompanyCount);
        }

        if (productionCountryList != null) {
            ContentValues[] productionCountryListCVs = ProductionCountryVO.parseToContentValueArray(productionCountryList);
            //Bulk insert to ProductionCountryEntry.
            int insertedProductionCountryCount = context.getContentResolver().bulkInsert(MovieContract.ProductionCountryEntry.CONTENT_URI, productionCountryListCVs);
            Log.d(MovieManiacApp.TAG, "Bulk inserted into production_country table : " + insertedProductionCountryCount);

            ContentValues[] movieProductionCountryListCVs = ProductionCountryVO.parseToContentValueArray(productionCountryList, id);
            //Bulk insert to MovieProductionCountryEntry.
            int insertedMovieProductionCountryCount = context.getContentResolver().bulkInsert(MovieContract.MovieProductionCountryEntry.CONTENT_URI, movieProductionCountryListCVs);
            Log.d(MovieManiacApp.TAG, "Bulk inserted into movie_production_country table : " + insertedMovieProductionCountryCount);
        }

        if (spokenLanguageList != null) {
            ContentValues[] spokenLanguageListCVs = SpokenLanguageVO.parseToContentValueArray(spokenLanguageList);
            //Bulk insert to SpokenLanguageEntry.
            int insertedSpokenLanguageCount = context.getContentResolver().bulkInsert(MovieContract.SpokenLanguageEntry.CONTENT_URI, spokenLanguageListCVs);
            Log.d(MovieManiacApp.TAG, "Bulk inserted into spoken_language table : " + insertedSpokenLanguageCount);

            ContentValues[] movieSpokenLanguageListCVs = SpokenLanguageVO.parseToContentValueArray(spokenLanguageList, id);
            //Bulk insert to MovieSpokenLanguageEntry.
            int insertedMovieSpokenLanguageCount = context.getContentResolver().bulkInsert(MovieContract.MovieSpokenLanguageEntry.CONTENT_URI, movieSpokenLanguageListCVs);
            Log.d(MovieManiacApp.TAG, "Bulk inserted into movie_spoken_language table : " + insertedMovieSpokenLanguageCount);
        }
    }

    public void saveTrailerList() {
        if (trailerList != null) {
            Context context = MovieManiacApp.getContext();
            ContentValues[] trailerListCVs = TrailerVO.parseToContentValueArray(trailerList, id);
            //Bulk insert to TrailerEntry.
            int insertedTrailerListCount = context.getContentResolver().bulkInsert(MovieContract.TrailerEntry.CONTENT_URI, trailerListCVs);
            Log.d(MovieManiacApp.TAG, "Bulk inserted into trailer table : " + insertedTrailerListCount);
        }
    }

    public static MovieVO parseFromListCursor(Cursor data) {
        MovieVO movie = new MovieVO();

        movie.id = data.getInt(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID));
        movie.posterPath = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH));
        movie.overview = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW));
        movie.releaseDateText = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE));
        movie.originalTitle = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE));
        movie.originalLanguage = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE));
        movie.title = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE));
        movie.backdropPath = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH));
        movie.popularity = data.getFloat(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_POPULARITY));
        movie.voteCount = data.getInt(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_COUNT));
        movie.voteAverage = data.getFloat(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE));
        movie.isAdult = data.getInt(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_IS_ADULT)) == 1;
        movie.isVideo = data.getInt(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_IS_VIDEO)) == 1;
        movie.collectionId = data.getInt(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_COLLECTION_ID));
        movie.movieType = data.getInt(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_TYPE));

        /*
        movie.budget = data.getLong(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_BUDGET));
        movie.homepage = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_HOMEPAGE));
        movie.imdbId = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_IMDB_ID));
        movie.revenue = data.getLong(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_REVENUE));
        movie.runtime = data.getInt(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_RUNTIME));
        movie.status = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_STATUS));
        movie.tagline = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_TAGLINE));

        movie.collection = CollectionVO.parseFromCursor(data);
        */

        return movie;
    }

    public void addGenreList(GenreVO genre) {
        if (genreList == null) {
            genreList = new ArrayList<>();
        }

        genreList.add(genre);
    }
}
