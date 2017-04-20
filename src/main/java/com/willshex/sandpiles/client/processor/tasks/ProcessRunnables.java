//
//  GenerateFiles.java
//  codegen
//
//  Created by William Shakour on 20/12/2012.
//
//
package com.willshex.sandpiles.client.processor.tasks;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.willshex.sandpiles.client.processor.Task;

public class ProcessRunnables implements Task {

	protected List<Runnable> runnables;
	protected List<String> names;
	protected int progress = 0;
	protected boolean running = false;

	public float getProgress () {
		return (float) progress / (float) runnables.size();
	}

	public void run () {
		running = true;

		if (runnables != null && runnables.size() > progress) {
			GWT.log("Running task named [" + names.get(progress) + "]");
			runnables.get(progress).run();
		}

		progress++;
	}

	public void addRunnable (String name, Runnable runnable) {
		if (running) {
			GWT.log("[" + name
					+ "] cannot be updated (discarded)... task is already running");
		} else {
			ensureNames().add(name);
			ensureRunnables().add(runnable);
		}
	}

	private List<Runnable> ensureRunnables () {
		if (runnables == null) {
			runnables = new ArrayList<>();
		}

		return runnables;
	}

	private List<String> ensureNames () {
		if (names == null) {
			names = new ArrayList<>();
		}

		return names;
	}

	/* (non-Javadoc)
	 * @see com.willshex.sandpiles.client.processor.Task#reset()
	 */
	@Override
	public void reset () {
		progress = 0;
	}
}
