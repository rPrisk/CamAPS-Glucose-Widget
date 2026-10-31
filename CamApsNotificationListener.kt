package nz.prisk.camapsglucosewidget
import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
class CamApsNotificationListener : NotificationListenerService() {
 override fun onNotificationPosted(sbn: StatusBarNotification) {
  try {
   val label=try { packageManager.getApplicationLabel(packageManager.getApplicationInfo(sbn.packageName,0)).toString() } catch (_:Exception) { "" }
   if (!(label.contains("CamAPS",true) || sbn.packageName.contains("camaps",true) || sbn.packageName.contains("camdiab",true))) return
   val e=sbn.notification.extras; val parts=mutableListOf<String>()
   listOf(Notification.EXTRA_TITLE,Notification.EXTRA_TEXT,Notification.EXTRA_BIG_TEXT,Notification.EXTRA_SUB_TEXT,Notification.EXTRA_INFO_TEXT,Notification.EXTRA_SUMMARY_TEXT,Notification.EXTRA_TITLE_BIG).forEach { k -> e.getCharSequence(k)?.toString()?.let { parts.add(it) } }
   e.getCharSequenceArray(Notification.EXTRA_TEXT_LINES)?.forEach { parts.add(it.toString()) }
   for (k in e.keySet()) { val value=e.get(k); if(value is CharSequence) parts.add(value.toString()) }
   val raw=parts.distinct().joinToString(" | ")
   val match=Regex("(?i)(\d{1,2}(?:[.,]\d)?)\s*mmol\s*/?\s*l").find(raw)
   val glucose=match?.groupValues?.get(1)?.replace(',', '.')
   val arrow=listOf("↗","↑","↖","→","↘","↓","↙").firstOrNull { raw.contains(it) }
   if(glucose!=null || arrow!=null) WidgetData.save(this,glucose,arrow,raw)
   Log.d("CamAPSWidget","CamAPS notification text: $raw")
  } catch(ex:Exception) { Log.e("CamAPSWidget","Notification parse failed",ex) }
 }
}
