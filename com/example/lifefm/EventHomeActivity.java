package com.example.lifefm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.TextView;
import java.net.URL;
import java.util.List;

public class EventHomeActivity extends Activity
{
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903040);
    Event localEvent = (Event)getIntent().getExtras().get("EVENT");
    ((TextView)findViewById(2131296256)).setText(localEvent.getTitle());
    DownloadImageTask localDownloadImageTask = new DownloadImageTask((ImageView)findViewById(2131296257));
    String[] arrayOfString = new String[1];
    arrayOfString[0] = ((ImageSource)localEvent.getImages().get(3)).getSrc();
    localDownloadImageTask.execute(arrayOfString);
    ((TextView)findViewById(2131296258)).setText(localEvent.getVenue().getName());
    ((TextView)findViewById(2131296259)).setText(localEvent.getPrettyDate());
    ((TextView)findViewById(2131296260)).setText(localEvent.getPrettyTags());
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131230720, paramMenu);
    return true;
  }

  private class DownloadImageTask extends AsyncTask<String, Void, Bitmap>
  {
    ImageView bmImage;

    public DownloadImageTask(ImageView arg2)
    {
      Object localObject;
      this.bmImage = localObject;
    }

    protected Bitmap doInBackground(String[] paramArrayOfString)
    {
      String str = paramArrayOfString[0];
      Bitmap localBitmap = null;
      try
      {
        localBitmap = BitmapFactory.decodeStream(new URL(str).openStream());
        localBitmap = localBitmap;
        return localBitmap;
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }

    protected void onPostExecute(Bitmap paramBitmap)
    {
      this.bmImage.setImageBitmap(paramBitmap);
    }
  }
}

/* Location:           /home/hernanjw/Desktop/LifeFM/classes_dex2jar.jar
 * Qualified Name:     com.example.lifefm.EventHomeActivity
 * JD-Core Version:    0.6.2
 */