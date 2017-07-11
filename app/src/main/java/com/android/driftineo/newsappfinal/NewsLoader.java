package com.android.driftineo.newsappfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.driftineo.newsappfinal.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by driftineo on 10/7/17.
 */

public class NewsLoader extends android.support.v4.content.AsyncTaskLoader<ArrayList<News>> {

    public NewsLoader(Context context) {
        super(context);
    }

    @Override
    public ArrayList<News> loadInBackground() {

        ArrayList<News> newsArrayList = searchAsyncTask();
        return newsArrayList;
    }

    public ArrayList<News> searchAsyncTask() {

        String searchString = "http://content.guardianapis.com/search?q=android&api-key=aec0d753-633c-43df-815a-68bdc6067819&show-fields=thumbnail&show-tags=contributor&page-size=30";
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookString = "";
        String blankString = "";

        try {
            URL url = new URL(searchString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer stringBuffer = new StringBuffer();
            if (inputStream == null) {
                bookString = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            while ((blankString = reader.readLine()) != null) {
                stringBuffer.append(blankString + "\n");
            }
            if (stringBuffer.length() == 0) {
                bookString = null;
            }
            bookString = stringBuffer.toString();
        } catch (IOException e) {
            bookString = null;
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonToNews(bookString);
    }

    private ArrayList<News> jsonToNews(String bookString) {

        final String RESULTS = "results";
        final String RESPONSE = "response";
        final String WEB_TITLE = "webTitle";
        final String WEB_PUBLICATION_DATE = "webPublicationDate";
        final String SECTION_NAME = "sectionName";
        final String FIELDS = "fields";
        final String THUMBNAIL = "thumbnail";
        final String TAGS = "tags";
        final String WEB_URL = "webUrl";


        ArrayList<News> aNewses = new ArrayList<>();

        try {
            JSONObject rootObject = new JSONObject(bookString);
            JSONObject primaryObject = rootObject.getJSONObject(RESPONSE);
            JSONArray primaryArray = primaryObject.getJSONArray(RESULTS);
            JSONObject imageObject;


            for (int i = 0; i < primaryArray.length(); i++) {
                String title = "";
                String section = "";
                String imageUrl = "";
                String dateOf = "";
                String author = "";
                String webUrl = "";
                Bitmap bmp = null;
                JSONObject singleNew = primaryArray.getJSONObject(i);
                title = singleNew.getString(WEB_TITLE);
                section = singleNew.getString(SECTION_NAME);
                dateOf = singleNew.getString(WEB_PUBLICATION_DATE);
                webUrl = singleNew.getString(WEB_URL);
                author = "";

                try {
                    if (singleNew.has(FIELDS)) {
                        imageObject = singleNew.getJSONObject(FIELDS);
                        imageUrl = imageObject.getString(THUMBNAIL);
                        URL url = new URL(imageUrl);
                        bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    }
                } catch (JSONException e) {
                } finally {
                    try {
                        if (singleNew.has(TAGS)) {
                            JSONArray secondaryArray = singleNew.getJSONArray(TAGS);
                            for (int j = 0; j < secondaryArray.length(); j++) {
                                JSONObject aux = secondaryArray.getJSONObject(j);
                                author = aux.getString(WEB_TITLE);
                            }
                        }
                    } catch (JSONException e) {
                    }
                }
                aNewses.add(new News(title, section, dateOf, author, bmp, webUrl));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return aNewses;
    }
}

