package com.example.widgetroutines

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Implementation of App Widget functionality.
 */
class RoutineWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent != null) {
            if (ACTION_APP_WIDGET === intent.action) {
                val ids = intent.getIntArrayExtra(WIDGET_IDS)
                val remoteViews = RemoteViews(context?.packageName, R.layout.routine_widget)
                if (ids != null) {
                    for (appWidgetId in ids) {
                        if (context != null) {
                            updateAppWidget(context, AppWidgetManager.getInstance(context), appWidgetId )
                        }
                    }
                }
//                AppWidgetManager.getInstance(context).updateAppWidget(context?.let { ComponentName(it, RoutineWidget::class.java) }, remoteViews)
            }
        }
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.routine_widget)
    var text = ""
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        var answer: String =  current.format(formatter)
        Log.d("answer",answer)
        text = answer.toString()
    } else {
        var date = Date()
        val formatter = SimpleDateFormat("HH:mma")
        val answer: String = formatter.format(date)
        Log.d("answer",answer)
        text = answer.toString()
    }
    var routine_text: String = getRoutineFromTime(text)
    views.setTextViewText(R.id.appwidget_text_time, text);
    views.setTextViewText(R.id.appwidget_routine_task, routine_text)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}

fun getRoutineFromTime(time : String): String {
    if (time.split(":")[0].toInt() === 6) {
        if (time.split(":")[1].toInt() < 30 ){
            return "Freshen,Pushups,Skips"
        }
        return "Brush,PreWorkout Meal,AudioBook,Sun Walk"
    }
    if (time.split(":")[0].toInt() === 7) {
        if (time.split(":")[1].toInt() < 30 ){
            return "Classroom"
        }
        return "Warmup,Yoga,Workout"
    }
    if (time.split(":")[0].toInt() === 8) {
        if (time.split(":")[1].toInt() < 30 ){
            return "Bands|Weights, Postworkout Meal"
        }
        return "Meditation,Podcast,Cleanup Work area"
    }
    if (time.split(":")[0].toInt() === 9) {
        if (time.split(":")[1].toInt() < 30 ){
            return "Inspiring Video, Bath, Payers"
        }
        return "---"
    }
    if (time.split(":")[0].toInt() === 10) {
        if (time.split(":")[1].toInt() < 30 ){
            return "Breakfast"
        }
        return "Breakfast"
    }
    if (time.split(":")[0].toInt() === 11) {
        return "Cortex - 1"
    }
    if (time.split(":")[0].toInt() === 12) {
        return "Cortex - 2"
    }
    if (time.split(":")[0].toInt() === 13) {
        return "Cortex - 3"
    }
    if (time.split(":")[0].toInt() === 14) {
        return "Lunch"
    }
    if (time.split(":")[0].toInt() === 15) {
        return "Read"
    }
    if (time.split(":")[0].toInt() === 16) {
        return "Nap"
    }
    if (time.split(":")[0].toInt() === 17) {
        if (time.split(":")[1].toInt() < 30 ){
            return "Skips, Core, Meditation"
        }
        return "YT, Bath"
    }
    if (time.split(":")[0].toInt() === 18) {
        return "Freelance - 1"
    }
    if (time.split(":")[0].toInt() === 19) {
        return "Freelance - 2"
    }
    if (time.split(":")[0].toInt() === 20) {
        return "Show Your Work"
    }
    if (time.split(":")[0].toInt() === 21) {
        return "Dinner"
    }
    if (time.split(":")[0].toInt() === 22) {
        return "Duolingo, Read"
    }
    if (time.split(":")[0].toInt() === 23) {
        return "Journal"
    }
    return time.split(":")[0]
}