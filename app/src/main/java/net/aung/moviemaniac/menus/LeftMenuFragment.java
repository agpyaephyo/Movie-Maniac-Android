package net.aung.moviemaniac.menus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.aung.moviemaniac.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aung on 12/17/15.
 */
public class LeftMenuFragment extends Fragment {

    private DrawerLayout mDrawerLayout;
    private View mFragmentContainerView;

    @Bind(R.id.tv_app_version)
    TextView tvAppVersion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_left_menu, container, false);
        ButterKnife.bind(this, rootView);

        tvAppVersion.setText(getString(R.string.title_yote_shin)+" v0.1");

        return rootView;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout){
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
    }

    public void openMenu() {
        mDrawerLayout.openDrawer(mFragmentContainerView);
    }
}
