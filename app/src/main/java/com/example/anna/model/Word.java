package com.example.anna.model;

public class Word {
    private int mID;
    private String mEnglish;
    private String mType;
    private String mVietnamese;
    private String mSpecializedName;
    private int isStar;

    public Word() {
    }

    public Word(String mEnglish, String mType, String mVietnamese, String mSpecializedName, int isStar) {
        this.mEnglish = mEnglish;
        this.mType = mType;
        this.mVietnamese = mVietnamese;
        this.mSpecializedName = mSpecializedName;
        this.isStar = isStar;
    }

    public Word(int mID, String mEnglish, String mType, String mVietnamese, String mSpecializedName, int isStar) {
        this.mID = mID;
        this.mEnglish = mEnglish;
        this.mType = mType;
        this.mVietnamese = mVietnamese;
        this.mSpecializedName = mSpecializedName;
        this.isStar = isStar;
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmEnglish() {
        return mEnglish;
    }

    public void setmEnglish(String mEnglish) {
        this.mEnglish = mEnglish;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getmVietnamese() {
        return mVietnamese;
    }

    public void setmVietnamese(String mVietnamese) {
        this.mVietnamese = mVietnamese;
    }

    public String getmSpecializedName() {
        return mSpecializedName;
    }

    public void setmSpecializedName(String mSpecializedName) {
        this.mSpecializedName = mSpecializedName;
    }

    public int getIsStar() {
        return isStar;
    }

    public void setIsStar(int star) {
        isStar = star;
    }
}
