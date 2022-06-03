package es.antonborri.home_widget_example

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.widget.RemoteViews
import es.antonborri.home_widget.HomeWidgetBackgroundIntent
import es.antonborri.home_widget.HomeWidgetLaunchIntent
import es.antonborri.home_widget.HomeWidgetProvider

class HomeWidgetExampleProvider : HomeWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray, widgetData: SharedPreferences) {
        appWidgetIds.forEach { widgetId ->
            val views = RemoteViews(context.packageName, R.layout.example_layout).apply {
                // Open App on Widget Click
                val pendingIntent = HomeWidgetLaunchIntent.getActivity(
                        context,
                        MainActivity::class.java)
                setOnClickPendingIntent(R.id.widget_container, pendingIntent)

                // Swap Title Text by calling Dart Code in the Background
                setTextViewText(R.id.widget_title, widgetData.getString("title", null)
                        ?: "No Title Set")
                val backgroundIntent = HomeWidgetBackgroundIntent.getBroadcast(
                        context,
                        Uri.parse("homeWidgetExample://titleClicked")
                )
                setOnClickPendingIntent(R.id.widget_title, backgroundIntent)

                val message = widgetData.getString("message", null)
                setTextViewText(R.id.widget_message, message
                        ?: "No Message Set")

                // Detect App opened via Click inside Flutter
                val pendingIntentWithData = HomeWidgetLaunchIntent.getActivity(
                        context,
                        MainActivity::class.java,
                        Uri.parse("homeWidgetExample://message?message=$message"))
                setOnClickPendingIntent(R.id.widget_message, pendingIntentWithData)

                // val img = widgetData.getString("img", "")
                // setImageViewUri(R.id.imageView, Uri.parse(
                //     "https://images.unsplash.com/photo-1554755229-ca4470e07232?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTZ8fHZlbHZldHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=900&q=60")?: null)
//                val imageGetter =


//                val image = widgetData.getString("image", null)
//                setImageViewUri(R.id.imageView, Uri.parse(image) ?: null)
//                val imageIntent = HomeWidgetLaunchIntent.getActivity(
//                    context,
//                    MainActivity::class.java,
//                    )
//                setOnClickPendingIntent(R.id.imageView, imageIntent)
            }

            appWidgetManager.updateAppWidget(widgetId, views)
        }
    } 
}