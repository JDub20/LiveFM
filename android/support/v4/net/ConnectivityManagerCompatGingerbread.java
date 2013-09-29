package android.support.v4.net;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

class ConnectivityManagerCompatGingerbread
{
  public static boolean isActiveNetworkMetered(ConnectivityManager paramConnectivityManager)
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
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      }
    return bool;
  }
}

/* Location:           /home/hernanjw/Desktop/LifeFM/classes_dex2jar.jar
 * Qualified Name:     android.support.v4.net.ConnectivityManagerCompatGingerbread
 * JD-Core Version:    0.6.2
 */