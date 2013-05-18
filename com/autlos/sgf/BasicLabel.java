package com.autlos.sgf;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Autlos
 * 
 */
public class BasicLabel {
	private BitmapFont bitmapFont;
	private String text;
	private String sequence;
	protected Vector2 position;
	private TextBounds textBounds;
	private long value;

	private boolean visible;

	/**
	 * Creates a Label with text (for example "GAME OVER")
	 * 
	 * @param bitMapFont
	 * @param text
	 */
	public BasicLabel(BitmapFont bitMapFont, String text) {
		this(bitMapFont, text, null);
	}

	/**
	 * Creates a Label with text (for example "GAME OVER")
	 * 
	 * @param bitMapFont
	 * @param text
	 * @param position
	 */
	public BasicLabel(BitmapFont bitMapFont, String text, Vector2 position) {
		this.bitmapFont = bitMapFont;
		this.sequence = text;
		this.text = sequence;
		textBounds = new TextBounds(bitmapFont.getBounds(text));
		if (position != null) {
			this.position = new Vector2(position);
		}
		visible = true;
	}

	/**
	 * Creates a Label with text and a value (for example "SCORE: 10")
	 * 
	 * @param bitMapFont
	 * @param text
	 * @param value
	 */
	public BasicLabel(BitmapFont bitMapFont, String text, long value) {
		this(bitMapFont, text, value, new Vector2());
	}

	/**
	 * Creates a Label with text and value (for example "SCORE: 10")
	 * 
	 * @param bitMapFont
	 * @param text
	 * @param value
	 * @param position
	 */
	public BasicLabel(BitmapFont bitMapFont, String text, long value, Vector2 position) {
		this.bitmapFont = bitMapFont;
		this.position = new Vector2(position);
		this.sequence = text;
		// Text to show will be the sequence plus the value.
		this.text = this.sequence + value;
		textBounds = new TextBounds(bitmapFont.getBounds(text + value));
		visible = true;
	}

	public void createMultiRow(int ROWS) {
		textBounds.width = textBounds.width / ROWS;
		textBounds.height = textBounds.height * ROWS;
	}

	public void setPosition(Vector2 position) {
		if (position == null) {
			this.position = new Vector2(position);
		} else {
			this.position.set(position);
		}
	}

	public void setPosition(float x, float y) {
		if (position != null) {
			position.set(x, y);
		} else {
			position = new Vector2(x, y);
		}
	}

	public void switchVisibility() {
		visible = !visible;
	}

	public void setVisibility(boolean visible) {
		this.visible = visible;
	}

	/**
	 * Sets the new value for the text.
	 * 
	 * @param value
	 */
	public void setValue(long value) {
		// This is why the sequence must be saved.
		this.value = value;
		text = this.sequence + value;
	}

	public void setText(String text) {
		this.sequence = text;
		this.text = value == 0 ? text : sequence + value;
		if (textBounds == null) {
			textBounds = new TextBounds(bitmapFont.getBounds(text));
		} else {
			textBounds.set(bitmapFont.getBounds(text));
		}
	}

	public Vector2 getPosition() {
		return new Vector2(position.x, position.y - textBounds.height);
	}

	public float getWidth() {
		return textBounds.width;
	}

	public float getHeight() {
		return textBounds.height;
	}

	/**
	 * Calls the bitmapFont's draw method with it's text.
	 * 
	 * @param batch
	 */
	public void draw(SpriteBatch batch) {
		if (visible) {
			bitmapFont.drawMultiLine(batch, text, position.x, position.y);
		}
	}

}
