package net.aung.moviemaniac.data.vos;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
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
@Entity(foreignKeys = {
        @ForeignKey(entity = MovieVO.class, parentColumns = "id", childColumns = "movieId"),
}, tableName = "production_company")
public class ProductionCompanyVO {

    @SerializedName("id")
    @PrimaryKey
    private int id;

    private int movieId;

    @SerializedName("name")
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private ContentValues parseToContentValue() {
        ContentValues cv = new ContentValues();
        cv.put(MovieContract.ProductionCompanyEntry.COLUMN_PRODUCTION_COMPANY_ID, id);
        cv.put(MovieContract.ProductionCompanyEntry.COLUMN_NAME, name);
        return cv;
    }

    /**
     * For production_company table.
     *
     * @param productionCompanyList
     * @return
     */
    public static ContentValues[] parseToContentValueArray(ArrayList<ProductionCompanyVO> productionCompanyList) {
        ContentValues[] productionCompanyCVs = new ContentValues[productionCompanyList.size()];
        for (int index = 0; index < productionCompanyList.size(); index++) {
            ProductionCompanyVO productionCompany = productionCompanyList.get(index);
            productionCompanyCVs[index] = productionCompany.parseToContentValue();
        }

        return productionCompanyCVs;
    }

    /**
     * For movie_production_company table.
     *
     * @param productionCompanyList
     * @param movieId
     * @return
     */
    public static ContentValues[] parseToContentValueArray(ArrayList<ProductionCompanyVO> productionCompanyList, int movieId) {
        ContentValues[] productionCompanyCVs = new ContentValues[productionCompanyList.size()];
        for (int index = 0; index < productionCompanyList.size(); index++) {
            ProductionCompanyVO productionCompany = productionCompanyList.get(index);

            ContentValues cv = new ContentValues();
            cv.put(MovieContract.MovieProductionCompanyEntry.COLUMN_MOVIE_ID, movieId);
            cv.put(MovieContract.MovieProductionCompanyEntry.COLUMN_PRODUCTION_COMPANY_ID, productionCompany.getId());

            productionCompanyCVs[index] = cv;
        }

        return productionCompanyCVs;
    }

    public static ProductionCompanyVO parseFromCursor(Cursor productionCompanyCursor) {
        ProductionCompanyVO productionCompany = new ProductionCompanyVO();
        productionCompany.id = productionCompanyCursor.getInt(productionCompanyCursor.getColumnIndex(MovieContract.ProductionCompanyEntry.COLUMN_PRODUCTION_COMPANY_ID));
        productionCompany.name = productionCompanyCursor.getString(productionCompanyCursor.getColumnIndex(MovieContract.ProductionCompanyEntry.COLUMN_NAME));
        return productionCompany;
    }

    public static ArrayList<ProductionCompanyVO> loadProductionCompanyListByMovieId(int movieId) {
        Context context = MovieManiacApp.getContext();
        ArrayList<ProductionCompanyVO> productionCompanyList = new ArrayList<>();
        Cursor productionCompanyCursor = context.getContentResolver().query(MovieContract.MovieProductionCompanyEntry.CONTENT_URI,
                null,
                MovieContract.MovieProductionCompanyEntry.COLUMN_MOVIE_ID + " = ?",
                new String[]{String.valueOf(movieId)},
                null);

        if (productionCompanyCursor != null && productionCompanyCursor.moveToFirst()) {
            do {
                productionCompanyList.add(ProductionCompanyVO.parseFromCursor(productionCompanyCursor));

            } while (productionCompanyCursor.moveToNext());
            productionCompanyCursor.close();
        }

        return productionCompanyList;
    }

    public static ArrayList<ProductionCompanyVO> loadProductionCompanyListByTVSeriesId(int tvSeriesId) {
        Context context = MovieManiacApp.getContext();
        ArrayList<ProductionCompanyVO> productionCompanyList = new ArrayList<>();
        Cursor productionCompanyCursor = context.getContentResolver().query(MovieContract.TVSeriesProductionCompanyEntry.CONTENT_URI,
                null,
                MovieContract.TVSeriesProductionCompanyEntry.COLUMN_TV_SERIES_ID + " = ?",
                new String[]{String.valueOf(tvSeriesId)},
                null);

        if (productionCompanyCursor != null && productionCompanyCursor.moveToFirst()) {
            do {
                productionCompanyList.add(ProductionCompanyVO.parseFromCursor(productionCompanyCursor));

            } while (productionCompanyCursor.moveToNext());
            productionCompanyCursor.close();
        }

        return productionCompanyList;
    }

    public static ContentValues[] parseToContentValueArrayTVSeries(ArrayList<ProductionCompanyVO> productionCompanyList, int tvSeriesId) {
        ContentValues[] productionCompanyCVs = new ContentValues[productionCompanyList.size()];
        for (int index = 0; index < productionCompanyList.size(); index++) {
            ProductionCompanyVO productionCompany = productionCompanyList.get(index);

            ContentValues cv = new ContentValues();
            cv.put(MovieContract.TVSeriesProductionCompanyEntry.COLUMN_TV_SERIES_ID, tvSeriesId);
            cv.put(MovieContract.TVSeriesProductionCompanyEntry.COLUMN_PRODUCTION_COMPANY_ID, productionCompany.getId());

            productionCompanyCVs[index] = cv;
        }

        return productionCompanyCVs;
    }
}
