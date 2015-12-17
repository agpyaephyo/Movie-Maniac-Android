package net.aung.moviemaniac.data.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.data.vos.UserVO;
import net.aung.moviemaniac.events.UserEvent;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by aung on 12/17/15.
 */
public class UserModel {

    public static final String PREF_CONFIG_USER = "CONFIG_USER";
    public static final String PK_CONNECT_TYPE = "PK_CONNECT_TYPE";
    public static final String PK_LOGIN_USER_ID = "PK_LOGIN_USER_ID";

    private static final int CONNECT_TYPE_NONE = 1;
    private static final int CONNECT_TYPE_FACEBOOK = 2;
    private static final int CONNECT_TYPE_GOOGLE = 3;

    private static UserModel objInstance;

    private Context context;
    private UserVO loginUser;
    private int connectType;
    private Realm realm;

    private UserModel() {
        context = MovieManiacApp.getContext();
        realm = Realm.getInstance(context);
        realm.beginTransaction();

        SharedPreferences loginUserPref = context.getSharedPreferences(PREF_CONFIG_USER, Context.MODE_PRIVATE);
        connectType = loginUserPref.getInt(PK_CONNECT_TYPE, CONNECT_TYPE_NONE);
        if (connectType != CONNECT_TYPE_NONE) {
            //load the login user info from our data store.
            String loginUserId = loginUserPref.getString(PK_LOGIN_USER_ID, null);
            if (loginUserId != null) {
                RealmResults<UserVO> results = realm.where(UserVO.class).equalTo("facebookId", loginUserId).findAll();
                if (results.size() > 0) {
                    loginUser = results.get(0);
                } else {
                    //PROBLEM : Even after login, no user data in realm datastore.
                    loginUser = realm.createObject(UserVO.class);
                }
            } else {
                //PROBLEM : Even CONNECT_TYPE is not none, no user id in share preferences.
                loginUser = realm.createObject(UserVO.class);
            }
        } else {
            loginUser = realm.createObject(UserVO.class);
        }
        realm.commitTransaction();

        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    public static UserModel getInstance() {
        if (objInstance == null) {
            objInstance = new UserModel();
        }

        return objInstance;
    }

    public void initUserByFacebook(JSONObject obj) {
        realm.beginTransaction();
        try {
            if (obj.has(UserVO.JK_ID))
                loginUser.setFacebookId(obj.getString(UserVO.JK_ID));

            if (obj.has(UserVO.JK_NAME))
                loginUser.setName(obj.getString(UserVO.JK_NAME));

            if (obj.has(UserVO.JK_GENDER))
                loginUser.setGender(obj.getString(UserVO.JK_GENDER));

            if (obj.has(UserVO.JK_EMAIL)) {
                loginUser.setEmail(obj.getString(UserVO.JK_EMAIL));
            }

            if (obj.has(UserVO.JK_DOB)) {
                loginUser.setDateOfBirth(obj.getString(UserVO.JK_DOB));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //save loginUser
        realm.commitTransaction();

        SharedPreferences loginUserPref = context.getSharedPreferences(PREF_CONFIG_USER, Context.MODE_PRIVATE);
        loginUserPref.edit().putInt(PK_CONNECT_TYPE, CONNECT_TYPE_FACEBOOK).apply();
        loginUserPref.edit().putString(PK_LOGIN_USER_ID, loginUser.getFacebookId()).apply();
    }

    public UserVO getLoginUser() {
        return loginUser;
    }

    public boolean isUserLogin() {
        return connectType != CONNECT_TYPE_NONE;
    }

    public void addFacebookCoverUrl(String coverUrl) {
        realm.beginTransaction();
        loginUser.setCoverUrl(coverUrl);
        realm.commitTransaction();
        //Save User Info
    }

    public void addFacebookProfileUrl(String profileUrl) {
        realm.beginTransaction();
        loginUser.setProfileUrl(profileUrl);
        realm.commitTransaction();
        //Save User Info
    }

    public void onEventMainThread(UserEvent.LogoutEvent event) {
        SharedPreferences loginUserPref = context.getSharedPreferences(PREF_CONFIG_USER, Context.MODE_PRIVATE);
        loginUserPref.edit().putInt(PK_CONNECT_TYPE, CONNECT_TYPE_NONE).apply();
        loginUserPref.edit().putString(PK_LOGIN_USER_ID, null).apply();

        realm.beginTransaction();
        realm.allObjects(UserVO.class).clear();
        loginUser = realm.createObject(UserVO.class);
        realm.commitTransaction();
    }
}
