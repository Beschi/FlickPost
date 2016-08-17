package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.app.flickPost.R;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import app.AppController;
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
        public TextView title;
        public NetworkImageView avatar;
        public TextView timeAgo;
        public ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            if (imageLoader == null)
                imageLoader = AppController.getInstance().getImageLoader();

            title = (TextView) itemView.findViewById(R.id.title);
            avatar = (NetworkImageView) itemView.findViewById(R.id.profilePic);
            timeAgo = (TextView)itemView.findViewById(R.id.date_time);
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
            //title
            TextView textView = viewHolder.title;
            textView.setText(feed.getTitle());

            TextView timeAgo=viewHolder.timeAgo;
            timeAgo.setText(getDate(feed.getCreatedDate()));
            // user profile pic
            ImageLoader imageLoader = viewHolder.imageLoader;
            NetworkImageView avatar= viewHolder.avatar;
            avatar.setImageUrl(feed.getAvatar(), imageLoader);
        }
    }

    private String getDate(String OurDate)
    {
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(OurDate);

            SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy HH:mm"); //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault());
            OurDate = dateFormatter.format(value);

            //Log.d("OurDate", OurDate);
        }
        catch (Exception e)
        {
            OurDate = "00-00-0000 00:00";
        }
        return OurDate;
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

}
