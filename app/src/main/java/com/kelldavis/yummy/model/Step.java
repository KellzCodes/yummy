package com.kelldavis.yummy.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Step object.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Step implements Parcelable {
    @JsonProperty("videoURL")
    private final String videoURL;
    @JsonProperty("description")
    private final String description;
    @JsonProperty("id")
    private final int id;
    @JsonProperty("shortDescription")
    private final String shortDescription;
    @JsonProperty("thumbnailURL")
    private final String thumbnailURL;

    /**
     * Instantiates a new Step object.
     */
    public Step() {
        this.videoURL = "";
        this.description = "";
        this.id = 0;
        this.shortDescription = "";
        this.thumbnailURL = "";
    }

    // Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.videoURL);
        dest.writeString(this.description);
        dest.writeInt(this.id);
        dest.writeString(this.shortDescription);
        dest.writeString(this.thumbnailURL);
    }

    /**
     * Instantiates a new Step.
     *
     * @param in the Parcel
     */
    protected Step(Parcel in) {
        this.videoURL = in.readString();
        this.description = in.readString();
        this.id = in.readInt();
        this.shortDescription = in.readString();
        this.thumbnailURL = in.readString();
    }


    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel source) {
            return new Step(source);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    /**
     * get video URL
     *
     * @return video URL
     */
    public String getVideoURL() {
        return videoURL;
    }

    /**
     * get description
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets short description
     * @return shortDescription
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * Gets thumbnail URL
     * @return thumbnailUrl
     */
    public String getThumbnailURL() {
        return thumbnailURL;
    }

    @Override
    public String toString() {
        return "Step{" +
                "videoURL='" + videoURL + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", shortDescription='" + shortDescription + '\'' +
                ", thumbnailURL='" + thumbnailURL + '\'' +
                '}';
    }
}
