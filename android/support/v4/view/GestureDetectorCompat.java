package android.support.v4.view;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

public class GestureDetectorCompat
{
  private final GestureDetectorCompatImpl mImpl;

  public GestureDetectorCompat(Context paramContext, GestureDetector.OnGestureListener paramOnGestureListener)
  {
    this(paramContext, paramOnGestureListener, null);
  }

  public GestureDetectorCompat(Context paramContext, GestureDetector.OnGestureListener paramOnGestureListener, Handler paramHandler)
  {
    if (Build.VERSION.SDK_INT < 17)
      this.mImpl = new GestureDetectorCompatImplBase(paramContext, paramOnGestureListener, paramHandler);
    else
      this.mImpl = new GestureDetectorCompatImplJellybeanMr1(paramContext, paramOnGestureListener, paramHandler);
  }

  public boolean isLongpressEnabled()
  {
    return this.mImpl.isLongpressEnabled();
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    return this.mImpl.onTouchEvent(paramMotionEvent);
  }

  public void setIsLongpressEnabled(boolean paramBoolean)
  {
    this.mImpl.setIsLongpressEnabled(paramBoolean);
  }

  public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener paramOnDoubleTapListener)
  {
    this.mImpl.setOnDoubleTapListener(paramOnDoubleTapListener);
  }

  static class GestureDetectorCompatImplJellybeanMr1
    implements GestureDetectorCompat.GestureDetectorCompatImpl
  {
    private final GestureDetector mDetector;

    public GestureDetectorCompatImplJellybeanMr1(Context paramContext, GestureDetector.OnGestureListener paramOnGestureListener, Handler paramHandler)
    {
      this.mDetector = new GestureDetector(paramContext, paramOnGestureListener, paramHandler);
    }

    public boolean isLongpressEnabled()
    {
      return this.mDetector.isLongpressEnabled();
    }

    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
      return this.mDetector.onTouchEvent(paramMotionEvent);
    }

    public void setIsLongpressEnabled(boolean paramBoolean)
    {
      this.mDetector.setIsLongpressEnabled(paramBoolean);
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener paramOnDoubleTapListener)
    {
      this.mDetector.setOnDoubleTapListener(paramOnDoubleTapListener);
    }
  }

  static class GestureDetectorCompatImplBase
    implements GestureDetectorCompat.GestureDetectorCompatImpl
  {
    private static final int DOUBLE_TAP_TIMEOUT = 0;
    private static final int LONGPRESS_TIMEOUT = 0;
    private static final int LONG_PRESS = 2;
    private static final int SHOW_PRESS = 1;
    private static final int TAP = 3;
    private static final int TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
    private boolean mAlwaysInBiggerTapRegion;
    private boolean mAlwaysInTapRegion;
    private MotionEvent mCurrentDownEvent;
    private GestureDetector.OnDoubleTapListener mDoubleTapListener;
    private int mDoubleTapSlopSquare;
    private float mDownFocusX;
    private float mDownFocusY;
    private final Handler mHandler;
    private boolean mInLongPress;
    private boolean mIsDoubleTapping;
    private boolean mIsLongpressEnabled;
    private float mLastFocusX;
    private float mLastFocusY;
    private final GestureDetector.OnGestureListener mListener;
    private int mMaximumFlingVelocity;
    private int mMinimumFlingVelocity;
    private MotionEvent mPreviousUpEvent;
    private boolean mStillDown;
    private int mTouchSlopSquare;
    private VelocityTracker mVelocityTracker;

    public GestureDetectorCompatImplBase(Context paramContext, GestureDetector.OnGestureListener paramOnGestureListener, Handler paramHandler)
    {
      if (paramHandler == null)
        this.mHandler = new GestureHandler();
      else
        this.mHandler = new GestureHandler(paramHandler);
      this.mListener = paramOnGestureListener;
      if ((paramOnGestureListener instanceof GestureDetector.OnDoubleTapListener))
        setOnDoubleTapListener((GestureDetector.OnDoubleTapListener)paramOnGestureListener);
      init(paramContext);
    }

    private void cancel()
    {
      this.mHandler.removeMessages(1);
      this.mHandler.removeMessages(2);
      this.mHandler.removeMessages(3);
      this.mVelocityTracker.recycle();
      this.mVelocityTracker = null;
      this.mIsDoubleTapping = false;
      this.mStillDown = false;
      this.mAlwaysInTapRegion = false;
      this.mAlwaysInBiggerTapRegion = false;
      if (this.mInLongPress)
        this.mInLongPress = false;
    }

    private void cancelTaps()
    {
      this.mHandler.removeMessages(1);
      this.mHandler.removeMessages(2);
      this.mHandler.removeMessages(3);
      this.mIsDoubleTapping = false;
      this.mAlwaysInTapRegion = false;
      this.mAlwaysInBiggerTapRegion = false;
      if (this.mInLongPress)
        this.mInLongPress = false;
    }

    private void dispatchLongPress()
    {
      this.mHandler.removeMessages(3);
      this.mInLongPress = true;
      this.mListener.onLongPress(this.mCurrentDownEvent);
    }

    private void init(Context paramContext)
    {
      if (paramContext != null)
      {
        if (this.mListener != null)
        {
          this.mIsLongpressEnabled = true;
          ViewConfiguration localViewConfiguration = ViewConfiguration.get(paramContext);
          int i = localViewConfiguration.getScaledTouchSlop();
          int j = localViewConfiguration.getScaledDoubleTapSlop();
          this.mMinimumFlingVelocity = localViewConfiguration.getScaledMinimumFlingVelocity();
          this.mMaximumFlingVelocity = localViewConfiguration.getScaledMaximumFlingVelocity();
          this.mTouchSlopSquare = (i * i);
          this.mDoubleTapSlopSquare = (j * j);
          return;
        }
        throw new IllegalArgumentException("OnGestureListener must not be null");
      }
      throw new IllegalArgumentException("Context must not be null");
    }

    private boolean isConsideredDoubleTap(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, MotionEvent paramMotionEvent3)
    {
      boolean bool = false;
      if ((this.mAlwaysInBiggerTapRegion) && (paramMotionEvent3.getEventTime() - paramMotionEvent2.getEventTime() <= DOUBLE_TAP_TIMEOUT))
      {
        int j = (int)paramMotionEvent1.getX() - (int)paramMotionEvent3.getX();
        int i = (int)paramMotionEvent1.getY() - (int)paramMotionEvent3.getY();
        if (j * j + i * i < this.mDoubleTapSlopSquare)
          bool = true;
      }
      return bool;
    }

    public boolean isLongpressEnabled()
    {
      return this.mIsLongpressEnabled;
    }

    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
      int k = paramMotionEvent.getAction();
      if (this.mVelocityTracker == null)
        this.mVelocityTracker = VelocityTracker.obtain();
      this.mVelocityTracker.addMovement(paramMotionEvent);
      int n;
      if ((k & 0xFF) != 6)
        n = 0;
      else
        n = 1;
      int m;
      if (n == 0)
        m = -1;
      else
        m = MotionEventCompat.getActionIndex(paramMotionEvent);
      float f4 = 0.0F;
      float f1 = 0.0F;
      int i = MotionEventCompat.getPointerCount(paramMotionEvent);
      for (float f10 = 0; ; f10++)
      {
        boolean bool1;
        float f8;
        int j;
        if (f10 >= i)
        {
          if (n == 0)
            m = i;
          else
            m = i - 1;
          f4 /= m;
          float f7 = f1 / m;
          bool1 = false;
          float f6;
          float f3;
          switch (k & 0xFF)
          {
          case 0:
            if (this.mDoubleTapListener != null)
            {
              boolean bool2 = this.mHandler.hasMessages(3);
              if (bool2)
                this.mHandler.removeMessages(3);
              if ((this.mCurrentDownEvent == null) || (this.mPreviousUpEvent == null) || (!bool2) || (!isConsideredDoubleTap(this.mCurrentDownEvent, this.mPreviousUpEvent, paramMotionEvent)))
              {
                this.mHandler.sendEmptyMessageDelayed(3, DOUBLE_TAP_TIMEOUT);
              }
              else
              {
                this.mIsDoubleTapping = true;
                bool1 = false | this.mDoubleTapListener.onDoubleTap(this.mCurrentDownEvent) | this.mDoubleTapListener.onDoubleTapEvent(paramMotionEvent);
              }
            }
            this.mLastFocusX = f4;
            this.mDownFocusX = f4;
            this.mLastFocusY = f7;
            this.mDownFocusY = f7;
            if (this.mCurrentDownEvent != null)
              this.mCurrentDownEvent.recycle();
            this.mCurrentDownEvent = MotionEvent.obtain(paramMotionEvent);
            this.mAlwaysInTapRegion = true;
            this.mAlwaysInBiggerTapRegion = true;
            this.mStillDown = true;
            this.mInLongPress = false;
            if (this.mIsLongpressEnabled)
            {
              this.mHandler.removeMessages(2);
              this.mHandler.sendEmptyMessageAtTime(2, this.mCurrentDownEvent.getDownTime() + TAP_TIMEOUT + LONGPRESS_TIMEOUT);
            }
            this.mHandler.sendEmptyMessageAtTime(1, this.mCurrentDownEvent.getDownTime() + TAP_TIMEOUT);
            bool1 |= this.mListener.onDown(paramMotionEvent);
            break;
          case 1:
            this.mStillDown = false;
            MotionEvent localMotionEvent = MotionEvent.obtain(paramMotionEvent);
            if (!this.mIsDoubleTapping)
            {
              if (!this.mInLongPress)
              {
                if (!this.mAlwaysInTapRegion)
                {
                  VelocityTracker localVelocityTracker = this.mVelocityTracker;
                  f8 = MotionEventCompat.getPointerId(paramMotionEvent, 0);
                  localVelocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
                  f4 = VelocityTrackerCompat.getYVelocity(localVelocityTracker, f8);
                  f6 = VelocityTrackerCompat.getXVelocity(localVelocityTracker, f8);
                  if ((Math.abs(f4) > this.mMinimumFlingVelocity) || (Math.abs(f6) > this.mMinimumFlingVelocity))
                    bool1 = this.mListener.onFling(this.mCurrentDownEvent, paramMotionEvent, f6, f4);
                }
                else
                {
                  bool1 = this.mListener.onSingleTapUp(paramMotionEvent);
                }
              }
              else
              {
                this.mHandler.removeMessages(3);
                this.mInLongPress = false;
              }
            }
            else
              bool1 = false | this.mDoubleTapListener.onDoubleTapEvent(paramMotionEvent);
            if (this.mPreviousUpEvent != null)
              this.mPreviousUpEvent.recycle();
            this.mPreviousUpEvent = localMotionEvent;
            if (this.mVelocityTracker != null)
            {
              this.mVelocityTracker.recycle();
              this.mVelocityTracker = null;
            }
            this.mIsDoubleTapping = false;
            this.mHandler.removeMessages(1);
            this.mHandler.removeMessages(2);
            break;
          case 2:
            if (!this.mInLongPress)
            {
              f6 = this.mLastFocusX - f4;
              f3 = this.mLastFocusY - f8;
              if (!this.mIsDoubleTapping)
              {
                if (!this.mAlwaysInTapRegion)
                {
                  if ((Math.abs(f6) >= 1.0F) || (Math.abs(f3) >= 1.0F))
                  {
                    bool1 = this.mListener.onScroll(this.mCurrentDownEvent, paramMotionEvent, f6, f3);
                    this.mLastFocusX = f4;
                    this.mLastFocusY = f8;
                  }
                }
                else
                {
                  f10 = (int)(f4 - this.mDownFocusX);
                  n = (int)(f8 - this.mDownFocusY);
                  n = f10 * f10 + n * n;
                  if (n > this.mTouchSlopSquare)
                  {
                    bool1 = this.mListener.onScroll(this.mCurrentDownEvent, paramMotionEvent, f6, f3);
                    this.mLastFocusX = f4;
                    this.mLastFocusY = f8;
                    this.mAlwaysInTapRegion = false;
                    this.mHandler.removeMessages(3);
                    this.mHandler.removeMessages(1);
                    this.mHandler.removeMessages(2);
                  }
                  if (n > this.mTouchSlopSquare)
                    this.mAlwaysInBiggerTapRegion = false;
                }
              }
              else
                bool1 = false | this.mDoubleTapListener.onDoubleTapEvent(paramMotionEvent);
            }
            break;
          case 3:
            cancel();
            break;
          case 5:
            this.mLastFocusX = f4;
            this.mDownFocusX = f4;
            this.mLastFocusY = f8;
            this.mDownFocusY = f8;
            cancelTaps();
            break;
          case 6:
            this.mLastFocusX = f4;
            this.mDownFocusX = f4;
            this.mLastFocusY = f8;
            this.mDownFocusY = f8;
            this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
            j = MotionEventCompat.getActionIndex(paramMotionEvent);
            f8 = MotionEventCompat.getPointerId(paramMotionEvent, j);
            f6 = VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, f8);
            float f9 = VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, f8);
            f8 = 0;
            while (f8 < f3)
            {
              if (f8 != j)
              {
                f10 = MotionEventCompat.getPointerId(paramMotionEvent, f8);
                if (f6 * VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, f10) + f9 * VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, f10) < 0.0F);
              }
              else
              {
                f8++;
                continue;
              }
              this.mVelocityTracker.clear();
            }
          case 4:
          }
          return bool1;
        }
        if (f8 != f10)
        {
          float f5;
          j += MotionEventCompat.getX(paramMotionEvent, f10);
          float f2;
          bool1 += MotionEventCompat.getY(paramMotionEvent, f10);
        }
      }
    }

    public void setIsLongpressEnabled(boolean paramBoolean)
    {
      this.mIsLongpressEnabled = paramBoolean;
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener paramOnDoubleTapListener)
    {
      this.mDoubleTapListener = paramOnDoubleTapListener;
    }

    private class GestureHandler extends Handler
    {
      GestureHandler()
      {
      }

      GestureHandler(Handler arg2)
      {
        super();
      }

      public void handleMessage(Message paramMessage)
      {
        switch (paramMessage.what)
        {
        default:
          throw new RuntimeException("Unknown message " + paramMessage);
        case 1:
          GestureDetectorCompat.GestureDetectorCompatImplBase.this.mListener.onShowPress(GestureDetectorCompat.GestureDetectorCompatImplBase.this.mCurrentDownEvent);
          break;
        case 2:
          GestureDetectorCompat.GestureDetectorCompatImplBase.this.dispatchLongPress();
          break;
        case 3:
          if ((GestureDetectorCompat.GestureDetectorCompatImplBase.this.mDoubleTapListener != null) && (!GestureDetectorCompat.GestureDetectorCompatImplBase.this.mStillDown))
            GestureDetectorCompat.GestureDetectorCompatImplBase.this.mDoubleTapListener.onSingleTapConfirmed(GestureDetectorCompat.GestureDetectorCompatImplBase.this.mCurrentDownEvent);
          break;
        }
      }
    }
  }

  static abstract interface GestureDetectorCompatImpl
  {
    public abstract boolean isLongpressEnabled();

    public abstract boolean onTouchEvent(MotionEvent paramMotionEvent);

    public abstract void setIsLongpressEnabled(boolean paramBoolean);

    public abstract void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener paramOnDoubleTapListener);
  }
}

/* Location:           /home/hernanjw/Desktop/LifeFM/classes_dex2jar.jar
 * Qualified Name:     android.support.v4.view.GestureDetectorCompat
 * JD-Core Version:    0.6.2
 */