package info.androidhive.listviewfeed.adapter;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.listviewfeed.R;
import info.androidhive.listviewfeed.data.FeedItem;
import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;

import com.commonsware.cwac.endless.EndlessAdapter;

// http://droidista.blogspot.hk/2011/04/using-cwacs-endlessadapter-with-custom.html
public class EndlessFeedListAdapter extends EndlessAdapter {

	private static final int BATCH_SIZE = 8;
	private static final int NUM_RECORDS = 200;
	
	private RotateAnimation rotate=null;
	private View pendingView=null;

	private List<FeedItem> tmpBuffer;
	private int offset;	
	private List<FeedItem> dummyData; 

	private boolean isFakeDelay = true;
	
	public EndlessFeedListAdapter(Context ctxt, List<FeedItem> wrapped) {
		
		super(new FeedListAdapter(ctxt, R.layout.feed_item, wrapped));
		rotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
			            0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotate.setDuration(600);
		rotate.setRepeatMode(Animation.RESTART);
		rotate.setRepeatCount(Animation.INFINITE);
		
		tmpBuffer = new ArrayList<FeedItem>();
		offset = -1;
				
		// show initial number of items
		populateDummyData(NUM_RECORDS);
		initData();
	}
	
	@Override
	protected View getPendingView(ViewGroup parent) {
	    View row=LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item,  null);
	    
	    pendingView=row.findViewById(R.id.throbber);
	    pendingView.setVisibility(View.VISIBLE);
	    startProgressAnimation();
	    
	    return(row);
	}
	
	@Override
	protected boolean cacheInBackground() throws Exception {
		if (isFakeDelay) {
			SystemClock.sleep(1000);       // prepend to do some work
		}
		
		tmpBuffer.clear();
		if (dummyData.size() - offset > 0) {
			// has item not retrieved
			int bound = Math.min(offset + BATCH_SIZE, dummyData.size());
			for (int i = offset; i < bound; i++) {
				tmpBuffer.add(dummyData.get(i));
			}
			offset = bound;
			if (offset < dummyData.size()) {
				return true;
			}
			return false;
		} else {
			return false;
		}
	}

	@Override
	protected void appendCachedData() {

		@SuppressWarnings("unchecked")
		ArrayAdapter<FeedItem> buffer = (ArrayAdapter<FeedItem>) this.getWrappedAdapter();
		for (FeedItem item : tmpBuffer) {
			buffer.add(item);
		}
	}
	
	 void startProgressAnimation() {
		if (pendingView!=null) {
		    pendingView.startAnimation(rotate);
		}
    }
	 
	private void initData() {
		
		@SuppressWarnings("unchecked")
		ArrayAdapter<FeedItem> buffer = (ArrayAdapter<FeedItem>) this.getWrappedAdapter();
		for (int i = 0; i < BATCH_SIZE; i++) {
			buffer.add(dummyData.get(i));
		}
		offset = BATCH_SIZE;
	}

	private void populateDummyData(int numData) {
		dummyData = new ArrayList<FeedItem>();

		
//		{
//            "id": 1,
//            "name": "National Geographic Channel",
//            "image": "http://api.androidhive.info/feed/img/cosmos.jpg",
//            "status": "\"Science is a beautiful and emotional human endeavor,\" says Brannon Braga, executive producer and director. \"And Cosmos is all about making science an experience.\"",
//            "profilePic": "http://api.androidhive.info/feed/img/nat.jpg",
//            "timeStamp": "1403375851930",
//            "url": null
//        },
		
		for (int i = 0; i < numData; i++) {
		
			FeedItem fi = new FeedItem();
			fi.setId(i+1);
			fi.setName("National Geographic Channel " + i);
			fi.setImage("http://api.androidhive.info/feed/img/cosmos.jpg");
			fi.setStatus("\"Science is a beautiful and emotional human endeavor,\" says Brannon Braga, executive producer and director. \"And Cosmos is all about making science an experience." 
						+ i + "\"");
			fi.setProfilePic("http://api.androidhive.info/feed/img/nat.jpg");
			fi.setTimeStamp("1403375851930");
			fi.setUrl(null);
			dummyData.add(fi);
		}
	}
}
