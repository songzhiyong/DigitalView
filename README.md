# DigitalView

A custom View to show digits in Electronic clock

<img style="cursor: zoom-in;" src="https://raw.githubusercontent.com/songzhiyong/DigitalView/master/art/screenshots/device-2015-09-23-195532.png" width="260" height="463">
<img style="cursor: zoom-in;" src="https://raw.githubusercontent.com/songzhiyong/DigitalView/master/art/screenshots/device-2015-09-23-195623.png" width="260" height="463">

<img style="cursor: zoom-in;" src="https://raw.githubusercontent.com/songzhiyong/DigitalView/master/art/screenshots/device-2015-09-23-200007.gif" width="260" height="463">

# Usage

Include the DigitalView widget in your layout.

```xml

<me.ziso.digitalview.DigitalView
      android:id="@+id/digital"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_gravity="center"
      app:bit="5"
      app:decimal_bit="2"
      app:animate_duration="200"
      />
```

```java

long l = 12345;
digitalView.setDigital(l);

double d = 12.34; // format based on your config in xml
digitalView.setDigital(d);

```
