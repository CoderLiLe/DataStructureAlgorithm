package com.lile.sort;

public class CountingSort extends Sort<Integer> {

	// T = 3 * O(n) + O(k) = O(n + k)
	// S = O(n + k)
	@Override
	protected void sort() {
		// 找出最值
		int max = array[0];
		int min = array[0];
		for (int i = 0; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
			}
			if (array[i] < min) {
				min = array[i];
			}
		}
		
		// 开辟内存空间，存储次数
		int[] counts = new int[max - min + 1]; // S = O(k)，k 最大最小值之差
		// 统计每个整数出现的次数
		for (int i = 0; i < array.length; i++) { // T = O(n)
			counts[array[i] - min]++;
		}
		
		// 累加次数
		for (int i = 1; i < counts.length; i++) { // T = O(k)，k 最大最小值之差
			counts[i] += counts[i - 1];
		}
		
		// 从后往前遍历元素，将它放到有序数组中的合适位置
		int[] newArray = new int[array.length]; // S = O(n)
		for (int i = array.length - 1; i >= 0; i--) { // T = O(n)
			newArray[--counts[array[i] - min]] = array[i];
		}
		
		// 将有序数组赋值到 array
		for (int i = 0; i < newArray.length; i++) { // T = O(n)
			array[i] = newArray[i];
		}
	}
	
	protected void sort0() {
		// 找出最大值
		int max = array[0];
		for (int i = 0; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		}
		
		// 开辟内存空间，存储每个整数出现的次数
		int[] counts = new int[1 + max];
		// 统计每个整数出现的次数
		for (int i = 0; i < array.length; i++) {
			counts[array[i]]++;
		}
		
		// 根据整数的出现次数，对整数进行排序
		int index = 0;
		for (int i = 0; i < counts.length; i++) {
			while (counts[i]-- > 0) {
				array[index++] = i;
			}
		}	
	}
	
	public static void main() {
		Person[] persons = new Person[] {
				new Person(20, "A"),
				new Person(-13, "B"),
				new Person(17, "C"),
				new Person(12, "D"),
				new Person(-13, "E"),
				new Person(20, "F")
		};
		
		// 找出最值
		int max = persons[0].age;
		int min = persons[0].age;
		for (int i = 0; i < persons.length; i++) {
			if (persons[i].age > max) {
				max = persons[i].age;
			}
			if (persons[i].age < min) {
				min = persons[i].age;
			}
		}
		
		// 开辟内存空间，存储次数
		int[] counts = new int[max - min + 1];
		// 统计每个整数出现的次数
		for (int i = 0; i < persons.length; i++) {
			counts[persons[i].age - min]++;
		}
		
		// 累加次数
		for (int i = 1; i < counts.length; i++) {
			counts[i] += counts[i - 1];
		}
		
		// 从后往前遍历元素，将它放到有序数组中的合适位置
		Person[] newArray = new Person[persons.length];
		for (int i = persons.length - 1; i >= 0; i--) {
			newArray[--counts[persons[i].age - min]] = persons[i];
		}
		
		// 将有序数组赋值到 array
		for (int i = 0; i < newArray.length; i++) {
			persons[i] = newArray[i];
		}
		
		for (int i = 0; i < persons.length; i++) {
			System.out.println(persons[i]);
		}
	}
	
	private static class Person { 
		int age;
		String name;
		Person(int age, String name) {
			this.age = age;
			this.name = name;
		}
		
		@Override
		public String toString() {
			return "Person [age = " + age + ", name = " + name + "]";
		}
		
	}

}
