package com.jozapps.inspire.alarm;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.jozapps.inspire.R;
import com.jozapps.inspire.dao.QuoteDAO;
import com.jozapps.inspire.model.Quote;

public class AlarmReceiver extends BroadcastReceiver implements QuoteDAO.QuoteListener {

    NotificationManager mNotificationManager;
    private QuoteDAO mQuoteDAO = new QuoteDAO();
    private Context mContext;

    @Override
    public void onReceive(Context pContext, Intent pIntent) {
        mContext = pContext;
        mQuoteDAO.addListener(this);
        mQuoteDAO.getQuoteToday();
        mQuoteDAO.getUpcoming();
        mQuoteDAO.clearOldCache();
        mNotificationManager =
                (NotificationManager) pContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }


    @Override
    public void onTodayQuoteReceived(Quote quote) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(quote.getText()))
                .setSmallIcon(R.drawable.ic_notif).setContentTitle(String.format(mContext
                        .getResources().getString(R.string.notification_title), quote
                        .getAuthor()))
                .setContentText(quote.getText());
        //TODO set pending intent to open main fragment
        mNotificationManager.notify(0, notificationBuilder.build());
        mQuoteDAO.removeListener(this);
    }
}
