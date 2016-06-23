package com.android.app.flickPost;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by Developer on 13/5/2016.
 */
public class ProfileFragment extends Fragment {
    private static final String STARTING_TEXT = "Four Buttons Bottom Navigation";

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance(String text) {
        Bundle args = new Bundle();
        args.putString(STARTING_TEXT, text);

        ProfileFragment sampleFragment = new ProfileFragment();
        sampleFragment.setArguments(args);

        return sampleFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile,
                container, false);
        return view;
    }
}