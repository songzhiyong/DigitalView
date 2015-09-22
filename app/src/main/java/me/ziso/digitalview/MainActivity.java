package me.ziso.digitalview;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
  private static final String TAG = "MainActivity";
  private DigitalView mDigital;

  private EditText editLong;
  private EditText editDouble;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mDigital = (DigitalView) findViewById(R.id.digital);
    editLong = (EditText) findViewById(R.id.edittext_long);
    editLong.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
      }

      @Override public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
      }

      @Override public void afterTextChanged(Editable editable) {
        Long number = null;
        try {
          number = Long.valueOf(editLong.getText().toString());
        } catch (NumberFormatException e) {
          e.printStackTrace();
          number = (long) 0;
        }
        mDigital.setDigital(number);
      }
    });
    editDouble = (EditText) findViewById(R.id.edittext_double);
    editDouble.addTextChangedListener(new TextWatcher() {
      String lastString = "";

      @Override public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
      }

      @Override public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
      }

      @Override public void afterTextChanged(Editable editable) {
        Double number = null;
        try {
          number = Double.valueOf(editDouble.getText().toString());
        } catch (NumberFormatException e) {
          e.printStackTrace();
          number = (double) 0;
        }
        try {
          mDigital.setDigital(number);
        } catch (Exception e) {
          Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
          editDouble.setText(lastString);
        }
        lastString = editDouble.getText().toString();
        editDouble.setSelection(lastString.length());
      }
    });
  }
}
