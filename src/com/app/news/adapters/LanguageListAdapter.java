package com.app.news.adapters;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.news.NewspaperActivity;
import com.app.news.R;

public class LanguageListAdapter extends BaseExpandableListAdapter implements
		OnClickListener {

	Context context;
	private LayoutInflater inf;
	List<String> languageArrayList;

	public LanguageListAdapter(Context context, List<String> languageArrayList) {
		this.context = context;
		this.languageArrayList = languageArrayList;
		inf = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inf.inflate(R.layout.language_list_child, parent,
					false);
		}
		String[] newspaperArray = context.getResources().getStringArray(
				R.array.newspaper_list);

		LinearLayout linearLayout = (LinearLayout) convertView
				.findViewById(R.id.list_child);
		linearLayout.removeAllViews();
		if (languageArrayList.get(groupPosition).equalsIgnoreCase("hindi")) {
			TextView t1 = new TextView(context);
			t1.setText("Amar Ujala");
			t1.setTag("Amar Ujala");
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			layoutParams.setMargins(20, 20, 20, 20);
			t1.setLayoutParams(layoutParams);
			t1.setOnClickListener(this);
			linearLayout.addView(t1);
		} else {
			for (int i = 0; i < newspaperArray.length; i++) {
				TextView t1 = new TextView(context);
				t1.setText(newspaperArray[i]);
				t1.setTag(newspaperArray[i]);
				t1.setOnClickListener(this);
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				layoutParams.setMargins(20, 20, 20, 20);
				t1.setLayoutParams(layoutParams);
				linearLayout.addView(t1);

				View v1 = new View(context);
				layoutParams = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, 1);
				layoutParams.setMargins(20, 0, 20, 0);
				v1.setLayoutParams(layoutParams);
				v1.setBackgroundColor(Color.BLACK);
				linearLayout.addView(v1);
			}
		}

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return languageArrayList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return languageArrayList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = inf.inflate(R.layout.language_list_parent, parent,
					false);
		}

		TextView language = (TextView) convertView
				.findViewById(R.id.textview_list_language);
		language.setText(languageArrayList.get(groupPosition));

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

	@Override
	public void onClick(View v) {
		if (v.getTag().equals("Amar Ujala")) {
			Intent i = new Intent(context, NewspaperActivity.class);
			context.startActivity(i);
		}
	}

}
