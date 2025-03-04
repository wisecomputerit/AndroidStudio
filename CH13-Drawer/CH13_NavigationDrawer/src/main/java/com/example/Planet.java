package com.example;

public class Planet {
	public static String[] planetTitles; // 八大行星名稱
    public static int[] planetImgResId; // 八大行星圖
    static {
    	planetTitles = new String[] {
    			"Mercury 水星", "Venus 金星", "Earth 地球", "Mars 火星",
    			"Jupiter 木星", "Saturn 土星", "Uranus 天王星", "Neptune 海王星"
    	};
    	planetImgResId = new int[] { 
        		R.drawable.mercury, R.drawable.venus, 
        		R.drawable.earth, R.drawable.mars, 
        		R.drawable.jupiter, R.drawable.saturn, 
        		R.drawable.uranus, R.drawable.neptune
        };
    }
}
