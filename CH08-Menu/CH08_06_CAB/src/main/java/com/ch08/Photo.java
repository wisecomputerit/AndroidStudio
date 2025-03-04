package com.ch08;

import java.util.ArrayList;
import java.util.List;

public class Photo {
	
	private static List<Integer> photoList;
	
	public static List<Integer> getPhotoList() {
		if(photoList == null) {
			photoList = new ArrayList<Integer>();
			for(Integer url : URLs) {
				photoList.add(url);
			}
		}
		return photoList;
	}
	
	private final static int[] URLs = new int[] {
		R.drawable.p0, R.drawable.p1, R.drawable.p2,
		R.drawable.p3, R.drawable.p4, R.drawable.p5,
		R.drawable.p6, R.drawable.p7, R.drawable.p8,
		R.drawable.p9, R.drawable.p10, R.drawable.p11,
		R.drawable.p12, R.drawable.p13, R.drawable.p14,
		R.drawable.p15, R.drawable.p16, R.drawable.p17,
		R.drawable.p18, R.drawable.p19
	};
}
