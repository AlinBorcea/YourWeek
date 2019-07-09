package com.apps.a7pl4y3r.yourweek.independent

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity

import com.apps.a7pl4y3r.yourweek.R
import com.apps.a7pl4y3r.yourweek.databases.Daydb
import com.apps.a7pl4y3r.yourweek.databases.Task
import com.apps.a7pl4y3r.yourweek.helpers.TimePickerFragment

import kotlinx.android.synthetic.main.activity_add.*


class EditTask : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {

    /*
     * timePickerId changes when one of the timePickers is shown
     * timePickerId = 1 -> timePicker one was created
     * timePickerId = 2 -> timePicker two was created
     */

    private var timePickerId = 0
    private var strStartHour: String? = null
    private var strStartMinute: String? = null
    private var strEndHour: String? = null
    private var strEndMinute: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppTheme(this)
        setContentView(R.layout.activity_add)
        setData()

        tvChosenStartTime.setOnClickListener {
            timePickerId = 1
            TimePickerFragment().show(supportFragmentManager, "time1")
        }

        tvChosenEndTime.setOnClickListener {
            timePickerId = 2
            TimePickerFragment().show(supportFragmentManager, "time2")
        }

        btCreate.setOnClickListener {

            if (dataIsValid()) {

                editTask()
                getSharedPreferences(settTaskWasAdded, Context.MODE_PRIVATE).edit().putBoolean(valueSettTaskWasAdded, true).apply()
                finish()

            } else {
                toastMessage(this, "I need more info in order to create your task",false)
            }
        }

        btExitAdd.setOnClickListener {
            getSharedPreferences(settTaskWasAdded, Context.MODE_PRIVATE).edit().putBoolean(valueSettTaskWasAdded, true).apply()
            finish()
        }
    }

    // Updates the task
    private fun editTask() {

        Daydb(this, intent.getStringExtra("DAYID"))
            .updateData(intent.getIntExtra("TASKID", 0).toString(),
                Task(strStartHour, strStartMinute, strEndHour, strEndMinute, etTask.text.toString()))

    }

    // Sets the initial data
    // There will always be a task in the db with the intentExtra id
    // The user will not be able to open this activity if the task does not exist
    // This means there is no need for null checks
    private fun setData() {

        val res = Daydb(this, intent.getStringExtra("DAYID")).getData()

        linearNavigation.removeView(btMin1Day)
        linearNavigation.removeView(btPlus1Day)

        res.moveToFirst()
        while (res.getInt(0) != intent.getIntExtra("TASKID", 0))
            res.moveToNext()

        etTask.setText(res.getString(5))
        tvChosenStartTime.text = ("${res.getString(1)}:${res.getString(2)}")
        tvChosenEndTime.text = ("${res.getString(3)}:${res.getString(4)}")

        strStartHour = res.getString(1)
        strStartMinute = res.getString(2)
        strEndHour = res.getString(3)
        strEndMinute = res.getString(4)

        res.close()
    }

    private fun dataIsValid(): Boolean {

        val startTime = tvChosenStartTime.text.toString()
        val endTime = tvChosenEndTime.text.toString()

        if (etTask.text.isEmpty() || startTime == "No chosen start time" || endTime == "No chosen end time")
            return false

        return true
    }

    private fun getTime(hour: Int, minute: Int, id: Int): String{

        val strHour: String
        val strMinute: String
        /*val sharedPreferences = getSharedPreferences(settHourFormat, Context.MODE_PRIVATE)

        if (sharedPreferences.getInt(valueSettHourFormat, 1) == 1) {
            //American
            if(hour >= 12) {
                strHour = "${hour - 12}"
                strMinute = if(minute < 10) "0$minute PM" else "$minute PM"

            } else {
                strHour = "$hour"
                strMinute = if(minute < 10) "0$minute AM" else "$minute AM"
            }

        } else {*/
            //International
            strHour = if (hour < 10) "0$hour" else "$hour"
            strMinute = if (minute < 10) "0$minute" else "$minute"
        //}

        when(id){

            1 -> {
                strStartHour = strHour
                strStartMinute = strMinute
            }
            2 -> {
                strEndHour = strHour
                strEndMinute = strMinute
            }
        }
        return "$strHour:$strMinute"
    }

    // timePickerId is used to set the time for the desired textView
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

        when (timePickerId){
            1 -> tvChosenStartTime.text = getTime(hourOfDay, minute, 1)
            2 -> tvChosenEndTime.text = getTime(hourOfDay,minute, 2)
        }
    }
}