package com.autlos.sgf.models;

import com.autlos.sgf.GameAbstract;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Autlos
 * 
 */
public abstract class MoveableEntity extends Entity {
	/**
	 * Enum with the states LEFT and RIGHT
	 * 
	 * @author Autlos
	 * 
	 */
	public static enum RotationDirection {
		LEFT, RIGHT
	}

	// This variables will be used only if rotating is set to true. If so, rotationDirection and SPEED needs to be set.
	protected RotationDirection rotationDirection;
	protected boolean rotating = false;
	protected float ROTATION_SPEED;

	protected float SPEED;
	protected Vector2 velocity;
	protected boolean moving = false;

	/**
	 * Creates a MoveableEntity without animation. Scale will be 1f.
	 * 
	 * @param textureRegion
	 * @param position
	 * @param SPEED
	 * @param rotation
	 */
	public MoveableEntity(TextureRegion textureRegion, Vector2 position, float SPEED, float rotation) {
		this(textureRegion, position, SPEED, 1f, 1f, rotation);
	}

	/**
	 * Creates a MoveableEntity without animation.
	 * 
	 * @param textureRegion
	 * @param position
	 * @param SPEED
	 * @param scaleX
	 * @param scaleY
	 * @param rotation
	 */
	public MoveableEntity(TextureRegion textureRegion, Vector2 position, float SPEED, float scaleX, float scaleY,
	      float rotation) {
		super(textureRegion, position, rotation, scaleX, scaleY);
		this.SPEED = SPEED;
	}
	
	/**
	 * Creates a MoveableEntity with frames but not animated. Scale will be 1f
	 * @param textureRegion
	 * @param FRAME_ROWS
	 * @param FRAME_COLS
	 * @param position
	 * @param SPEED
	 * @param rotation
	 */
	public MoveableEntity(TextureRegion textureRegion, int FRAME_ROWS, int FRAME_COLS, Vector2 position, float SPEED, float rotation) {
		this(textureRegion, FRAME_ROWS, FRAME_COLS, position, SPEED, FRAME_COLS, 1f, 1f);
	}
	
	/**
	 * Creates a MoveableEntity with frames but not animated.
	 * @param textureRegion
	 * @param FRAME_ROWS
	 * @param FRAME_COLS
	 * @param position
	 * @param SPEED
	 * @param scaleX
	 * @param scaleY
	 * @param rotation
	 */
	public MoveableEntity(TextureRegion textureRegion, int FRAME_ROWS, int FRAME_COLS, Vector2 position, float SPEED, float scaleX, float scaleY, float rotation) {
		super(textureRegion, FRAME_ROWS, FRAME_COLS, position, rotation, scaleX, scaleY);
		this.SPEED = SPEED;
	}

	/**
	 * Creates a MoveableEntity with animation. Scale will be 1f.
	 * 
	 * @param textureRegion
	 * @param FRAME_ROWS
	 * @param FRAME_COLS
	 * @param frameDuration
	 * @param playMode
	 *           sets the Animation.playMode to the animation
	 * @param position
	 * @param SPEED
	 * @param rotation
	 */
	public MoveableEntity(TextureRegion textureRegion, int FRAME_ROWS, int FRAME_COLS, float frameDuration,
	      int playMode, Vector2 position, float SPEED, float rotation) {

		this(textureRegion, FRAME_ROWS, FRAME_COLS, frameDuration, playMode, position, SPEED, 1f, 1f, rotation);
	}

	/**
	 * Creates a MoveableEntity with animation.
	 * 
	 * @param textureRegion
	 * @param FRAME_ROWS
	 * @param FRAME_COLS
	 * @param frameDuration
	 * @param playMode
	 *           sets the Animation.playMode to the animation
	 * @param position
	 * @param SPEED
	 * @param scaleX
	 * @param scaleY
	 * @param rotation
	 */
	public MoveableEntity(TextureRegion textureRegion, int FRAME_ROWS, int FRAME_COLS, float frameDuration,
	      int playMode, Vector2 position, float SPEED, float scaleX, float scaleY, float rotation) {
		super(textureRegion, FRAME_ROWS, FRAME_COLS, position, rotation, scaleX, scaleY, frameDuration, playMode);
		this.SPEED = SPEED;

	}

	/**
	 * Checks if the entity has left one side to appear from the other. Use it in the update method if you want PacMan/Asteroids effect.
	 */
	protected void checkReversePositions() {
		if (position.x + width < 0) {
			position.x = GameAbstract.screenWidth - origin.x;
		} else if (position.x > GameAbstract.screenWidth) {
			position.x = -origin.x;
		}

		if (position.y + height / 2 > GameAbstract.screenHeight) {
			position.y = -origin.y;
		} else if (position.y + height / 2 < 0) {
			position.y = GameAbstract.screenHeight - origin.y;
		}
	}

	/**
	 * Checks if the entity has left one side to appear from the other, relative to a position. Use it in the update method if you want
	 * PacMan/Asteroids effect.
	 */
	public void checkReversePositions(float minX, float minY, float maxX, float maxY) {
		if (position.x + origin.x <= minX) {
			position.x = GameAbstract.screenWidth - origin.x;
		} else if (position.x + origin.x > maxX) {
			position.x = -origin.x;
		}

		if (position.y + origin.y >= maxY) {
			position.y = minY - origin.y;
		} else if (position.y + origin.y < minY) {
			position.y = maxY - origin.y;
		}
	}

	/**
	 * Sets whether the MoveableEntity is rotating or not (false by default).
	 * 
	 * @param rotating
	 */
	public void setRotating(boolean rotating) {
		this.rotating = rotating;
	}

	/**
	 * Sets the rotating direction for the MoveableEntity if it is rotating
	 * 
	 * @param rotationDirection
	 */
	public void setRotatingSide(RotationDirection rotationDirection) {
		rotating = true;
		this.rotationDirection = rotationDirection;
	}

	/**
	 * Sets the rotation speed for the MoveableEntity if it is rotating.
	 * 
	 * @param ROTATION_SPEED
	 */
	public void setRotationSpeed(float ROTATION_SPEED) {
		this.ROTATION_SPEED = ROTATION_SPEED;
	}

	/**
	 * Updates bounds. In case it is an animation, updates the state. And in case it is rotating, updates the rotation.
	 */
	public void update(float delta) {
		super.update(delta);
		this.update();
		if (rotating) {
			rotation = (rotationDirection == RotationDirection.LEFT) ? rotation + ROTATION_SPEED * delta : rotation
			      - ROTATION_SPEED * delta;
		}
	}

	/**
	 * Updates the bounds position for the MoveableEntity.
	 */
	public void update() {
		bounds.x = position.x + minBoundsX;
		bounds.y = position.y + minBoundsY;
	}

	/**
	 * 
	 * @return the MoveableEntity's rotation.
	 */
	public float getRotation() {
		return rotation;
	}

	/**
	 * 
	 * @param rotation in degrees.
	 */
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	/**
	 * 
	 * @return if the MoveableEntity is moving or not.
	 */
	public boolean isMoving() {
		return moving;
	}

	/**
	 * Sets if the MoveableEntity is moving or not.
	 * 
	 * @param moving
	 */
	public void setMoving(boolean moving) {
		this.moving = moving;
	}

}
