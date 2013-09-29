package com.example.lifefm;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FlushedInputStream extends FilterInputStream
{
  public FlushedInputStream(InputStream paramInputStream)
  {
    super(paramInputStream);
  }

  public long skip(long paramLong)
    throws IOException
  {
    long l1;
    for (long l2 = 0L; ; l2 += l1)
    {
      if (l2 < paramLong)
      {
        l1 = this.in.skip(paramLong - l2);
        if (l1 != 0L)
          continue;
        if (read() >= 0);
      }
      else
      {
        return l2;
      }
      l1 = 1L;
    }
  }
}

/* Location:           /home/hernanjw/Desktop/LifeFM/classes_dex2jar.jar
 * Qualified Name:     com.example.lifefm.FlushedInputStream
 * JD-Core Version:    0.6.2
 */