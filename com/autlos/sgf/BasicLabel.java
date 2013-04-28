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
	private Vector2 position;
	private TextBounds textBounds;
	
	/**
	 * Creates a Label with text (for example "GAME OVER")
	 * 
	 * @param bitMapFont
	 * @param text
	 */
	public BasicLabel(BitmapFont bitMapFont, String text) {
		this(bitMapFont, text, new Vector2());
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
		this.sequence = new String(text);
		this.text = sequence;
		textBounds = bitmapFont.getBounds(text);
		this.position = new Vector2(position);
	}

	/**
	 * Creates a Label with text and a value (for example "SCORE: 10")
	 * 
	 * @param bitMapFont
	 * @param text
	 * @param value
	 */
	public BasicLabel(BitmapFont bitMapFont, String text, int value) {
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
	public BasicLabel(BitmapFont bitMapFont, String text, int value, Vector2 position) {
		this.bitmapFont = bitMapFont;
		this.position = new Vector2(position);
		this.sequence = new String(text);
		// Text to show will be the sequence plus the value. 
		this.text = this.sequence + value;
		textBounds = bitmapFont.getBounds(this.text);
	}

	public void setPosition(Vector2 position) {
		this.position = new Vector2(position);
	}

	public Vector2 getPosition() {
		 //FIXME: fix this or create a Vector2 variable storing this data:
		return new Vector2(position.x, position.y - textBounds.height);
	}

	public float getWidth() {
		return textBounds.width;
	}

	public float getHeight() {
		return textBounds.height;
	}

	/**
	 * Sets the new value for the text.
	 * 
	 * @param value
	 */
	public void setValue(int value) {
		// This is why the sequence must be saved.
		text = this.sequence + value;
	}

	/**
	 * Calls the bitmapFont's draw method with it's text. 
	 * @param batch
	 */
	public void draw(SpriteBatch batch) {
			bitmapFont.draw(batch, text, position.x, position.y);
	}

}
