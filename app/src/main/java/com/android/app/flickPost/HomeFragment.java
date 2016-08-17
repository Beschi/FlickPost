package com.android.app.flickPost;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import Helper.EndlessRecyclerOnScrollListener;
import adapter.FeedAdapter;
import app.AppController;
import model.FeedItem;


/**
 * Created by Developer on 13/5/2016.
 */
public class HomeFragment extends Fragment {
    private RecyclerView rcv;
    private FeedAdapter feedAdapter;
    private LinearLayoutManager llm;
    private List<FeedItem> feedItems = new ArrayList<>();
    public HomeFragment() {
    }

    public static HomeFragment newInstance(String text) {
        Bundle args = new Bundle();

        HomeFragment sampleFragment = new HomeFragment();
        sampleFragment.setArguments(args);

        return sampleFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home,container, false);

        RecyclerView rvItems = (RecyclerView)view.findViewById(R.id.feedView);

        fetchFreshData("4040",0,0);
        feedAdapter = new FeedAdapter(feedItems);
        rvItems.setAdapter(feedAdapter);
        feedAdapter.notifyDataSetChanged();

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvItems.setLayoutManager(linearLayoutManager);

        rvItems.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                fetchFreshData("4040",page,0);
                int curSize = feedAdapter.getItemCount();
                //allContacts.addAll(moreContacts);
                feedAdapter.notifyItemRangeInserted(curSize, feedItems.size() - 1);
            }
        });
        return view;
    }

    /**
     *
     * @param userId
     * @param index
     * @param isTimeline
     */
    private  void fetchFreshData(String userId, int index, int isTimeline){

        final String url = "http://flickpost.net/api/feeds/getFeeds/"+userId+"/"+index+"/"+isTimeline;
        Log.d("Info","URL :"+url);

        // We first check for cached request
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);

        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonFeed(new JSONArray(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {

            JsonArrayRequest jsonReq = new JsonArrayRequest(url,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            if (response != null) {
                                parseJsonFeed(response);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
        }
    }

    /**
     * Parsing json response and passing the data to feed view list adapter
     * */
    private void parseJsonFeed(JSONArray response) {
        Log.d("Simple 1","Received Response"+response);
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject feedObj = response.getJSONObject(i);
                FeedItem item = new FeedItem();
                item.setTitle(feedObj.getString("title"));
                item.setAvatar(feedObj.getString("avatar"));
                item.setCreatedDate(feedObj.getString("createdDate"));
                feedItems.add(item);
            }
            // notify data changes to list adapater
            feedAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}