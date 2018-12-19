package com.kelldavis.yummy.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {
    public static final Parcelable.Creator<Ingredient> CREATOR
            = new Parcelable.Creator<Ingredient>() {

        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
    // for some reason the JSON uses "UNIT" to indicate no unit
    private final static String NO_UNIT_IN_JSON = "UNIT";
    private String name;
    private int quantity;
    private String measurementUnit;

    private Ingredient(Parcel in) {
        name = in.readString();
        quantity = in.readInt();
        measurementUnit = in.readString();
    }

    public Ingredient(String ingredient, int quantity, String measure) {
        this.name = ingredient;
        this.quantity = quantity;
        this.measurementUnit = measure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public String getQuantityUnitNameString() {
        if (getMeasurementUnit().equals(NO_UNIT_IN_JSON)) {
            return String.format("%s %s", getQuantity(), getName());
        } else {
            return String.format("%s %s %s", getQuantity(), getMeasurementUnit().toLowerCase(), getName());
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(quantity);
        parcel.writeString(measurementUnit);
    }
}
