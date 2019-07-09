package com.apps.a7pl4y3r.yourweek.helpers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Vibrator
import com.apps.a7pl4y3r.yourweek.independent.getCalendarDateString
import com.apps.a7pl4y3r.yourweek.independent.monthExtra
import com.apps.a7pl4y3r.yourweek.independent.nameExtra
import com.apps.a7pl4y3r.yourweek.independent.yearExtra
import com.apps.a7pl4y3r.yourweek.independent.dayExtra
import com.apps.a7pl4y3r.yourweek.independent.hourExtra
import com.apps.a7pl4y3r.yourweek.independent.minuteExtra


class AlertReceiver : BroadcastReceiver() {

    //Sends the notification and starts the alarm
    override fun onReceive(context: Context?, intent: Intent?) {

        val notificationHelper = NotificationHelper(context!!)
        (context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).vibrate(5000)

        notificationHelper.mManager.notify(1,
            notificationHelper.getTestNotification(
                intent!!.getStringExtra(nameExtra), getCalendarDateString(
                    intent.getIntExtra(yearExtra, 1970),
                    intent.getIntExtra(monthExtra, 0),
                    intent.getIntExtra(dayExtra, 1),
                    intent.getIntExtra(hourExtra, 0),
                    intent.getIntExtra(minuteExtra, 0))).build())

    }

}
