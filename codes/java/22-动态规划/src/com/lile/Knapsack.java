package com.lile;

import com.lile.tools.Asserts;
import com.lile.tools.Times;

public class Knapsack {

	public static void main(String[] args) {
		int[] values = {6, 3, 5, 4, 6};
		int[] weights = {2, 2, 6, 5, 4};
		int capacity = 10;
		Times.test("二维数组", () -> {
			Asserts.test(maxValue1(values, weights, capacity) == 15);
		});
		Times.test("用一维数组优化", () -> {
			Asserts.test(maxValue2(values, weights, capacity) == 15);
		});
		Times.test("用一维数组优化 + 循环条件优化", () -> {
			Asserts.test(maxValue3(values, weights, capacity) == 15);
		});
		Times.test("恰好装满，用一维数组优化 + 循环条件优化", () -> {
			Asserts.test(maxValueExactly(values, weights, capacity) == 14);
		});
	}
	
	/**
	 * 恰好装满
	 * 用一维数组优化 + 循环条件优化
	 */
	static int maxValueExactly(int[] values, int[] weights, int capacity) {
		if (values == null || values.length == 0) return 0;
		if (weights == null || weights.length == 0) return 0;
		if (values.length != weights.length || capacity < 0) return 0;
		int[] dp = new int[capacity + 1];
		for (int j = 1; j < capacity; j++) {
			dp[j] = Integer.MIN_VALUE;
		}
		for (int i = 1; i <= values.length; i++) {
			for (int j = capacity; j >= weights[i - 1]; j--) {
				dp[j] = Math.max(dp[j], dp[j - weights[i - 1]] + values[i - 1]);
			}
		}
		return dp[capacity] < 0 ? -1 : dp[capacity];
	}
	
	/**
	 * 用一维数组优化 + 循环条件优化
	 */
	static int maxValue3(int[] values, int[] weights, int capacity) {
		if (values == null || values.length == 0) return 0;
		if (weights == null || weights.length == 0) return 0;
		if (values.length != weights.length || capacity < 0) return 0;
		int[] dp = new int[capacity + 1];
		for (int i = 1; i <= values.length; i++) {
			for (int j = capacity; j >= weights[i - 1]; j--) {
				dp[j] = Math.max(dp[j], dp[j - weights[i - 1]] + values[i - 1]);
			}
		}
		return dp[capacity];
	}
	
	/**
	 * 用一维数组优化
	 */
	static int maxValue2(int[] values, int[] weights, int capacity) {
		if (values == null || values.length == 0) return 0;
		if (weights == null || weights.length == 0) return 0;
		if (values.length != weights.length || capacity < 0) return 0;
		int[] dp = new int[capacity + 1];
		for (int i = 1; i <= values.length; i++) {
			for (int j = capacity; j >= 1; j--) {
				if (j < weights[i - 1]) continue;
				dp[j] = Math.max(dp[j], dp[j - weights[i - 1]] + values[i - 1]);
			}
		}
		return dp[capacity];
	}
	
	static int maxValue1(int[] values, int[] weights, int capacity) {
		if (values == null || values.length == 0) return 0;
		if (weights == null || weights.length == 0) return 0;
		if (values.length != weights.length || capacity < 0) return 0;
		int[][] dp = new int[values.length + 1][capacity + 1];
		for (int i = 1; i <= values.length; i++) {
			for (int j = 1; j <= capacity; j++) {
				if (j < weights[i - 1]) {
					dp[i][j] = dp[i - 1][j];
				} else {
					dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weights[i - 1]] + values[i - 1]);
				}
			}
		}
		return dp[values.length][capacity];
	}

}
