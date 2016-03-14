package net.aung.moviemaniac.data.vos;

import android.content.ContentValues;

import com.google.gson.annotations.SerializedName;

import net.aung.moviemaniac.data.persistence.MovieContract;

import java.util.ArrayList;

/**
 * Created by aung on 12/16/15.
 */
public class ProductionCompanyVO {

    @SerializedName("id")
    private int id;

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
}
