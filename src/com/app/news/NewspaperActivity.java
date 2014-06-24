package com.app.news;

import java.text.ParseException;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.app.news.adapters.SectionsPagerAdapter;
import com.app.news.database.CustomDbAdapter;
import com.app.news.database.helpers.NewsListHelper;

public class NewspaperActivity extends FragmentActivity implements
		ActionBar.TabListener {

	Context context;
	ActionBar actionBar;
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	ArrayList<String> categoryList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newspaper_home);

		context = this;
		actionBar = getActionBar();

		CustomDbAdapter dbManager = CustomDbAdapter
				.getInstance(getBaseContext());
		NewsListHelper newsListHelper = new NewsListHelper(dbManager);

		categoryList = null;

		try {
			categoryList = (ArrayList<String>) newsListHelper.getCategories();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (categoryList != null) {

			mSectionsPagerAdapter = new SectionsPagerAdapter(
					getSupportFragmentManager(), getResources(), context,
					categoryList);

			// Set up the ViewPager with the sections adapter.
			mViewPager = (ViewPager) findViewById(R.id.testfragment_frame);
			mViewPager.setAdapter(mSectionsPagerAdapter);

			for (String s : categoryList) {
				System.out.println("category is " + s);
				actionBar.addTab(actionBar.newTab().setText(s)
						.setTabListener(this));
			}
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
		}

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// mViewPager.removeViewAt(tab.getPosition());
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		categoryList = null;
	}

}