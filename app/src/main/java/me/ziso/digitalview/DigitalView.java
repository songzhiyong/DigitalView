package me.ziso.digitalview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class DigitalView extends LinearLayout {
  public static final String TAG = "DigitalView";

  public static final int DEFAULT_DIGITAL_BIT = 5;
  public static final int DEFAULT_DECIMAL_BIT = 2;
  public static final int DEFAULT_DURATION = 200;
  private int digitalBit;//总位数
  private int decimalBit;//小数点后几位
  private long duration;

  private DigitalItemView[] mChildren;

  public DigitalView(Context context) {
    super(context);
    init(context, R.drawable.digital_img, DEFAULT_DIGITAL_BIT, DEFAULT_DECIMAL_BIT,
        DEFAULT_DURATION);
  }

  public DigitalView(Context context, AttributeSet attrs) {
    super(context, attrs);
    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DigitalView);
    int bit = a.getInt(R.styleable.DigitalView_bit, DEFAULT_DIGITAL_BIT);
    int decimalBit = a.getInt(R.styleable.DigitalView_decimal_bit, DEFAULT_DECIMAL_BIT);
    int resId = a.getResourceId(R.styleable.DigitalView_digital_img, R.drawable.digital_img);
    long duration = a.getInt(R.styleable.DigitalView_animate_duration, DEFAULT_DURATION);
    init(context, resId, bit, decimalBit, duration);
  }

  private void init(Context context, int resId, int bit, int decimalBit, long duration) {
    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
    LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, bitmap.getHeight() / 11);
    this.digitalBit = bit;
    this.decimalBit = decimalBit;
    this.duration = duration;
    mChildren = new DigitalItemView[bit];
    for (int i = 0; i < digitalBit; i++) {
      mChildren[digitalBit - 1 - i] = new DigitalItemView(context, bitmap, this.duration);
      addView(mChildren[digitalBit - 1 - i], i, params);
    }
    setDigital((long) 0);
  }

  public void setDigital(long digital) {
    int log10 = Math.max(0, (int) Math.log10(digital));
    for (int i = digitalBit - 1; i > log10; i--) {
      mChildren[i].setVisibility(View.GONE);
    }
    for (int i = log10; i > 0; i--) {
      int base = (int) Math.pow(10, i);
      int temp = (int) (digital / base);
      digital = (int) (digital % base);

      mChildren[i].setVisibility(View.VISIBLE);
      mChildren[i].setDigital(temp);
    }
    mChildren[0].setVisibility(View.VISIBLE);
    mChildren[0].setDigital((int) (digital % 10));
  }

  public void setDigital(double digital) throws Exception {
    long digitalLong = (long) digital;
    if (String.valueOf(digitalLong).length() > digitalBit - decimalBit - 1) {
      throw new IllegalArgumentException("Expect a decimal point based on your config!");
    }
    int log10 = Math.max(0, (int) Math.log10(digitalLong));
    for (int i = digitalBit - 1, n = log10 + decimalBit + 1; i > n; i--) {
      mChildren[i].setVisibility(View.GONE);
    }
    int lastPos = log10 + decimalBit + 1;
    int pointPos = decimalBit;
    for (int i = lastPos; i >= pointPos + 2; i--) {
      Log.d(TAG, "pointPos=" + pointPos + ",i=" + i);
      int base = (int) Math.pow(10, i - decimalBit - 1);
      int temp = (int) (digitalLong / base);
      digitalLong = (int) (digitalLong % base);
      mChildren[i].setVisibility(View.VISIBLE);
      mChildren[i].setDigital(temp);
    }

    mChildren[decimalBit + 1].setVisibility(View.VISIBLE);
    mChildren[decimalBit + 1].setDigital((int) (digitalLong % 10));

    mChildren[decimalBit].setVisibility(VISIBLE);
    mChildren[decimalBit].setDigital(10);

    double total = Math.pow(10, decimalBit);
    digitalLong = (long) (digital * total % total);
    for (int i = decimalBit - 1; i >= 0; i--) {
      int base = (int) Math.pow(10, i);
      int temp = (int) (digitalLong / base);
      digitalLong = (int) (digitalLong % base);

      mChildren[i].setVisibility(View.VISIBLE);
      mChildren[i].setDigital(temp);
    }
  }
}
