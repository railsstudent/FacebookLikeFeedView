package info.androidhive.listviewfeed.adapter;

import info.androidhive.listviewfeed.FeedImageView;
import info.androidhive.listviewfeed.R;
import info.androidhive.listviewfeed.app.AppController;
import info.androidhive.listviewfeed.data.FeedItem;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class FeedListAdapter extends ArrayAdapter<FeedItem> {	
	private Context context;
	private LayoutInflater inflater;
	private List<FeedItem> feedItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	private int resourceId;
	
	public FeedListAdapter(Context context, int resourceId, List<FeedItem> feedItems) {
		super(context, resourceId, feedItems);
		this.context = context;
		this.resourceId = resourceId;
		this.feedItems = feedItems;
	}

	@Override
	public int getCount() {
		return feedItems.size();
	}

	@Override
	public FeedItem getItem(int location) {
		return feedItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null) {
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		// use View Holder pattern
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = inflater.inflate(resourceId, /*R.layout.feed_item,*/ null);
			
			viewHolder = new ViewHolder();
			viewHolder.name = (TextView) convertView.findViewById(R.id.name);
			viewHolder.timestamp = (TextView) convertView
					.findViewById(R.id.timestamp);
			viewHolder.statusMsg = (TextView) convertView
					.findViewById(R.id.txtStatusMsg);
			viewHolder.url = (TextView) convertView.findViewById(R.id.txtUrl);
			viewHolder.profilePic = (NetworkImageView) convertView
					.findViewById(R.id.profilePic);
			viewHolder.feedImageView = (FeedImageView) convertView
					.findViewById(R.id.feedImage1);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (imageLoader == null) {
			imageLoader = AppController.getInstance().getImageLoader();
		}

		FeedItem item = feedItems.get(position);

		viewHolder.name.setText(item.getName());

		// Converting timestamp into x ago format
		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
				Long.parseLong(item.getTimeStamp()),
				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
		viewHolder.timestamp.setText(timeAgo);

		// Chcek for empty status message
		if (!TextUtils.isEmpty(item.getStatus())) {
			viewHolder.statusMsg.setText(item.getStatus());
			viewHolder.statusMsg.setVisibility(View.VISIBLE);
		} else {
			// status is empty, remove from view
			viewHolder.statusMsg.setVisibility(View.GONE);
		}

		// Checking for null feed url
		if (item.getUrl() != null) {
			viewHolder.url.setText(Html.fromHtml("<a href=\"" + item.getUrl() + "\">"
					+ item.getUrl() + "</a> "));

			// Making url clickable
			viewHolder.url.setMovementMethod(LinkMovementMethod.getInstance());
			viewHolder.url.setVisibility(View.VISIBLE);
		} else {
			// url is null, remove from the view
			viewHolder.url.setVisibility(View.GONE);
		}

		// user profile pic
		viewHolder.profilePic.setImageUrl(item.getProfilePic(), imageLoader);

		// Feed image
		if (item.getImage() != null) {
			viewHolder.feedImageView.setImageUrl(item.getImage(), imageLoader);
			viewHolder.feedImageView.setVisibility(View.VISIBLE);
			viewHolder.feedImageView
					.setResponseObserver(new FeedImageView.ResponseObserver() {
						@Override
						public void onError() {
						}

						@Override
						public void onSuccess() {
						}
					});
		} else {
			viewHolder.feedImageView.setVisibility(View.GONE);
		}

		return convertView;
	}
	
	private static class ViewHolder {
		TextView name;
		TextView timestamp;
		TextView statusMsg;
		TextView url;
		NetworkImageView profilePic;
		FeedImageView feedImageView;
	}

}
