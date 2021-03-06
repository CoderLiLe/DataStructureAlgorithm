package 细节实现.Interval;

/**
 * 57. 插入区间
 * 给你一个 无重叠的 ，按照区间起始端点排序的区间列表。
 *
 * 在列表中插入一个新的区间，你需要确保列表中的区间仍然有序且不重叠（如果有必要的话，可以合并区间）。
 *
 * Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).
 * You may assume that the intervals were initially sorted according to their start times.
 * Example 1: Given intervals [1,3],[6,9], insert and merge [2,5] in as [1,5],[6,9].
 * Example 2: Given [1,2],[3,5],[6,7],[8,10],[12,16], insert and merge [4,9] in as
 * [1,2],[3,10],[12,16].
 * This is because the new interval [4,9] overlaps with [3,5],[6,7],[8,10].
 */

/**
 *
 * （1）首先将新区间左边且相离的区间加入结果集（遍历时，如果当前区间的结束位置小于新区间的开始位置，说明当前区间在新区间的左边且相离）；
 *
 * （2）接着判断当前区间是否与新区间重叠，重叠的话就进行合并，直到遍历到当前区间在新区间的右边且相离，将最终合并后的新区间加入结果集；
 *
 * （3）最后将新区间右边且相离的区间加入结果集。
 * */

import java.util.ArrayList;
import java.util.List;

public class InsertInterval {

    public static void main(String[] args) {
        int[][] intervals = {{1, 3}, {6, 9}};
        int[] newInterval = {2, 5};
        int[][] res = insert(intervals, newInterval);
        for (int[] interval : res) {
            for (int num : interval) {
                System.out.print(num + " ");
            }
            System.out.println();
        }

        System.out.println("---------------");

        int[][] intervals2 = {{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}};
        int[] newInterval2 = {4, 9};
        int[][] res2 = insert(intervals2, newInterval2);
        for (int[] interval : res2) {
            for (int num : interval) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }

    public static int[][] insert(int[][] intervals, int[] newInterval) {
        int idx = 0;
        List<int[]> res = new ArrayList<>();
        // 步骤一：找到需要合并的区间
        while (idx < intervals.length && intervals[idx][1] < newInterval[0]) {
            res.add(intervals[idx++]);
        }

        // 待合并区间头小于当前区间尾
        while (idx < intervals.length && intervals[idx][0] <= newInterval[1]) {
            newInterval[0] = Math.min(newInterval[0], intervals[idx][0]); // 更新待合并区间头
            newInterval[1] = Math.max(newInterval[1], intervals[idx][1]); // 更新待合并区间尾
            idx++;
        }

        // 加入合并区间
        res.add(newInterval);
        // 加入剩余区间
        while (idx < intervals.length) {
            res.add(intervals[idx++]);
        }
        return res.toArray(new int[res.size()][]);
    }
}
