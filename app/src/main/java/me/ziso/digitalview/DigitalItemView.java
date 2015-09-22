package me.ziso.digitalview;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ScrollView;

public class DigitalItemView extends ScrollView {
  private static final String TAG = "DigitalItemView";
  protected int mDigital = 0;
  protected long mSwitchDuration = 200;

  private ImageView mImageView;

  private DigitalItemView(Context context) {
    super(context);
  }

  private DigitalItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  private DigitalItemView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public DigitalItemView(Context context, Bitmap bitmap, long duration) {
    super(context);
    mSwitchDuration = duration;
    setDigitalBitmap(bitmap);
  }

  private void setDigitalBitmap(Bitmap bitmap) {
    setVerticalScrollBarEnabled(false);
    setVerticalFadingEdgeEnabled(false);
    setHorizontalScrollBarEnabled(false);
    setHorizontalFadingEdgeEnabled(false);
    setFillViewport(true);

    mImageView = new ImageView(getContext());
    mImageView.setScaleType(ScaleType.MATRIX);
    mImageView.setImageBitmap(bitmap);
    addView(mImageView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
  }

  public void setDigital(int digital) {
    if (digital >= 0 && digital <= 10 && mDigital != digital) {
      Log.d(TAG, "fromY=" + (-mDigital / 11.0f));
      Log.d(TAG, "toY=" + (-digital / 11.0f));
      TranslateAnimation transAnim =
          new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0,
              Animation.RELATIVE_TO_SELF, -mDigital / 11.0f, Animation.RELATIVE_TO_SELF,
              -digital / 11.0f);
      transAnim.setDuration(mSwitchDuration);
      transAnim.setFillBefore(true);
      transAnim.setFillAfter(true);
      mImageView.startAnimation(transAnim);
      mDigital = digital;
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent ev) {
    return false;
  }

  @Override
  public boolean onTrackballEvent(MotionEvent ev) {
    return false;
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    return false;
  }
}
