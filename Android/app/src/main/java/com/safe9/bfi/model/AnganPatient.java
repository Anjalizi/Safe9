package com.safe9.bfi.model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model class for patient data for anganwadi workers retrieved from server
 */
public class AnganPatient implements Parcelable {

    public static final String ANGAN_PATIENT_CONSTANT="a_patient";
    private String mName;
    private int mWeeks;
    private double mLatitude;
    private double mLongitude;
    private String mVaccineDate;

    public String getmVaccineDate() {
        return mVaccineDate;
    }

    public AnganPatient(String mName, int mWeeks, double mLatitude, double mLongitude, String mVaccineDate) {
        this.mName = mName;
        this.mWeeks = mWeeks;
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
        this.mVaccineDate = mVaccineDate;
    }

    public void setmVaccineDate(String mVaccineDate) {
        this.mVaccineDate = mVaccineDate;
    }

    public AnganPatient(String mName, int mWeeks, double mLatitude, double mLongitude) {
        this.mName = mName;
        this.mWeeks = mWeeks;
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmWeeks() {
        return mWeeks;
    }

    public void setmWeeks(int mWeeks) {
        this.mWeeks = mWeeks;
    }

    public double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeInt(this.mWeeks);
        dest.writeDouble(this.mLatitude);
        dest.writeDouble(this.mLongitude);
        dest.writeString(this.mVaccineDate);
    }

    protected AnganPatient(Parcel in) {
        this.mName = in.readString();
        this.mWeeks = in.readInt();
        this.mLatitude = in.readDouble();
        this.mLongitude = in.readDouble();
        this.mVaccineDate = in.readString();
    }

    public static final Creator<AnganPatient> CREATOR = new Creator<AnganPatient>() {
        @Override
        public AnganPatient createFromParcel(Parcel source) {
            return new AnganPatient(source);
        }

        @Override
        public AnganPatient[] newArray(int size) {
            return new AnganPatient[size];
        }
    };
}
