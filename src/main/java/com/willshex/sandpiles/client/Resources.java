//
//  Resources.java
//  blogwt
//
//  Created by William Shakour (billy1380) on 20 Dec 2014.
//  Copyright © 2014 WillShex Limited. All rights reserved.
//
package com.willshex.sandpiles.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

/**
 * @author William Shakour (billy1380)
 *
 */
public interface Resources extends ClientBundle {
	public interface ResourceStyles extends CssResource {

		String image ();

		String brand ();

		String internalLink ();

		String externalLink ();
	}

	public static final Resources RES = GWT.create(Resources.class);

	@Source("res/styles.gss")
	ResourceStyles styles ();

	@Source("res/brand.png")
	ImageResource brand ();

}
