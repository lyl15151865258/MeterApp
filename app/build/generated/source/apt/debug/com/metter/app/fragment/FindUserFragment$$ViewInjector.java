// Generated code from Butter Knife. Do not modify!
package com.metter.app.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class FindUserFragment$$ViewInjector {
  public static void inject(Finder finder, final com.metter.app.fragment.FindUserFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493105, "field 'mListView'");
    target.mListView = (android.widget.ListView) view;
    view = finder.findRequiredView(source, 2131492980, "field 'mErrorLayout'");
    target.mErrorLayout = (com.metter.app.ui.empty.EmptyLayout) view;
  }

  public static void reset(com.metter.app.fragment.FindUserFragment target) {
    target.mListView = null;
    target.mErrorLayout = null;
  }
}
