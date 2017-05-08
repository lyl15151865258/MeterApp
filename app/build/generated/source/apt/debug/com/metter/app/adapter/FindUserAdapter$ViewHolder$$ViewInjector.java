// Generated code from Butter Knife. Do not modify!
package com.metter.app.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class FindUserAdapter$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.metter.app.adapter.FindUserAdapter.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493185, "field 'name'");
    target.name = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131493187, "field 'from'");
    target.from = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131493196, "field 'desc'");
    target.desc = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131493195, "field 'gender'");
    target.gender = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131493184, "field 'avatar'");
    target.avatar = (com.metter.app.widget.AvatarView) view;
  }

  public static void reset(com.metter.app.adapter.FindUserAdapter.ViewHolder target) {
    target.name = null;
    target.from = null;
    target.desc = null;
    target.gender = null;
    target.avatar = null;
  }
}
