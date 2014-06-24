package com.app.news.util;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.app.news.adapters.NewsListAdapter;
import com.app.news.model.News;

public class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {
	private final WeakReference<ImageView> imageViewReference;
	public String url;
	List<News> newsArrayList;
	int position;

	public ImageDownloaderTask(ImageView imageView, List<News> newsArrayList, int position) {
		imageViewReference = new WeakReference<ImageView>(imageView);
		this.newsArrayList = newsArrayList;
		this.position = position;
	}

	@Override
	// Actual download method, run in the task thread
	protected Bitmap doInBackground(String... params) {
		// params comes from the execute() call: params[0] is the url.
		// return downloadBitmap(params[0]);
		url = params[0];
		URL url = null;
		try {
			url = new URL(params[0]);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		Bitmap bmp = null;
		try {
			bmp = BitmapFactory.decodeStream(url.openConnection()
					.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bmp;
	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {
		if (isCancelled()) {
			bitmap = null;
		}

		if (imageViewReference != null) {

			ImageView imageView = imageViewReference.get();
			if (imageView != null) {
				ImageDownloaderTask bitmapDownloaderTask = NewsListAdapter
						.getBitmapDownloaderTask(imageView);
				if (this == bitmapDownloaderTask) {
					imageView.setImageBitmap(bitmap);
					newsArrayList.get(position).setBitmap(bitmap);
				}
			}
		}
	}
}