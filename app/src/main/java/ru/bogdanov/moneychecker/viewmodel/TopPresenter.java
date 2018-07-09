package ru.bogdanov.moneychecker.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import ru.bogdanov.moneychecker.BR;
import ru.bogdanov.moneychecker.model.SmsHelper;
import ru.bogdanov.moneychecker.model.items.DayItem;


public class TopPresenter extends BaseObservable {
    private static final String TAG = "top_pr";
    private float money = 0, day, month;
    private String dateStart;
    private RecAdapter recAdapter = new RecAdapter();
    private Context context;

    @Bindable
    public RecAdapter getRecAdapter() {
        return recAdapter;
    }

    public void setRecAdapter(RecAdapter recAdapter) {
        this.recAdapter = recAdapter;
        notifyPropertyChanged(BR.recAdapter);
    }

    @Bindable
    public float getMoney() {
        return this.money;
    }

    @Bindable
    public float getDay() {
        return this.day;
    }

    @Bindable
    public float getMonth() {
        return this.month;
    }

    @Bindable
    public String getDateStart() {
        return this.dateStart;
    }

    public void setMoney(float money) {
        this.money = money;
        notifyPropertyChanged(BR.money);
    }

    public void setDay(float day) {
        this.day = day;
        notifyPropertyChanged(BR.day);
    }

    public void setMonth(float month) {
        this.month = month;
        notifyPropertyChanged(BR.month);
    }

    public void setDateStart(String dateStart) {
        ;
        this.dateStart = dateStart;
        notifyPropertyChanged(BR.dateStart);
    }

    public void saveToShPref(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat("day", day);
        editor.putString("date", dateStart);
        editor.apply();
    }

    public void getFromShPref(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        setDay(sharedPref.getFloat("day", 1000));
        setDateStart(sharedPref.getString("date", "01.07.18"));
        setMonth(day * 30);
        this.context=context;
        new AsyncSms().execute();
    }

    String newDate;
    int newMonth = -1, newDay = -1;

    public void onTextChangedDate(CharSequence s) {
        if (s.length() == 8) {
            newDate = s.toString();
            setDateStart(newDate);
        } else newDate = null;
    }

    public void onTextChangedMonth(CharSequence s) {
        if (s.length() >= 2) {
            newMonth = Integer.parseInt(s.toString());
            setDay(newMonth / 30);
            setMonth(newMonth);
        } else newMonth = -1;
    }

    public void onTextChangedDay(CharSequence s) {
        if (s.length() >= 2) {
            newDay = Integer.parseInt(s.toString());
            setDay(newDay);
            setMonth(newDay * 30);
        } else newDay = -1;
    }

    public void refresh(Context context) {
        Log.e(TAG, "refresh");
        saveToShPref(context);
        Toast.makeText(context, "Обновлено", Toast.LENGTH_SHORT).show();
        new AsyncSms().execute();
    }

    class AsyncSms extends AsyncTask {
        HashMap< String,DayItem> map;
        ArrayList<DayItem> list;
        float summ=0;

        @Override
        protected Object doInBackground(Object[] objects) {
            SmsHelper smsHelper = new SmsHelper();
            map = smsHelper.parseSms(context);

            Calendar calendar=Calendar.getInstance();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd.MM.yy");
            Log.e(TAG, "datestart "+dateStart);
            while (true){
                Log.e(TAG, simpleDateFormat.format(calendar.getTime()));
                String key=simpleDateFormat.format(calendar.getTime());
                if (map.containsKey(key)) summ += map.get(key).getSumm(day);
                else summ+=day;

                Log.e(TAG, "summ "+key+" "+summ);
                if (key.equals(dateStart)) break;

                calendar.add(Calendar.DAY_OF_MONTH,-1);
            }

            Log.e(TAG, "list size "+map.size());


            list=new ArrayList<>();
            calendar=Calendar.getInstance();
            int count=100;
            for (int i=0;i<count;i++){
                String key=simpleDateFormat.format(calendar.getTime());
                if (map.containsKey(key)) list.add(map.get(key));
                calendar.add(Calendar.DAY_OF_MONTH,-1);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (list!=null) recAdapter.setList(list);
            setMoney(summ);
        }
    }

}
