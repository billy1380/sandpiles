//
//  FooterPart.java
//  blogwt
//
//  Created by billy1380 on 31 Jul 2013.
//  Copyright © 2015 WillShex Limited. All rights reserved.
//
package com.willshex.sandpiles.client.part;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import static com.willshex.sandpiles.client.Resources.RES;

public class FooterPart extends Composite {

	private static FooterPartUiBinder uiBinder = GWT
			.create(FooterPartUiBinder.class);

	interface FooterPartUiBinder extends UiBinder<Widget, FooterPart> {}

	public interface FooterTemplates extends SafeHtmlTemplates {
		FooterTemplates FOOTER_TEMPLATES = GWT.create(FooterTemplates.class);

		@Template("Copyright &copy; <a href=\"{0}\" target=\"_blank\" class=\"{4}\">{1}</a> {2}. All rights reserved - {3}.")
		SafeHtml copyrightNotice (SafeUri uri, String holder, String years,
				String name, String style);
	}

	@UiField DivElement divCopyright;

	private FooterPart () {
		initWidget(uiBinder.createAndBindUi(this));

		divCopyright.setInnerSafeHtml(
				FooterTemplates.FOOTER_TEMPLATES.copyrightNotice(
						UriUtils.fromSafeConstant("https://www.willshex.com"),
						"WillShex Limited", years(), "Sandpiles",
						RES.styles().externalLink()));
	}

	@SuppressWarnings("deprecation")
	private String years () {
		Date started = DateTimeFormat.getFormat("yyyy").parse("2017");
		Date now = new Date();

		if (started.getYear() == now.getYear()) {
			return Integer.toString(1900 + now.getYear());
		} else {
			return Integer.toString(1900 + started.getYear()) + "-"
					+ Integer.toString(1900 + now.getYear());
		}
	}

	private static FooterPart one;

	/**
	 * @return
	 */
	public static FooterPart get () {
		if (one == null) {
			one = new FooterPart();
		}

		return one;
	}

}
