package com.app.news.adapters;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.news.R;
import com.app.news.model.News;
import com.app.news.util.ImageDownloaderTask;

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

		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inf = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inf.inflate(R.layout.news_feed_list_layout, parent,
					false);

			holder = new ViewHolder();
			holder.textTitle = (TextView) convertView
					.findViewById(R.id.textview_newsfeedtitle);
			holder.textDummy = (TextView) convertView
					.findViewById(R.id.textview_newsfeeddummy);
			holder.textSummary = (TextView) convertView
					.findViewById(R.id.textview_newsfeedsummary);
			holder.imageNews = (ImageView) convertView
					.findViewById(R.id.image_newsfeed);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.textTitle.setText(newsArrayList.get(position).getTitle());

		holder.textDummy.setText(newsArrayList.get(position).getSummary());

		if (newsArrayList.get(position).getSummary().length() > 30) {
			holder.textSummary.setText(newsArrayList.get(position).getSummary()
					.substring(0, 30)
					+ "...");
		} else {
			holder.textSummary
					.setText(newsArrayList.get(position).getSummary());
		}

		if (holder.imageNews != null) {
			if (newsArrayList.get(position).getBitmap() == null) {
				if (!newsArrayList.get(position).getImage_src()
						.equalsIgnoreCase("none")) {
					if (cancelPotentialDownload(newsArrayList.get(position)
							.getImage_src(), holder.imageNews)) {
						ImageDownloaderTask task = new ImageDownloaderTask(
								holder.imageNews, newsArrayList, position);
						DownloadedDrawable downloadedDrawable = new DownloadedDrawable(
								task);
						holder.imageNews.setImageDrawable(downloadedDrawable);
						task.execute(newsArrayList.get(position).getImage_src());
					}
				}
			} else {
				holder.imageNews.setImageBitmap(newsArrayList.get(position)
						.getBitmap());
			}
		}

		/*
		 * if (!newsArrayList.get(position).getImage_src()
		 * .equalsIgnoreCase("none")) { try { if (position == 0) {
		 * System.out.println("dwnlding image"); } AsyncTask<String, Void,
		 * Bitmap> x = new RetrieveFeedTask()
		 * .execute(newsArrayList.get(position).getImage_src()); Bitmap bmp =
		 * x.get(); newsImageView.setImageBitmap(bmp);
		 * 
		 * } catch (InterruptedException e) { e.printStackTrace(); } catch
		 * (ExecutionException e) { e.printStackTrace(); } }
		 */

		return convertView;
	}

	static class ViewHolder {
		TextView textTitle;
		TextView textSummary;
		TextView textDummy;
		ImageView imageNews;
	}

	public static ImageDownloaderTask getBitmapDownloaderTask(
			ImageView imageView) {
		if (imageView != null) {
			Drawable drawable = imageView.getDrawable();
			if (drawable instanceof DownloadedDrawable) {
				DownloadedDrawable downloadedDrawable = (DownloadedDrawable) drawable;
				return downloadedDrawable.getBitmapDownloaderTask();
			}
		}
		return null;
	}

	static class DownloadedDrawable extends ColorDrawable {
		private final WeakReference<ImageDownloaderTask> bitmapDownloaderTaskReference;

		public DownloadedDrawable(ImageDownloaderTask bitmapDownloaderTask) {
			super(Color.WHITE);
			bitmapDownloaderTaskReference = new WeakReference<ImageDownloaderTask>(
					bitmapDownloaderTask);
		}

		public ImageDownloaderTask getBitmapDownloaderTask() {
			return bitmapDownloaderTaskReference.get();
		}
	}

	private static boolean cancelPotentialDownload(String url,
			ImageView imageView) {
		ImageDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);

		if (bitmapDownloaderTask != null) {
			String bitmapUrl = bitmapDownloaderTask.url;
			if ((bitmapUrl == null) || (!bitmapUrl.equals(url))) {
				bitmapDownloaderTask.cancel(true);
			} else {
				return false;
			}
		}
		return true;
	}

}