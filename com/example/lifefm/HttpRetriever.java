package com.example.lifefm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpRetriever
{
  private DefaultHttpClient client = new DefaultHttpClient();

  public String retrieve(String paramString)
  {
    Object localObject2 = null;
    Object localObject1 = new HttpGet(paramString);
    try
    {
      HttpResponse localHttpResponse = this.client.execute((HttpUriRequest)localObject1);
      int i = localHttpResponse.getStatusLine().getStatusCode();
      if (i != 200)
      {
        Log.w(getClass().getSimpleName(), "Error " + i + " for URL " + paramString);
      }
      else
      {
        HttpEntity localHttpEntity = localHttpResponse.getEntity();
        if (localHttpEntity != null)
        {
          localObject1 = EntityUtils.toString(localHttpEntity);
          localObject2 = localObject1;
        }
      }
    }
    catch (IOException localIOException)
    {
      ((HttpGet)localObject1).abort();
      Log.w(getClass().getSimpleName(), "Error for URL " + paramString, localIOException);
    }
    return localObject2;
  }

  public Bitmap retrieveBitmap(String paramString)
    throws Exception
  {
    InputStream localInputStream = null;
    try
    {
      localInputStream = retrieveStream(paramString);
      Bitmap localBitmap = BitmapFactory.decodeStream(new FlushedInputStream(localInputStream));
      return localBitmap;
    }
    finally
    {
      Utils.closeStreamQuietly(localInputStream);
    }
  }

  public InputStream retrieveStream(String paramString)
  {
    InputStream localInputStream = null;
    HttpGet localHttpGet = new HttpGet(paramString);
    try
    {
      HttpResponse localHttpResponse = this.client.execute(localHttpGet);
      int i = localHttpResponse.getStatusLine().getStatusCode();
      if (i != 200)
      {
        Log.w(getClass().getSimpleName(), "Error " + i + " for URL " + paramString);
      }
      else
      {
        localInputStream = localHttpResponse.getEntity().getContent();
        localInputStream = localInputStream;
      }
    }
    catch (IOException localIOException)
    {
      localHttpGet.abort();
      Log.w(getClass().getSimpleName(), "Error for URL " + paramString, localIOException);
    }
    return localInputStream;
  }
}

/* Location:           /home/hernanjw/Desktop/LifeFM/classes_dex2jar.jar
 * Qualified Name:     com.example.lifefm.HttpRetriever
 * JD-Core Version:    0.6.2
 */