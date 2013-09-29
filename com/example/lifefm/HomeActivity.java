package com.example.lifefm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

public class HomeActivity extends Activity
{
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903042);
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131230722, paramMenu);
    return true;
  }

  public void sendMessage(View paramView)
  {
    startActivity(new Intent(this, EventListActivity.class));
  }
}

/* Location:           /home/hernanjw/Desktop/LifeFM/classes_dex2jar.jar
 * Qualified Name:     com.example.lifefm.HomeActivity
 * JD-Core Version:    0.6.2
 */