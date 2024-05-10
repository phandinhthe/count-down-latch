package org.example.worker;

import org.example.common.Color;

public class WorkerReport {
	private final String name;
	private final long duration;

	public WorkerReport(String name, long duration) {
		this.name = name;
		this.duration = duration;
	}

	public long duration() {
		return duration;
	}

	public String report(Color color) {
		return report(color, "'%s' has finished his work within %d milliseconds", name, duration);
	}

	public String report(Color color, String format, Object... args) {
		return String.format(color.encodedColor() + format + Color.BLACK.encodedColor(), args);
	}
}
