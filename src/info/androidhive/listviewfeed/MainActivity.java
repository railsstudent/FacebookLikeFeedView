package info.androidhive.listviewfeed;

import info.androidhive.listviewfeed.adapter.EndlessFeedListAdapter;
import info.androidhive.listviewfeed.app.AppController;
import info.androidhive.listviewfeed.data.FeedItem;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

public class MainActivity extends ActionBarActivity {
	private static final String TAG = MainActivity.class.getSimpleName();
	private ListView listView;
	private EndlessFeedListAdapter listAdapter;
	private List<FeedItem> feedItems;
	private String URL_FEED = "http://api.androidhive.info/feed/feed.json";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.list);

		feedItems = new ArrayList<FeedItem>();

		listAdapter = new EndlessFeedListAdapter(this, feedItems);
		listView.setAdapter(listAdapter);
		
		// These two lines not needed,
		// just to get the look of facebook (changing background color & hiding the icon)
//		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b5998")));
//		getSupportActionBar().setIcon(
//				   new ColorDrawable(getResources().getColor(android.R.color.transparent)));

		// We first check for cached request, will move to endlessfeedlistadapter later
//		Cache cache = AppController.getInstance().getRequestQueue().getCache();
//		Entry entry = cache.get(URL_FEED);
//		if (entry != null) {
//			// fetch the data from cache
//			try {
//				String data = new String(entry.data, "UTF-8");
//				try {
//					parseJsonFeed(new JSONObject(data));
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//
//		} else {
//			// making fresh volley request and getting json
//			JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
//					URL_FEED, null, new Response.Listener<JSONObject>() {
//
//						@Override
//						public void onResponse(JSONObject response) {
//							VolleyLog.d(TAG, "Response: " + response.toString());
//							if (response != null) {
//								parseJsonFeed(response);
//							}
//						}
//					}, new Response.ErrorListener() {
//
//						@Override
//						public void onErrorResponse(VolleyError error) {
//							VolleyLog.d(TAG, "Error: " + error.getMessage());
//						}
//					});
//
//			// Adding request to volley request queue
//			AppController.getInstance().addToRequestQueue(jsonReq);
//		}

	}

	/**
	 * Parsing json reponse and passing the data to feed view list adapter
	 * */
//	private void parseJsonFeed(JSONObject response) {
//		try {
//			JSONArray feedArray = response.getJSONArray("feed");
//
//			for (int i = 0; i < feedArray.length(); i++) {
//				JSONObject feedObj = (JSONObject) feedArray.get(i);
//
//				FeedItem item = new FeedItem();
//				item.setId(feedObj.getInt("id"));
//				item.setName(feedObj.getString("name"));
//
//				// Image might be null sometimes
//				String image = feedObj.isNull("image") ? null : feedObj
//						.getString("image");
//				item.setImage(image);
//				item.setStatus(feedObj.getString("status"));
//				item.setProfilePic(feedObj.getString("profilePic"));
//				item.setTimeStamp(feedObj.getString("timeStamp"));
//
//				// url might be null sometimes
//				String feedUrl = feedObj.isNull("url") ? null : feedObj
//						.getString("url");
//				item.setUrl(feedUrl);
//
//				feedItems.add(item);
//			}
//
//			// notify data changes to list adapter
//			listAdapter.notifyDataSetChanged();
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
