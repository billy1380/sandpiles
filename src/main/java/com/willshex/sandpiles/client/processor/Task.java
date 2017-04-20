//
//  Task.java
//  codegen
//
//  Created by William Shakour on 20/12/2012.
//
//
package com.willshex.sandpiles.client.processor;

public interface Task {

	public void run ();

	public float getProgress ();
	
	public void reset();

}
