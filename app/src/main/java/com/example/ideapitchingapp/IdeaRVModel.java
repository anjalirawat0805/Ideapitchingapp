package com.example.ideapitchingapp;

import android.os.Parcel;
import android.os.Parcelable;

public class IdeaRVModel implements Parcelable {
    private String ideaName;
    private String ideaDescription;
    private String ideaUser;
    private String ideaContactInfo;
    private String ideaImg;
    private String ideaID;

   public IdeaRVModel(){
       /* empty constructor */
   }

    public IdeaRVModel(String ideaName, String ideaDescription, String ideaUser, String ideaContactInfo, String ideaImg, String ideaID) {
        this.ideaName = ideaName;
        this.ideaDescription = ideaDescription;
        this.ideaUser = ideaUser;
        this.ideaContactInfo = ideaContactInfo;
        this.ideaImg = ideaImg;
        this.ideaID = ideaID;
    }

    protected IdeaRVModel(Parcel in) {
        ideaName = in.readString();
        ideaDescription = in.readString();
        ideaUser = in.readString();
        ideaContactInfo = in.readString();
        ideaImg = in.readString();
        ideaID = in.readString();
    }

    public static final Creator<IdeaRVModel> CREATOR = new Creator<IdeaRVModel>() {
        @Override
        public IdeaRVModel createFromParcel(Parcel in) {
            return new IdeaRVModel(in);
        }

        @Override
        public IdeaRVModel[] newArray(int size) {
            return new IdeaRVModel[size];
        }
    };

    public String getIdeaName() {
        return ideaName;
    }

    public void setIdeaName(String ideaName) {
        this.ideaName = ideaName;
    }

    public String getIdeaDescription() {
        return ideaDescription;
    }

    public void setIdeaDescription(String ideaDescription) {
        this.ideaDescription = ideaDescription;
    }

    public String getIdeaUser() {
        return ideaUser;
    }

    public void setIdeaUser(String ideaUser) {
        this.ideaUser = ideaUser;
    }

    public String getIdeaContactInfo() {
        return ideaContactInfo;
    }

    public void setIdeaContactInfo(String ideaContactInfo) {
        this.ideaContactInfo = ideaContactInfo;
    }

    public String getIdeaImg() {
        return ideaImg;
    }

    public void setIdeaImg(String ideaImg) {
        this.ideaImg = ideaImg;
    }

    public String getIdeaID() {
        return ideaID;
    }

    public void setIdeaID(String ideaID) {
        this.ideaID = ideaID;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ideaName);
        dest.writeString(ideaDescription);
        dest.writeString(ideaUser);
        dest.writeString(ideaContactInfo);
        dest.writeString(ideaImg);
        dest.writeString(ideaID);
    }
}
