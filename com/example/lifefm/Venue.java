package com.example.lifefm;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.List;

public class Venue
  implements Parcelable
{
  public static final Parcelable.Creator<Venue> CREATOR = new Parcelable.Creator()
  {
    public Venue createFromParcel(Parcel paramAnonymousParcel)
    {
      return new Venue(paramAnonymousParcel);
    }

    public Venue[] newArray(int paramAnonymousInt)
    {
      return new Venue[paramAnonymousInt];
    }
  };
  private int id;
  private List<ImageSource> images;
  private VenueLocation location;
  private String name;
  private String phoneNumber;
  private String url;
  private String website;

  public Venue(int paramInt, String paramString1, VenueLocation paramVenueLocation, String paramString2, String paramString3, String paramString4, List<ImageSource> paramList)
  {
    this.id = paramInt;
    this.name = paramString1;
    this.location = paramVenueLocation;
    this.url = paramString2;
    this.website = paramString3;
    this.phoneNumber = paramString4;
    this.images = paramList;
  }

  public Venue(Parcel paramParcel)
  {
    this.id = paramParcel.readInt();
    this.name = paramParcel.readString();
    this.location = ((VenueLocation)paramParcel.readParcelable(VenueLocation.class.getClassLoader()));
    this.url = paramParcel.readString();
    this.website = paramParcel.readString();
    this.phoneNumber = paramParcel.readString();
    this.images = new ArrayList();
    paramParcel.readTypedList(this.images, ImageSource.CREATOR);
  }

  public int describeContents()
  {
    return 0;
  }

  public int getId()
  {
    return this.id;
  }

  public List<ImageSource> getImages()
  {
    return this.images;
  }

  public VenueLocation getLocation()
  {
    return this.location;
  }

  public String getName()
  {
    return this.name;
  }

  public String getPhoneNumber()
  {
    return this.phoneNumber;
  }

  public String getUrl()
  {
    return this.url;
  }

  public String getWebsite()
  {
    return this.website;
  }

  public void setId(int paramInt)
  {
    this.id = paramInt;
  }

  public void setImages(List<ImageSource> paramList)
  {
    this.images = paramList;
  }

  public void setLocation(VenueLocation paramVenueLocation)
  {
    this.location = paramVenueLocation;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setPhoneNumber(String paramString)
  {
    this.phoneNumber = paramString;
  }

  public void setUrl(String paramString)
  {
    this.url = paramString;
  }

  public void setWebsite(String paramString)
  {
    this.website = paramString;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.id);
    paramParcel.writeString(this.name);
    paramParcel.writeParcelable(this.location, 0);
    paramParcel.writeString(this.url);
    paramParcel.writeString(this.website);
    paramParcel.writeString(this.phoneNumber);
    paramParcel.writeTypedList(this.images);
  }
}

/* Location:           /home/hernanjw/Desktop/LifeFM/classes_dex2jar.jar
 * Qualified Name:     com.example.lifefm.Venue
 * JD-Core Version:    0.6.2
 */