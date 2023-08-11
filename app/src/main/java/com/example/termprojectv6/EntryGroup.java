package com.example.termprojectv6;

import java.util.ArrayList;

public class EntryGroup {
    private final int size;
    private final int month;
    private final int year;
    private float minWeight;
    private float avgWeight;
    private float maxWeight;

    public EntryGroup(ArrayList<Entry> entries, int month, int year) {
        size = entries.size();
        this.month = month;
        this.year = year;
        if (size > 0) {
            minWeight = entries.get(0).getWeight();
            float current;
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
    // for predictions
    public EntryGroup(int month, int year, float minWeight, float avgWeight, float maxWeight) {
        size = 1;
        this.month = month;
        this.year = year;
        this.minWeight = minWeight;
        this.avgWeight = avgWeight;
        this.maxWeight = maxWeight;
    }

    public int getSize() {
        return size;
    }
    public int getMonth() {
        return month;
    }
    public int getYear() {
        return year;
    }
    public float getMinWeight() {
        return minWeight;
    }
    public float getAvgWeight() {
        return avgWeight;
    }
    public float getMaxWeight() {
        return maxWeight;
    }
}
