//
//  PageType.java
//  blogwt
//
//  Created by William Shakour (billy1380) on 11 May 2015.
//  Copyright © 2015 WillShex Limited. All rights reserved.
//
package com.willshex.sandpiles.shared.page;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.willshex.utility.StringUtils;

/**
 * @author William Shakour (billy1380)
 *
 */
public enum PageType {
	HomePageType("home"),;

	private String value;
	private static Map<String, PageType> valueLookup = null;

	private PageType (String value) {
		this.value = value;
	}

	public String toString () {
		return value;
	}

	public String toString (String... params) {
		String asString;
		String joinedParams = StringUtils.join(Arrays.asList(params), "/");

		if (joinedParams == null || joinedParams.length() == 0) {
			asString = toString();
		} else {
			asString = toString() + "/" + joinedParams;
		}

		return asString;
	}

	public String asTargetHistoryToken () {
		return "!" + toString();
	}

	public String asTargetHistoryToken (String... params) {
		return "!" + toString(params);
	}

	public static PageType fromString (String value) {
		value = stripExclamation(value);

		if (valueLookup == null) {
			valueLookup = new HashMap<String, PageType>();

			for (PageType currentPageType : PageType.values()) {
				valueLookup.put(currentPageType.value, currentPageType);
			}
		}

		return value == null ? null : valueLookup.get(value);
	}

	public boolean equals (String value) {
		value = stripExclamation(value);

		return this.value.equals(value);
	}

	private static String stripExclamation (String value) {
		if (value != null && value.length() > 0 && value.charAt(0) == '!') {
			value = value.substring(1);
		}

		return value;
	}
}
