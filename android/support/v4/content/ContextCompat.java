package android.support.v4.content;

import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;

public class ContextCompat
{
  public static boolean startActivities(Context paramContext, Intent[] paramArrayOfIntent)
  {
    return startActivities(paramContext, paramArrayOfIntent, null);
  }

  public static boolean startActivities(Context paramContext, Intent[] paramArrayOfIntent, Bundle paramBundle)
  {
    boolean bool = true;
    int i = Build.VERSION.SDK_INT;
    if (i < 16)
    {
      if (i < 11)
        bool = false;
      else
        ContextCompatHoneycomb.startActivities(paramContext, paramArrayOfIntent);
    }
    else
      ContextCompatJellybean.startActivities(paramContext, paramArrayOfIntent, paramBundle);
    return bool;
  }
}

/* Location:           /home/hernanjw/Desktop/LifeFM/classes_dex2jar.jar
 * Qualified Name:     android.support.v4.content.ContextCompat
 * JD-Core Version:    0.6.2
 */