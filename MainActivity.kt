package nz.prisk.camapsglucosewidget
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
class MainActivity : Activity() {
 override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState)
  val p=WidgetData.prefs(this); val layout=LinearLayout(this).apply { orientation=LinearLayout.VERTICAL; setPadding(32,40,32,32) }
  layout.addView(TextView(this).apply { text="CamAPS Glucose Widget"; textSize=22f })
  layout.addView(TextView(this).apply { text="Enable notification access, then add the CamAPS Glucose widget to your home screen. This app reads notification text only; it does not communicate with or control your pump."; textSize=16f; setPadding(0,24,0,24) })
  layout.addView(Button(this).apply { text="Open notification access settings"; setOnClickListener { startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)) } })
  layout.addView(Button(this).apply { text="Refresh widget"; setOnClickListener { GlucoseWidgetProvider.updateAll(this@MainActivity) } })
  layout.addView(TextView(this).apply { text="Last notification text detected:\n${p.getString("raw","No CamAPS notification captured yet.")}"; textSize=13f; setPadding(0,24,0,0) })
  setContentView(layout)
 }
 override fun onResume() { super.onResume(); GlucoseWidgetProvider.updateAll(this) }
}
