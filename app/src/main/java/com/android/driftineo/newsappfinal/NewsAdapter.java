package com.android.driftineo.newsappfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.driftineo.newsappfinal.model.News;

import java.util.ArrayList;

/**
 * Created by driftineo on 10/7/17.
 */

public class NewsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<News> newsArrayList;

    public NewsAdapter(Context context, ArrayList newsArrayList) {
        this.newsArrayList = newsArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        return newsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news = (News) getItem(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listitem_new, null);
        }

        TextView newSection = (TextView) convertView.findViewById(R.id.section);
        TextView newTitle = (TextView) convertView.findViewById(R.id.newsTitle);
        TextView newAuthor = (TextView) convertView.findViewById(R.id.author);
        TextView newDate = (TextView) convertView.findViewById(R.id.dateOf);
        ImageView image = (ImageView) convertView.findViewById(R.id.imageView);

        if (news != null) {

            newTitle.setText(news.getTitle());
            newSection.setText(news.getSection());
            image.setImageBitmap(news.getImage());
            newAuthor.setText(news.getAuthor());
            newDate.setText(news.getDateOfpublication());
        }
        return convertView;
    }

    public void setNews(ArrayList<News>data){
        newsArrayList.clear();
        newsArrayList.addAll(data);
        notifyDataSetChanged();
    }
}

