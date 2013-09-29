package com.example.lifefm;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import java.util.List;

public class EventLoader extends AsyncTaskLoader<List<Event>>
{
  private final String API_KEY = "782e9b0e229abcb8a02edb69017141c8";
  private final String API_KEY_QUERY = "api_key=";
  private final String BASE_URL = "http://ws.audioscrobbler.com/2.0/";
  private final String FORMAT = "json";
  private final String FORMAT_QUERY = "&format=";
  private final String LOCATION = "boston";
  private final String LOCATION_QUERY = "location=";
  private final String METHOD = "geo.getevents";
  private final String METHOD_QUERY = "method=";
  List<Event> mEvents;

  public EventLoader(Context paramContext)
  {
    super(paramContext);
  }

  public void deliverResult(List<Event> paramList)
  {
    if ((isReset()) && (paramList != null))
      onReleaseResources(paramList);
    this.mEvents = paramList;
    if (isStarted())
      super.deliverResult(paramList);
    if (paramList != null)
      onReleaseResources(paramList);
  }

  public List<Event> loadInBackground()
  {
    return JSONUtils.responseToEventList(new HttpRetriever().retrieve("http://ws.audioscrobbler.com/2.0/?method=geo.getevents&location=boston&api_key=782e9b0e229abcb8a02edb69017141c8&&format=json"));
  }

  public void onCanceled(List<Event> paramList)
  {
    super.onCanceled(paramList);
    onReleaseResources(paramList);
  }

  protected void onReleaseResources(List<Event> paramList)
  {
  }

  protected void onReset()
  {
    super.onReset();
    onStopLoading();
    if (this.mEvents != null)
    {
      onReleaseResources(this.mEvents);
      this.mEvents = null;
    }
  }

  protected void onStartLoading()
  {
    if (this.mEvents != null)
      deliverResult(this.mEvents);
    if ((takeContentChanged()) || (this.mEvents == null))
      forceLoad();
  }

  protected void onStopLoading()
  {
    cancelLoad();
  }
}

/* Location:           /home/hernanjw/Desktop/LifeFM/classes_dex2jar.jar
 * Qualified Name:     com.example.lifefm.EventLoader
 * JD-Core Version:    0.6.2
 */