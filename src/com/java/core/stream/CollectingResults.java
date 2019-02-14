package com.java.core.stream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 收集结果：
 * 1.Iterator<T> iterator() 产生一个用于获取当前流中各个元素的迭代器，此为一种终结操作。
 * 2.void forEach(Consumer<? super T> action)
 * 3.Object toArray()
 * 4.<A> A[] toArray(IntFunction<A[]> generator)
 * 5.<R,A> R collect(Collector<? super T,A,R> collector)
 */
public class CollectingResults {

	public static Stream<String> noVowels() throws IOException {
		String contents = new String(Files.readAllBytes(Paths.get("gutenberg/alice30.txt")),StandardCharsets.UTF_8);
		List<String> wordList = Arrays.asList(contents.split("\\PL+"));
		Stream<String> words = wordList.stream();
		return words.map(s -> s.replaceAll("[aeiouAEIOU]",""));
	}

	public static <T> void show(String label, Set<T>set){
		System.out.println(label + ":" + set.getClass().getName());
		System.out.println("[" + set.stream().limit(10).map(Object::toString).collect(Collectors.joining(", ")) + "]");
	}

	public static void main(String [] args) throws IOException {
		Iterator<Integer> iter = Stream.iterate(0,n -> n+1).limit(10).iterator();
		while(iter.hasNext())
			System.out.println(iter.next());

		Object [] numbers = Stream.iterate(0,n -> n+1).limit(10).toArray();
		System.out.println("Object Array:" + numbers);

		try{
			Integer number = (Integer)numbers[0];
			System.out.println("number:" + number);
			System.out.println("The following statement throws an exception:");
			Integer [] numbers2 = (Integer []) numbers;
		}catch (ClassCastException ex){
			System.out.println(ex);
		}

		Integer [] numbers3 = Stream.iterate(0,n -> n+1).limit(10).toArray(Integer[]::new);
		System.out.println("Integer Array:"+ numbers3);

		Set<String> noVowelSet = noVowels().collect(Collectors.toSet());
		show("noVowelSet",noVowelSet);

		TreeSet<String> noVowelTreeSet = noVowels().collect(Collectors.toCollection(TreeSet::new));
		show("noVowelTreeSet",noVowelTreeSet);

		String result = noVowels().limit(10).collect(Collectors.joining());
		System.out.println("Joining:"+result);

		result = noVowels().limit(10).collect(Collectors.joining(", "));
		System.out.println("Joining with commas:"+result);

		IntSummaryStatistics summaryStatistics = noVowels().collect(Collectors.summarizingInt(String::length));
		double averageWordLength = summaryStatistics.getAverage();
		double maxWordLength = summaryStatistics.getMax();
		double minWordLength = summaryStatistics.getMin();
		double sum = summaryStatistics.getSum();
		double count = summaryStatistics.getCount();
		System.out.println("averageLength:"+averageWordLength);
		System.out.println("maxLength:"+ maxWordLength);
		System.out.println("minLength:"+minWordLength);
		System.out.println("sumLength:"+sum);
		System.out.println("count:"+count);
	}

}
