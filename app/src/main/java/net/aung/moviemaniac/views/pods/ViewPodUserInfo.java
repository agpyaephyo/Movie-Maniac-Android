package net.aung.moviemaniac.views.pods;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import net.aung.moviemaniac.R;
import net.aung.moviemaniac.data.model.UserModel;
import net.aung.moviemaniac.data.vos.UserVO;
import net.aung.moviemaniac.utils.FacebookUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aung on 12/17/15.
 */
public class ViewPodUserInfo extends FrameLayout {

    @Bind(R.id.iv_profile)
    ImageView ivProfile;

    @Bind(R.id.iv_cover)
    ImageView ivCover;

    @Bind(R.id.btn_login_with_facebook)
    LoginButton btnLoginWithFacebook;

    @Bind(R.id.ll_container_not_login)
    LinearLayout llContainerNotLogin;

    @Bind(R.id.ll_container_login_user_info)
    LinearLayout llContainerLoginUserInfo;

    @Bind(R.id.tv_username)
    TextView tvUsername;

    public ViewPodUserInfo(Context context) {
        super(context);
    }

    public ViewPodUserInfo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPodUserInfo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this, this);
    }

    public void setUpFacebookLoginButton(Fragment hostFragment, CallbackManager callbackManager) {
        btnLoginWithFacebook.setReadPermissions(FacebookUtils.FB_PERMISSION_LIST);
        btnLoginWithFacebook.setFragment(hostFragment);
        btnLoginWithFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                processFacebookLoginResult(loginResult);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {

            }
        });
    }

    private void processFacebookLoginResult(LoginResult loginResult) {
        final AccessToken accessToken = loginResult.getAccessToken();
        FacebookUtils.getInstance().requestFacebookLoginUser(accessToken, new FacebookUtils.FacebookGetLoginUserCallback() {
            @Override
            public void onSuccess() {
                displayLoginUserInfo(UserModel.getInstance().getLoginUser());
            }
        });

        FacebookUtils.getInstance().requestFacebookCoverPhoto(accessToken, new FacebookUtils.FacebookGetLoginUserCallback() {
            @Override
            public void onSuccess() {
                String coverUrl = UserModel.getInstance().getLoginUser().getCoverUrl();

                Glide.with(getContext())
                        .load(coverUrl)
                        .centerCrop()
                        .into(ivCover);
            }
        });

        FacebookUtils.getInstance().requestFacebookProfilePhoto(accessToken, new FacebookUtils.FacebookGetLoginUserCallback() {
            @Override
            public void onSuccess() {
                String profileUrl = UserModel.getInstance().getLoginUser().getProfileUrl();

                Glide.with(ivProfile.getContext()).load(profileUrl).asBitmap().centerCrop().into(new BitmapImageViewTarget(ivProfile) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(ivProfile.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        ivProfile.setImageDrawable(circularBitmapDrawable);
                    }
                });
            }
        });
    }

    private void displayLoginUserInfo(UserVO loginUser) {
        llContainerNotLogin.setVisibility(View.GONE);
        llContainerLoginUserInfo.setVisibility(View.VISIBLE);

        tvUsername.setText(loginUser.getName());
    }
}
