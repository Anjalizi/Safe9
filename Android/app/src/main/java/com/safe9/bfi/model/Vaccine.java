package com.safe9.bfi.model;

import java.util.Date;

public class Vaccine {

    private Date mDate;
    private boolean isDone;
    private String mTitle;
    private Date mCompleteDate;
    private String DBKey;

    public Date getmDate() {
        return mDate;
    }

    public Vaccine(Date mDate, boolean isDone, String mTitle, Date mCompleteDate) {
        this.mDate = mDate;
        this.isDone = isDone;
        this.mTitle = mTitle;
        this.mCompleteDate = mCompleteDate;
    }

    public Vaccine(Date mDate, boolean isDone, String mTitle, String DBKey) {
        this.mDate = mDate;
        this.isDone = isDone;
        this.mTitle = mTitle;
        this.DBKey = DBKey;
    }

    public String getDBKey() {
        return DBKey;
    }

    public void setDBKey(String DBKey) {
        this.DBKey = DBKey;
    }

    public Date getmCompleteDate() {
        return mCompleteDate;
    }

    public void setmCompleteDate(Date mCompleteDate) {
        this.mCompleteDate = mCompleteDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Vaccine(Date mDate, boolean isDone, String mTitle) {
        this.mDate = mDate;
        this.isDone = isDone;
        this.mTitle = mTitle;
    }
}

