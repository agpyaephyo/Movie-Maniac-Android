package net.aung.moviemaniac.data.vos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aung on 3/19/16.
 */
public class MenuVO {

    public static final int MENU_INDEX_MOVIE_SHELF = 100;
    public static final int MENU_INDEX_TV_SERIES_SHELF = 200;

    @SerializedName("menu_id")
    private int menuId;

    @SerializedName("menu_label")
    private String menuLabel;

    @SerializedName("menu_icon")
    private String menuIcon;

    public int getMenuId() {
        return menuId;
    }

    public String getMenuLabel() {
        return menuLabel;
    }

    public String getMenuIcon() {
        return menuIcon;
    }
}
