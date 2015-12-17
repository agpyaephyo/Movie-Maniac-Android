package net.aung.moviemaniac.data.model;

import android.content.Context;
import android.content.SharedPreferences;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.data.vos.UserVO;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aung on 12/17/15.
 */
public class UserModel {

    public static final String PREF_CONFIG_USER = "CONFIG_USER";
    public static final String PK_USER_FACEBOOK_CONNECT = "PK_USER_FACEBOOK_CONNECT";
    public static final String PK_IS_USER_LOGIN = "PK_IS_USER_LOGIN";

    private static UserModel objInstance;

    private Context context;
    private UserVO loginUser;
    private boolean isUserLogin;

    private UserModel() {
        context = MovieManiacApp.getContext();
        SharedPreferences loginUserPref = context.getSharedPreferences(PREF_CONFIG_USER, Context.MODE_PRIVATE);
        isUserLogin = loginUserPref.getBoolean(PK_IS_USER_LOGIN, false);
        if (isUserLogin) {
            //load the login user info from our data store.
            loginUser = new UserVO();
        } else {
            loginUser = new UserVO();
        }
    }

    public static UserModel getInstance() {
        if (objInstance == null) {
            objInstance = new UserModel();
        }

        return objInstance;
    }

    public void initUserByFacebook(JSONObject obj) {
        try {
            if (obj.has(UserVO.JK_ID))
                loginUser.setFacebookId(obj.getString(UserVO.JK_ID));

            if (obj.has(UserVO.JK_NAME))
                loginUser.setName(obj.getString(UserVO.JK_NAME));

            if (obj.has(UserVO.JK_GENDER))
                loginUser.setGender(obj.getString(UserVO.JK_GENDER));

            String email = null;
            if (obj.has(UserVO.JK_EMAIL)) {
                loginUser.setEmail(obj.getString(UserVO.JK_EMAIL));
            }

            String dateOfBirth = null;
            if (obj.has(UserVO.JK_DOB)) {
                loginUser.setDateOfBirth(obj.getString(UserVO.JK_DOB));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //save loginUser

        SharedPreferences loginUserPref = context.getSharedPreferences(PREF_CONFIG_USER, Context.MODE_PRIVATE);
        loginUserPref.edit().putBoolean(PK_USER_FACEBOOK_CONNECT, true).commit();
        loginUserPref.edit().putBoolean(PK_IS_USER_LOGIN, true).commit();
    }

    public UserVO getLoginUser() {
        return loginUser;
    }

    public void addFacebookCoverUrl(String coverUrl) {
        loginUser.setCoverUrl(coverUrl);
        //Save User Info
    }

    public void addFacebookProfileUrl(String profileUrl) {
        loginUser.setProfileUrl(profileUrl);
        //Save User Info
    }
}
