//
//  PageTypeHelper.java
//  blogwt
//
//  Created by William Shakour (billy1380) on 13 Sep 2015.
//  Copyright © 2015 WillShex Limited. All rights reserved.
//
package com.willshex.sandpiles.client.helper;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.user.client.History;
import com.willshex.sandpiles.client.page.HomePage;
import com.willshex.sandpiles.client.page.Page;
import com.willshex.sandpiles.shared.page.PageType;

/**
 * @author William Shakour (billy1380)
 *
 */
public class PageTypeHelper {
	private static Page defaultPage = null;

	public static final SafeUri HOME_PAGE_HREF = asHref(PageType.HomePageType);

	/**
	 * 
	 * @param pageType
	 * @return
	 */
	public static Page createPage (PageType pageType) {
		Page page = null;

		switch (pageType) {
		case HomePageType:
		default:
			if (defaultPage == null) {
				defaultPage = new HomePage();
			}
			page = defaultPage;
			break;
		}

		return page;
	}

	public static void show (final PageType pageType) {
		show(pageType.asTargetHistoryToken());
	}

	public static void show (final PageType pageType, final String... params) {
		show(pageType.asTargetHistoryToken(params));
	}

	public static void show (final String targetHistoryToken) {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute () {
				History.newItem(targetHistoryToken);
			}
		});
	}

	public static SafeUri asHref (PageType pageType) {
		return UriUtils.fromString("#" + pageType.asTargetHistoryToken());
	}

	public static SafeUri asHref (PageType pageType, String... params) {
		return UriUtils.fromString("#" + pageType.asTargetHistoryToken(params));
	}

	public static SafeUri slugToHref (String slug) {
		return UriUtils.fromString("#" + slugToTargetHistoryToken(slug));
	}

	public static String slugToTargetHistoryToken (String slug) {
		return "!" + slug;
	}
}
