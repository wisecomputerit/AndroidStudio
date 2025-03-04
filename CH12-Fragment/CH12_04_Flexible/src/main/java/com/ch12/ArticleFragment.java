package com.ch12;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ArticleFragment extends Fragment {
    
    @Override
    public View onCreateView(LayoutInflater inflater, 
    		ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.article_view, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            updateArticleView(args.getInt("position"));
        }
    }

    public void updateArticleView(int position) {
        TextView article = (TextView) getActivity()
        		.findViewById(R.id.article);
		article.setText(Data.Articles[position]);
	}

}