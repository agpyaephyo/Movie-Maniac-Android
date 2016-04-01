package net.aung.moviemaniac.menus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.google.gson.reflect.TypeToken;

import net.aung.moviemaniac.BuildConfig;
import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.R;
import net.aung.moviemaniac.adapters.MenuListAdapter;
import net.aung.moviemaniac.data.vos.MenuVO;
import net.aung.moviemaniac.utils.CommonInstances;
import net.aung.moviemaniac.utils.JsonUtils;
import net.aung.moviemaniac.utils.MovieManiacConstants;
import net.aung.moviemaniac.views.items.ViewItemMenu;
import net.aung.moviemaniac.views.pods.ViewPodUserInfo;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aung on 12/17/15.
 */
public class LeftMenuFragment extends Fragment {

    private DrawerLayout mDrawerLayout;
    private View mFragmentContainerView;
    private CallbackManager mCallbackManager;

    @Bind(R.id.tv_app_version)
    TextView tvAppVersion;

    @Bind(R.id.vp_user_info)
    ViewPodUserInfo vpUserInfo;

    @Bind(R.id.lv_left_menu)
    ListView lvLeftMenu;

    private MenuListAdapter menuAdapter;
    private ViewItemMenu.MenuItemController menuItemController;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        menuItemController = (ViewItemMenu.MenuItemController) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_left_menu, container, false);
        ButterKnife.bind(this, rootView);
        String appName = getString(R.string.app_name);
        tvAppVersion.setText(getString(R.string.format_app_name, appName, BuildConfig.VERSION_NAME));

        try {
            String leftMenu = JsonUtils.getInstance().loadLeftMenuData();
            Type listType = new TypeToken<List<MenuVO>>() {
            }.getType();
            List<MenuVO> menuList = CommonInstances.getGsonInstance().fromJson(leftMenu, listType);
            menuAdapter = new MenuListAdapter(menuList, menuItemController);

            lvLeftMenu.setAdapter(menuAdapter);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vpUserInfo.setUpFacebookLoginButton(this, mCallbackManager);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, CallbackManager callbackManager) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mCallbackManager = callbackManager;
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
    }

    public void openMenu() {
        mDrawerLayout.openDrawer(mFragmentContainerView);
    }
}
