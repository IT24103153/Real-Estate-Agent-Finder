package com.realestate.agentsystem.util.datastructure;

import com.realestate.agentsystem.model.Agent;
import java.util.List;


// SelectionSort.java
public class SelectionSort {
    public static <T extends Comparable<T>> void sort(List<T> list) {
        int n = list.size();

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < n; j++) {
                if (list.get(j).compareTo(list.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }

            // Swap
            T temp = list.get(minIndex);
            list.set(minIndex, list.get(i));
            list.set(i, temp);
        }
    }

    // Specific method to sort agents by rating
    public static void sortAgentsByRating(List<Agent> agents) {
        int n = agents.size();

        for (int i = 0; i < n - 1; i++) {
            int maxIndex = i;

            for (int j = i + 1; j < n; j++) {
                if (agents.get(j).getAverageRating() > agents.get(maxIndex).getAverageRating()) {
                    maxIndex = j;
                }
            }

            // Swap
            Agent temp = agents.get(maxIndex);
            agents.set(maxIndex, agents.get(i));
            agents.set(i, temp);
        }
    }
}
