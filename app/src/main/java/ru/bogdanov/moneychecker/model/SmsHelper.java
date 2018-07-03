package ru.bogdanov.moneychecker.model;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import ru.bogdanov.moneychecker.model.items.DayItem;
import ru.bogdanov.moneychecker.model.items.SmsItem;

public class SmsHelper {
    private static final String TAG = "sms_help";
    String[] mProjection =
            {
                    Telephony.Sms.ADDRESS,
                    Telephony.Sms.DATE,
                    Telephony.Sms.BODY
            };

    private ArrayList<String> getSmsFromProvider(Context context){
        ArrayList<String> sms = new ArrayList<String>();
        Uri uriSMSURI = Uri.parse("content://sms/inbox");

        Cursor cur = context.getContentResolver().query(uriSMSURI, mProjection, Telephony.Sms.ADDRESS+" = 900", null, Telephony.Sms.DATE+" DESC");

        while (cur != null && cur.moveToNext()) {
            String body = cur.getString(cur.getColumnIndexOrThrow("body"));
            sms.add(body);
        }

        if (cur != null) {
            cur.close();
        }


        return sms;
    }

    private HashMap<String, DayItem> parseSms(Context context){
        HashMap<String, DayItem> map=new HashMap<>();

        for (String s:getSmsFromProvider(context)){
            String[] subString= s.split(" ");
            if (!subString[0].equals("ECMC8632")) continue;
            String cardId=subString[0];
            String date=subString[1];
            String time=subString[2];

            String summ=subString[4];
            Log.d(TAG, subString.toString());
            if (!summ.substring(summ.length()-1).equals("р")) continue;
            summ =summ.substring(0, summ.length()-1);
            float movement= Float.parseFloat(summ);
            if (subString[3].equals("покупка")||subString[3].equals("списание")||subString[3].equals("выдача")||subString[3].equals("оплата")) movement*=-1;

            int balIndex=6;
            String destination="";
            for (int i=5; i<subString.length;i++)
                if (subString[i].equals("Баланс:")){
                balIndex=i;
                break;
                } else destination=destination+subString[i]+" ";

            String bal=subString[balIndex+1];
            if (!bal.substring(bal.length()-1).equals("р")) continue;
            bal =bal.substring(0, bal.length()-1);
            float balance=Float.parseFloat(bal);

            SmsItem smsItem=new SmsItem(date,time,balance,movement, cardId, destination);
           if ( map.containsKey(smsItem.getDate()) ) map.get(smsItem.getDate()).addSms(smsItem);
           else {
               DayItem dayItem=new DayItem(smsItem.getDate());
               dayItem.addSms(smsItem);
               map.put(smsItem.getDate(), dayItem);
           }
        }
        return map;
    }

    public ArrayList<DayItem> getSms(Context context, int daysCount){
        HashMap<String, DayItem> map=parseSms(context);
       // Log.e(TAG, map.keySet().toString());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd.MM.yy");
        Calendar calendar=Calendar.getInstance();
        ArrayList<DayItem> list=new ArrayList<>();

        while (daysCount>0)
        {
          //  Log.e(TAG, simpleDateFormat.format(calendar.getTime()));
            if (map.containsKey(simpleDateFormat.format(calendar.getTime()))) {
                DayItem dayItem=map.get(simpleDateFormat.format(calendar.getTime()));
                dayItem.fillInfo();
                list.add(dayItem);
            }

            calendar.add(Calendar.DAY_OF_YEAR, -1);
            daysCount--;
        }
        return list;
    }
}
