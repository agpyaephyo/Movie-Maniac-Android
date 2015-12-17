package net.aung.moviemaniac.menus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.CallbackManager;

import net.aung.moviemaniac.R;
import net.aung.moviemaniac.views.pods.ViewPodUserInfo;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_left_menu, container, false);
        ButterKnife.bind(this, rootView);

        tvAppVersion.setText(getString(R.string.title_yote_shin)+" v0.1");

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

    public void setUp(int fragmentId, DrawerLayout drawerLayout, CallbackManager callbackManager){
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mCallbackManager = callbackManager;
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
    }

    public void openMenu() {
        mDrawerLayout.openDrawer(mFragmentContainerView);
    }
}
