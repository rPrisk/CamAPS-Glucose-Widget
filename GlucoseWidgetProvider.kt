package nz.prisk.camapsglucosewidget
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
class GlucoseWidgetProvider : AppWidgetProvider() {
 override fun onUpdate(context: Context, manager: AppWidgetManager, ids: IntArray) { ids.forEach { update(context, manager, it) } }
 companion object {
  fun updateAll(context: Context) { val m=AppWidgetManager.getInstance(context); m.getAppWidgetIds(ComponentName(context, GlucoseWidgetProvider::class.java)).forEach { update(context,m,it) } }
  private fun update(context: Context, manager: AppWidgetManager, id: Int) {
   val p=WidgetData.prefs(context); val glucose=p.getString("glucose",null); val arrow=p.getString("arrow",null); val stamp=p.getLong("updated",0L)
   val fresh=stamp>0L && System.currentTimeMillis()-stamp<=600000L
   val v=RemoteViews(context.packageName,R.layout.glucose_widget)
   v.setTextViewText(R.id.widget_glucose,if(fresh && glucose!=null) glucose else "--.-")
   v.setTextViewText(R.id.widget_arrow,if(fresh && arrow!=null) arrow else "?")
   v.setTextViewText(R.id.widget_status,if (stamp==0L) "Waiting for CamAPS notification" else "Captured " + SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(stamp)))
   manager.updateAppWidget(id,v)
  }
 }
}
