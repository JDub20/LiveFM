package com.example.lifefm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.Iterator;
import java.util.List;

public class EventListFragment extends ListFragment
  implements LoaderManager.LoaderCallbacks<List<Event>>
{
  EventListAdapter mAdapter;

  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    setEmptyText("No concerts");
    setHasOptionsMenu(true);
    this.mAdapter = new EventListAdapter(getActivity());
    setListAdapter(this.mAdapter);
    setListShown(false);
    getLoaderManager().initLoader(0, null, this);
  }

  public Loader<List<Event>> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    return new EventLoader(getActivity());
  }

  public void onListItemClick(ListView paramListView, View paramView, int paramInt, long paramLong)
  {
    Intent localIntent = new Intent(getActivity(), EventHomeActivity.class);
    Event localEvent = (Event)paramListView.getAdapter().getItem(paramInt);
    Iterator localIterator = localEvent.getImages().iterator();
    Object localObject;
    while (localIterator.hasNext())
    {
      localObject = (ImageSource)localIterator.next();
      Log.i(getClass().getSimpleName(), "images: " + ((ImageSource)localObject).getSize());
      Log.i(getClass().getSimpleName(), "images: " + ((ImageSource)localObject).getSrc());
    }
    Log.i(JSONUtils.class.getSimpleName(), localEvent.getPrettyTags());
    localIterator = localEvent.getTags().iterator();
    while (localIterator.hasNext())
    {
      localObject = (String)localIterator.next();
      Log.i(JSONUtils.class.getSimpleName(), "tag: " + (String)localObject);
    }
    localIntent.putExtra("EVENT", localEvent);
    startActivity(localIntent);
  }

  public void onLoadFinished(Loader<List<Event>> paramLoader, List<Event> paramList)
  {
    this.mAdapter.clear();
    if (paramList != null)
    {
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        Event localEvent = (Event)localIterator.next();
        this.mAdapter.add(localEvent);
      }
    }
    if (!isResumed())
      setListShownNoAnimation(true);
    else
      setListShown(true);
  }

  public void onLoaderReset(Loader<List<Event>> paramLoader)
  {
    this.mAdapter.clear();
  }
}

/* Location:           /home/hernanjw/Desktop/LifeFM/classes_dex2jar.jar
 * Qualified Name:     com.example.lifefm.EventListFragment
 * JD-Core Version:    0.6.2
 */