/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ch12;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HeadlinesFragment extends ListFragment {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(getActivity(), 
        		android.R.layout.simple_list_item_1, Data.Headlines));
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getFragmentManager()
        		.findFragmentById(R.id.article_fragment) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // highlighted
        getListView().setItemChecked(position, true);
        
        ArticleFragment articleFragment = (ArticleFragment) getActivity()
        		.getSupportFragmentManager()
        		.findFragmentById(R.id.article_fragment);

        if (articleFragment == null) {
        	articleFragment = new ArticleFragment();
            Bundle args = new Bundle();
            args.putInt("position", position);
            articleFragment.setArguments(args);
            FragmentTransaction transaction = getActivity()
            		.getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, articleFragment);
            transaction.addToBackStack(null);

            transaction.commit();
        	
        } else {
        	articleFragment.updateArticleView(position);
        }
    }
}