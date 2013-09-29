package android.support.v4.accessibilityservice;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;

public class AccessibilityServiceInfoCompat
{
  public static final int FEEDBACK_ALL_MASK = -1;
  private static final AccessibilityServiceInfoVersionImpl IMPL;

  static
  {
    if (Build.VERSION.SDK_INT < 14)
      IMPL = new AccessibilityServiceInfoStubImpl();
    else
      IMPL = new AccessibilityServiceInfoIcsImpl();
  }

  public static String feedbackTypeToString(int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("[");
    while (true)
    {
      if (paramInt <= 0)
      {
        localStringBuilder.append("]");
        return localStringBuilder.toString();
      }
      int i = 1 << Integer.numberOfTrailingZeros(paramInt);
      paramInt &= (i ^ 0xFFFFFFFF);
      if (localStringBuilder.length() > 1)
        localStringBuilder.append(", ");
      switch (i)
      {
      default:
        break;
      case 1:
        localStringBuilder.append("FEEDBACK_SPOKEN");
        break;
      case 2:
        localStringBuilder.append("FEEDBACK_HAPTIC");
        break;
      case 4:
        localStringBuilder.append("FEEDBACK_AUDIBLE");
        break;
      case 8:
        localStringBuilder.append("FEEDBACK_VISUAL");
        break;
      case 16:
        localStringBuilder.append("FEEDBACK_GENERIC");
      }
    }
  }

  public static String flagToString(int paramInt)
  {
    String str;
    switch (paramInt)
    {
    default:
      str = null;
      break;
    case 1:
      str = "DEFAULT";
    }
    return str;
  }

  public static boolean getCanRetrieveWindowContent(AccessibilityServiceInfo paramAccessibilityServiceInfo)
  {
    return IMPL.getCanRetrieveWindowContent(paramAccessibilityServiceInfo);
  }

  public static String getDescription(AccessibilityServiceInfo paramAccessibilityServiceInfo)
  {
    return IMPL.getDescription(paramAccessibilityServiceInfo);
  }

  public static String getId(AccessibilityServiceInfo paramAccessibilityServiceInfo)
  {
    return IMPL.getId(paramAccessibilityServiceInfo);
  }

  public static ResolveInfo getResolveInfo(AccessibilityServiceInfo paramAccessibilityServiceInfo)
  {
    return IMPL.getResolveInfo(paramAccessibilityServiceInfo);
  }

  public static String getSettingsActivityName(AccessibilityServiceInfo paramAccessibilityServiceInfo)
  {
    return IMPL.getSettingsActivityName(paramAccessibilityServiceInfo);
  }

  static class AccessibilityServiceInfoIcsImpl extends AccessibilityServiceInfoCompat.AccessibilityServiceInfoStubImpl
  {
    public boolean getCanRetrieveWindowContent(AccessibilityServiceInfo paramAccessibilityServiceInfo)
    {
      return AccessibilityServiceInfoCompatIcs.getCanRetrieveWindowContent(paramAccessibilityServiceInfo);
    }

    public String getDescription(AccessibilityServiceInfo paramAccessibilityServiceInfo)
    {
      return AccessibilityServiceInfoCompatIcs.getDescription(paramAccessibilityServiceInfo);
    }

    public String getId(AccessibilityServiceInfo paramAccessibilityServiceInfo)
    {
      return AccessibilityServiceInfoCompatIcs.getId(paramAccessibilityServiceInfo);
    }

    public ResolveInfo getResolveInfo(AccessibilityServiceInfo paramAccessibilityServiceInfo)
    {
      return AccessibilityServiceInfoCompatIcs.getResolveInfo(paramAccessibilityServiceInfo);
    }

    public String getSettingsActivityName(AccessibilityServiceInfo paramAccessibilityServiceInfo)
    {
      return AccessibilityServiceInfoCompatIcs.getSettingsActivityName(paramAccessibilityServiceInfo);
    }
  }

  static class AccessibilityServiceInfoStubImpl
    implements AccessibilityServiceInfoCompat.AccessibilityServiceInfoVersionImpl
  {
    public boolean getCanRetrieveWindowContent(AccessibilityServiceInfo paramAccessibilityServiceInfo)
    {
      return false;
    }

    public String getDescription(AccessibilityServiceInfo paramAccessibilityServiceInfo)
    {
      return null;
    }

    public String getId(AccessibilityServiceInfo paramAccessibilityServiceInfo)
    {
      return null;
    }

    public ResolveInfo getResolveInfo(AccessibilityServiceInfo paramAccessibilityServiceInfo)
    {
      return null;
    }

    public String getSettingsActivityName(AccessibilityServiceInfo paramAccessibilityServiceInfo)
    {
      return null;
    }
  }

  static abstract interface AccessibilityServiceInfoVersionImpl
  {
    public abstract boolean getCanRetrieveWindowContent(AccessibilityServiceInfo paramAccessibilityServiceInfo);

    public abstract String getDescription(AccessibilityServiceInfo paramAccessibilityServiceInfo);

    public abstract String getId(AccessibilityServiceInfo paramAccessibilityServiceInfo);

    public abstract ResolveInfo getResolveInfo(AccessibilityServiceInfo paramAccessibilityServiceInfo);

    public abstract String getSettingsActivityName(AccessibilityServiceInfo paramAccessibilityServiceInfo);
  }
}

/* Location:           /home/hernanjw/Desktop/LifeFM/classes_dex2jar.jar
 * Qualified Name:     android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat
 * JD-Core Version:    0.6.2
 */