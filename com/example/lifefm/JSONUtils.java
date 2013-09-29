package com.example.lifefm;

import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils
{
  private static final String LIVEFM_DATE_FORMAT = "EEE, dd MMMM yyyy HH:mm:ss";

  public static ArtistInfo jsonObjectToArtistInfo(JSONObject paramJSONObject)
  {
    Object localObject1 = new ArrayList();
    try
    {
      Object localObject2 = paramJSONObject.get("artist");
      if ((localObject2 instanceof String))
        ((List)localObject1).add((String)localObject2);
      if ((localObject2 instanceof JSONArray));
      for (int i = 0; ; i++)
      {
        if (i >= ((JSONArray)localObject2).length())
        {
          localObject1 = new ArtistInfo((List)localObject1, paramJSONObject.getString("headliner"));
          break;
        }
        ((List)localObject1).add(((JSONArray)localObject2).getString(i));
      }
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
      ArtistInfo localArtistInfo = null;
      return localArtistInfo;
    }
  }

  public static Event jsonObjectToEvent(JSONObject paramJSONObject)
  {
    try
    {
      int i = paramJSONObject.getInt("id");
      String str1 = paramJSONObject.getString("title");
      ArtistInfo localArtistInfo = jsonObjectToArtistInfo(paramJSONObject.getJSONObject("artists"));
      Object localObject1 = jsonObjectToVenue(paramJSONObject.getJSONObject("venue"));
      Date localDate = new Date();
      String str2 = paramJSONObject.getString("startDate");
      Object localObject2 = new SimpleDateFormat("EEE, dd MMMM yyyy HH:mm:ss", Locale.US);
      try
      {
        localDate = ((SimpleDateFormat)localObject2).parse(str2);
        localDate = localDate;
        String str5 = paramJSONObject.getString("description");
        localObject2 = jsonObjectToImageSourceList(paramJSONObject.getJSONArray("image"));
        int j = paramJSONObject.getInt("attendance");
        int k = paramJSONObject.getInt("reviews");
        String str4 = paramJSONObject.getString("tag");
        String str7 = paramJSONObject.getString("url");
        String str3 = paramJSONObject.getString("website");
        String str6 = paramJSONObject.getString("tickets");
        if (paramJSONObject.getInt("cancelled") == 1)
        {
          bool = true;
          Object localObject3 = new ArrayList();
          if ((paramJSONObject.has("tags")) && (!paramJSONObject.isNull("tags")))
            localObject3 = jsonObjectToTagList(paramJSONObject.getJSONObject("tags"));
          localObject1 = new Event(i, str1, localArtistInfo, (Venue)localObject1, localDate, str5, (List)localObject2, j, k, str4, str7, str3, str6, bool, (List)localObject3);
        }
      }
      catch (ParseException localParseException)
      {
        while (true)
          localParseException.printStackTrace();
      }
    }
    catch (JSONException localJSONException)
    {
      Event localEvent;
      while (true)
      {
        localJSONException.printStackTrace();
        localEvent = null;
        break;
        boolean bool = false;
      }
      return localEvent;
    }
  }

  private static GeoPoint jsonObjectToGeoPoint(JSONObject paramJSONObject)
  {
    try
    {
      GeoPoint localGeoPoint = new GeoPoint(paramJSONObject.getDouble("geo:lat"), paramJSONObject.getDouble("geo:long"));
      return localGeoPoint;
    }
    catch (JSONException localJSONException)
    {
      while (true)
      {
        localJSONException.printStackTrace();
        Object localObject = null;
      }
    }
  }

  private static List<ImageSource> jsonObjectToImageSourceList(JSONArray paramJSONArray)
  {
    ArrayList localArrayList;
    try
    {
      localArrayList = new ArrayList();
      for (int i = 0; i < paramJSONArray.length(); i++)
      {
        JSONObject localJSONObject = paramJSONArray.getJSONObject(i);
        localArrayList.add(new ImageSource(localJSONObject.getString("#text"), localJSONObject.getString("size")));
      }
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
      localArrayList = null;
    }
    return localArrayList;
  }

  private static List<String> jsonObjectToTagList(JSONObject paramJSONObject)
  {
    try
    {
      ArrayList localArrayList = new ArrayList();
      Object localObject = paramJSONObject.get("tag");
      if ((localObject instanceof String))
        localArrayList.add((String)localObject);
      if ((localObject instanceof JSONArray))
        for (int i = 0; i < ((JSONArray)localObject).length(); i++)
          localArrayList.add(((JSONArray)localObject).getString(i));
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
      List<String> localList = null;
      return localList;
    }
  }

  public static Venue jsonObjectToVenue(JSONObject paramJSONObject)
  {
    try
    {
      Venue localVenue = new Venue(paramJSONObject.getInt("id"), paramJSONObject.getString("name"), jsonObjectToVenueLocation(paramJSONObject.getJSONObject("location")), paramJSONObject.getString("url"), paramJSONObject.getString("website"), paramJSONObject.getString("phonenumber"), jsonObjectToImageSourceList(paramJSONObject.getJSONArray("image")));
      return localVenue;
    }
    catch (JSONException localJSONException)
    {
      while (true)
      {
        localJSONException.printStackTrace();
        Object localObject = null;
      }
    }
  }

  private static VenueLocation jsonObjectToVenueLocation(JSONObject paramJSONObject)
  {
    try
    {
      VenueLocation localVenueLocation = new VenueLocation(jsonObjectToGeoPoint(paramJSONObject.getJSONObject("geo:point")), paramJSONObject.getString("city"), paramJSONObject.getString("street"), paramJSONObject.getString("postalcode"));
      return localVenueLocation;
    }
    catch (JSONException localJSONException)
    {
      while (true)
      {
        localJSONException.printStackTrace();
        Object localObject = null;
      }
    }
  }

  public static List<Event> responseToEventList(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    try
    {
      JSONArray localJSONArray = new JSONObject(paramString).getJSONObject("events").getJSONArray("event");
      for (int i = 0; i < localJSONArray.length(); i++)
      {
        JSONObject localJSONObject = localJSONArray.getJSONObject(i);
        Log.i(JSONUtils.class.getSimpleName(), localJSONObject.getString("title"));
        localArrayList.add(jsonObjectToEvent(localJSONObject));
      }
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
      localArrayList = null;
    }
    return localArrayList;
  }
}

/* Location:           /home/hernanjw/Desktop/LifeFM/classes_dex2jar.jar
 * Qualified Name:     com.example.lifefm.JSONUtils
 * JD-Core Version:    0.6.2
 */