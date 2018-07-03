package ru.bogdanov.moneychecker.model.items;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import ru.bogdanov.moneychecker.DayActivity;

public class DayItem {
    private float balance;
    private String date;
    private float movement;
    private float kredit;
    private ArrayList<SmsItem> smsList;

    public DayItem(String date) {
        this.date = date;
    }

    public void addSms(SmsItem smsItem){
        if (smsList==null) smsList=new ArrayList<>();

        smsList.add(smsItem);
    }

    public void fillInfo(){
        kredit=0;
        movement=0;
        for (SmsItem smsItem:smsList){
            movement+=smsItem.getMovement();
            if (smsItem.getMovement()<0) kredit+=smsItem.getMovement();
        }
        balance=smsList.get(0).getBalance();
    }

    @Override
    public String toString() {
        return "date "+date+"   movement "+movement+"    balance "+balance;
    }

    public float getSumm(float day) {
        return day+kredit;
    }

    public String getDate() {
        return date;
    }

    public float getBalance() {
        return balance;
    }

    public float getKredit() {
        return kredit;
    }

    public void dateClicked(Context context){
        Intent intent=new Intent(context, DayActivity.class);
        intent.putExtra("list", smsList);
        context.startActivity(intent);
    }
}
