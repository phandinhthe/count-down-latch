package org.example.worker;

import org.example.common.Color;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class Worker implements Callable<WorkerReport> {
	private final String name;
	private final Color color;
	private final CountDownLatch countDownLatch;

	public Worker(String name, Color color, CountDownLatch countDownLatch) {
		this.name = name;
		this.color = color;
		this.countDownLatch = countDownLatch;
	}

	@Override
	public WorkerReport call() throws Exception {
		Random random = new Random();
		long sleepingTime = random.nextLong(800L, 2001L);
		System.out.printf(color.encodedColor()
				+ "%s has been working for %d milliseconds....\n"
				+ Color.BLACK.encodedColor(), name, sleepingTime);
		Thread.sleep(sleepingTime);
		countDownLatch.countDown();
		return new WorkerReport(name, sleepingTime);
	}
}
