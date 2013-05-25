package com.autlos.sgf;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Autlos
 * 
 */
public class GuiTextElement extends GuiElement{
	/**
	 * enum with states CENTER, TOP, LEFT, RIGHT and BOTTOM
	 * 
	 * @author Autlos
	 * 
	 */
	public static enum TEXT_ALIGNMENT {
		CENTER, TOP, LEFT, RIGHT, BOTTOM
	}

	private TEXT_ALIGNMENT align;
	// The BasicLabel object for this GuiTextElement
	private BasicLabel label;
	

	/********** CONSTRUCTORS **********/

	/**
	 * Creates a GuiTextElement at the specified position.
	 * 
	 * @param textureRegion
	 * @param position
	 * @param font
	 * @param text
	 */
	public GuiTextElement(TextureRegion textureRegion, Vector2 position, BitmapFont font, String text) {
		// Constructor for a basic Gui Element:
		this(textureRegion, position, font, text, 1f, 1f);
	}

	/**
	 * Creates a GuiTextElement without position. Position has to be set later.
	 * 
	 * @param textureRegion
	 * @param font
	 * @param text
	 */
	public GuiTextElement(TextureRegion textureRegion, BitmapFont font, String text) {
		// Constructor for a basic Entity:
		this(textureRegion, new Vector2(), font, text);
	}

	/**
	 * Creates a GuiText element with multiple frames. Position has to be set later.
	 * 
	 * @param textureRegion
	 * @param font
	 * @param text
	 * @param FRAME_ROWS
	 * @param FRAME_COLS
	 */
	public GuiTextElement(TextureRegion textureRegion, BitmapFont font, String text, int FRAME_ROWS, int FRAME_COLS) {
		this(textureRegion, font, new Vector2(), text, FRAME_ROWS, FRAME_COLS);
	}

	/**
	 * Creates a GuiTextElement at the specified position.
	 * 
	 * @param textureRegion
	 * @param position
	 * @param font
	 * @param text
	 * @param scaleX
	 * @param scaleY
	 */
	public GuiTextElement(TextureRegion textureRegion, Vector2 position, BitmapFont font, String text, float scaleX,
	      float scaleY) {
		// Constructor for a basic Gui Element with scale:
		super(textureRegion, position, scaleX, scaleY);

		label = new BasicLabel(font, text, new Vector2());
		alignText(TEXT_ALIGNMENT.CENTER);
	}

	/**
	 * Creates a GuiText element with multiple frames.
	 * 
	 * @param textureRegion
	 * @param font
	 * @param position
	 * @param text
	 * @param FRAME_ROWS
	 * @param FRAME_COLS
	 */
	public GuiTextElement(TextureRegion textureRegion, BitmapFont font, Vector2 position, String text, int FRAME_ROWS,
	      int FRAME_COLS) {
		super(textureRegion, position, FRAME_ROWS, FRAME_COLS);
		label = new BasicLabel(font, text, new Vector2());
		alignText(TEXT_ALIGNMENT.CENTER);
	}

	/**
	 * Creates a GuiText element with multiple frames. The result will be "text + " " + value. Value can be changed.
	 * 
	 * @param textureRegion
	 * @param font
	 * @param text
	 * @param value
	 * @param FRAME_ROWS
	 * @param FRAME_COLS
	 */
	public GuiTextElement(TextureRegion textureRegion, BitmapFont font, String text, int value, int FRAME_ROWS,
	      int FRAME_COLS) {
		super(textureRegion, new Vector2(), FRAME_ROWS, FRAME_COLS);

		label = new BasicLabel(font, text, value);
		alignText(TEXT_ALIGNMENT.CENTER);
	}

	/********** METHODS **********/

	/**
	 * Draws the GuiElement and the label.
	 */
	public void draw(SpriteBatch batch) {
		super.draw(batch);
		if (visible) {
			label.draw(batch);
		}
	}

	/**
	 * Aligns the text relative to the center of the GuiElement
	 * 
	 * @param align
	 */
	public void alignText(TEXT_ALIGNMENT align) {
		float x = 0f;
		float y = 0f;
		this.align = align;
		switch (align) {
		case CENTER:
			x = this.getOriginCoordinates().x - label.getWidth() / 2;
			y = this.getOriginCoordinates().y + label.getHeight() / 2;
			break;
		case LEFT:
			x = this.getPosition().x;
			y = this.getOriginCoordinates().y + label.getHeight() / 2;
			break;
		case RIGHT:
			x = getPosition().x + this.getWidth() - label.getWidth();
			y = this.getOriginCoordinates().y + label.getHeight() / 2;
			break;
		case TOP:
			x = this.getOriginCoordinates().x - label.getWidth() / 2;
			y = this.getPosition().y + getHeight();
			break;
		case BOTTOM:
			x = this.getOriginCoordinates().x - label.getWidth() / 2;
			y = this.getPosition().y + label.getHeight();
			break;				
		}

		label.setPosition(x, y);
	}

	/**
	 * Move the text, relative to it's position and the alignment.
	 * 
	 * @param x
	 * @param y
	 */
	public void addTextPadding(float x, float y) {
		switch (align) {
		case LEFT:
			label.getPosition().add(x, y);
			break;
		case RIGHT:
			label.getPosition().add(-x, y);
			break;
		case TOP:
			label.getPosition().add(x, -y);
			break;
		case BOTTOM:
			label.getPosition().add(x, y);
			break;
		default:

		}
	}

	/**
	 * Sets the position and aligns the text to center.
	 */
	public void setPosition(Vector2 position) {
		setPosition(position.x, position.y);
	}

	public void setPosition(float x, float y){
		super.setPosition(x, y);
		alignText(TEXT_ALIGNMENT.CENTER);
	}
	
	public BasicLabel getLabel() {
		return label;
	}

}
