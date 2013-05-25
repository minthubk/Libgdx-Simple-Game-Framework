package com.autlos.sgf;

import com.autlos.sgf.ui.ITableItem;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Autlos
 * 
 */
public class BasicLabel implements ITableItem {
	private BitmapFont bitmapFont;
	
	// Text for the label
	private String text;
	// Value for the label
	private long value;
	// text + value
	private String sequence;
	
	protected Vector2 position;
	protected Vector2 realPosition;
	private TextBounds textBounds;

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
		setPosition(position);
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
		this(bitMapFont, text, value, null);
	}

	/**
	 * Creates a Label with text and value (for example "SCORE: 10")
	 * 
	 * @param bitMapFont
	 * @param text
	 * @param value
	 * @param position
	 */
	public BasicLabel(BitmapFont bitMapFont, String text, long value,
			Vector2 position) {
		this.bitmapFont = bitMapFont;
		this.sequence = text;
		// Text to show will be the sequence plus the value.
		this.text = this.sequence + value;
		textBounds = new TextBounds(bitmapFont.getBounds(text + value));
		setPosition(position);
		visible = true;
	}

	/**
	 * Adjust the bounds when multiple row labels are created. This is not 100% precise, but it does the job.
	 * @param ROWS
	 */
	public void adjustBoundsMultiRow(int ROWS) {
		textBounds.width = textBounds.width / ROWS;
		textBounds.height = textBounds.height * ROWS;
	}

	/**
	 * Switchs the visibility of the label. 
	 */
	public void switchVisibility() {
		visible = !visible;
	}

	/**
	 * If false, the element won't be draw.
	 * @param visible
	 */
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

	public void setText(String sequence) {
		this.sequence = sequence;
		this.text = value == 0 ? sequence : this.sequence + value;
		if (textBounds == null) {
			textBounds = new TextBounds(bitmapFont.getBounds(text));
		} else {
			textBounds.set(bitmapFont.getBounds(text));
		}
	}

	public void setPosition(Vector2 position) {
		if (position != null) {
			setPosition(position.x, position.y);
		}else{
			setPosition(0f, 0f);
		}
	}

	public void setPosition(float x, float y) {
		if (position != null) {
			position.set(x, y);
		} else {
			position = new Vector2(x, y);
		}
		
		if (realPosition == null) {
			realPosition = new Vector2(position.x, position.y
					- textBounds.height);
		} else {
			realPosition.set(position.x, position.y
					- textBounds.height);
		}
	}

	public Vector2 getPosition() {
		return realPosition;
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

	@Override
	public void drawShape(ShapeRenderer sr) {
		sr.rect(position.x, getPosition().y, getWidth(), getHeight());
	}

}
