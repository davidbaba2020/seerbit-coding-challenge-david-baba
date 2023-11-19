package com.davacom.algorithmSolutions;

import lombok.Data;

import java.util.*;

public class Algorithm {

    //Use the main to call and test algorithms
    public static void main(String[] args) {

        System.out.println("*********************Algorithm 1*************************");
        //Sample data for algorithm one two sum
        int[] array = {2, 7, 11, 15};
        int target1 = 9;
        int target2 = 26;
        int target3 = 10;
        System.out.println("The array supplied for all target is:  "+ Arrays.toString(array));
        System.out.println("For target1 as 9:   "+hasTwoSum(array, target1));
        System.out.println("For target2 as 26:  "+hasTwoSum(array, target2));
        System.out.println("For target3 as 10:  "+hasTwoSum(array, target3));
        System.out.println(" ");

        System.out.println("*********************Algorithm 2*************************");
        //Sample data for algorithm two  find indexes
        int[] array2 = {1, 2, 3, 3, 3, 4, 4, 5, 5, 5, 5, 6};
        int target4 = 1;
        int target5 = 5;
        int target6 = 7;

        System.out.println("The array supplied for all target is:  "+ Arrays.toString(array2));
        System.out.println("For target4 as 1:  "+Arrays.toString(findIndices(array2,target4)));
        System.out.println("For target5 as 5:  "+Arrays.toString(findIndices(array2,target5)));
        System.out.println("For target6 as 17: "+Arrays.toString(findIndices(array2,target6)));
        System.out.println(" ");

        System.out.println("*********************Algorithm 3*************************");
        //Sample data for algorithm three merge intervals
        List<Interval> intervals = new ArrayList<>();
        intervals.add(new Interval(1, 3));
        intervals.add(new Interval(2, 6));
        intervals.add(new Interval(8, 10));
        intervals.add(new Interval(15, 18));
        System.out.println("The supplied intervals are:  "+ intervals);
        System.out.println("The merged intervals are :   "+mergeIntervals(intervals));
        System.out.println("**********************************************");





    }


//    Methods defined here
    //1. Two sum done in O(n) time complexity
    public static boolean hasTwoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return true;
            }
            map.put(nums[i], i);
        }

        return false;
    }






    //2.  findIndices done in O(log n) time complexity
    public static int[] findIndices(int[] nums, int target) {
        int[] result = {-1, -1};

        // Find low index
        int low = 0;
        int high = nums.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (nums[mid] >= target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }

            if (nums[mid] == target) {
                result[0] = mid;
            }
        }

        // Find high index
        low = 0;
        high = nums.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (nums[mid] <= target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }

            if (nums[mid] == target) {
                result[1] = mid;
            }
        }

        return result;
    }


    //3.  merge intervals done in O(n log n) time complexity
    public static List<Interval> mergeIntervals(List<Interval> intervals) {
        if (intervals.size() <= 1) {
            return intervals;
        }

        intervals.sort(Comparator.comparingInt(i -> i.start));

        int index = 0;
        for (int i = 1; i < intervals.size(); i++) {
            Interval current = intervals.get(index);
            Interval next = intervals.get(i);

            if (next.start <= current.end) {
                current.end = Math.max(current.end, next.end);
            } else {
                intervals.set(++index, next);
            }
        }

        intervals.subList(index + 1, intervals.size()).clear();
        return intervals;
    }
}

@Data
class Interval {
    int start;
    int end;

    Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
