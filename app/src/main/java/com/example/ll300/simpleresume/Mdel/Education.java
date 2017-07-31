package com.example.ll300.simpleresume.Mdel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

/**
 * Created by ll300 on 2017/7/29.
 */

public class Education implements Parcelable{
    public String id;
    public String school;
    public String Major;
    public Date startDate;
    public Date endDate;
    public List<String> courses;

    public Education() {}

    protected Education(Parcel in) {
        id = in.readString();
        school = in.readString();
        Major = in.readString();
        startDate = new Date(in.readLong());
        endDate = new Date(in.readLong());
        courses = in.createStringArrayList();
    }

    public static final Creator<Education> CREATOR = new Creator<Education>() {
        @Override
        public Education createFromParcel(Parcel in) {
            return new Education(in);
        }

        @Override
        public Education[] newArray(int size) {
            return new Education[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(school);
        dest.writeString(Major);
        dest.writeLong(startDate.getTime());
        dest.writeLong(endDate.getTime());
        dest.writeStringList(courses);
    }
}
