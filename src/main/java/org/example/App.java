package org.example;

import org.example.common.Color;
import org.example.worker.Worker;
import org.example.worker.WorkerReport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Hello world!
 */
public class App {
	public static void main(String[] args) {
		System.out.println(Color.YELLOW.encodedColor() +
				"================== Message System - Java Multithreading using BlockingQueue ===============\n"
				+ Color.BLACK.encodedColor()
		);
		int processors = Runtime.getRuntime().availableProcessors();
		int size = 3;
		CountDownLatch countDownLatch = new CountDownLatch(size);
		Worker[] workers = {new Worker("The Phan", Color.RED, countDownLatch)
				, new Worker("Terry Phan", Color.ORANGE, countDownLatch)
				, new Worker("Frank", Color.VIOLET, countDownLatch)};
		List<WorkerReport> results = new ArrayList<>(size);

		try (ExecutorService service = Executors.newFixedThreadPool(processors)) {
			List<Future<WorkerReport>> futureResults = service.invokeAll(Stream.of(workers).collect(Collectors.toList()));
			countDownLatch.await(5000L, TimeUnit.MILLISECONDS);
			for (Future<WorkerReport> futureResult : futureResults) {
				results.add(futureResult.get());
			}
		} catch (InterruptedException | ExecutionException exception) {
			Thread.currentThread().interrupt();
			System.err.print(exception.getMessage());
		} finally {
			long total = results.stream().map(WorkerReport::duration).mapToLong(Long::longValue).sum();
			System.out.println(Color.YELLOW.encodedColor()
					+ String.format("\n================== Finished all within %d milliseconds !!!!==================", total)
					+ Color.BLACK.encodedColor());
			results.forEach(r -> System.out.println(" »» " + r.report(Color.YELLOW)));
		}
	}
}
