package com.ch15.vo;

public class CoffeeItem {
	
	private String title;
	private int image;
	
	public CoffeeItem(String title, int image) {
		super();
		this.title = title;
		this.image = image;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	
}
