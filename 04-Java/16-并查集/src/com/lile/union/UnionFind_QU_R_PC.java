package com.lile.union;

/**
 * Quick Union 基于 rank 的优化 - 路径压缩（Path Compression）
 * @author lile
 *
 */
public class UnionFind_QU_R_PC extends UnionFind_QU_R {

	public UnionFind_QU_R_PC(int capacity) {
		super(capacity);
	}
	
	@Override
	public int find(int v) {
		rangeCheck(v);
		
		if (v != parents[v]) {
			parents[v] = find(parents[v]);
		}
		
		return parents[v];
	}

}
