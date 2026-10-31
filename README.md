# CamAPS Glucose Widget

Android Studio project for a home-screen widget that reads glucose and trend text from CamAPS FX notifications.

## Important limitations

- This is source code, not a compiled or signed APK.
- It can display only information exposed as readable text in the CamAPS FX notification.
- Glucose extraction requires a value followed by mmol/L. Trend extraction looks for a text arrow character. If CamAPS draws the arrow as an image rather than including it as text, the widget will show `?` for the arrow.
- The widget blanks the reading after 10 minutes without a new captured notification. This is a stale-reading safeguard, not a guarantee that the CGM itself is current.
- This app does not read CGM sensors, communicate with a pump, calculate insulin, or change CamAPS settings. It is not a medical device and must not be used for treatment decisions.

## Build

Open this folder in Android Studio and allow Gradle sync. Choose **Build > Build Bundle(s) / APK(s) > Build APK(s)**. The debug APK is normally created at `app/build/outputs/apk/debug/app-debug.apk`.

## Install and configure

1. Install the APK on the phone.
2. Open CamAPS Glucose Widget.
3. Tap **Open notification access settings** and enable access for CamAPS Glucose Widget.
4. Return to the app and inspect the diagnostic text under “Last notification text detected”.
5. Add the **CamAPS Glucose** widget from the Android home-screen widget picker.

If the diagnostic text omits the glucose value or arrow, Android is not exposing that part of the CamAPS notification as text. The parser must then be adjusted based on the diagnostic output; an image-only arrow cannot be extracted by this text-based method.
