package com.example.daxian.myapplication;


import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;

import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.CallLog;
import android.telephony.SmsManager;



public class MyService extends Service {
    String  ph1,ph2,ph3;
    public MyService() {


    }

    @Override
    public void onCreate() {
        getconfig();
        sendSmstoQin();

    }

    private int getNewSmsCount() {
        int result = 0;
        Cursor csr = getContentResolver().query(Uri.parse("content://sms"), null,
                "type = 1 and read = 0", null, null);
        if (csr != null) {
            result = csr.getCount();
            csr.close();
        }
        return result;
    }

    private int readMissCall() {
        int result = 0;

        Cursor cursor;
        cursor = getContentResolver().query( Uri.parse("content://call_log/calls"), new String[]{
                CallLog.Calls.TYPE
        }, " type=? and new=?", new String[]{
                CallLog.Calls.MISSED_TYPE + "", "1"
        }, null);

        if (cursor != null) {
            result = cursor.getCount();
            cursor.close();
        }

        return result;
    }
    public int readacceptCall(){
        int result = 0;
       Cursor cursor = getContentResolver().query( Uri.parse("content://call_log/calls"), new String[]{
                CallLog.Calls.TYPE
        }, " type=? and new=?", new String[]{
                CallLog.Calls.TYPE + "", "1"
        }, null);

        if (cursor != null) {
            result = cursor.getCount();
            cursor.close();
        }

        return result;

    }
    public String sendCall(){
       String result = null;
        Cursor cursor = getContentResolver().query( Uri.parse("content://call_log/calls"), new String[]{
                CallLog.Calls.TYPE,CallLog.Calls.DATE
        }, " type=? ",new String[]{
                "2"
        }, "date DESC");

        if (cursor != null) {
            result = String.valueOf(cursor.moveToFirst());
            cursor.close();
        }

        return result;

    }
    public int sendMsgs(){
        int result = 0;
        Cursor csr = getContentResolver().query(Uri.parse("content://sms/sent"), null,
                "type = 2 ", null, null);
        if (csr != null) {
            result = csr.getCount();
            csr.close();
        }

        return result;

    }

    public void getconfig(){

        SharedPreferences preferences ;

        preferences = getSharedPreferences("phoneconfig",MODE_PRIVATE);
        this.ph1 = preferences.getString("phone1", null);
        this.ph2 = preferences.getString("phone2",null);
        this.ph3 = preferences.getString("phone3",null);
    }
    public void sendSmstoQin(){
        String smstr="未读短信数："+ String.valueOf(getNewSmsCount())+"未读来电数："
                + String.valueOf(readMissCall())+"已拨电话数："+ String.valueOf(sendMsgs())
                +"最后拨出电话时间："+sendCall();
        SmsManager sManager;
        sManager = SmsManager.getDefault();
        PendingIntent pi = PendingIntent.getActivity(this,0,new Intent(),0);
        sManager.sendTextMessage(this.ph1,null,smstr,pi,null);
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
