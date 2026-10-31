package nz.prisk.camapsglucosewidget
import android.content.Context
object WidgetData {
 private const val PREFS = "camaps_widget_data"
 fun save(context: Context, glucose: String?, arrow: String?, raw: String) {
  context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().putString("glucose", glucose).putString("arrow", arrow).putString("raw", raw.take(1800)).putLong("updated", System.currentTimeMillis()).apply()
  GlucoseWidgetProvider.updateAll(context)
 }
 fun prefs(context: Context) = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
}
