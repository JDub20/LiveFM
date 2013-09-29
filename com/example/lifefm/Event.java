package com.example.lifefm;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class Event
  implements Parcelable
{
  public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator()
  {
    public Event createFromParcel(Parcel paramAnonymousParcel)
    {
      return new Event(paramAnonymousParcel);
    }

    public Event[] newArray(int paramAnonymousInt)
    {
      return new Event[paramAnonymousInt];
    }
  };
  private ArtistInfo artistInfo;
  private int attendance;
  private boolean cancelled;
  private String description;
  private int id;
  private List<ImageSource> imageSources;
  private int reviews;
  private Date startDate;
  private String tag;
  private List<String> tags;
  private String tickets;
  private String title;
  private String url;
  private Venue venue;
  private String website;

  public Event(int paramInt1, String paramString1, ArtistInfo paramArtistInfo, Venue paramVenue, Date paramDate, String paramString2, List<ImageSource> paramList, int paramInt2, int paramInt3, String paramString3, String paramString4, String paramString5, String paramString6, boolean paramBoolean, List<String> paramList1)
  {
    this.id = paramInt1;
    this.title = paramString1;
    this.artistInfo = paramArtistInfo;
    this.venue = paramVenue;
    this.startDate = paramDate;
    this.description = paramString2;
    this.imageSources = paramList;
    this.attendance = paramInt2;
    this.reviews = paramInt3;
    this.tag = paramString3;
    this.url = paramString4;
    this.website = paramString5;
    this.tickets = paramString6;
    this.cancelled = paramBoolean;
    this.tags = paramList1;
  }

  public Event(Parcel paramParcel)
  {
    this.id = paramParcel.readInt();
    this.title = paramParcel.readString();
    this.artistInfo = ((ArtistInfo)paramParcel.readParcelable(ArtistInfo.class.getClassLoader()));
    this.venue = ((Venue)paramParcel.readParcelable(Venue.class.getClassLoader()));
    this.startDate = new Date(paramParcel.readLong());
    this.description = paramParcel.readString();
    this.imageSources = new ArrayList();
    paramParcel.readTypedList(this.imageSources, ImageSource.CREATOR);
    this.attendance = paramParcel.readInt();
    this.reviews = paramParcel.readInt();
    this.tag = paramParcel.readString();
    this.url = paramParcel.readString();
    this.website = paramParcel.readString();
    this.tickets = paramParcel.readString();
    boolean bool;
    if (paramParcel.readInt() != 1)
      bool = false;
    else
      bool = true;
    this.cancelled = bool;
    this.tags = new ArrayList();
    paramParcel.readStringList(this.tags);
  }

  public int describeContents()
  {
    return 0;
  }

  public ArtistInfo getArtistInfo()
  {
    return this.artistInfo;
  }

  public int getAttendace()
  {
    return this.attendance;
  }

  public boolean getCancelled()
  {
    return this.cancelled;
  }

  public String getDescription()
  {
    return this.description;
  }

  public int getId()
  {
    return this.id;
  }

  public List<ImageSource> getImages()
  {
    return this.imageSources;
  }

  public String getPrettyDate()
  {
    return new SimpleDateFormat("E, MMM d, y 'at' hh:mm a", Locale.US).format(this.startDate);
  }

  public String getPrettyDate(String paramString)
  {
    return new SimpleDateFormat(paramString, Locale.US).format(this.startDate);
  }

  public String getPrettyTags()
  {
    StringBuilder localStringBuilder = new StringBuilder("tags: ");
    List localList = getTags();
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
      localStringBuilder.append((String)localIterator.next()).append(", ");
    if (localList.size() > 0)
      localStringBuilder.deleteCharAt(-1 + localStringBuilder.length()).deleteCharAt(-1 + localStringBuilder.length());
    return localStringBuilder.toString();
  }

  public int getReviews()
  {
    return this.reviews;
  }

  public Date getStartDate()
  {
    return this.startDate;
  }

  public String getTag()
  {
    return this.tag;
  }

  public List<String> getTags()
  {
    return this.tags;
  }

  public String getTickets()
  {
    return this.tickets;
  }

  public String getTitle()
  {
    return this.title;
  }

  public String getUrl()
  {
    return this.url;
  }

  public Venue getVenue()
  {
    return this.venue;
  }

  public String getWebsite()
  {
    return this.website;
  }

  public void setArtistInfo(ArtistInfo paramArtistInfo)
  {
    this.artistInfo = paramArtistInfo;
  }

  public void setAttendace(int paramInt)
  {
    this.attendance = paramInt;
  }

  public void setCancelled(boolean paramBoolean)
  {
    this.cancelled = paramBoolean;
  }

  public void setDescription(String paramString)
  {
    this.description = paramString;
  }

  public void setId(int paramInt)
  {
    this.id = paramInt;
  }

  public void setImages(List<ImageSource> paramList)
  {
    this.imageSources = paramList;
  }

  public void setReviews(int paramInt)
  {
    this.reviews = paramInt;
  }

  public void setStartDate(Date paramDate)
  {
    this.startDate = paramDate;
  }

  public void setTag(String paramString)
  {
    this.tag = paramString;
  }

  public void setTags(List<String> paramList)
  {
    this.tags = paramList;
  }

  public void setTickets(String paramString)
  {
    this.tickets = paramString;
  }

  public void setTitle(String paramString)
  {
    this.title = paramString;
  }

  public void setUrl(String paramString)
  {
    this.url = paramString;
  }

  public void setVenue(Venue paramVenue)
  {
    this.venue = paramVenue;
  }

  public void setWebsite(String paramString)
  {
    this.website = paramString;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    int i = 0;
    paramParcel.writeInt(this.id);
    paramParcel.writeString(this.title);
    paramParcel.writeParcelable(this.artistInfo, 0);
    paramParcel.writeParcelable(this.venue, 0);
    paramParcel.writeLong(this.startDate.getTime());
    paramParcel.writeString(this.description);
    paramParcel.writeTypedList(this.imageSources);
    paramParcel.writeInt(this.attendance);
    paramParcel.writeInt(this.reviews);
    paramParcel.writeString(this.tag);
    paramParcel.writeString(this.url);
    paramParcel.writeString(this.website);
    paramParcel.writeString(this.tickets);
    if (this.cancelled)
      i = 1;
    paramParcel.writeInt(i);
    paramParcel.writeStringList(this.tags);
  }
}

/* Location:           /home/hernanjw/Desktop/LifeFM/classes_dex2jar.jar
 * Qualified Name:     com.example.lifefm.Event
 * JD-Core Version:    0.6.2
 */