package com.app.news;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.news.adapters.NewsListAdapter;
import com.app.news.database.CustomDbAdapter;
import com.app.news.database.helpers.NewsListHelper;
import com.app.news.model.News;

public class NewspaperActivity extends Activity {

	Context context;
	ListView feedList;
	ArrayList<News> newsData;
	NewsListAdapter newsListAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newspaper);

		context = this;

		CustomDbAdapter dbManager = CustomDbAdapter
				.getInstance(getBaseContext());
		NewsListHelper newsListHelper = new NewsListHelper(dbManager);

		ArrayList<News> newsData = null;
		try {
			newsData = newsListHelper.getAllNews();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		feedList = (ListView) findViewById(R.id.listview_newsarticles);

		newsListAdapter = new NewsListAdapter(newsData, context);

		feedList.setAdapter(newsListAdapter);

		feedList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView textViewTitle = ((TextView) view.findViewById(R.id.textview_newsfeedname));
				TextView textViewSummary = ((TextView) view.findViewById(R.id.textview_newsfeeddummy));
				ImageView imageNews = (ImageView) view.findViewById(R.id.image_newsfeed);
				Bitmap bitmap = ((BitmapDrawable)imageNews.getDrawable()).getBitmap();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); 
				byte[] b = baos.toByteArray();

				String title = textViewTitle.getText().toString();
            	String summary = textViewSummary.getText().toString();
            	Intent intent = new Intent(context, NewsDetailActivity.class);
				intent.putExtra("title", title);
				intent.putExtra("summary", summary);
				intent.putExtra("picture", b);
				startActivity(intent);

			}
		});

	}

}
