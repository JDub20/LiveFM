package android.support.v4.app;

import android.support.v4.util.LogWriter;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;

final class BackStackRecord extends FragmentTransaction
  implements FragmentManager.BackStackEntry, Runnable
{
  static final int OP_ADD = 1;
  static final int OP_ATTACH = 7;
  static final int OP_DETACH = 6;
  static final int OP_HIDE = 4;
  static final int OP_NULL = 0;
  static final int OP_REMOVE = 3;
  static final int OP_REPLACE = 2;
  static final int OP_SHOW = 5;
  static final String TAG = "FragmentManager";
  boolean mAddToBackStack;
  boolean mAllowAddToBackStack = true;
  int mBreadCrumbShortTitleRes;
  CharSequence mBreadCrumbShortTitleText;
  int mBreadCrumbTitleRes;
  CharSequence mBreadCrumbTitleText;
  boolean mCommitted;
  int mEnterAnim;
  int mExitAnim;
  Op mHead;
  int mIndex = -1;
  final FragmentManagerImpl mManager;
  String mName;
  int mNumOp;
  int mPopEnterAnim;
  int mPopExitAnim;
  Op mTail;
  int mTransition;
  int mTransitionStyle;

  public BackStackRecord(FragmentManagerImpl paramFragmentManagerImpl)
  {
    this.mManager = paramFragmentManagerImpl;
  }

  private void doAddOp(int paramInt1, Fragment paramFragment, String paramString, int paramInt2)
  {
    paramFragment.mFragmentManager = this.mManager;
    if (paramString != null)
    {
      if ((paramFragment.mTag == null) || (paramString.equals(paramFragment.mTag)))
        paramFragment.mTag = paramString;
    }
    else
    {
      if (paramInt1 != 0)
      {
        if ((paramFragment.mFragmentId == 0) || (paramFragment.mFragmentId == paramInt1))
        {
          paramFragment.mFragmentId = paramInt1;
          paramFragment.mContainerId = paramInt1;
        }
      }
      else
      {
        Op localOp = new Op();
        localOp.cmd = paramInt2;
        localOp.fragment = paramFragment;
        addOp(localOp);
        return;
      }
      throw new IllegalStateException("Can't change container ID of fragment " + paramFragment + ": was " + paramFragment.mFragmentId + " now " + paramInt1);
    }
    throw new IllegalStateException("Can't change tag of fragment " + paramFragment + ": was " + paramFragment.mTag + " now " + paramString);
  }

  public FragmentTransaction add(int paramInt, Fragment paramFragment)
  {
    doAddOp(paramInt, paramFragment, null, 1);
    return this;
  }

  public FragmentTransaction add(int paramInt, Fragment paramFragment, String paramString)
  {
    doAddOp(paramInt, paramFragment, paramString, 1);
    return this;
  }

  public FragmentTransaction add(Fragment paramFragment, String paramString)
  {
    doAddOp(0, paramFragment, paramString, 1);
    return this;
  }

  void addOp(Op paramOp)
  {
    if (this.mHead != null)
    {
      paramOp.prev = this.mTail;
      this.mTail.next = paramOp;
      this.mTail = paramOp;
    }
    else
    {
      this.mTail = paramOp;
      this.mHead = paramOp;
    }
    paramOp.enterAnim = this.mEnterAnim;
    paramOp.exitAnim = this.mExitAnim;
    paramOp.popEnterAnim = this.mPopEnterAnim;
    paramOp.popExitAnim = this.mPopExitAnim;
    this.mNumOp = (1 + this.mNumOp);
  }

  public FragmentTransaction addToBackStack(String paramString)
  {
    if (this.mAllowAddToBackStack)
    {
      this.mAddToBackStack = true;
      this.mName = paramString;
      return this;
    }
    throw new IllegalStateException("This FragmentTransaction is not allowed to be added to the back stack.");
  }

  public FragmentTransaction attach(Fragment paramFragment)
  {
    Op localOp = new Op();
    localOp.cmd = 7;
    localOp.fragment = paramFragment;
    addOp(localOp);
    return this;
  }

  void bumpBackStackNesting(int paramInt)
  {
    Op localOp;
    if (this.mAddToBackStack)
    {
      if (FragmentManagerImpl.DEBUG)
        Log.v("FragmentManager", "Bump nesting in " + this + " by " + paramInt);
      localOp = this.mHead;
      if (localOp != null);
    }
    else
    {
      return;
    }
    Fragment localFragment;
    if (localOp.fragment != null)
    {
      localFragment = localOp.fragment;
      localFragment.mBackStackNesting = (paramInt + localFragment.mBackStackNesting);
      if (FragmentManagerImpl.DEBUG)
        Log.v("FragmentManager", "Bump nesting of " + localOp.fragment + " to " + localOp.fragment.mBackStackNesting);
    }
    if (localOp.removed != null);
    for (int i = -1 + localOp.removed.size(); ; i--)
    {
      if (i < 0)
      {
        localOp = localOp.next;
        break;
      }
      localFragment = (Fragment)localOp.removed.get(i);
      localFragment.mBackStackNesting = (paramInt + localFragment.mBackStackNesting);
      if (FragmentManagerImpl.DEBUG)
        Log.v("FragmentManager", "Bump nesting of " + localFragment + " to " + localFragment.mBackStackNesting);
    }
  }

  public int commit()
  {
    return commitInternal(false);
  }

  public int commitAllowingStateLoss()
  {
    return commitInternal(true);
  }

  int commitInternal(boolean paramBoolean)
  {
    if (!this.mCommitted)
    {
      if (FragmentManagerImpl.DEBUG)
      {
        Log.v("FragmentManager", "Commit: " + this);
        dump("  ", null, new PrintWriter(new LogWriter("FragmentManager")), null);
      }
      this.mCommitted = true;
      if (!this.mAddToBackStack)
        this.mIndex = -1;
      else
        this.mIndex = this.mManager.allocBackStackIndex(this);
      this.mManager.enqueueAction(this, paramBoolean);
      return this.mIndex;
    }
    throw new IllegalStateException("commit already called");
  }

  public FragmentTransaction detach(Fragment paramFragment)
  {
    Op localOp = new Op();
    localOp.cmd = 6;
    localOp.fragment = paramFragment;
    addOp(localOp);
    return this;
  }

  public FragmentTransaction disallowAddToBackStack()
  {
    if (!this.mAddToBackStack)
    {
      this.mAllowAddToBackStack = false;
      return this;
    }
    throw new IllegalStateException("This transaction is already being added to the back stack");
  }

  public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    dump(paramString, paramPrintWriter, true);
  }

  public void dump(String paramString, PrintWriter paramPrintWriter, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mName=");
      paramPrintWriter.print(this.mName);
      paramPrintWriter.print(" mIndex=");
      paramPrintWriter.print(this.mIndex);
      paramPrintWriter.print(" mCommitted=");
      paramPrintWriter.println(this.mCommitted);
      if (this.mTransition != 0)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mTransition=#");
        paramPrintWriter.print(Integer.toHexString(this.mTransition));
        paramPrintWriter.print(" mTransitionStyle=#");
        paramPrintWriter.println(Integer.toHexString(this.mTransitionStyle));
      }
      if ((this.mEnterAnim != 0) || (this.mExitAnim != 0))
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mEnterAnim=#");
        paramPrintWriter.print(Integer.toHexString(this.mEnterAnim));
        paramPrintWriter.print(" mExitAnim=#");
        paramPrintWriter.println(Integer.toHexString(this.mExitAnim));
      }
      if ((this.mPopEnterAnim != 0) || (this.mPopExitAnim != 0))
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mPopEnterAnim=#");
        paramPrintWriter.print(Integer.toHexString(this.mPopEnterAnim));
        paramPrintWriter.print(" mPopExitAnim=#");
        paramPrintWriter.println(Integer.toHexString(this.mPopExitAnim));
      }
      if ((this.mBreadCrumbTitleRes != 0) || (this.mBreadCrumbTitleText != null))
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mBreadCrumbTitleRes=#");
        paramPrintWriter.print(Integer.toHexString(this.mBreadCrumbTitleRes));
        paramPrintWriter.print(" mBreadCrumbTitleText=");
        paramPrintWriter.println(this.mBreadCrumbTitleText);
      }
      if ((this.mBreadCrumbShortTitleRes != 0) || (this.mBreadCrumbShortTitleText != null))
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mBreadCrumbShortTitleRes=#");
        paramPrintWriter.print(Integer.toHexString(this.mBreadCrumbShortTitleRes));
        paramPrintWriter.print(" mBreadCrumbShortTitleText=");
        paramPrintWriter.println(this.mBreadCrumbShortTitleText);
      }
    }
    String str1;
    Op localOp;
    int i;
    if (this.mHead != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.println("Operations:");
      str1 = paramString + "    ";
      localOp = this.mHead;
      i = 0;
      if (localOp != null);
    }
    else
    {
      return;
    }
    String str2;
    switch (localOp.cmd)
    {
    default:
      str2 = "cmd=" + localOp.cmd;
      break;
    case 0:
      str2 = "NULL";
      break;
    case 1:
      str2 = "ADD";
      break;
    case 2:
      str2 = "REPLACE";
      break;
    case 3:
      str2 = "REMOVE";
      break;
    case 4:
      str2 = "HIDE";
      break;
    case 5:
      str2 = "SHOW";
      break;
    case 6:
      str2 = "DETACH";
      break;
    case 7:
      str2 = "ATTACH";
    }
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("  Op #");
    paramPrintWriter.print(i);
    paramPrintWriter.print(": ");
    paramPrintWriter.print(str2);
    paramPrintWriter.print(" ");
    paramPrintWriter.println(localOp.fragment);
    if (paramBoolean)
    {
      if ((localOp.enterAnim != 0) || (localOp.exitAnim != 0))
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("enterAnim=#");
        paramPrintWriter.print(Integer.toHexString(localOp.enterAnim));
        paramPrintWriter.print(" exitAnim=#");
        paramPrintWriter.println(Integer.toHexString(localOp.exitAnim));
      }
      if ((localOp.popEnterAnim != 0) || (localOp.popExitAnim != 0))
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("popEnterAnim=#");
        paramPrintWriter.print(Integer.toHexString(localOp.popEnterAnim));
        paramPrintWriter.print(" popExitAnim=#");
        paramPrintWriter.println(Integer.toHexString(localOp.popExitAnim));
      }
    }
    if ((localOp.removed != null) && (localOp.removed.size() > 0));
    for (int j = 0; ; j++)
    {
      if (j >= localOp.removed.size())
      {
        localOp = localOp.next;
        i++;
        break;
      }
      paramPrintWriter.print(str1);
      if (localOp.removed.size() != 1)
      {
        if (j == 0)
          paramPrintWriter.println("Removed:");
        paramPrintWriter.print(str1);
        paramPrintWriter.print("  #");
        paramPrintWriter.print(j);
        paramPrintWriter.print(": ");
      }
      else
      {
        paramPrintWriter.print("Removed: ");
      }
      paramPrintWriter.println(localOp.removed.get(j));
    }
  }

  public CharSequence getBreadCrumbShortTitle()
  {
    CharSequence localCharSequence;
    if (this.mBreadCrumbShortTitleRes == 0)
      localCharSequence = this.mBreadCrumbShortTitleText;
    else
      localCharSequence = this.mManager.mActivity.getText(this.mBreadCrumbShortTitleRes);
    return localCharSequence;
  }

  public int getBreadCrumbShortTitleRes()
  {
    return this.mBreadCrumbShortTitleRes;
  }

  public CharSequence getBreadCrumbTitle()
  {
    CharSequence localCharSequence;
    if (this.mBreadCrumbTitleRes == 0)
      localCharSequence = this.mBreadCrumbTitleText;
    else
      localCharSequence = this.mManager.mActivity.getText(this.mBreadCrumbTitleRes);
    return localCharSequence;
  }

  public int getBreadCrumbTitleRes()
  {
    return this.mBreadCrumbTitleRes;
  }

  public int getId()
  {
    return this.mIndex;
  }

  public String getName()
  {
    return this.mName;
  }

  public int getTransition()
  {
    return this.mTransition;
  }

  public int getTransitionStyle()
  {
    return this.mTransitionStyle;
  }

  public FragmentTransaction hide(Fragment paramFragment)
  {
    Op localOp = new Op();
    localOp.cmd = 4;
    localOp.fragment = paramFragment;
    addOp(localOp);
    return this;
  }

  public boolean isAddToBackStackAllowed()
  {
    return this.mAllowAddToBackStack;
  }

  public boolean isEmpty()
  {
    boolean bool;
    if (this.mNumOp != 0)
      bool = false;
    else
      bool = true;
    return bool;
  }

  public void popFromBackStack(boolean paramBoolean)
  {
    if (FragmentManagerImpl.DEBUG)
    {
      Log.v("FragmentManager", "popFromBackStack: " + this);
      dump("  ", null, new PrintWriter(new LogWriter("FragmentManager")), null);
    }
    bumpBackStackNesting(-1);
    for (Op localOp = this.mTail; ; localOp = localOp.prev)
    {
      if (localOp == null)
      {
        if (paramBoolean)
          this.mManager.moveToState(this.mManager.mCurState, FragmentManagerImpl.reverseTransit(this.mTransition), this.mTransitionStyle, true);
        if (this.mIndex >= 0)
        {
          this.mManager.freeBackStackIndex(this.mIndex);
          this.mIndex = -1;
        }
        return;
      }
      Fragment localFragment1;
      int i;
      switch (localOp.cmd)
      {
      default:
        throw new IllegalArgumentException("Unknown cmd: " + localOp.cmd);
      case 1:
        localFragment1 = localOp.fragment;
        localFragment1.mNextAnim = localOp.popExitAnim;
        this.mManager.removeFragment(localFragment1, FragmentManagerImpl.reverseTransit(this.mTransition), this.mTransitionStyle);
        break;
      case 2:
        localFragment1 = localOp.fragment;
        if (localFragment1 != null)
        {
          localFragment1.mNextAnim = localOp.popExitAnim;
          this.mManager.removeFragment(localFragment1, FragmentManagerImpl.reverseTransit(this.mTransition), this.mTransitionStyle);
        }
        if (localOp.removed != null)
          i = 0;
        break;
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
        while (i < localOp.removed.size())
        {
          Fragment localFragment3 = (Fragment)localOp.removed.get(i);
          localFragment3.mNextAnim = localOp.popEnterAnim;
          this.mManager.addFragment(localFragment3, false);
          i++;
          continue;
          Fragment localFragment2 = localOp.fragment;
          localFragment2.mNextAnim = localOp.popEnterAnim;
          this.mManager.addFragment(localFragment2, false);
          break;
          localFragment2 = localOp.fragment;
          localFragment2.mNextAnim = localOp.popEnterAnim;
          this.mManager.showFragment(localFragment2, FragmentManagerImpl.reverseTransit(this.mTransition), this.mTransitionStyle);
          break;
          localFragment2 = localOp.fragment;
          localFragment2.mNextAnim = localOp.popExitAnim;
          this.mManager.hideFragment(localFragment2, FragmentManagerImpl.reverseTransit(this.mTransition), this.mTransitionStyle);
          break;
          localFragment2 = localOp.fragment;
          localFragment2.mNextAnim = localOp.popEnterAnim;
          this.mManager.attachFragment(localFragment2, FragmentManagerImpl.reverseTransit(this.mTransition), this.mTransitionStyle);
          break;
          localFragment2 = localOp.fragment;
          localFragment2.mNextAnim = localOp.popEnterAnim;
          this.mManager.detachFragment(localFragment2, FragmentManagerImpl.reverseTransit(this.mTransition), this.mTransitionStyle);
        }
      }
    }
  }

  public FragmentTransaction remove(Fragment paramFragment)
  {
    Op localOp = new Op();
    localOp.cmd = 3;
    localOp.fragment = paramFragment;
    addOp(localOp);
    return this;
  }

  public FragmentTransaction replace(int paramInt, Fragment paramFragment)
  {
    return replace(paramInt, paramFragment, null);
  }

  public FragmentTransaction replace(int paramInt, Fragment paramFragment, String paramString)
  {
    if (paramInt != 0)
    {
      doAddOp(paramInt, paramFragment, paramString, 2);
      return this;
    }
    throw new IllegalArgumentException("Must use non-zero containerViewId");
  }

  public void run()
  {
    if (FragmentManagerImpl.DEBUG)
      Log.v("FragmentManager", "Run: " + this);
    if ((!this.mAddToBackStack) || (this.mIndex >= 0))
    {
      bumpBackStackNesting(1);
      for (Op localOp = this.mHead; ; localOp = localOp.next)
      {
        if (localOp == null)
        {
          this.mManager.moveToState(this.mManager.mCurState, this.mTransition, this.mTransitionStyle, true);
          if (this.mAddToBackStack)
            this.mManager.addBackStackState(this);
          return;
        }
        Fragment localFragment2;
        switch (localOp.cmd)
        {
        default:
          throw new IllegalArgumentException("Unknown cmd: " + localOp.cmd);
        case 1:
          Fragment localFragment1 = localOp.fragment;
          localFragment1.mNextAnim = localOp.enterAnim;
          this.mManager.addFragment(localFragment1, false);
          break;
        case 2:
          Fragment localFragment3 = localOp.fragment;
          if (this.mManager.mAdded != null);
          for (int i = 0; ; i++)
          {
            if (i >= this.mManager.mAdded.size())
            {
              if (localFragment3 == null)
                break;
              localFragment3.mNextAnim = localOp.enterAnim;
              this.mManager.addFragment(localFragment3, false);
              break;
            }
            Fragment localFragment4 = (Fragment)this.mManager.mAdded.get(i);
            if (FragmentManagerImpl.DEBUG)
              Log.v("FragmentManager", "OP_REPLACE: adding=" + localFragment3 + " old=" + localFragment4);
            if ((localFragment3 == null) || (localFragment4.mContainerId == localFragment3.mContainerId))
              if (localFragment4 != localFragment3)
              {
                if (localOp.removed == null)
                  localOp.removed = new ArrayList();
                localOp.removed.add(localFragment4);
                localFragment4.mNextAnim = localOp.exitAnim;
                if (this.mAddToBackStack)
                {
                  localFragment4.mBackStackNesting = (1 + localFragment4.mBackStackNesting);
                  if (FragmentManagerImpl.DEBUG)
                    Log.v("FragmentManager", "Bump nesting of " + localFragment4 + " to " + localFragment4.mBackStackNesting);
                }
                this.mManager.removeFragment(localFragment4, this.mTransition, this.mTransitionStyle);
              }
              else
              {
                localFragment3 = null;
                localOp.fragment = null;
              }
          }
        case 3:
          localFragment2 = localOp.fragment;
          localFragment2.mNextAnim = localOp.exitAnim;
          this.mManager.removeFragment(localFragment2, this.mTransition, this.mTransitionStyle);
          break;
        case 4:
          localFragment2 = localOp.fragment;
          localFragment2.mNextAnim = localOp.exitAnim;
          this.mManager.hideFragment(localFragment2, this.mTransition, this.mTransitionStyle);
          break;
        case 5:
          localFragment2 = localOp.fragment;
          localFragment2.mNextAnim = localOp.enterAnim;
          this.mManager.showFragment(localFragment2, this.mTransition, this.mTransitionStyle);
          break;
        case 6:
          localFragment2 = localOp.fragment;
          localFragment2.mNextAnim = localOp.exitAnim;
          this.mManager.detachFragment(localFragment2, this.mTransition, this.mTransitionStyle);
          break;
        case 7:
          localFragment2 = localOp.fragment;
          localFragment2.mNextAnim = localOp.enterAnim;
          this.mManager.attachFragment(localFragment2, this.mTransition, this.mTransitionStyle);
        }
      }
    }
    throw new IllegalStateException("addToBackStack() called after commit()");
  }

  public FragmentTransaction setBreadCrumbShortTitle(int paramInt)
  {
    this.mBreadCrumbShortTitleRes = paramInt;
    this.mBreadCrumbShortTitleText = null;
    return this;
  }

  public FragmentTransaction setBreadCrumbShortTitle(CharSequence paramCharSequence)
  {
    this.mBreadCrumbShortTitleRes = 0;
    this.mBreadCrumbShortTitleText = paramCharSequence;
    return this;
  }

  public FragmentTransaction setBreadCrumbTitle(int paramInt)
  {
    this.mBreadCrumbTitleRes = paramInt;
    this.mBreadCrumbTitleText = null;
    return this;
  }

  public FragmentTransaction setBreadCrumbTitle(CharSequence paramCharSequence)
  {
    this.mBreadCrumbTitleRes = 0;
    this.mBreadCrumbTitleText = paramCharSequence;
    return this;
  }

  public FragmentTransaction setCustomAnimations(int paramInt1, int paramInt2)
  {
    return setCustomAnimations(paramInt1, paramInt2, 0, 0);
  }

  public FragmentTransaction setCustomAnimations(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mEnterAnim = paramInt1;
    this.mExitAnim = paramInt2;
    this.mPopEnterAnim = paramInt3;
    this.mPopExitAnim = paramInt4;
    return this;
  }

  public FragmentTransaction setTransition(int paramInt)
  {
    this.mTransition = paramInt;
    return this;
  }

  public FragmentTransaction setTransitionStyle(int paramInt)
  {
    this.mTransitionStyle = paramInt;
    return this;
  }

  public FragmentTransaction show(Fragment paramFragment)
  {
    Op localOp = new Op();
    localOp.cmd = 5;
    localOp.fragment = paramFragment;
    addOp(localOp);
    return this;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder(128);
    localStringBuilder.append("BackStackEntry{");
    localStringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
    if (this.mIndex >= 0)
    {
      localStringBuilder.append(" #");
      localStringBuilder.append(this.mIndex);
    }
    if (this.mName != null)
    {
      localStringBuilder.append(" ");
      localStringBuilder.append(this.mName);
    }
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }

  static final class Op
  {
    int cmd;
    int enterAnim;
    int exitAnim;
    Fragment fragment;
    Op next;
    int popEnterAnim;
    int popExitAnim;
    Op prev;
    ArrayList<Fragment> removed;
  }
}

/* Location:           /home/hernanjw/Desktop/LifeFM/classes_dex2jar.jar
 * Qualified Name:     android.support.v4.app.BackStackRecord
 * JD-Core Version:    0.6.2
 */