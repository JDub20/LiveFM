package android.support.v4.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;

final class BackStackState
  implements Parcelable
{
  public static final Parcelable.Creator<BackStackState> CREATOR = new Parcelable.Creator()
  {
    public BackStackState createFromParcel(Parcel paramAnonymousParcel)
    {
      return new BackStackState(paramAnonymousParcel);
    }

    public BackStackState[] newArray(int paramAnonymousInt)
    {
      return new BackStackState[paramAnonymousInt];
    }
  };
  final int mBreadCrumbShortTitleRes;
  final CharSequence mBreadCrumbShortTitleText;
  final int mBreadCrumbTitleRes;
  final CharSequence mBreadCrumbTitleText;
  final int mIndex;
  final String mName;
  final int[] mOps;
  final int mTransition;
  final int mTransitionStyle;

  public BackStackState(Parcel paramParcel)
  {
    this.mOps = paramParcel.createIntArray();
    this.mTransition = paramParcel.readInt();
    this.mTransitionStyle = paramParcel.readInt();
    this.mName = paramParcel.readString();
    this.mIndex = paramParcel.readInt();
    this.mBreadCrumbTitleRes = paramParcel.readInt();
    this.mBreadCrumbTitleText = ((CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(paramParcel));
    this.mBreadCrumbShortTitleRes = paramParcel.readInt();
    this.mBreadCrumbShortTitleText = ((CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(paramParcel));
  }

  public BackStackState(FragmentManagerImpl paramFragmentManagerImpl, BackStackRecord paramBackStackRecord)
  {
    int i = 0;
    for (BackStackRecord.Op localOp = paramBackStackRecord.mHead; ; localOp = localOp.next)
    {
      int j;
      if (localOp == null)
      {
        this.mOps = new int[i + 7 * paramBackStackRecord.mNumOp];
        if (paramBackStackRecord.mAddToBackStack)
        {
          localOp = paramBackStackRecord.mHead;
          i = 0;
          if (localOp == null)
          {
            this.mTransition = paramBackStackRecord.mTransition;
            this.mTransitionStyle = paramBackStackRecord.mTransitionStyle;
            this.mName = paramBackStackRecord.mName;
            this.mIndex = paramBackStackRecord.mIndex;
            this.mBreadCrumbTitleRes = paramBackStackRecord.mBreadCrumbTitleRes;
            this.mBreadCrumbTitleText = paramBackStackRecord.mBreadCrumbTitleText;
            this.mBreadCrumbShortTitleRes = paramBackStackRecord.mBreadCrumbShortTitleRes;
            this.mBreadCrumbShortTitleText = paramBackStackRecord.mBreadCrumbShortTitleText;
            return;
          }
          int[] arrayOfInt3 = this.mOps;
          int k = i + 1;
          arrayOfInt3[i] = localOp.cmd;
          int[] arrayOfInt6 = this.mOps;
          i = k + 1;
          int n;
          if (localOp.fragment == null)
            n = -1;
          else
            n = localOp.fragment.mIndex;
          arrayOfInt6[k] = n;
          int[] arrayOfInt4 = this.mOps;
          k = i + 1;
          arrayOfInt4[i] = localOp.enterAnim;
          arrayOfInt4 = this.mOps;
          i = k + 1;
          arrayOfInt4[k] = localOp.exitAnim;
          int[] arrayOfInt2 = this.mOps;
          int i1 = i + 1;
          arrayOfInt2[i] = localOp.popEnterAnim;
          int[] arrayOfInt1 = this.mOps;
          int m = i1 + 1;
          arrayOfInt1[i1] = localOp.popExitAnim;
          if (localOp.removed == null)
          {
            int[] arrayOfInt5 = this.mOps;
            j = m + 1;
            arrayOfInt5[m] = 0;
          }
          else
          {
            j = localOp.removed.size();
            arrayOfInt6 = this.mOps;
            i2 = m + 1;
            arrayOfInt6[m] = j;
            m = 0;
          }
          int i3;
          for (int i2 = i2; ; i2 = i3)
          {
            if (m >= j)
            {
              j = i2;
              localOp = localOp.next;
              j = j;
              break;
            }
            int[] arrayOfInt7 = this.mOps;
            i3 = i2 + 1;
            arrayOfInt7[i2] = ((Fragment)localOp.removed.get(m)).mIndex;
            m++;
          }
        }
        throw new IllegalStateException("Not on back stack");
      }
      if (localOp.removed != null)
        j += localOp.removed.size();
    }
  }

  public int describeContents()
  {
    return 0;
  }

  public BackStackRecord instantiate(FragmentManagerImpl paramFragmentManagerImpl)
  {
    BackStackRecord localBackStackRecord = new BackStackRecord(paramFragmentManagerImpl);
    int j = 0;
    int i = 0;
    if (j >= this.mOps.length)
    {
      localBackStackRecord.mTransition = this.mTransition;
      localBackStackRecord.mTransitionStyle = this.mTransitionStyle;
      localBackStackRecord.mName = this.mName;
      localBackStackRecord.mIndex = this.mIndex;
      localBackStackRecord.mAddToBackStack = true;
      localBackStackRecord.mBreadCrumbTitleRes = this.mBreadCrumbTitleRes;
      localBackStackRecord.mBreadCrumbTitleText = this.mBreadCrumbTitleText;
      localBackStackRecord.mBreadCrumbShortTitleRes = this.mBreadCrumbShortTitleRes;
      localBackStackRecord.mBreadCrumbShortTitleText = this.mBreadCrumbShortTitleText;
      localBackStackRecord.bumpBackStackNesting(1);
      return localBackStackRecord;
    }
    BackStackRecord.Op localOp = new BackStackRecord.Op();
    int[] arrayOfInt3 = this.mOps;
    int m = j + 1;
    localOp.cmd = arrayOfInt3[j];
    if (FragmentManagerImpl.DEBUG)
      Log.v("FragmentManager", "Instantiate " + localBackStackRecord + " op #" + i + " base fragment #" + this.mOps[m]);
    arrayOfInt3 = this.mOps;
    j = m + 1;
    m = arrayOfInt3[m];
    if (m < 0)
      localOp.fragment = null;
    else
      localOp.fragment = ((Fragment)paramFragmentManagerImpl.mActive.get(m));
    arrayOfInt3 = this.mOps;
    m = j + 1;
    localOp.enterAnim = arrayOfInt3[j];
    arrayOfInt3 = this.mOps;
    j = m + 1;
    localOp.exitAnim = arrayOfInt3[m];
    int[] arrayOfInt2 = this.mOps;
    int n = j + 1;
    localOp.popEnterAnim = arrayOfInt2[j];
    int[] arrayOfInt1 = this.mOps;
    Fragment localFragment2 = n + 1;
    localOp.popExitAnim = arrayOfInt1[n];
    int[] arrayOfInt4 = this.mOps;
    int k = localFragment2 + 1;
    int i1 = arrayOfInt4[localFragment2];
    int i2;
    if (i1 > 0)
    {
      localOp.removed = new ArrayList(i1);
      i2 = 0;
    }
    while (true)
    {
      if (i2 >= i1)
      {
        k = k;
        localBackStackRecord.addOp(localOp);
        i++;
        break;
      }
      if (FragmentManagerImpl.DEBUG)
        Log.v("FragmentManager", "Instantiate " + localBackStackRecord + " set remove fragment #" + this.mOps[k]);
      ArrayList localArrayList = paramFragmentManagerImpl.mActive;
      int[] arrayOfInt5 = this.mOps;
      localFragment2 = k + 1;
      Fragment localFragment1 = (Fragment)localArrayList.get(arrayOfInt5[k]);
      localOp.removed.add(localFragment1);
      i2++;
      localFragment1 = localFragment2;
    }
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeIntArray(this.mOps);
    paramParcel.writeInt(this.mTransition);
    paramParcel.writeInt(this.mTransitionStyle);
    paramParcel.writeString(this.mName);
    paramParcel.writeInt(this.mIndex);
    paramParcel.writeInt(this.mBreadCrumbTitleRes);
    TextUtils.writeToParcel(this.mBreadCrumbTitleText, paramParcel, 0);
    paramParcel.writeInt(this.mBreadCrumbShortTitleRes);
    TextUtils.writeToParcel(this.mBreadCrumbShortTitleText, paramParcel, 0);
  }
}

/* Location:           /home/hernanjw/Desktop/LifeFM/classes_dex2jar.jar
 * Qualified Name:     android.support.v4.app.BackStackState
 * JD-Core Version:    0.6.2
 */