package com.ch09;

public class Fastfood {
	private String name;
	private int price;
	private int imageId;
	public Fastfood(String name, int price, int imageId) {
		setName(name);
		setPrice(price);
		setImageId(imageId);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
}
