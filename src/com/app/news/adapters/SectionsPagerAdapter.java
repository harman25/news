package com.app.news.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.news.fragment.TabContentFragment;

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

	Resources res;
	Context context;
	ArrayList<String> categories;

	public SectionsPagerAdapter(FragmentManager fm, Resources res, Context context, ArrayList<String> categories) {
		super(fm);
		this.res = res;
		this.context = context;
		this.categories = categories;
	}

	@Override
	public Fragment getItem(int position) {
		return new TabContentFragment(categories.get(position), context);
	}

	@Override
	public int getCount() {
		return categories.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return categories.get(position);
	}
}
