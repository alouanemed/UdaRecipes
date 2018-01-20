package com.malouane.udarecipes.databinding;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;
import com.malouane.udarecipes.R;
import com.squareup.picasso.Picasso;

public final class BindingAdapters {

  @BindingAdapter(value = "url") public static void loadImageUrl(ImageView view, String url) {
    if (url != null && !url.equals("")) {
      Picasso.with(view.getContext()).load(url).placeholder(R.color.primaryDarkColor)
          .into(view);
    }
  }

  @BindingAdapter(value = "showView")
  public static void showView(View v, boolean value) {
    if (v != null) v.setVisibility(value ? View.VISIBLE : View.GONE);
  }

  @BindingAdapter(value = "hideView")
  public static void hideView(View v, boolean value) {
    showView(v, !value);
  }
}
