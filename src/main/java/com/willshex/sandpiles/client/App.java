//
//  App.java
//  sandpiles
//
//  Created by William Shakour (billy1380) on 19 Apr 2017.
//  Copyright © 2017 WillShex Limited. All rights reserved.
//
package com.willshex.sandpiles.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.willshex.sandpiles.client.controller.NavigationController;
import com.willshex.sandpiles.client.part.FooterPart;
import com.willshex.sandpiles.client.part.HeaderPart;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * @author William Shakour (billy1380)
 *
 */
public class App implements EntryPoint {

	private HTMLPanel content;

	/* (non-Javadoc)
	 * 
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad() */
	@Override
	public void onModuleLoad () {
		History.addValueChangeHandler(NavigationController.get());
		createContentAndPages();
		History.fireCurrentHistoryState();
	}

	private void createContentAndPages () {
		content = new HTMLPanel("<!-- content -->");
		RootPanel.get().add(content);
		
		content.add(HeaderPart.get());
		content.add(NavigationController.get()
				.setPageHolder(new HTMLPanel("<!-- pages -->")));
		content.add(FooterPart.get());
	}
}