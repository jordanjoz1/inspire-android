package com.jozapps.inspire.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.jozapps.inspire.util.DateUtils;

import java.util.Calendar;

public class AlarmService {
    private Context context;
    private PendingIntent mAlarmSender;

    public AlarmService(Context context) {
        this.context = context;
        mAlarmSender = PendingIntent.getBroadcast(context, 0, new Intent(context, AlarmReceiver
                .class), 0);
    }

    public void startAlarm() {
        Calendar start = DateUtils.createCalendarAtTimeToday(0, 0, 10);
        Calendar now = DateUtils.createCalendarNow();
        if (start.before(now)) {
            start.add(Calendar.HOUR, 24);
        }
        long firstTime = start.getTimeInMillis();
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC, firstTime, AlarmManager.INTERVAL_HOUR * 3,
                mAlarmSender);
    }
}
