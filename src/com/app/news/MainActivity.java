package com.app.news;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.widget.ExpandableListView;

import com.app.news.adapters.LanguageListAdapter;
import com.app.news.database.CustomDbAdapter;
import com.app.news.fragment.SlidingMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnCloseListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenListener;

public class MainActivity extends FragmentActivity {

	private SlidingMenu menu;
	Context context;
	ExpandableListView languageListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		CustomDbAdapter dbManager = CustomDbAdapter.getInstance(getBaseContext());
		
		context = this;
		menu = new SlidingMenu(this);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		menu.setMenu(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new SlidingMenuFragment()).commit();
		menu.setOnOpenListener(new OnOpenListener() {

			@Override
			public void onOpen() {
			}
		});

		menu.setOnCloseListener(new OnCloseListener() {

			@Override
			public void onClose() {
			}
		});

		languageListView = (ExpandableListView) findViewById(R.id.list_languages);

		String[] languageArray = getResources().getStringArray(
				R.array.language_list);
		List<String> languageArrayList = Arrays.asList(languageArray);

		LanguageListAdapter languageListAdapter = new LanguageListAdapter(
				context, languageArrayList);
		
		languageListView.setAdapter(languageListAdapter);

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		int width = metrics.widthPixels;

		if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
			languageListView.setIndicatorBounds(width - 150, width);
		} else {
			languageListView.setIndicatorBoundsRelative(width - 150, width);
		}

	}

}