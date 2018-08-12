package net.aung.moviemaniac.fragments;

import android.arch.lifecycle.LifecycleFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.android.gms.analytics.Tracker;

import net.aung.moviemaniac.MovieManiacApp;
import net.aung.moviemaniac.utils.GAUtils;

/**
 * Created by aung on 12/15/15.
 */
public abstract class BaseFragment extends LifecycleFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            readArguments(bundle);
        }
    }

    protected void readArguments(Bundle bundle) {

    }

    @Override
    public void onStart() {
        super.onStart();
        sendScreenHit();
    }

    protected abstract void sendScreenHit();
}
