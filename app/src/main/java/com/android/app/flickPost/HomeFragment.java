package com.android.app.flickPost;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import Helper.LazyLoader;
import adapter.FeedAdapter;
import app.AppController;
import model.FeedItem;

import static Helper.EndlessRecyclerOnScrollListener.TAG;


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
        rcv  =  (RecyclerView)view.findViewById(R.id.feedView);
        rcv.setHasFixedSize(true);
        //--------------------------------------------------------------------
        fetchFreshData("4040",1,0);
        /*for (int i = 0; i < 30; i++) {
            FeedItem user = new FeedItem();
            user.setTitle("Name " + i);
            feedItems.add(user);
        }*/
        //---------------------------------------------------------------
        llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rcv.setLayoutManager(llm);
        feedAdapter = new FeedAdapter(rcv, feedItems);
        rcv.setAdapter(feedAdapter);
        feedAdapter.notifyDataSetChanged();
        feedAdapter.setLazyLoader(new LazyLoader() {
            @Override
            public void onLoad() {
                Log.e("haint", "Load More");
                feedItems.add(null);
                feedAdapter.notifyItemInserted(feedItems.size() - 1);

                //Load more data for reyclerview
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("haint", "Load More 2");

                        //Remove loading item
                        feedItems.remove(feedItems.size() - 1);
                        feedAdapter.notifyItemRemoved(feedItems.size());

                        //Load data
                        int index = feedItems.size();
                        index = index/10;
                       // fetchFreshData("4040",index,0);
                        /*int end = index + 20;
                        for (int i = index; i < end; i++) {
                            FeedItem fi = new FeedItem();
                            fi.setTitle("Name " + i);
                            feedItems.add(fi);
                        }*/
                       // feedAdapter.notifyDataSetChanged();
                        feedAdapter.setLoaded();
                    }
                }, 5000);
            }
        });
        return view;
    }
    /**
     * Parsing json response and passing the data to feed view list adapter
     * */
    private void parseJsonFeed(JSONObject response) {
        Log.d("Simple 1","Received Response"+response);
        try {
            JSONArray feedArray = response.getJSONArray("feed");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);
                FeedItem item = new FeedItem();
                item.setTitle(feedObj.getString("name"));

                feedItems.add(item);
            }
            // notify data changes to list adapater
            feedAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            // making fresh volley request and getting json
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());

                    if (response != null) {
                        parseJsonFeed(response);
                    }
                }
            }, new Response.ErrorListener() {


                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.d("sdfd",error.toString());

                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
        }
    }
}