package com.android.app.flickPost;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Helper.EndlessRecyclerOnScrollListener;
import adapter.FriendAdapter;
import model.Friends;

/**
 * Created by Developer on 13/5/2016.
 */
public class FriendFragment extends Fragment {
    private static final String STARTING_TEXT = "Four Buttons Bottom Navigation";

    private List<Friends> friendsList=new ArrayList<>();
    private RecyclerView recyclerView;
    private FriendAdapter friendAdapter;

    // on scroll

    private static int current_page = 1;

    private int ival = 1;
    private int loadLimit = 5;

    public FriendFragment() {
    }

    public static FriendFragment newInstance(String text) {
        Bundle args = new Bundle();
        args.putString(STARTING_TEXT, text);

        FriendFragment sampleFragment = new FriendFragment();
        sampleFragment.setArguments(args);

        return sampleFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initialize dataset
        loadMoreData(current_page);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_friends,container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_friend);

        friendAdapter= new FriendAdapter(friendsList);
        LinearLayoutManager mLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

       /* recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                Log.d("onLoadMore : ",Integer.toString(current_page));
                loadMoreData(current_page);
            }
        });*/

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(friendAdapter);

        return view;
    }

    private void loadMoreData(int current_page) {
        loadLimit = ival + 5;
        for (int i = ival; i <= loadLimit; i++) {
            Friends friends = new Friends("Friend-"+ Integer.toString(i), "123");
            friendsList.add(friends);
        }
        //friendAdapter.notifyDataSetChanged();
    }
}