package com.example.ll300.simpleresume.Mdel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ll300 on 2017/7/30.
 */

public class BasicInfo implements Parcelable{
    public String name;
    public String email;

    public BasicInfo() {}

    protected BasicInfo(Parcel in) {
        name = in.readString();
        email = in.readString();
    }

    public static final Creator<BasicInfo> CREATOR = new Creator<BasicInfo>() {
        @Override
        public BasicInfo createFromParcel(Parcel in) {
            return new BasicInfo(in);
        }

        @Override
        public BasicInfo[] newArray(int size) {
            return new BasicInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
    }
}
