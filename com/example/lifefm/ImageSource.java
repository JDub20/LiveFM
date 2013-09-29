package com.example.lifefm;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ImageSource
  implements Parcelable
{
  public static final Parcelable.Creator<ImageSource> CREATOR = new Parcelable.Creator()
  {
    public ImageSource createFromParcel(Parcel paramAnonymousParcel)
    {
      return new ImageSource(paramAnonymousParcel);
    }

    public ImageSource[] newArray(int paramAnonymousInt)
    {
      return new ImageSource[paramAnonymousInt];
    }
  };
  private String size;
  private String src;

  public ImageSource(Parcel paramParcel)
  {
    this.src = paramParcel.readString();
    this.size = paramParcel.readString();
  }

  public ImageSource(String paramString1, String paramString2)
  {
    this.src = paramString1;
    this.size = paramString2;
  }

  public int describeContents()
  {
    return 0;
  }

  public String getSize()
  {
    return this.size;
  }

  public String getSrc()
  {
    return this.src;
  }

  public void setSize(String paramString)
  {
    this.size = paramString;
  }

  public void setSrc(String paramString)
  {
    this.src = paramString;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.src);
    paramParcel.writeString(this.size);
  }
}

/* Location:           /home/hernanjw/Desktop/LifeFM/classes_dex2jar.jar
 * Qualified Name:     com.example.lifefm.ImageSource
 * JD-Core Version:    0.6.2
 */