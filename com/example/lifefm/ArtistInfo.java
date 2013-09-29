package com.example.lifefm;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.List;

public class ArtistInfo
  implements Parcelable
{
  public static final Parcelable.Creator<ArtistInfo> CREATOR = new Parcelable.Creator()
  {
    public ArtistInfo createFromParcel(Parcel paramAnonymousParcel)
    {
      return new ArtistInfo(paramAnonymousParcel);
    }

    public ArtistInfo[] newArray(int paramAnonymousInt)
    {
      return new ArtistInfo[paramAnonymousInt];
    }
  };
  List<String> artistNames;
  String headliner;

  public ArtistInfo(Parcel paramParcel)
  {
    this.artistNames = new ArrayList();
    paramParcel.readStringList(this.artistNames);
    this.headliner = paramParcel.readString();
  }

  public ArtistInfo(List<String> paramList, String paramString)
  {
    this.artistNames = paramList;
    this.headliner = paramString;
  }

  public int describeContents()
  {
    return 0;
  }

  public List<String> getArtistNames()
  {
    return this.artistNames;
  }

  public String getHeadliner()
  {
    return this.headliner;
  }

  public void setArtistNames(List<String> paramList)
  {
    this.artistNames = paramList;
  }

  public void setHeadliner(String paramString)
  {
    this.headliner = paramString;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeStringList(this.artistNames);
    paramParcel.writeString(this.headliner);
  }
}

/* Location:           /home/hernanjw/Desktop/LifeFM/classes_dex2jar.jar
 * Qualified Name:     com.example.lifefm.ArtistInfo
 * JD-Core Version:    0.6.2
 */