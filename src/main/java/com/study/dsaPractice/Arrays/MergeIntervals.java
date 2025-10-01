package com.study.dsaPractice.Arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Problem: https://leetcode.com/problems/merge-intervals/
 */
public class MergeIntervals {

    /**
     * Merges overlapping intervals using sorting.
     * 
     * @param intervals The intervals to merge
     * @return The merged intervals
     */
    public static int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }

        // Sort by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        List<int[]> merged = new ArrayList<>();
        int[] currentInterval = intervals[0];
        merged.add(currentInterval);

        for (int[] interval : intervals) {
            // If current interval overlaps with the last merged interval
            if (interval[0] <= currentInterval[1]) {
                // Update end time of current interval if needed
                currentInterval[1] = Math.max(currentInterval[1], interval[1]);
            } else {
                // No overlap, add new interval
                currentInterval = interval;
                merged.add(currentInterval);
            }
        }

        // Convert list to array and return
        return merged.toArray(new int[merged.size()][]);
    }

    public static int[][] mergeUsingPriorityQueue(int[][] intervals) {
        if (intervals == null || intervals.length == 0)
            return new int[0][];

        // Create a min-heap based on the start time of intervals
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        for (int[] interval : intervals) {
            pq.offer(interval);
        }

        List<int[]> res = new ArrayList<>();
        int[] current = pq.poll();

        // Process intervals in the priority queue
        while (!pq.isEmpty()) {
            int[] next = pq.poll();

            // No overlap
            if (next[0] > current[1]) {
                res.add(current);
                current = next;
            } else {
                // Overlap, merge intervals
                current[1] = Math.max(current[1], next[1]);
            }
        }

        // Add the last interval
        res.add(current);

        // Convert list to array and return
        return res.toArray(new int[res.size()][]);
    }

    public static void main(String[] args) {
        int[][] intervals = { { 1, 3 }, { 2, 6 }, { 8, 10 }, { 15, 18 } };

        System.out.println("Using Sorting:");
        int[][] mergedIntervals = merge(intervals);
        for (int[] interval : mergedIntervals) {
            System.out.print(Arrays.toString(interval));
        }

        System.out.println("\n\nUsing Priority Queue:");
        int[][] mergedIntervalsPQ = mergeUsingPriorityQueue(intervals);
        for (int[] interval : mergedIntervalsPQ) {
            System.out.print(Arrays.toString(interval));
        }
    }
}
