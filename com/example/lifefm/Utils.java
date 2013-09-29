package com.example.lifefm;

import java.io.IOException;
import java.io.InputStream;

public class Utils
{
  public static void closeStreamQuietly(InputStream paramInputStream)
  {
    if (paramInputStream != null);
    try
    {
      paramInputStream.close();
      label8: return;
    }
    catch (IOException localIOException)
    {
      break label8;
    }
  }
}

/* Location:           /home/hernanjw/Desktop/LifeFM/classes_dex2jar.jar
 * Qualified Name:     com.example.lifefm.Utils
 * JD-Core Version:    0.6.2
 */