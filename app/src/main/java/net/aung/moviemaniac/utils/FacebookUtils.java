package net.aung.moviemaniac.utils;

import android.content.Context;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.data.model.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by aung on 12/17/15.
 */
public class FacebookUtils {

    public static final List<String> FB_PERMISSION_LIST = Arrays.asList("public_profile", "email", "user_friends");

    private static FacebookUtils objInstance;

    private Context context;

    private FacebookUtils() {
        context = MovieManiacApp.getContext();
    }

    public static FacebookUtils getInstance() {
        if (objInstance == null) {
            objInstance = new FacebookUtils();
        }

        return objInstance;
    }

    public void requestFacebookLoginUser(final AccessToken accessToken, final FacebookGetLoginUserCallback callback) {
        if (NetworkUtils.getInstance().isOnline()) {
            GraphRequest.newMeRequest(accessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                            UserModel.getInstance().initUserByFacebook(jsonObject);
                            callback.onSuccess();
                        }
                    }).executeAsync();
        }
    }

    public void requestFacebookCoverPhoto(AccessToken accessToken, final FacebookGetLoginUserCallback callback) {
        if (NetworkUtils.getInstance().isOnline()) {
            Bundle params = new Bundle();
            params.putString("fields", "cover");
            GraphRequest request = new GraphRequest(
                    accessToken,
                    "me",
                    params,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            try {
                                JSONObject coverResponse = response.getJSONObject().getJSONObject("cover");
                                String coverUrl = coverResponse.getString("source");
                                UserModel.getInstance().addFacebookCoverUrl(coverUrl);
                                callback.onSuccess();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
            request.executeAsync();
        }
    }

    public void requestFacebookProfilePhoto(AccessToken accessToken, final FacebookGetLoginUserCallback callback) {
        if (NetworkUtils.getInstance().isOnline()) {
            Bundle params = new Bundle();
            params.putString("redirect", "false");
            params.putString("type", "large");
            GraphRequest request = new GraphRequest(
                    accessToken,
                    "me/picture",
                    params,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            try {
                                String profilePhotoUrl = response.getJSONObject().getJSONObject("data").getString("url");
                                UserModel.getInstance().addFacebookProfileUrl(profilePhotoUrl);
                                callback.onSuccess();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
            request.executeAsync();
        }
    }

    public interface FacebookGetLoginUserCallback {
        void onSuccess();
    }
}
