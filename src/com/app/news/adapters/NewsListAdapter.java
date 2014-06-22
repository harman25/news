package com.app.news.adapters;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.news.R;
import com.app.news.model.News;

public class NewsListAdapter extends BaseAdapter {

	List<News> newsArrayList;
	Context context;

	public NewsListAdapter(List<News> newsArrayList, Context context) {
		this.newsArrayList = newsArrayList;
		this.context = context;
	}

	@Override
	public int getCount() {
		return newsArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inf = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inf.inflate(R.layout.feed_list_layout, parent, false);
		}

		TextView usernameText = (TextView) convertView
				.findViewById(R.id.textview_newsfeedname);
		usernameText.setText(newsArrayList.get(position).getTitle());

		TextView dummyText = (TextView) convertView
				.findViewById(R.id.textview_newsfeeddummy);
		dummyText.setText(newsArrayList.get(position).getSummary());
		
		TextView summaryText = (TextView) convertView
				.findViewById(R.id.textview_newsfeedsummary);
		if (newsArrayList.get(position).getSummary().length() > 30) {
			summaryText.setText(newsArrayList.get(position).getSummary()
					.substring(0, 30)
					+ "...");
		} else {
			summaryText.setText(newsArrayList.get(position).getSummary());
		}

		ImageView profileImageView = (ImageView) convertView
				.findViewById(R.id.image_newsfeed);

		if (!newsArrayList.get(position).getImage_src()
				.equalsIgnoreCase("none")) {
			URL url = null;
			try {
				System.out.println("img url is "
						+ newsArrayList.get(position).getImage_src());
				url = new URL(newsArrayList.get(position).getImage_src());

				AsyncTask<String, Void, Bitmap> x = new RetrieveFeedTask()
						.execute(newsArrayList.get(position).getImage_src());
				Bitmap bmp = x.get();
				profileImageView.setImageBitmap(bmp);

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return convertView;
	}

}

class RetrieveFeedTask extends AsyncTask<String, Void, Bitmap> {

	private Exception exception;

	protected Bitmap doInBackground(String... urls) {
		try {
			URL url = new URL(urls[0]);
			Bitmap bmp = BitmapFactory.decodeStream(url.openConnection()
					.getInputStream());
			return bmp;
		} catch (Exception e) {
			this.exception = e;
			return null;
		}
	}

}