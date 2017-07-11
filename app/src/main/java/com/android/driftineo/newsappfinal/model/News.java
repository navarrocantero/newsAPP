package com.android.driftineo.newsappfinal.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by driftineo on 25/6/17.
 */


public class News implements Parcelable {

    private String title;
    private String section;
    private String dateOfpublication;
    private String author;
    private Bitmap image;
    private String webTitle;

    public News() {

    }

    public News(String title){
        this.title = title;
    }

    public News(String section, String title, String dateOfpublication, String author, Bitmap image, String webTitle) {
        this.section = section;
        this.title = title;
        this.dateOfpublication = dateOfpublication;
        this.author = author;
        this.image = image;
        this.webTitle = webTitle;

    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getSection() {
        return section;
    }

    public String getDateOfpublication() {
        return dateOfpublication;
    }

    public Bitmap getImage() {
        return image;
    }

    protected News(Parcel in) {
        this.section = in.readString();
        this.title = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.section);
        dest.writeString(this.title);
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

}
