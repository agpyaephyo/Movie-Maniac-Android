package net.aung.moviemaniac.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;

import net.aung.moviemaniac.events.UserEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by aung on 12/15/15.
 */
public class BaseActivity extends AppCompatActivity {

    protected CallbackManager mCallbackManager;
    protected AccessTokenTracker mAccessTokenTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();

        mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    //logout from facebook
                    EventBus.getDefault().post(new UserEvent.LogoutEvent());
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAccessTokenTracker.startTracking();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAccessTokenTracker.stopTracking();
    }
}