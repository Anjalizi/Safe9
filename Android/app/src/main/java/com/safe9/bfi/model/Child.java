package com.safe9.bfi.model;


import java.util.ArrayList;
import java.util.Date;

public class Child {

    private String mAadhaar;
    private Date mBirthdate;
    private ArrayList<Date> mHepB,mTetanus,mPneumo,mRota;

    public Child(String mAadhaar, Date mBirthdate, ArrayList<Date> mHepB, ArrayList<Date> mTetanus, ArrayList<Date> mPneumo, ArrayList<Date> mRota) {
        this.mAadhaar = mAadhaar;
        this.mBirthdate = mBirthdate;
        this.mHepB = mHepB;
        this.mTetanus = mTetanus;
        this.mPneumo = mPneumo;
        this.mRota = mRota;
    }

    public String getmAadhaar() {
        return mAadhaar;
    }

    public void setmAadhaar(String mAadhaar) {
        this.mAadhaar = mAadhaar;
    }

    public Date getmBirthdate() {
        return mBirthdate;
    }

    public void setmBirthdate(Date mBirthdate) {
        this.mBirthdate = mBirthdate;
    }

    public ArrayList<Date> getmHepB() {
        return mHepB;
    }

    public void setmHepB(ArrayList<Date> mHepB) {
        this.mHepB = mHepB;
    }

    public ArrayList<Date> getmTetanus() {
        return mTetanus;
    }

    public void setmTetanus(ArrayList<Date> mTetanus) {
        this.mTetanus = mTetanus;
    }

    public ArrayList<Date> getmPneumo() {
        return mPneumo;
    }

    public void setmPneumo(ArrayList<Date> mPneumo) {
        this.mPneumo = mPneumo;
    }

    public ArrayList<Date> getmRota() {
        return mRota;
    }

    public void setmRota(ArrayList<Date> mRota) {
        this.mRota = mRota;
    }
}
