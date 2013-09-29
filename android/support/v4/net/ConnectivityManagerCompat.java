package android.support.v4.net;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;

public class ConnectivityManagerCompat
{
  private static final ConnectivityManagerCompatImpl IMPL;

  static
  {
    if (Build.VERSION.SDK_INT < 16)
    {
      if (Build.VERSION.SDK_INT < 13)
      {
        if (Build.VERSION.SDK_INT < 8)
          IMPL = new BaseConnectivityManagerCompatImpl();
        else
          IMPL = new GingerbreadConnectivityManagerCompatImpl();
      }
      else
        IMPL = new HoneycombMR2ConnectivityManagerCompatImpl();
    }
    else
      IMPL = new JellyBeanConnectivityManagerCompatImpl();
  }

  public static NetworkInfo getNetworkInfoFromBroadcast(ConnectivityManager paramConnectivityManager, Intent paramIntent)
  {
    return paramConnectivityManager.getNetworkInfo(((NetworkInfo)paramIntent.getParcelableExtra("networkInfo")).getType());
  }

  public static boolean isActiveNetworkMetered(ConnectivityManager paramConnectivityManager)
  {
    return IMPL.isActiveNetworkMetered(paramConnectivityManager);
  }

  static class JellyBeanConnectivityManagerCompatImpl
    implements ConnectivityManagerCompat.ConnectivityManagerCompatImpl
  {
    public boolean isActiveNetworkMetered(ConnectivityManager paramConnectivityManager)
    {
      return ConnectivityManagerCompatJellyBean.isActiveNetworkMetered(paramConnectivityManager);
    }
  }

  static class HoneycombMR2ConnectivityManagerCompatImpl
    implements ConnectivityManagerCompat.ConnectivityManagerCompatImpl
  {
    public boolean isActiveNetworkMetered(ConnectivityManager paramConnectivityManager)
    {
      return ConnectivityManagerCompatHoneycombMR2.isActiveNetworkMetered(paramConnectivityManager);
    }
  }

  static class GingerbreadConnectivityManagerCompatImpl
    implements ConnectivityManagerCompat.ConnectivityManagerCompatImpl
  {
    public boolean isActiveNetworkMetered(ConnectivityManager paramConnectivityManager)
    {
      return ConnectivityManagerCompatGingerbread.isActiveNetworkMetered(paramConnectivityManager);
    }
  }

  static class BaseConnectivityManagerCompatImpl
    implements ConnectivityManagerCompat.ConnectivityManagerCompatImpl
  {
    public boolean isActiveNetworkMetered(ConnectivityManager paramConnectivityManager)
    {
      boolean bool = true;
      NetworkInfo localNetworkInfo = paramConnectivityManager.getActiveNetworkInfo();
      if (localNetworkInfo != null)
        switch (localNetworkInfo.getType())
        {
        default:
          break;
        case 1:
          bool = false;
        case 0:
        }
      return bool;
    }
  }

  static abstract interface ConnectivityManagerCompatImpl
  {
    public abstract boolean isActiveNetworkMetered(ConnectivityManager paramConnectivityManager);
  }
}

/* Location:           /home/hernanjw/Desktop/LifeFM/classes_dex2jar.jar
 * Qualified Name:     android.support.v4.net.ConnectivityManagerCompat
 * JD-Core Version:    0.6.2
 */