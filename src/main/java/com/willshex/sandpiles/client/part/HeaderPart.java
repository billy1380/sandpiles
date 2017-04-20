//
//  HeaderPart.java
//  blogwt
//
//  Created by billy1380 on 31 Jul 2013.
//  Copyright © 2015 WillShex Limited. All rights reserved.
//
package com.willshex.sandpiles.client.part;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.willshex.sandpiles.client.Resources;
import com.willshex.sandpiles.client.helper.PageTypeHelper;
import com.willshex.sandpiles.shared.page.PageType;

public class HeaderPart extends Composite
		 {

	private static HeaderPartUiBinder uiBinder = GWT
			.create(HeaderPartUiBinder.class);

	interface HeaderPartUiBinder extends UiBinder<Widget, HeaderPart> {}

	@UiField Element elName;
	@UiField AnchorElement btnHome;
	@UiField Image imgLogo;
	@UiField CollapseButton btnNavExpand;

	private HeaderPart () {
		initWidget(uiBinder.createAndBindUi(this));
		Resources.RES.styles().ensureInjected();

		String titleInNavBar = "Sandpiles";

		imgLogo.setResource(Resources.RES.brand());
		imgLogo.setTitle(titleInNavBar);
		imgLogo.setAltText(titleInNavBar);

		setupNavBarPages();
	}

	private void setupNavBarPages () {

		btnHome.setHref(PageTypeHelper.asHref(PageType.HomePageType));
	}

	private static HeaderPart one;

	/**
	 * @return
	 */
	public static HeaderPart get () {
		if (one == null) {
			one = new HeaderPart();
		}

		return one;
	}
}
