package com.example.ll300.simpleresume.Mdel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by ll300 on 2017/8/1.
 */

public class Experience implements Parcelable {
    public String id;
    public String company;
    public Date startDate;
    public Date endDate;
    public String details;

    public Experience() { id = UUID.randomUUID().toString(); }
    protected Experience(Parcel in) {
        id = in.readString();
        company = in.readString();
        startDate = new Date(in.readLong());
        endDate = new Date(in.readLong());
        details = in.readString();
    }

    public static final Creator<Experience> CREATOR = new Creator<Experience>() {
        @Override
        public Experience createFromParcel(Parcel in) {
            return new Experience(in);
        }

        @Override
        public Experience[] newArray(int size) {
            return new Experience[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(company);
        dest.writeLong(startDate.getTime());
        dest.writeLong(endDate.getTime());
        dest.writeString(details);
    }
}
