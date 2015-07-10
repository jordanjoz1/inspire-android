package com.jozapps.inspire.views;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import com.jozapps.inspire.alarm.AlarmService;
import com.jozapps.inspire.model.AlarmTime;
import com.jozapps.inspire.util.LocalStorage;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlarmTime alarmTime = new LocalStorage(getActivity()).getAlarmTime();
        return new TimePickerDialog(getActivity(), this, alarmTime.getHour(), alarmTime.getMinute(),
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hour, int minute) {
        new LocalStorage(getActivity()).storeAlarmTime(new AlarmTime(hour, minute));
        new AlarmService(getActivity()).startAlarm();
    }
}
