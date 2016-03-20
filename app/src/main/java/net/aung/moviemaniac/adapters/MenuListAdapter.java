package net.aung.moviemaniac.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.R;
import net.aung.moviemaniac.data.vos.MenuVO;
import net.aung.moviemaniac.views.items.ViewItemMenu;

import java.util.List;

/**
 * Created by aung on 3/19/16.
 */
public class MenuListAdapter extends BaseAdapter {

    private List<MenuVO> mMenuList;
    private LayoutInflater inflater;
    private ViewItemMenu.MenuItemController menuItemController;

    public MenuListAdapter(List<MenuVO> mMenuList, ViewItemMenu.MenuItemController menuItemController) {
        this.mMenuList = mMenuList;
        inflater = LayoutInflater.from(MovieManiacApp.getContext());
        this.menuItemController = menuItemController;
    }

    @Override
    public int getCount() {
        return mMenuList.size();
    }

    @Override
    public MenuVO getItem(int position) {
        return mMenuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.view_item_menu, parent, false);
        }

        if (convertView instanceof ViewItemMenu) {
            ViewItemMenu viMenu = (ViewItemMenu) convertView;
            viMenu.setData(getItem(position));
            viMenu.setController(menuItemController);
        }
        return convertView;
    }
}
