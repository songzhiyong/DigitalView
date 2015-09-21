package me.ziso.digitalview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class DigitalView extends LinearLayout {
  private int mDigitalBit;     // 数字的位数：double数值类型包含小数点和小数位

  private DigitalItemView[] mChildren;

  public DigitalView(Context context) {
    super(context);

    initChildren(context, R.drawable.number_money, 1);
  }

  public DigitalView(Context context, AttributeSet attrs) {
    super(context, attrs);

    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DigitalView);
    int bit = a.getInt(R.styleable.DigitalView_bit, 13);
    int resId = a.getResourceId(R.styleable.DigitalView_digitalImg, R.drawable.number_money);
    initChildren(context, resId, bit);
  }

  public int getDigitalBit() {
    return mDigitalBit;
  }

  private void initChildren(Context context, int resId, int bit) {
    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
    LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, bitmap.getHeight() / 11);
    mDigitalBit = bit;
    mChildren = new DigitalItemView[bit];
    for (int i = 0; i < mDigitalBit; i++) {
      mChildren[mDigitalBit - 1 - i] = new DigitalItemView(context, bitmap);
      addView(mChildren[mDigitalBit - 1 - i], i, params);
    }
    setDigital(0.0);
  }

  public void setDigital(long digital) {
    int log10 = Math.max(0, (int) Math.log10(digital));

    // 不显示前面的零
    for (int i = mDigitalBit - 1; i > log10; i--) {
      mChildren[i].setVisibility(View.GONE);
    }

    // 显示整数部分（除个位数）
    for (int i = log10; i > 0; i--) {
      int base = (int) Math.pow(10, i);
      int temp = (int) (digital / base);
      digital = (int) (digital % base);

      mChildren[i].setVisibility(View.VISIBLE);
      mChildren[i].setDigital(temp);
    }

    // 显示个位数，即使个位数为零，也要显示出来。
    mChildren[0].setVisibility(View.VISIBLE);
    mChildren[0].setDigital((int) (digital % 10));
  }

  public void setDigital(double fdigital) {
    long digital = (long) fdigital;
    int log10 = Math.max(0, (int) Math.log10(digital));

    // 不显示前面的零
    for (int i = mDigitalBit - 1, n = log10 + 3; i > n; i--) {
      mChildren[i].setVisibility(View.GONE);
    }

    // 显示整数部分（除个位数和小数）
    for (int i = log10 + 3; i >= 4; i--) {
      int base = (int) Math.pow(10, i - 3);
      int temp = (int) (digital / base);
      digital = (int) (digital % base);

      mChildren[i].setVisibility(View.VISIBLE);
      mChildren[i].setDigital(temp);
    }

    // 显示个位数
    mChildren[3].setVisibility(View.VISIBLE);
    mChildren[3].setDigital((int) (digital % 10));

    // 显示小数点
    mChildren[2].setVisibility(VISIBLE);
    mChildren[2].setDigital(10);

    // 显示小数部分
    //        fdigital = fdigital - Math.floor(fdigital);
    //        digital = (long) (fdigital * 100);
    digital = (long) (fdigital * 100 % 100);
    for (int i = 1; i >= 0; i--) {
      int basee = (int) Math.pow(10, i);
      int temp = (int) (digital / basee);
      digital = (int) (digital % basee);

      mChildren[i].setVisibility(View.VISIBLE);
      mChildren[i].setDigital(temp);
    }
  }
}
