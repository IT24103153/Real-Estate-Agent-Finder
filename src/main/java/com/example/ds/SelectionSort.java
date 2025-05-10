package com.example.ds;

import com.example.entity.Agents;

import java.util.List;

public class SelectionSort {
    public static void sortByRating(List<Agents> agents) {
        int n = agents.size();
        for (int i = 0; i < n - 1; i++) {
            int maxIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (agents.get(j).getRating() > agents.get(maxIdx).getRating()) {
                    maxIdx = j;
                }
            }
            Agents temp = agents.get(maxIdx);
            agents.set(maxIdx, agents.get(i));
            agents.set(i, temp);
        }
    }
}