package com.app.news.model;

import java.io.Serializable;

public class News implements Serializable {

	private static final long serialVersionUID = 1L;
	private String title;
	private String date;
	private String summary;
	private String image_src;
	private String main_link;
	private String category;
	private String news_src;

	public String getImage_src() {
		return image_src;
	}

	public void setImage_src(String image_src) {
		this.image_src = image_src;
	}

	public String getMain_link() {
		return main_link;
	}

	public void setMain_link(String main_link) {
		this.main_link = main_link;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getNews_src() {
		return news_src;
	}

	public void setNews_src(String news_src) {
		this.news_src = news_src;
	}

	public String getDate() {
		return date;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
