package com.apps.a7pl4y3r.yourweek.helpers

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

import java.util.*


class TimePickerFragment: DialogFragment() {

    // Creates the TimePickerDialog
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val c = Calendar.getInstance()
        return TimePickerDialog(activity, activity as TimePickerDialog.OnTimeSetListener?, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true)
    }

}
