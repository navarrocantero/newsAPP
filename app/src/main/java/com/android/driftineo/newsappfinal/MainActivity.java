package com.android.driftineo.newsappfinal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.driftineo.newsappfinal.model.News;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks {

    NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsAdapter = new NewsAdapter(this, new ArrayList<News>());
        ListView newsListView = (ListView) findViewById(R.id.newsListView);
        newsListView.setAdapter(newsAdapter);
        getSupportLoaderManager().initLoader(1, null, this).forceLoad();
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News currentNews = (News) newsAdapter.getItem(position);
                Uri newURL = Uri.parse(currentNews.getWebTitle());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newURL);
                startActivity(websiteIntent);
            }
        });
    }

    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        newsAdapter.setNews((ArrayList<News>) data);


    }

    @Override
    public void onLoaderReset(Loader loader) {

        newsAdapter.setNews(new ArrayList<News>());
    }
}
