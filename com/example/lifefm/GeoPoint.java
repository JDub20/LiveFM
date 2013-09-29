package com.example.lifefm;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class GeoPoint
  implements Parcelable
{
  public static final Parcelable.Creator<GeoPoint> CREATOR = new Parcelable.Creator()
  {
    public GeoPoint createFromParcel(Parcel paramAnonymousParcel)
    {
      return new GeoPoint(paramAnonymousParcel);
    }

    public GeoPoint[] newArray(int paramAnonymousInt)
    {
      return new GeoPoint[paramAnonymousInt];
    }
  };
  private Double latitude;
  private Double longitude;

  public GeoPoint(double paramDouble1, double paramDouble2)
  {
    this.latitude = Double.valueOf(paramDouble1);
    this.longitude = Double.valueOf(paramDouble2);
  }

  public GeoPoint(Parcel paramParcel)
  {
    this.latitude = Double.valueOf(paramParcel.readDouble());
    this.longitude = Double.valueOf(paramParcel.readDouble());
  }

  public int describeContents()
  {
    return 0;
  }

  public Double getGeoLatitude()
  {
    return this.latitude;
  }

  public Double getGeoLongitude()
  {
    return this.longitude;
  }

  public void setGeoLatitude(Double paramDouble)
  {
    this.latitude = paramDouble;
  }

  public void setGeoLongitude(Double paramDouble)
  {
    this.longitude = paramDouble;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeDouble(this.latitude.doubleValue());
    paramParcel.writeDouble(this.longitude.doubleValue());
  }
}

/* Location:           /home/hernanjw/Desktop/LifeFM/classes_dex2jar.jar
 * Qualified Name:     com.example.lifefm.GeoPoint
 * JD-Core Version:    0.6.2
 */