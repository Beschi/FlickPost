package adapter;

import android.content.Context;
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

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    // Store a member variable for the contacts
    private List<FeedItem> mfeeds;

    // Pass in the contact array into the constructor
    public FeedAdapter(List<FeedItem> feeds) {
        mfeeds = feeds;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView feedname;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            feedname = (TextView) itemView.findViewById(R.id.feedname);
            //messageButton = (Button) itemView.findViewById(R.id.message_button);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View feedView = inflater.inflate(R.layout.feed_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(feedView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        FeedItem feed = mfeeds.get(position);

        if(feed != null) {
            TextView textView = viewHolder.feedname;
            textView.setText(feed.getTitle());
        }

        /*Button button = viewHolder.messageButton;
        if (contact.isOnline()) {
            button.setText("Message");
            button.setEnabled(true);
        }
        else {
            button.setText("Offline");
            button.setEnabled(false);
        }*/
    }

    @Override
    public int getItemCount() {
        return  (mfeeds == null)? 0 : mfeeds.size();
    }
    static class FeedLoadingViewHolder extends  RecyclerView.ViewHolder{
        private ProgressBar pb;
        public  FeedLoadingViewHolder(View v){
            super(v);
            pb = (ProgressBar)v.findViewById(R.id.feed_progressbar);
        }
    }
    static class FeedViewHolder extends RecyclerView.ViewHolder{
        private TextView feedName;
        public FeedViewHolder(View v) {
            super(v);
            feedName = (TextView) v.findViewById(R.id.feedname);
        }
    }

}
