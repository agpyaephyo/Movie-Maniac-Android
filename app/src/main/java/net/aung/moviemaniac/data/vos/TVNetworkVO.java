package net.aung.moviemaniac.data.vos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aung on 3/20/16.
 */
public class TVNetworkVO {

    @SerializedName("id")
    private int tvNetworkId;

    @SerializedName("name")
    private String name;

    public int getTvNetworkId() {
        return tvNetworkId;
    }

    public String getName() {
        return name;
    }
}
