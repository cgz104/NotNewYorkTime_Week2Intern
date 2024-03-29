package com.cuongz.notnewyorktimes.view

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.DatePicker
import android.widget.TextView
import com.cuongz.notnewyorktimes.R
import java.util.*


class EndDatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(activity, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        val dayView = activity!!.findViewById(R.id.endDayView) as TextView
        val monthView = activity!!.findViewById(R.id.endMonthView) as TextView
        val yearView = activity!!.findViewById(R.id.endYearView) as TextView
        if(day < 10){
            dayView.text = StringBuilder().append(0).append(day)
        }else{
            dayView.text = day.toString()
        }
        if (month < 10){
            monthView.text = StringBuilder().append(0).append(month + 1)
        }else{
            monthView.text = month.toString()
        }

        yearView.text = year.toString()
    }
}