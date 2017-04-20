//
//  HomePage.java
//  sandpiles
//
//  Created by William Shakour (billy1380) on 19 Apr 2017.
//  Copyright © 2017 WillShex Limited. All rights reserved.
//
package com.willshex.sandpiles.client.page;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.willshex.sandpiles.client.DefaultEventBus;
import com.willshex.sandpiles.client.event.TaskCompleteEventHandler;
import com.willshex.sandpiles.client.processor.Processor;
import com.willshex.sandpiles.client.processor.Task;
import com.willshex.sandpiles.shared.Sand;
import com.willshex.sandpiles.shared.Sand.Builder;
import com.willshex.sandpiles.shared.Tileable;

/**
 * @author William Shakour (billy1380)
 *
 */
public class HomePage extends Page implements TaskCompleteEventHandler {

	private static HomePageUiBinder uiBinder = GWT
			.create(HomePageUiBinder.class);

	interface HomePageUiBinder extends UiBinder<Widget, HomePage> {}

	protected static final String UPGRADE_MESSAGE = "<strong>Error!</strong> Your browser does not support the HTML5 Canvas. Please upgrade your browser to view this applications.";
	// canvas size, in px
	protected static final int HEIGHT = 600;
	protected static final int WIDTH = 600;

	private static boolean CIRCULAR_GRAINS = false;
	private static int SAND_DIM = 10;
	private static int TOPPLES_PER_FRAME = 1;
	private static final int ROW = WIDTH / SAND_DIM;

	@UiField(provided = true) Canvas elCanvas = Canvas.createIfSupported();
	@UiField HTMLPanel pnlError;
	@UiField Button btnPause;

	private Sand sand;
	private Context2d context;
	private Task TOPPLE_TASK = new Task() {

		int count = 1;
		int done = 0;

		@Override
		public void run () {
			for (int i = 0; i < TOPPLES_PER_FRAME; i++) {
				if (sand.topple()) {
					count++;
				}

				done++;
			}

			draw(sand, ROW * ROW);
		}

		@Override
		public float getProgress () {
			return done == count ? 1 : (float) done / (float) count;
		}

		@Override
		public void reset () {
			count = 1;
			done = 0;
		}
	};

	public HomePage () {
		initWidget(uiBinder.createAndBindUi(this));

		setupCanvas();

		uniformClicked(null);
	}

	private void draw (Sand sand, int totalItems) {
		int grains;
		for (int i = 0; i < totalItems; i++) {
			grains = sand.grains(i);
			context.setFillStyle(color(grains));

			if (CIRCULAR_GRAINS) {
				context.beginPath();
				context.arc(x(i, sand.getItemsPerRow()) + SAND_DIM / 2,
						y(i, sand.getItemsPerRow()) + SAND_DIM / 2,
						SAND_DIM / 2, 0, 2 * Math.PI, false);
				context.fill();
			} else {
				context.fillRect(x(i, sand.getItemsPerRow()),
						y(i, sand.getItemsPerRow()), SAND_DIM, SAND_DIM);
			}
		}
	}

	/**
	 * @param i
	 * @param itemsPerRow 
	 * @return
	 */
	private double y (int i, int itemsPerRow) {
		int row = i / itemsPerRow;
		return row * SAND_DIM;
	}

	/**
	 * @param i
	 * @param itemsPerRow 
	 * @return
	 */
	private double x (int i, int itemsPerRow) {
		int column = i % itemsPerRow;
		return column * SAND_DIM;
	}

	//	private static final String[] COLOURS = { CssColor.make("red").toString(),
	//			CssColor.make("orange").toString(),
	//			CssColor.make("yellow").toString(),
	//			CssColor.make("green").toString() };

	private static final String[] COLOURS = {
			CssColor.make("#CC33FF").toString(),
			CssColor.make("#9926FF").toString(),
			CssColor.make("#5214FF").toString(),
			CssColor.make("#0000FF").toString() };

	//	private static final String[] COLOURS = { CssColor.make("white").toString(),
	//			CssColor.make("black").toString(),
	//			CssColor.make("yellow").toString(),
	//			CssColor.make("brown").toString() };

	//	private static final String[] COLOURS = {
	//			CssColor.make("#9B67AD").toString(),
	//			CssColor.make("#B26ABF").toString(),
	//			CssColor.make("#C688B0").toString(),
	//			CssColor.make("#B6A1B6").toString() };

	/**
	 * @param grains
	 * @return
	 */
	private String color (int grains) {
		String color;
		switch (grains) {
		case 0:
		case 1:
		case 2:
		case 3:
			color = COLOURS[grains];
			break;
		default:
			int g = gray(grains);
			color = CssColor.make(g, g, g).toString();
			break;
		}
		return color;
	}

	private int gray (int grains) {
		return 255 - ((grains - 3) % 256);
	}

	/**
	 * @param builder
	 * @param itemsPerRow
	 * @param totalItems
	 * @param grains
	 * @return
	 */
	private Builder uniform (Builder builder, final int itemsPerRow,
			final int totalItems, int grains) {
		builder.itemsPerRow(itemsPerRow);

		for (int i = 0; i < totalItems; i++) {
			builder.start(grains, i);
		}

		return builder;
	}

	/**
	 * @param builder
	 * @param itemsPerRow
	 * @param totalItems
	 * @param maxGrains
	 * @return
	 */
	private Builder random (Builder builder, final int itemsPerRow,
			final int totalItems, int maxGrains) {
		builder.itemsPerRow(itemsPerRow);

		for (int i = 0; i < totalItems; i++) {
			builder.start(Random.nextInt(maxGrains), i);
		}

		return builder;
	}

	protected void setupCanvas () {
		if (elCanvas == null) {
			pnlError.add(new HTML(UPGRADE_MESSAGE));
			pnlError.setVisible(true);

			return;
		}

		elCanvas.setWidth(WIDTH + "px");
		elCanvas.setHeight(HEIGHT + "px");

		elCanvas.setCoordinateSpaceWidth(WIDTH);
		elCanvas.setCoordinateSpaceHeight(HEIGHT);

		context = elCanvas.getContext2d();

		elCanvas.addClickHandler(new ClickHandler() {

			@Override
			public void onClick (ClickEvent event) {
				int at = (event.getRelativeX(elCanvas.getElement()) / SAND_DIM)
						+ (event.getRelativeY(elCanvas.getElement()) / SAND_DIM
								* sand.getItemsPerRow());

				sand.add(1000, at);

				TOPPLE_TASK.reset();
				Processor.get().addTask(TOPPLE_TASK);
				btnPause.setVisible(true);
				btnPause.setText("pause");
			}
		});
	}

	@UiHandler("btnRandom")
	void randomClicked (ClickEvent ce) {
		Processor.get().removeTask(TOPPLE_TASK);
		sand = random(Sand.builder().shape(Tileable.Square), ROW, ROW * ROW, 4)
				.build();
		TOPPLE_TASK.reset();
		Processor.get().addTask(TOPPLE_TASK);
		btnPause.setVisible(true);
		btnPause.setText("pause");
	}

	@UiHandler({ "btnUniform", "btnClear" })
	void uniformClicked (ClickEvent ce) {
		Processor.get().removeTask(TOPPLE_TASK);
		sand = uniform(Sand.builder().shape(Tileable.Square), ROW, ROW * ROW,
				ce == null
						|| ((Button) ce.getSource()).getText().equals("clear")
								? 0 : 50).build();
		TOPPLE_TASK.reset();
		Processor.get().addTask(TOPPLE_TASK);
		btnPause.setVisible(true);
		btnPause.setText("pause");
	}

	@UiHandler("btnPause")
	void pauseClicked (ClickEvent ce) {
		if (Processor.get().hasOutstanding()) {
			Processor.get().removeTask(TOPPLE_TASK);
			btnPause.setVisible(true);
			btnPause.setText("resume");
		} else {
			Processor.get().addTask(TOPPLE_TASK);
			btnPause.setVisible(true);
			btnPause.setText("pause");
		}
	}

	/* (non-Javadoc)
	 * 
	 * @see
	 * com.willshex.sandpiles.client.event.TaskCompleteEventHandler#taskComplete
	 * (com.willshex.sandpiles.client.processor.Task) */
	@Override
	public void taskComplete (Task task) {
		btnPause.setVisible(false);
	}

	/* (non-Javadoc)
	 * 
	 * @see com.willshex.sandpiles.client.page.Page#onAttach() */
	@Override
	protected void onAttach () {
		super.onAttach();
		register(DefaultEventBus.get().addHandlerToSource(
				TaskCompleteEventHandler.TYPE, Processor.get(), this));
	}

}
