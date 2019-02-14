package com.java.core.stream;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 流的创建：
 * 1.Stream.of(...)  具有可变参数
 * 2.Array.stream(array,from,to)
 * 3.创建空流：Stream.empty()
 * 4.创建无限流：
 *      Stream.generate(Math::random)
 *      Stream.iterate(BigInteger.ZERO,n -> n.add(BigInteger.ONE));
 * 5.Pattern.compile("\\PL+").splitAsStream(contents)
 * 6.Files.lines(path);
 */
public class CreatingStreams {
	public static <T> void show(String title, Stream<T> stream){
		final int SIZE = 10;
		List<T> firstElments = stream.limit(SIZE + 1).collect(Collectors.toList());
		System.out.println(title+":");
		for(int i = 0;i < firstElments.size();i++){
			if(i>0) System.out.print(",");
			if(i<SIZE) System.out.print(firstElments.get(i));
			else System.out.print("....");
		}
		System.out.println();
	}

	public static void main(String [] args) throws IOException {
		Path path = Paths.get("gutenberg/alice30.txt");
		String contents = new String(Files.readAllBytes(path),StandardCharsets.UTF_8);

		Stream<String> words = Stream.of(contents.split("\\PL+"));
		show("words",words);

		Stream<String>song = Stream.of("gently","down","the","stream");
		show("song",song);

		Stream<String> echos = Stream.generate(() -> "Echo");
		show("echo",echos);

		Stream<Double> randoms = Stream.generate(Math::random);
		show("randoms",randoms);

		Stream<BigInteger> integers = Stream.iterate(BigInteger.ONE,n -> n.add(BigInteger.ONE));
		show("integers",integers);

		Stream<String>wordsAnotherWay = Pattern.compile("\\PL+").splitAsStream(contents);
		show("wordsAnotherWay",wordsAnotherWay);

		try(Stream<String> liens = Files.lines(path,StandardCharsets.UTF_8)){
			show("lines",liens);
		}

		String [] array = {"a","b","c","d"};
		Stream<String> arrays = Arrays.stream(array,1,3);
		show("arrays",arrays);
	}
}
