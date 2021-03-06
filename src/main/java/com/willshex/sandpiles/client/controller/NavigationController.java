//
//  NavigationController.java
//  blogwt
//
//  Created by billy1380 on 14 Aug 2013.
//  Copyright © 2013 WillShex Limited. All rights reserved.
//

package com.willshex.sandpiles.client.controller;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.willshex.sandpiles.client.DefaultEventBus;
import com.willshex.sandpiles.client.event.NavigationChangedEventHandler;
import com.willshex.sandpiles.client.event.NavigationChangedEventHandler.NavigationChangedEvent;
import com.willshex.sandpiles.client.helper.PageTypeHelper;
import com.willshex.sandpiles.shared.page.PageType;
import com.willshex.sandpiles.shared.page.Stack;

/**
 * @author billy1380
 * 
 */
public class NavigationController implements ValueChangeHandler<String> {
	private static NavigationController one = null;

	private HTMLPanel pageHolder = null;

	/**
	 * @return
	 */
	public static NavigationController get () {
		if (one == null) {
			one = new NavigationController();
		}

		return one;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public HTMLPanel setPageHolder (HTMLPanel value) {
		return pageHolder = value;
	}

	public void createAsyncPage (final PageType pageType,
			final NavigationChangedEvent event) {
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure (Throwable caught) {
				GWT.log("Could not create page " + pageType, caught);
			}

			public void onSuccess () {
				pages.put(pageKeyForCache(pageType),
						PageTypeHelper.createPage(pageType));

				attachPage(pageType, event);
			}
		});
	}

	public static final String ADD_ACTION_PARAMETER_VALUE = "add";
	public static final String EDIT_ACTION_PARAMETER_VALUE = "edit";
	public static final String DELETE_ACTION_PARAMETER_VALUE = "delete";
	public static final String VIEW_ACTION_PARAMETER_VALUE = "view";

	private Map<String, Composite> pages = new HashMap<String, Composite>();

	private Stack stack;

	private String intended = null;

	private void attachPage (PageType type,
			final NavigationChangedEvent event) {
		Composite page = null;

		if ((page = getPageFromCache(type)) == null) {
			createAsyncPage(type, event);
		}

		if (page != null) {
			if (!page.isAttached()) {
				pageHolder.clear();
				pageHolder.add(page);
			}

			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				@Override
				public void execute () {
					DefaultEventBus.get().fireEventFromSource(event,
							NavigationController.this);
				}
			});
		}
	}

	private String pageKeyForCache (PageType pageType) {
		return pageType.toString();
	}

	private Composite getPageFromCache (PageType type) {
		return pages.get(pageKeyForCache(type));
	}

	/**
	 * @param value
	 */
	public void addPage (String value) {
		value = (value == null || value.trim().length() == 0
				|| value.replace("!", "").trim().length() == 0)
						? PageType.HomePageType.asTargetHistoryToken() : value;
		Stack s = Stack.parse(value);

		if (intended != null && intended.equals(s.toString())) {
			intended = null;
		}

		addStack(s);
	}

	private void addStack (Stack value) {
		String page = value.getPage();

		PageType stackPage = PageType.fromString(page);

		if (stackPage == null) {
			stackPage = PageType.HomePageType;
		}

		final Stack previous = stack;
		stack = value;

		attachPage(stackPage,
				new NavigationChangedEventHandler.NavigationChangedEvent(
						previous, stack));
	}

	public PageType getCurrentPage () {
		PageType p = null;
		if (stack != null) {
			p = PageType.fromString(stack.getPage());
		}
		return p;
	}

	public Stack getStack () {
		return stack;
	}

	/* (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.event.logical.shared.ValueChangeHandler#onValueChange(
	 * com.google.gwt.event.logical.shared.ValueChangeEvent) */
	@Override
	public void onValueChange (ValueChangeEvent<String> event) {
		addPage(event.getValue());
	}

	public void showIntendedPage () {
		if (intended == null) {
			intended = PageType.HomePageType.asTargetHistoryToken();
		}

		addPage(intended);
	}

	public void setLastIntendedPage (Stack value) {
		intended = value.toString();
	}

	public void showNext () {
		if (stack.hasNext()) {
			PageTypeHelper.show(PageType.fromString(stack.getNext().getPage()),
					stack.getNext().toString(1));
		} else {
			PageTypeHelper.show(PageType.HomePageType.asTargetHistoryToken());
		}

	}

	public void showPrevious () {
		if (stack.hasPrevious()) {
			PageTypeHelper.show(
					PageType.fromString(stack.getPrevious().getPage()),
					stack.getPrevious().toString(1));
		} else {
			PageTypeHelper.show(PageType.HomePageType.asTargetHistoryToken());
		}
	}

	/**
	 * Purges all pages
	 */
	public void purgeAllPages () {
		pages.clear();
	}
}
