package adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.app.flickPost.R;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;


import java.util.List;

import app.AppController;
import model.FeedItem;

/**
 * Created by beschi.agent@gmail.com on 28/7/2016.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {
    private List<FeedItem> feeds;

    public FeedAdapter(List<FeedItem> items){
        this.feeds = items;
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View lstItmes = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_item, parent, false);
        return  new FeedViewHolder(lstItmes);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {
        FeedItem fi = feeds.get(position);
        holder.feedName.setText(fi.getName());
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }

    static class FeedViewHolder extends RecyclerView.ViewHolder{
        private TextView feedName;
        public FeedViewHolder(View itemView) {
            super(itemView);
            feedName = (TextView) itemView.findViewById(R.id.feedname);
        }
    }

}
