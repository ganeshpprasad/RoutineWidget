package com.example.widgetroutines

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

const val ACTION_APP_WIDGET = "widget update"
const val WIDGET_IDS = "widget ids"

class MainActivity : AppCompatActivity() {
    val UPDATE_TIME = 5900L

    private val updateWidgetHandler = Handler()

    private var updateWidgetRunnable: Runnable = Runnable {
        kotlin.run {
            sendUpdateBroadcast()
        }
    }

    private fun sendUpdateBroadcast() {
        updateWidgetHandler.postDelayed(updateWidgetRunnable, UPDATE_TIME)
        System.out.println("log")

        val updateWidgetIntent = Intent(this, RoutineWidget::class.java)
        updateWidgetIntent.setAction(ACTION_APP_WIDGET)

        val ids = AppWidgetManager.getInstance(application).getAppWidgetIds(ComponentName(application, RoutineWidget::class.java))
        updateWidgetIntent.putExtra(WIDGET_IDS, ids)

        sendBroadcast(updateWidgetIntent)
    }

    override fun onResume() {
        super.onResume()
        updateWidgetHandler.postDelayed(updateWidgetRunnable, UPDATE_TIME)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}