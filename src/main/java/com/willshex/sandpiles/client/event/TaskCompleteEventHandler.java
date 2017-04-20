//  
//  TaskCompleteEventHandler.java
//  processor
//
//  Created by William Shakour on March 25, 2017.
//  Copyright © 2017 WillShex Limited. All rights reserved.
//
package com.willshex.sandpiles.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.willshex.sandpiles.client.processor.Task;

public interface TaskCompleteEventHandler extends EventHandler {
	public static final GwtEvent.Type<TaskCompleteEventHandler> TYPE = new GwtEvent.Type<TaskCompleteEventHandler>();

	public void taskComplete (final Task task);

	public class TaskComplete extends GwtEvent<TaskCompleteEventHandler> {
		private Task task;

		public TaskComplete (final Task task) {
			this.task = task;
		}

		@Override
		public GwtEvent.Type<TaskCompleteEventHandler> getAssociatedType () {
			return TYPE;
		}

		@Override
		protected void dispatch (TaskCompleteEventHandler handler) {
			handler.taskComplete(task);
		}
	}

}