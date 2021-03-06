package com.lile;
/**
 * 最长公共子序列 Longest Common Subsequence
 * @author LiLe
 * Leetcode_1143
 */
public class LCS {

	public static void main(String[] args) {
		System.out.println(lcs(new int[] {1, 4, 5, 9, 10}, new int[] {1, 4, 9, 10}));
	} 
	
	// 非递归实现 + 滚动数组 优化
	static int lcs(int[] nums1, int[] nums2) {
		if (nums1 == null || nums1.length == 0) return 0;
		if (nums2 == null || nums2.length == 0 ) return 0;
		
		int[] rowNums = nums1, colNums = nums2;
		if (nums1.length < nums2.length) {
			rowNums = nums2;
			colNums = nums1;
		}
	
		int[] dp = new int[colNums.length + 1];
		
		for (int i = 1; i <= rowNums.length; i++) {
			int cur = 0;
			for (int j = 1; j <= colNums.length; j++) {
				int leftTop = cur;
				cur = dp[j];
				if (rowNums[i - 1] == colNums[j - 1]) {
					dp[j] = leftTop + 1;
				} else {
					dp[j] = Math.max(dp[j], dp[j - 1]);
				}
			}
		}
		return dp[colNums.length];
	}
	
	// 非递归实现 + 滚动数组 
	static int lcs4(int[] nums1, int[] nums2) {
		if (nums1 == null || nums1.length == 0) return 0;
		if (nums2 == null || nums2.length == 0 ) return 0;
		int[] dp = new int[nums2.length + 1];
		for (int i = 1; i <= nums1.length; i++) {
			int cur = 0;
			for (int j = 1; j <= nums2.length; j++) {
				int leftTop = cur;
				cur = dp[j];
				if (nums1[i - 1] == nums2[j - 1]) {
					dp[j] = leftTop + 1;
				} else {
					dp[j] = Math.max(dp[j], dp[j - 1]);
				}
			}
		}
		return dp[nums2.length];
	}
	
	// 非递归实现 + 滚动数组 
	static int lcs3(int[] nums1, int[] nums2) {
		if (nums1 == null || nums1.length == 0) return 0;
		if (nums2 == null || nums2.length == 0 ) return 0;
		int[][] dp = new int[2][nums2.length + 1];
		for (int i = 1; i <= nums1.length; i++) {
			int row = i & 1;
			int preRow = (i - 1) & 1;
			for (int j = 1; j <= nums2.length; j++) {
				if (nums1[i - 1] == nums2[j - 1]) {
					dp[row][j] = dp[preRow][j - 1] + 1;
				} else {
					dp[row][j] = Math.max(dp[preRow][j], dp[row][j - 1]);
				}
			}
		}
		return dp[nums1.length % 2][nums2.length];
	}
	
	// 非递归实现
	static int lcs2(int[] nums1, int[] nums2) {
		if (nums1 == null || nums1.length == 0) return 0;
		if (nums2 == null || nums2.length == 0 ) return 0;
		int[][] dp = new int[nums1.length + 1][nums2.length + 1];
		for (int i = 1; i <= nums1.length; i++) {
			for (int j = 1; j <= nums2.length; j++) {
				if (nums1[i - 1] == nums2[j - 1]) {
					dp[i][j] = dp[i - 1][j - 1] + 1;
				} else {
					dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
				}
			}
		}
		return dp[nums1.length][nums2.length];
	}
	
	// 递归实现
	static int lcs1(int[] nums1, int[] nums2) {
		if (nums1 == null || nums1.length == 0) return 0;
		if (nums2 == null || nums2.length == 0 ) return 0;
		return lcs1(nums1, nums1.length, nums2, nums2.length);
	}
	
	/**
	 * 求 nums1 前 i 个元素和 nums2 前 j 个元素的最长公共子序列长度
	 */
	static int lcs1(int[] nums1, int i, int[] nums2, int j) {
		if (i == 0 || j == 0) return 0;
		if (nums1[i - 1] == nums2[j - 1]) {
			return lcs1(nums1, i - 1, nums2, j - 1) + 1;
		}
		return Math.max(lcs1(nums1, i, nums2, j - 1), lcs1(nums1, i - 1, nums2, j));
	}
	
	// 非递归实现 + 滚动数组 
	static int longestCommonSubsequence(String text1, String text2) {
		if (text1 == null || text2 == null) return 0;
		char[] chars1 = text1.toCharArray();
		if (chars1.length == 0) return 0;
		char[] chars2 = text2.toCharArray();
		if (chars2.length == 0) return 0;
		
		char[] rowChars = chars1, colChars = chars2;
		if (chars1.length < chars2.length) {
			rowChars = chars2;
			colChars = chars1;
		}
	
		int[] dp = new int[colChars.length + 1];
		
		for (int i = 1; i <= rowChars.length; i++) {
			int cur = 0;
			for (int j = 1; j <= colChars.length; j++) {
				int leftTop = cur;
				cur = dp[j];
				if (rowChars[i - 1] == colChars[j - 1]) {
					dp[j] = leftTop + 1;
				} else {
					dp[j] = Math.max(dp[j], dp[j - 1]);
				}
			}
		}
		return dp[colChars.length];
	}

}
