/* ############################################################################
* Copyright 2013 Hewlett-Packard Co. All Rights Reserved.
* An unpublished and CONFIDENTIAL work. Reproduction,
* adaptation, or translation without prior written permission
* is prohibited except as allowed under the copyright laws.
*-----------------------------------------------------------------------------
* Project: AL Deal-Maker
* Module: DCR\Other Calls
* Source: ModelMasterHelper.java
* Author: HP
* Organization: HP BAS India
* Revision: 0.1
* Date: 08-22-2013
* Contents:
*-----------------------------------------------------------------------------
* Revision History:
*     who                                  when                                    what
*  	hsingh								08-22-2013								Initial functionality
* #############################################################################
*/
package com.app.news.database.helpers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import com.app.news.database.DatabaseManager;
import com.app.news.model.News;
public class NewsListHelper extends SQLHelper{
	public NewsListHelper(DatabaseManager dbManager) {
		super(dbManager);
	}
	
	private static final String TABLE_NAME = "news_feed";
	private static final String GET_ALL_NEWS = "SELECT * FROM "+TABLE_NAME;
	public static final String GET_NEWS_FOR_CATEGORY = "SELECT * FROM " + TABLE_NAME + " WHERE category=?";
	public static final String GET_CATEGORY = "SELECT DISTINCT(category) FROM " + TABLE_NAME;
	
	public ArrayList<News> getAllNews() throws ParseException{
		ArrayList<News> newsList = new ArrayList<News>();
		Cursor cursor = dbManager.rawQuery(GET_ALL_NEWS, null);
		News tempEvent = null;
		System.out.println("Cursor Count "+cursor.getCount());
		while(cursor.moveToNext()){
			tempEvent = new News();
			tempEvent.setTitle(cursor.getString(cursor.getColumnIndex("title")));
			tempEvent.setDate(cursor.getString(cursor.getColumnIndex("published_date")));
			tempEvent.setImage_src(cursor.getString(cursor.getColumnIndex("image_src")));
			tempEvent.setMain_link(cursor.getString(cursor.getColumnIndex("main_link")));
			tempEvent.setNews_src(cursor.getString(cursor.getColumnIndex("news_src")));
			tempEvent.setSummary(cursor.getString(cursor.getColumnIndex("summary")));
			tempEvent.setCategory(cursor.getString(cursor.getColumnIndex("category")));
			newsList.add(tempEvent);
		}
		close(cursor);
		return newsList;
	}
	
	public List<String> getCategories() throws ParseException{
		List<String> categoryList = new ArrayList<String>();
		Cursor cursor = dbManager.rawQuery(GET_CATEGORY, null);
		String categoryName = null;
		while(cursor.moveToNext()){
			categoryName = new String();
			categoryName = (cursor.getString(cursor.getColumnIndex("category")));
			categoryList.add(categoryName);
		}
		close(cursor);
		return categoryList;
	}

	public ArrayList<News> getNewsFromCategory(String category) throws ParseException{
		Cursor cursor = dbManager.rawQuery(GET_NEWS_FOR_CATEGORY, new String[] {category});
		ArrayList<News> newsList = new ArrayList<News>();
		News tempEvent = null;
		System.out.println("Cursor Count "+cursor.getCount());
		while(cursor.moveToNext()){
			tempEvent = new News();
			tempEvent.setTitle(cursor.getString(cursor.getColumnIndex("title")));
			tempEvent.setDate(cursor.getString(cursor.getColumnIndex("published_date")));
			tempEvent.setImage_src(cursor.getString(cursor.getColumnIndex("image_src")));
			tempEvent.setMain_link(cursor.getString(cursor.getColumnIndex("main_link")));
			tempEvent.setNews_src(cursor.getString(cursor.getColumnIndex("news_src")));
			tempEvent.setSummary(cursor.getString(cursor.getColumnIndex("summary")));
			tempEvent.setCategory(cursor.getString(cursor.getColumnIndex("category")));
			newsList.add(tempEvent);
		}
		close(cursor);
		return newsList;
	}

}