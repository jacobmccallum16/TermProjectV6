package com.example.termprojectv6;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class EntryGroup {
    private int size;
    private int month;
    private int year;
    private float minWeight;
    private float avgWeight;
    private float maxWeight;

    public EntryGroup(ArrayList<Entry> entries, int month, int year) {
        size = entries.size();
        this.month = month;
        this.year = year;
        if (size > 0) {
            minWeight = entries.get(0).getWeight();
            float current = 0;
            maxWeight = entries.get(0).getWeight();
            float total = 0;
            for (int i = 0; i < size; i++) {
                current = entries.get(i).getWeight();
                if (current < minWeight) {
                    minWeight = current;
                } else if (current > maxWeight) {
                    maxWeight = current;
                }
                total += current;
            }
            avgWeight = total / (float) size;
        }
    }
    public EntryGroup(ArrayList<Entry> entries) {
        size = entries.size();
        if (size > 0) {
            SimpleDateFormat dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = Date.parse(entries.get(0).getDate().toString());
            this.month = entries.get(0).getMonth();
            this.year = entries.get(0).getYear();
            minWeight = entries.get(0).getWeight();
            float current = 0;
            maxWeight = entries.get(0).getWeight();
            float total = 0;
            for (int i = 0; i < size; i++) {
                current = entries.get(i).getWeight();
                if (current < minWeight) {
                    minWeight = current;
                } else if (current > maxWeight) {
                    maxWeight = current;
                }
                total += current;
            }
            avgWeight = total / (float) size;
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getMinWeight() {
        return minWeight;
    }

    public void setMinWeight(float minWeight) {
        this.minWeight = minWeight;
    }

    public float getAvgWeight() {
        return avgWeight;
    }

    public void setAvgWeight(float avgWeight) {
        this.avgWeight = avgWeight;
    }

    public float getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(float maxWeight) {
        this.maxWeight = maxWeight;
    }
}
