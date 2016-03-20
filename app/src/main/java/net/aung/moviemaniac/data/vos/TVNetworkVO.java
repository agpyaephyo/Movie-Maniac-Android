package net.aung.moviemaniac.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.data.persistence.MovieContract;

import java.util.ArrayList;

/**
 * Created by aung on 3/20/16.
 */
public class TVNetworkVO {

    @SerializedName("id")
    private int networkId;

    @SerializedName("name")
    private String name;

    public int getNetworkId() {
        return networkId;
    }

    public String getName() {
        return name;
    }

    public static ContentValues[] parseToContentValueArray(ArrayList<TVNetworkVO> networkList) {
        ContentValues[] networkCVs = new ContentValues[networkList.size()];
        for (int index = 0; index < networkCVs.length; index++) {
            networkCVs[index] = networkList.get(index).parseToContentValues();
        }

        return networkCVs;
    }

    private ContentValues parseToContentValues() {
        ContentValues networkCV = new ContentValues();
        networkCV.put(MovieContract.NetworkEntry.COLUMN_NETWORK_ID, networkId);
        networkCV.put(MovieContract.NetworkEntry.COLUMN_NAME, name);
        return networkCV;
    }

    public static ContentValues[] parseToContentValueArray(ArrayList<TVNetworkVO> networkList, int tvSeriesId) {
        ContentValues[] networkCVs = new ContentValues[networkList.size()];
        for (int index = 0; index < networkCVs.length; index++) {
            ContentValues tvSeriesNetworkCV = new ContentValues();
            tvSeriesNetworkCV.put(MovieContract.TVSeriesNetworkEntry.COLUMN_NETWORK_ID, networkList.get(index).getNetworkId());
            tvSeriesNetworkCV.put(MovieContract.TVSeriesNetworkEntry.COLUMN_TV_SERIES_ID, tvSeriesId);
            networkCVs[index] = tvSeriesNetworkCV;
        }

        return networkCVs;
    }

    public static ArrayList<TVNetworkVO> loadNetworkListByTVSeriesId(int tvSeriesId) {
        Context context = MovieManiacApp.getContext();
        ArrayList<TVNetworkVO> networkList = new ArrayList<>();
        Cursor networkCursor = context.getContentResolver().query(MovieContract.TVSeriesNetworkEntry.buildTVSeriesNetworkUriWithTVSeriesId(tvSeriesId),
                null, null, null, null);

        if (networkCursor != null && networkCursor.moveToFirst()) {
            do {
                networkList.add(TVNetworkVO.parseFromCursor(networkCursor));
            } while (networkCursor.moveToNext());
            networkCursor.close();
        }

        return networkList;
    }

    private static TVNetworkVO parseFromCursor(Cursor networkCursor) {
        TVNetworkVO tvNetwork = new TVNetworkVO();
        tvNetwork.networkId = networkCursor.getInt(networkCursor.getColumnIndex(MovieContract.NetworkEntry.COLUMN_NETWORK_ID));
        tvNetwork.name = networkCursor.getString(networkCursor.getColumnIndex(MovieContract.NetworkEntry.COLUMN_NAME));
        return tvNetwork;
    }
}
