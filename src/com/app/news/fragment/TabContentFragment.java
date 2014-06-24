package com.app.news.fragment;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.news.NewsDetailActivity;
import com.app.news.R;
import com.app.news.adapters.NewsListAdapter;
import com.app.news.database.CustomDbAdapter;
import com.app.news.database.helpers.NewsListHelper;
import com.app.news.model.News;

public class TabContentFragment extends Fragment {
	private String mText;
	ListView feedList;
	ArrayList<News> newsData;
	NewsListAdapter newsListAdapter;
	Context context;

	public TabContentFragment(String text, Context context) {
		this.mText = text;
		this.context = context;
	}

	public String getText() {
		return mText;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fragView = inflater.inflate(R.layout.news_list_fragment,
				container, false);
		
		TextView textView = (TextView) fragView.findViewById(R.id.testfragmenttext);
		textView.setText(getText());

		CustomDbAdapter dbManager = CustomDbAdapter
				.getInstance(getActivity().getBaseContext());
		NewsListHelper newsListHelper = new NewsListHelper(dbManager);

		ArrayList<News> newsData = null;
		try {
			newsData = newsListHelper.getNewsFromCategory(getText());
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		feedList = (ListView) fragView.findViewById(R.id.listview_newsarticles);

		newsListAdapter = new NewsListAdapter(newsData, context);

		feedList.setAdapter(newsListAdapter);

		feedList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView textViewTitle = ((TextView) view
						.findViewById(R.id.textview_newsfeedtitle));
				TextView textViewSummary = ((TextView) view
						.findViewById(R.id.textview_newsfeeddummy));
				ImageView imageNews = (ImageView) view
						.findViewById(R.id.image_newsfeed);
				Bitmap bitmap = ((BitmapDrawable) imageNews.getDrawable())
						.getBitmap();
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

		return fragView;
	}
}