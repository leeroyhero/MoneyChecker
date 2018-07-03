package ru.bogdanov.moneychecker.model.items;

import java.io.Serializable;

public class SmsItem implements Serializable {
    private String date, time;
    private float balance, movement;
    private String cardId, destination;

    public SmsItem(String date, String time, float balance, float movement, String cardId, String destination) {
        this.date = date;
        this.time = time;
        this.balance = balance;
        this.movement = movement;
        this.cardId = cardId;
        this.destination = destination;
    }

    @Override
    public String toString() {
        return cardId+" " +date+" "+time+" "+destination+" mov: "+movement+" bal: "+balance;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public float getBalance() {
        return balance;
    }

    public float getMovement() {
        return movement;
    }

    public String getCardId() {
        return cardId;
    }

    public String getDestination() {
        return destination;
    }
}
