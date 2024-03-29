package com.lile.graph;

import com.lile.MinHeap;
import com.lile.UnionFind;

import java.util.*;
import java.util.Map.Entry;

@SuppressWarnings("unchecked")
public class ListGraph<V, E> extends Graph<V, E> {
	public ListGraph() {
		this(null);
	}
	
	public ListGraph(WeightManager<E> weightManager) {
		super(weightManager);
	}

	private Map<V, Vertex<V, E>> vertices = new HashMap<>();
	private Set<Edge<V, E>> edges = new HashSet<>(); 
	private Comparator<Edge<V, E>> edgeComparator = (Edge<V, E> e1, Edge<V, E> e2) -> {
		return weightManager.compare(e1.weight, e2.weight);
	}; 
	
	public void print() {
		System.out.println("---------[顶点]---------");
		vertices.forEach((V v, Vertex<V, E> vertex) -> {
			System.out.println(v) ;
			System.out.println("out---------------");
			System.out.println(vertex.outEdges);
			System.out.println("in----------------");
			System.out.println(vertex.inEdges);
		});
		System.out.println("---------[边]---------");
		edges.forEach((Edge<V, E> edge) -> {
			System.out.println(edge) ;
		});
	}
	
	@Override
	public int edgesSize() {
		return edges.size();
	}

	@Override
	public int verticesSize() {
		return vertices.size();
	}

	@Override
	public void addVertex(V v) {
		if (vertices.containsKey(v)) return;
		vertices.put(v, new Vertex<>(v));
	}

	@Override
	public void addEdge(V from, V to) {
		addEdge(from, to, null);
	}

	@Override
	public void addEdge(V from, V to, E weight) {
		Vertex<V, E> fromVertex = vertices.get(from);
		if (fromVertex == null) {
			fromVertex = new Vertex<>(from);
			vertices.put(from, fromVertex);
		}
		
		Vertex<V, E> toVertex = vertices.get(to);
		if (toVertex == null) {
			toVertex = new Vertex<>(to);
			vertices.put(to, toVertex);
		}
		Edge<V, E> edge = new Edge<>(fromVertex, toVertex);
		edge.weight = weight;
		
		if (fromVertex.outEdges.remove(edge)) {
			toVertex.inEdges.remove(edge);
			edges.remove(edge);
		}
		
		fromVertex.outEdges.add(edge);
		toVertex.inEdges.add(edge);
		edges.add(edge);
		
	}

	@Override
	public void removeVertex(V v) {
		Vertex<V, E> vertex = vertices.remove(v);
		if (vertex == null) return;
		
		for (Iterator iterator = vertex.outEdges.iterator(); iterator.hasNext();) {
			Edge<V, E> edge = (Edge<V, E>) iterator.next();
			edge.to.inEdges.remove(edge);
			// 将当前遍历到的元素edge从集合vertex.outEdges中删除
			iterator.remove();
			edges.remove(edge);
		}
		
		for (Iterator iterator = vertex.inEdges.iterator(); iterator.hasNext();) {
			Edge<V, E> edge = (Edge<V, E>) iterator.next();
			edge.from.outEdges.remove(edge);
			// 将当前遍历到的元素edge从集合vertex.outEdges中删除
			iterator.remove();
			edges.remove(edge);
		}
	}

	@Override
	public void removeEdge(V from, V to) {
		Vertex<V, E> fromVertex = vertices.get(from);
		if (fromVertex == null) return;
		Vertex<V, E> toVertex = vertices.get(to);
		if (toVertex == null) return;
		Edge<V, E> edge = new Edge<>(fromVertex, toVertex);
		if (fromVertex.outEdges.remove(edge)) {
			toVertex.inEdges.remove(edge);
			edges.remove(edge);
		}
	}
	
	@Override
	public void bfs(V begin, VertexVisitor<V> visitor) {
		if (visitor == null) return;
		Vertex<V, E> beginVertex = vertices.get(begin);
		if (beginVertex == null) return;
		
		Set<Vertex<V, E>> visitedVertices = new HashSet<>();
		Queue<Vertex<V, E>> queue = new LinkedList<>();
		queue.offer(beginVertex);
		visitedVertices.add(beginVertex);
		
		while (!queue.isEmpty()) {
			Vertex<V, E> vertex = queue.poll();
			if (visitor.visit(vertex.value)) return;
			
			for (Edge<V, E> edge : vertex.outEdges) {
				if (visitedVertices.contains(edge.to)) continue;
				queue.offer(edge.to);
				visitedVertices.add(edge.to);
			}
		}
		
	}

	@Override
	public void dfs(V begin, VertexVisitor<V> visitor) {
		if (visitor == null) return;
		Vertex<V, E> beginVertex = vertices.get(begin);
		if (beginVertex == null) return;
		
		Set<Vertex<V, E>> visitedVertices = new HashSet<>();
		Stack<Vertex<V, E>> stack = new Stack<>();
		
		stack.push(beginVertex);
		visitedVertices.add(beginVertex);
		if (visitor.visit(begin)) return;
		
		while (!stack.isEmpty()) {
			Vertex<V, E> vertex = stack.pop();
			
			for (Edge<V, E> edge : vertex.outEdges) {
				if (visitedVertices.contains(edge.to)) continue;
				
				stack.push(vertex);
				stack.push(edge.to);
				visitedVertices.add(edge.to);
				if (visitor.visit(edge.to.value)) return;
				break;
			}
		}
	}
	
	/*
	@Override
	public void bfs(V begin) {
		Vertex<V, E> beginVertex = vertices.get(begin);
		if (beginVertex == null) return;
		
		Set<Vertex<V, E>> visitedVertices = new HashSet<>();
		Queue<Vertex<V, E>> queue = new LinkedList<>();
		queue.offer(beginVertex);
		visitedVertices.add(beginVertex);
		
		while (!queue.isEmpty()) {
			Vertex<V, E> vertex = queue.poll();
			System.out.println(vertex.value);
			
			for (Edge<V, E> edge : vertex.outEdges) {
				if (visitedVertices.contains(edge.to)) continue;
				queue.offer(edge.to);
				visitedVertices.add(edge.to);
			}
		}
	}
	
	@Override
	public void dfs(V begin) {
		Vertex<V, E> beginVertex = vertices.get(begin);
		if (beginVertex == null) return;
//		dfs(beginVertex, new HashSet<>());
		dfs(beginVertex);
	}
	
	private void dfs(Vertex<V, E> vertex, Set<Vertex<V, E>> visitedVertices) {
		System.out.println(vertex.value);
		visitedVertices.add(vertex);
		
		for (Edge<V, E> edge : vertex.outEdges) {
			if (visitedVertices.contains(edge.to)) continue;
			dfs(edge.to, visitedVertices);
		}
	}
	
	private void dfs(Vertex<V, E> beginVertex) {
		Set<Vertex<V, E>> visitedVertices = new HashSet<>();
		Stack<Vertex<V, E>> stack = new Stack<>();
		stack.push(beginVertex);
		visitedVertices.add(beginVertex);
		System.out.println(beginVertex.value);
		
		while (!stack.isEmpty()) {
			Vertex<V, E> vertex = stack.pop();
			
			for (Edge<V, E> edge : vertex.outEdges) {
				if (visitedVertices.contains(edge.to)) continue;
				
				stack.push(vertex);
				stack.push(edge.to);
				visitedVertices.add(edge.to);
				System.out.println(edge.to.value);
				break;
			}
		}
	}
	*/
	
	@Override
	public List<V> topologicalSort() {
		List<V> list = new ArrayList<>();
		Queue<Vertex<V, E>> queue = new LinkedList<>();
		Map<Vertex<V, E>, Integer> ins = new HashMap<>();
		
		// 初始化（将度为 0 的节点放入队列）
		vertices.forEach((V v, Vertex<V, E> vertex) -> {
			int in = vertex.inEdges.size();
			if (in == 0) {
				queue.offer(vertex);
			} else {
				ins.put(vertex, in);
			}
		});
		
		while (!queue.isEmpty()) {
			Vertex<V, E> vertex = queue.poll();
			// 放入返回结果中
			list.add(vertex.value);
			
			for (Edge<V, E> edge : vertex.outEdges) {
				int toIn = ins.get(edge.to) - 1;
				if (toIn == 0) {
					queue.offer(edge.to);
				} else {
					ins.put(edge.to, toIn);
				}
			}
			
		}
		return list;
	}
	
	@Override
	public Set<EdgeInfo<V, E>> mst() {
//		return prim();
		return kruskal();
	}
	
	public Set<EdgeInfo<V, E>> prim() {
		Iterator<Vertex<V, E>> it = vertices.values().iterator();
		if (!it.hasNext()) return null;
		
		Set<EdgeInfo<V, E>> edgeInfos = new HashSet<>();
		Set<Vertex<V, E>> addedVertices = new HashSet<>();
		
		Vertex<V, E> vertex = it.next();
		addedVertices.add(vertex);
		MinHeap<Edge<V, E>> heap = new MinHeap<>(vertex.outEdges, edgeComparator);
		
		int edgeSize = vertices.size();
		while (!heap.isEmpty() && edgeInfos.size() < edgeSize) {
			Edge<V, E> edge = heap.remove();
			if (addedVertices.contains(edge.to)) continue;
			
			edgeInfos.add(edge.info());
			addedVertices.add(edge.to);
			heap.addAll(edge.to.outEdges);
		}
		
		return edgeInfos;
	}
	
	public Set<EdgeInfo<V, E>> kruskal() {
		int edgeSize = vertices.size();
		if (edgeSize <= 1) return null;
		
		Set<EdgeInfo<V, E>> edgeInfos = new HashSet<>();
		
		// O(E)
		MinHeap<Edge<V, E>> heap = new MinHeap<>(edges, edgeComparator);
		UnionFind<Vertex<V, E>> uf = new UnionFind<>();
		// O(V)
		vertices.forEach((V v, Vertex<V, E> vertex) -> {
			uf.makeSet(vertex);
		});
		// O(ElogE)
		while (!heap.isEmpty() && edgeInfos.size() < edgeSize) { // E
			Edge<V, E> edge = heap.remove(); // O(logE)
			if (uf.isSame(edge.from, edge.to)) continue;
			
			edgeInfos.add(edge.info());
			uf.union(edge.from, edge.to);
		}
		return edgeInfos;
	}
	
	public Map<V, PathInfo<V, E>> shortestPath(V begin) {
//		return bellmanFord(begin);
		return dijkstra(begin);
	}
	
	private Map<V, PathInfo<V, E>> bellmanFord(V begin) {
		Vertex<V, E> beginVertex = vertices.get(begin);
		if (beginVertex == null) return null;
		
		Map<V, PathInfo<V, E>> selectedPaths = new HashMap<>();
		selectedPaths.put(begin, new PathInfo<>(weightManager.zero()));
		
		int count = vertices.size() - 1;
		for (int i = 0; i < count; i++) {
			for (Edge<V, E> edge : edges) {
				PathInfo<V, E> fromPath = selectedPaths.get(edge.from.value);
				if (fromPath == null) continue;
				relax(edge, fromPath, selectedPaths);
			}
		}
		
		for (Edge<V, E> edge : edges) {
			PathInfo<V, E> fromPath = selectedPaths.get(edge.from.value);
			if (fromPath == null) continue;
			if (relax(edge, fromPath, selectedPaths)) {
				System.out.println("有负权环");
				return null;
			}
		}
		
		selectedPaths.remove(begin);
		return selectedPaths;
	}
	
	/**
	 * 松弛操作
	 * @param edge 需要进行松弛的边
	 * @param fromPath edge 的 from 的最短路径
	 * @param paths 存放着其他点（对于 dijkstra来说，就是没有离开桌面的点）的最短路径信息
	 */
	private boolean relax(Edge<V, E> edge, PathInfo<V, E> fromPath, Map<V, PathInfo<V, E>> paths) {
		// 新的可选最短路径：beginVertex 到 edge.from 的最短路径 + edge.weight
		E newWeight = weightManager.add(fromPath.weight, edge.weight);
		// 以前的最短路径：beginVertex 到 edge.to 的最短路径
		PathInfo<V, E> oldPath = paths.get(edge.to.value);
		if (oldPath != null && weightManager.compare(newWeight, oldPath.weight) >= 0) return false;
		
		if (oldPath == null) {
			oldPath = new PathInfo<>();
			paths.put(edge.to.value, oldPath);
		} else {
			oldPath.edgeInfos.clear();
		}
		
		oldPath.weight = newWeight;
		oldPath.edgeInfos.addAll(fromPath.edgeInfos);
		oldPath.edgeInfos.add(edge.info());
		
		return true;
	}
	
	private Map<V, PathInfo<V, E>> dijkstra(V begin) {
		Vertex<V, E> beginVertex = vertices.get(begin);
		if (beginVertex == null) return null;
		
		Map<V, PathInfo<V, E>> selectedPaths = new HashMap<>();
		Map<Vertex<V, E>, PathInfo<V, E>> paths = new HashMap<>();
		paths.put(beginVertex, new PathInfo<>(weightManager.zero()));
		
		while (!paths.isEmpty()) {
			Entry<Vertex<V, E>, PathInfo<V, E>> minEntry = getMinPath(paths);
			// minVertex 离开桌面
			Vertex<V, E> minVertex = minEntry.getKey();
			PathInfo<V, E> minPath = minEntry.getValue();
			selectedPaths.put(minVertex.value, minPath);
			paths.remove(minVertex);
			// 对他的 minVertex 的 outEdges 边进行松弛操作
			for (Edge<V, E> edge : minVertex.outEdges) {
				// 如果 edge.to 已经离开桌面，就没有必要进行松弛
				if (selectedPaths.containsKey(edge.to.value)) continue;
				relaxForDijkstra(edge, minPath, paths);
			}
		}
		selectedPaths.remove(begin);
		return selectedPaths;
	}
	
	private boolean relaxForDijkstra(Edge<V, E> edge, PathInfo<V, E> fromPath, Map<Vertex<V, E>, PathInfo<V, E>> paths) {
		// 新的可选最短路径：beginVertex 到 edge.from 的最短路径 + edge.weight
		E newWeight = weightManager.add(fromPath.weight, edge.weight);
		// 以前的最短路径：beginVertex 到 edge.to 的最短路径
		PathInfo<V, E> oldPath = paths.get(edge.to);
		if (oldPath != null && weightManager.compare(newWeight, oldPath.weight) >= 0) return false;
		
		if (oldPath == null) {
			oldPath = new PathInfo<>();
			paths.put(edge.to, oldPath);
		} else {
			oldPath.edgeInfos.clear();
		}
		
		oldPath.weight = newWeight;
		oldPath.edgeInfos.addAll(fromPath.edgeInfos);
		oldPath.edgeInfos.add(edge.info());
		
		return false;
	}
	
	/**
	 * 从 paths 中挑一个最小的路径出来
	 */
	private Entry<Vertex<V, E>, PathInfo<V, E>> getMinPath(Map<Vertex<V, E>, PathInfo<V, E>> paths) {
		Iterator<Entry<Vertex<V, E>, PathInfo<V, E>>> it = paths.entrySet().iterator();
		Entry<Vertex<V, E>, PathInfo<V, E>> minEntry = it.next();
		while (it.hasNext()) {
			Entry<Vertex<V, E>, PathInfo<V, E>> entry = it.next();
			if (weightManager.compare(entry.getValue().weight, minEntry.getValue().weight) < 0) {
				minEntry = entry;
			}
		}
		return minEntry;
	}
	
	private static class Vertex<V, E> {
		V value;
		int inDegree;
		Set<Edge<V, E>> inEdges = new HashSet<>();
		Set<Edge<V, E>> outEdges = new HashSet<>();
		Vertex(V value) {
			this.value = value;
		}
		@Override
		public boolean equals(Object obj) {
			return Objects.equals(value, ((Vertex<V, E>)obj).value);
		}
		@Override
		public int hashCode() {
			return value == null ? 0 : value.hashCode();
		}
		@Override
		public String toString() {
			return value == null ? "null" : value.toString();
		}
	} 
	
	private static class Edge<V, E> {
		Vertex<V, E> from;
		Vertex<V, E> to;
		E weight;
		Edge(Vertex<V, E> from, Vertex<V, E> to) { 
			this.from = from;
			this.to = to;
		}
		
		EdgeInfo<V, E> info() {
			return new EdgeInfo<>(from.value, to.value, weight);
		}
		@Override
		public boolean equals(Object obj) {
			Edge<V, E> edge = (Edge<V, E>)obj;
			return Objects.equals(from, edge.from) && Objects.equals(to, edge.to);
		}
		@Override
		public int hashCode() {
			return from.hashCode() * 31 + to.hashCode();
		}
	}
}
