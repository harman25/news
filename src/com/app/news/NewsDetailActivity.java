package com.app.news;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_detail);
		String title = getIntent().getStringExtra("title");
		String summary = getIntent().getStringExtra("summary");

		TextView titleView = (TextView) findViewById(R.id.textview_title);
		TextView summaryView = (TextView) findViewById(R.id.textview_summary);
		titleView.setText(title);
		summaryView.setText(summary);
		byte[] b = getIntent().getByteArrayExtra("picture");

		Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
		ImageView image = (ImageView) findViewById(R.id.image_newsdetail);

		image.setImageBitmap(bmp);

	}

}
