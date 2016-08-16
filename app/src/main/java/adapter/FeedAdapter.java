package adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.flickPost.R;
import java.util.List;

import Helper.LazyLoader;
import model.FeedItem;

/**
 * Created by beschi.agent@gmail.com on 28/7/2016.
 */

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FeedItem> feeds;
    private RecyclerView rcv;
    private LazyLoader lazyLoader;

    private boolean isLoading;
    private final int vtFeed = 0; // View Type Feed
    private final int vtLoading = 1; // View Type Loading

    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;




    public void setLazyLoader(LazyLoader lazyLoader) {
        this.lazyLoader = lazyLoader;
    }

    public FeedAdapter(RecyclerView rcv, final List<FeedItem> items){
        final LinearLayoutManager  llm = (LinearLayoutManager)rcv.getLayoutManager();
        rcv.addOnScrollListener( new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                /*totalItemCount = llm.getItemCount();
                lastVisibleItem = llm.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (lazyLoader != null) {
                        lazyLoader.onLoad();
                    }
                    isLoading = true;
                }*/
                if(llm.findLastCompletelyVisibleItemPosition() == items.size() -1){
                    //bottom of list!
                    if (lazyLoader != null) {
                        lazyLoader.onLoad();
                    }
                    isLoading = true;
                }
            }
        });
        this.rcv = rcv;
        this.feeds = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == vtFeed){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, parent, false);
            return  new FeedViewHolder(view);
        } else if (viewType == vtLoading) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_loading, parent, false);
            return new FeedLoadingViewHolder(view);
        }else {
            throw new IllegalStateException("Invalid type, this type ot items " + viewType + " can't be handled");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof  FeedViewHolder){
            FeedItem fi = feeds.get(position);
            if(fi != null) {
                FeedViewHolder fvh = (FeedViewHolder) holder;
                fvh.feedName.setText(fi.getTitle());
            }
        } else if (holder instanceof FeedLoadingViewHolder){
            FeedLoadingViewHolder flvh = (FeedLoadingViewHolder)holder;
            flvh.pb.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return  (feeds == null)? 0 : feeds.size();
    }


    public void setLoaded() {
        isLoading = false;
    }

    static class FeedViewHolder extends RecyclerView.ViewHolder{
        private TextView feedName;
        public FeedViewHolder(View v) {
            super(v);
            feedName = (TextView) v.findViewById(R.id.feedname);
        }
    }

    static class FeedLoadingViewHolder extends  RecyclerView.ViewHolder{
        private ProgressBar pb;
        public  FeedLoadingViewHolder(View v){
            super(v);
            pb = (ProgressBar)v.findViewById(R.id.feed_progressbar);
        }
    }
}
