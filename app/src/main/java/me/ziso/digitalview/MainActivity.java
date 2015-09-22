package me.ziso.digitalview;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {
  private static final String TAG = "MainActivity";
  private DigitalView mDigital;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mDigital = (DigitalView) findViewById(R.id.digital);
    final EditText edit = (EditText) findViewById(R.id.edittext);
    edit.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
      }

      @Override public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
      }

      @Override public void afterTextChanged(Editable editable) {
        Long number = null;
        try {
          number = Long.valueOf(edit.getText().toString());
        } catch (NumberFormatException e) {
          e.printStackTrace();
          number = (long) 0;
        }
        mDigital.setDigital(number);
      }
    });
  }
}
