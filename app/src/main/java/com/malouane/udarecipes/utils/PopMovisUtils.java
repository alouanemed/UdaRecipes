package com.malouane.udarecipes.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

public class PopMovisUtils {

  public static String buildYouTubeLink(@NonNull final String key) {
    final Uri.Builder builder = Uri.parse("https://www.youtube.com/watch").buildUpon();
    builder.appendQueryParameter("v", key);
    return builder.build().toString();
  }

  public static void shareTrailer(Context ctx, String ytKey) {
    Intent share = new Intent(Intent.ACTION_SEND);
    share.setType("text/plain");
    share.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
    share.putExtra(Intent.EXTRA_SUBJECT, "Check out this Trailer, You May Like it !");
    share.putExtra(Intent.EXTRA_TEXT, buildYouTubeLink(ytKey));
    ctx.startActivity(Intent.createChooser(share, "Share Trailer"));
  }

  public static void openTrailer(final Context context, @NonNull final String link) {
    Intent share = new Intent(Intent.ACTION_VIEW);
    share.setData(Uri.parse(link));
    context.startActivity(share);
  }
}
