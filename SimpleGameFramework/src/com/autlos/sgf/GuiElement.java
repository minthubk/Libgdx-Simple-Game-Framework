package com.autlos.sgf;

import com.autlos.sgf.models.Entity;
import com.autlos.sgf.screens.ScreenController.Action;
import com.autlos.sgf.ui.ITableItem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Autlos
 * 
 */
public class GuiElement extends Entity implements ITableItem{
		
	/**
	 * enum to with the states NOT_PRESSED and PRESSED
	 * 
	 * @author Autlos
	 * 
	 */
	public static enum State {
		NOT_PRESSED, PRESSED
	}

	/**
	 * enum to with the states LEFT, RIGHT, UP and DOWN
	 * 
	 * @author Autlos
	 * 
	 */
	public static enum TouchDirection {
		LEFT, RIGHT, UP, DOWN
	}

	protected State state;
	protected TouchDirection touchDirection;
	protected float touchAngle;
	protected Rectangle touchRecgantle;

	// The finger which touched the element
	protected int pointer;
	Action action;

	// If the element is visible or not:
	protected boolean visible;

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
		visible = true;
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
		super(textureRegion, FRAME_ROWS, FRAME_COLS, position, 0, scaleX, scaleY);
		state = State.NOT_PRESSED;
		visible = true;
	}

	public void draw(SpriteBatch batch) {
		if (visible) {
			super.draw(batch);
		}
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * 
	 * @param positionTouched
	 * @return true if the {@code Vector2} passed is inside the item bounds.
	 */
	public boolean isTouchingElement(float x, float y) {
		if (visible) {
			if (touchRecgantle == null) {
				touchRecgantle = new Rectangle(x, y, 1, 1);
			} else {
				touchRecgantle.set(x, y, 1, 1);
			}
			return this.getBounds().overlaps(touchRecgantle);
		} else {
			return false;
		}
	}

	/**
	 * Use this method to switch between frames (if the GuiElement has multiple frames).
	 * 
	 * @param state
	 *           PRESSED or NOT_PRESSED {@link State}
	 */
	public void setState(State state) {
		this.state = state;
		if(frames != null){
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
		if(frames != null){
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
	public void setTouchAngle(float touchedX, float touchedY) {
		float distX = touchedX - getOriginCoordinates().x;
		float distY = touchedY - getOriginCoordinates().y;
		touchAngle = MathUtils.atan2(distY, distX) * MathUtils.radDeg;
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
	

	public boolean isPressed() {
		return state == State.PRESSED;
	}

	public State getState() {
		return state;
	}

	/**
	 * Sets the pointer (finger) which touched the element
	 * 
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

	public void touchUp() {
		if(action != null){
			action.touchUp();
		}
	}

	public void touchDown() {
		if(action != null){
			action.touchDown();
		}
	}

	public void setAction(Action action) {
		this.action = action;
		
	}
	
	public void setPositionX(float x){
		setPosition(x, position.y);
	}
	
	public void setPositionY(float y){
		setPosition(position.x, y);
	}

}
