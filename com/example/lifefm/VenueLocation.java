package com.example.lifefm;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class VenueLocation
  implements Parcelable
{
  public static final Parcelable.Creator<VenueLocation> CREATOR = new Parcelable.Creator()
  {
    public VenueLocation createFromParcel(Parcel paramAnonymousParcel)
    {
      return new VenueLocation(paramAnonymousParcel);
    }

    public VenueLocation[] newArray(int paramAnonymousInt)
    {
      return new VenueLocation[paramAnonymousInt];
    }
  };
  private String city;
  private GeoPoint geoPoint;
  private String postalCode;
  private String street;

  public VenueLocation(Parcel paramParcel)
  {
    this.geoPoint = ((GeoPoint)paramParcel.readParcelable(GeoPoint.class.getClassLoader()));
    this.city = paramParcel.readString();
    this.street = paramParcel.readString();
    this.postalCode = paramParcel.readString();
  }

  public VenueLocation(GeoPoint paramGeoPoint, String paramString1, String paramString2, String paramString3)
  {
    this.geoPoint = paramGeoPoint;
    this.city = paramString1;
    this.street = paramString2;
    this.postalCode = paramString3;
  }

  public int describeContents()
  {
    return 0;
  }

  public String getCity()
  {
    return this.city;
  }

  public GeoPoint getGeoPoint()
  {
    return this.geoPoint;
  }

  public String getPostalCode()
  {
    return this.postalCode;
  }

  public String getStreet()
  {
    return this.street;
  }

  public void setCity(String paramString)
  {
    this.city = paramString;
  }

  public void setGeoPoint(GeoPoint paramGeoPoint)
  {
    this.geoPoint = paramGeoPoint;
  }

  public void setPostalCode(String paramString)
  {
    this.postalCode = paramString;
  }

  public void setStreet(String paramString)
  {
    this.street = paramString;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeParcelable(this.geoPoint, 0);
    paramParcel.writeString(this.city);
    paramParcel.writeString(this.street);
    paramParcel.writeString(this.postalCode);
  }
}

/* Location:           /home/hernanjw/Desktop/LifeFM/classes_dex2jar.jar
 * Qualified Name:     com.example.lifefm.VenueLocation
 * JD-Core Version:    0.6.2
 */