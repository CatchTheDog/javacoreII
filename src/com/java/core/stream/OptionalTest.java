package com.java.core.stream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Optional<T> 对象是一种类型T的对象的包装对象。
 * Optional对象创建：
 * 1.static <T> Optional <T> of(T value)
 * 2.static <T> Optional <T> ofNullable(T value)
 * 3.static <T> Optional <T> empty()
 * 获取Optional对象的值：
 * 1.T orElse(T other)
 * 2.T orElseGet(Supplier<? extends T> other>)
 * 3.<X extends Thrable> T orElseThrow(Supplier<? extends X> exceptionSupplier)
 * 4.void ifPresent(Consumer<? super T> consumer)
 * 5.<U> Optional <U> map(Function<? super T,? extends U> mapper)
 * 6.T get()
 * 7.boolean isPresent()
 *
 */
public class OptionalTest {
	public static void main(String [] args) throws IOException {
		String contents = new String(Files.readAllBytes(Paths.get("gutenberg/alice30.txt")),StandardCharsets.UTF_8);
		List<String> wordList = Arrays.asList(contents.split("\\PL+"));
		//流的约简操作生成Optional对象
		Optional<String> optionalValue = wordList.stream().filter(s -> s.contains("fred")).findFirst();
		System.out.println(optionalValue.orElse("No world") + "contains fred");

		Optional<String> optionalString = Optional.empty();
		String result = optionalString.orElse("N/A");
		System.out.println("result:"+result);
		result = optionalString.orElseGet(() -> Locale.getDefault().getDisplayName());
		System.out.println("result:"+result);

		optionalValue = wordList.stream().filter(s -> s.contains("red")).findFirst();
		optionalValue.ifPresent(s -> System.out.println(s + "contains red"));

		Set<String> results = new HashSet<>();
		optionalValue.ifPresent(results::add);
		Optional<Boolean> added = optionalValue.map(results::add);
		System.out.println(added);

		System.out.println(inverse(4.0).flatMap(OptionalTest::squareRoot));
		System.out.println(inverse(-1.0).flatMap(OptionalTest::squareRoot));
		System.out.println(inverse(0.0).flatMap(OptionalTest::squareRoot));
		Optional<Double> result2 = Optional.of(-4.0).flatMap(OptionalTest::inverse).flatMap(OptionalTest::squareRoot);
		System.out.println(result2);

		try{
			result = optionalString.orElseThrow(IllegalStateException::new);
			System.out.println("result:"+result);
		}catch (Throwable t){
			t.printStackTrace();
		}
	}

	private static Optional<Double> inverse(Double x){
		return x == 0 ? Optional.empty() : Optional.of(1/x);
	}

	private static Optional<Double> squareRoot(Double x){
		return x < 0 ? Optional.empty() : Optional.of(Math.sqrt(x));
	}
}
