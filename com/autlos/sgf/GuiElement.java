package com.autlos.sgf;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Autlos
 * 
 */
public class GuiElement extends Entity {
	/**
	 * enum to with the states NOT_PRESSED and PRESSED
	 * @author Autlos
	 *
	 */
	public static enum State {
		NOT_PRESSED, PRESSED
	}

	/**
	 * enum to with the states LEFT, RIGHT, UP and DOWN
	 * @author Autlos
	 *
	 */
	public static enum TouchDirection {
		LEFT, RIGHT, UP, DOWN
	}

	protected State state;
	protected TouchDirection touchDirection;
	protected boolean actionable;
	protected float touchAngle;

	// The finger which touched the element
	protected int pointer;

	/**
	 * Creates a GuiElement with a textureRegion to be drawn at a position. i.e the HUD background
	 * 
	 * @param textureRegion
	 * @param position
	 */
	public GuiElement(TextureRegion textureRegion, Vector2 position) {
		// Constructor for a basic Entity:
		this(textureRegion, position, 1f, 1f);
	}

	/**
	 * Creates a GuiElement with a textureRegion to be drawn at a position. i.e the GUI's background
	 * 
	 * @param textureRegion
	 * @param position
	 * @param scaleX
	 * @param scaleY
	 */
	public GuiElement(TextureRegion textureRegion, Vector2 position, float scaleX, float scaleY) {
		// Constructor for a basic Entity:
		super(textureRegion, position, 0f, scaleX, scaleY);
		state = State.NOT_PRESSED;
		// Sets the bounds, you can use the alternative createBounds method later.
		createBounds();
		actionable = false;
	}

	/**
	 * Creates a GuiElement with multiple frames to be drawn at a position. i.e game buttons. Frames will be managed via {@link setState} or
	 * {@link switchState}
	 * 
	 * @param textureRegion
	 * @param position
	 * @param FRAME_ROWS
	 * @param FRAME_COLS
	 */
	public GuiElement(TextureRegion textureRegion, Vector2 position, int FRAME_ROWS, int FRAME_COLS) {
		// Calls the super constructor for a multi-frame entity with no animation.
		this(textureRegion, position, FRAME_ROWS, FRAME_COLS, 1f, 1f);
		state = State.NOT_PRESSED;
		createBounds();
		actionable = true;
	}

	/**
	 * Creates a GuiElement with multiple frames to be drawn at a position. i.e game buttons. Frames will be managed via {@link setState} or
	 * {@link switchState}
	 * 
	 * @param textureRegion
	 * @param position
	 * @param FRAME_ROWS
	 * @param FRAME_COLS
	 * @param scaleX
	 * @param scaleY
	 */
	public GuiElement(TextureRegion textureRegion, Vector2 position, int FRAME_ROWS, int FRAME_COLS, float scaleX,
	      float scaleY) {
		// Calls the super constructor for a multi-frame entity with no animation.
		super(textureRegion, position, 0, scaleX, scaleY, FRAME_ROWS, FRAME_COLS);
		state = State.NOT_PRESSED;
		createBounds();
		actionable = true;
	}

	/**
	 * 
	 * @param positionTouched
	 * @return true if the {@code Vector2} passed is inside the item bounds.
	 */
	public boolean isTouchingElement(Vector2 positionTouched) {
		Rectangle rect = new Rectangle(positionTouched.x, positionTouched.y, 1, 1);
		return this.getBounds().overlaps(rect);
	}

	/**
	 * Use this method to switch between frames (if the GuiElement has multiple frames).
	 * 
	 * @param state
	 *           PRESSED or NOT_PRESSED {@link State}
	 */
	public void setState(State state) {
		this.state = state;
		if (actionable) {
			if (this.state == State.NOT_PRESSED) {
				currentFrame = frames[0];
			} else if (this.state == State.PRESSED) {
				currentFrame = frames[1];
			}
		}
	}

	/**
	 * Switch the state, from pressed to not_pressed and viceversa.
	 */
	public void switchState() {
		if (actionable) {
			if (this.state == State.PRESSED) {
				state = State.NOT_PRESSED;
				currentFrame = frames[0];
			} else {
				state = State.PRESSED;
				currentFrame = frames[1];
			}
		}
	}

	/**
	 * The angle between the position touched and the origin (center) of the element.
	 * 
	 * @param posTouched
	 */
	public void setTouchAngle(Vector2 posTouched) {
		float distX = posTouched.x - getOriginCoordinates().x;
		float distY = posTouched.y - getOriginCoordinates().y;
		this.touchAngle = MathUtils.atan2(distY, distX) * MathUtils.radDeg;
	}

	/**
	 * Sets the direction touched relative to the origin (center) of the element.
	 * 
	 * @param posTouched
	 *           {@link TouchDirection}
	 */
	public void setTouchDirection(Vector2 posTouched) {
		float distXLeft = getOriginCoordinates().x - posTouched.x;
		float distXRight = posTouched.x - getOriginCoordinates().x;
		float distYUp = posTouched.y - getOriginCoordinates().y;
		float distYDown = getOriginCoordinates().y - posTouched.y;

		float maxX, maxY;
		TouchDirection dirX;
		if (distXLeft >= distXRight) {
			maxX = distXLeft;
			dirX = TouchDirection.LEFT;
		} else {
			maxX = distXRight;
			dirX = TouchDirection.RIGHT;
		}

		TouchDirection dirY;
		if (distYUp >= distYDown) {
			maxY = distYUp;
			dirY = TouchDirection.UP;
		} else {
			maxY = distYDown;
			dirY = TouchDirection.DOWN;
		}

		if (maxX > maxY) {
			touchDirection = dirX;
		} else {
			touchDirection = dirY;
		}

	}

	public State getState() {
		return state;
	}

	/**
	 * Sets the pointer (finger) which touched the element
	 * @param pointer
	 */
	public void setPointer(int pointer) {
		this.pointer = pointer;
	}

	public int getPointer() {
		return pointer;
	}

	/**
	 * @return the angle in degrees.
	 */
	public float getTouchAngle() {
		return touchAngle;
	}

	public TouchDirection getTouchDirection() {
		return touchDirection;
	}

}
