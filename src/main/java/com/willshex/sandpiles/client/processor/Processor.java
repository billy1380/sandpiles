//
//  Processor.java
//  codegen
//
//  Created by William Shakour on 19/12/2012.
//
//
package com.willshex.sandpiles.client.processor;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.willshex.sandpiles.client.DefaultEventBus;
import com.willshex.sandpiles.client.event.TaskCompleteEventHandler.TaskComplete;

public class Processor {

	private static Processor one = null;

	private Processor () {}

	public static Processor get () {
		if (one == null) {
			one = new Processor();
		}

		return one;
	}

	private boolean isPaused = true, inUpdate = false;
	protected List<Task> taskQueue = null;

	public void addTask (Task task) {
		if (taskQueue == null) {
			taskQueue = new ArrayList<Task>();
		}

		taskQueue.add(task);

		if (isPaused && !inUpdate) {
			GWT.log("resuming update loop...");

			resume();
		}
	}

	public void removeTask (Task task) {
		if (task != null && taskQueue != null) {
			boolean paused = false;

			if (!isPaused) {
				pause();
				paused = true;
			}

			taskQueue.remove(task);

			if (paused) {
				resume();
			}
		}
	}

	public boolean update () {
		inUpdate = true;

		boolean more = false;

		if (!isPaused && hasOutstanding()) {
			Task task = taskQueue.get(0);

			if (task != null) {
				GWT.log("Found task 1 of " + taskQueue.size());

				GWT.log("running task...");
				task.run();

				float progress = task.getProgress();
				GWT.log("current run is " + progress
						+ " of total task size (0.0 - 1.0)");
				if (progress >= 1.0f) {
					GWT.log("task complete removing...");
					taskQueue.remove(task);

					DefaultEventBus.get().fireEventFromSource(
							new TaskComplete(task), Processor.this);
				}

				if (hasOutstanding()) {
					GWT.log("resuming update loop...");
					more = true;
				}
			}
		} else {
			GWT.log("Task queue is empty");
		}

		isPaused = !more;

		inUpdate = false;

		return more;
	}

	public boolean hasOutstanding () {
		return taskQueue != null && taskQueue.size() > 0;
	}

	private void pause () {
		isPaused = true;
	}

	private void resume () {
		isPaused = false;

		if (!inUpdate && hasOutstanding()) {
			Scheduler.get().scheduleIncremental(new RepeatingCommand() {

				@Override
				public boolean execute () {
					return update();
				}
			});
		}
	}
}