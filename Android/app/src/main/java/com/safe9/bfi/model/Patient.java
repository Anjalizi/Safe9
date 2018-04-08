package com.safe9.bfi.model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model class to store data of patients locally
 */
public class Patient implements Parcelable {

    public static final String PATIENT_CONSTANT="patient";
    private String mName;
    private int mId;
    private String mDoctor;
    private String mAadhaar;

    public Patient(String mName, int mId) {
        this.mName = mName;
        this.mId = mId;
    }

    public Patient(String mName, int mId, String mDoctor) {
        this.mName = mName;
        this.mId = mId;
        this.mDoctor = mDoctor;
    }

    public Patient(String mName, int mId, String mDoctor, String mAadhaar) {
        this.mName = mName;
        this.mId = mId;
        this.mDoctor = mDoctor;
        this.mAadhaar = mAadhaar;
    }

    public String getmAadhaar() {
        return mAadhaar;
    }

    public void setmAadhaar(String mAadhaar) {
        this.mAadhaar = mAadhaar;
    }

    public String getmDoctor() {
        return mDoctor;
    }

    public void setmDoctor(String mDoctor) {
        this.mDoctor = mDoctor;
    }

    public int getmId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeInt(this.mId);
        dest.writeString(this.mDoctor);
        dest.writeString(this.mAadhaar);
    }

    protected Patient(Parcel in) {
        this.mName = in.readString();
        this.mId = in.readInt();
        this.mDoctor = in.readString();
        this.mAadhaar = in.readString();
    }

    public static final Creator<Patient> CREATOR = new Creator<Patient>() {
        @Override
        public Patient createFromParcel(Parcel source) {
            return new Patient(source);
        }

        @Override
        public Patient[] newArray(int size) {
            return new Patient[size];
        }
    };
}
