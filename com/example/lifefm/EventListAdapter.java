package com.example.lifefm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.Iterator;
import java.util.List;

public class EventListAdapter extends ArrayAdapter<Event>
{
  private final LayoutInflater mInflater;

  public EventListAdapter(Context paramContext)
  {
    super(paramContext, 17367043);
    this.mInflater = ((LayoutInflater)paramContext.getSystemService("layout_inflater"));
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    View localView;
    if (paramView != null)
      localView = paramView;
    else
      localView = this.mInflater.inflate(2130903043, paramViewGroup, false);
    Event localEvent = (Event)getItem(paramInt);
    ((TextView)localView.findViewById(2131296256)).setText(localEvent.getTitle() + ":");
    ((TextView)localView.findViewById(2131296263)).setText(localEvent.getPrettyDate());
    return localView;
  }

  public void setData(List<Event> paramList)
  {
    clear();
    if (paramList != null)
    {
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
        add((Event)localIterator.next());
    }
  }
}

/* Location:           /home/hernanjw/Desktop/LifeFM/classes_dex2jar.jar
 * Qualified Name:     com.example.lifefm.EventListAdapter
 * JD-Core Version:    0.6.2
 */