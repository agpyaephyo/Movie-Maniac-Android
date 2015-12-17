package net.aung.moviemaniac.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by aung on 12/15/15.
 */
public class BaseFragment extends Fragment {

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
}
