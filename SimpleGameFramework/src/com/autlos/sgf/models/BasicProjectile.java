package com.autlos.sgf.models;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Autlos
 * 
 */
public class BasicProjectile extends MoveableEntity {
	// This variables will be used only if the constructor for projectiles with lifetime is choosen.
	protected boolean finished = false;
	protected float lifeTime = 0f;
	protected float currentTime = 0f;

	/**
	 * Creates a basic projectile. Position will update based on it's rotation and SPEED.
	 * 
	 * @param textureRegion
	 * @param position
	 * @param SPEED
	 * @param rotation
	 */
	public BasicProjectile(TextureRegion textureRegion, Vector2 position, float SPEED, float rotation) {
		super(textureRegion, position, SPEED, rotation);
		velocity = new Vector2(1f, 1f);
	}

	/**
	 * Creates a basic projectile. Position will update based on it's rotation and SPEED.
	 * 
	 * @param textureRegion
	 * @param position
	 * @param SPEED
	 * @param rotation
	 * @param lifeTime
	 *           the projectile will set to finished state after it's lifeTime
	 */
	public BasicProjectile(TextureRegion textureRegion, Vector2 position, float SPEED, float rotation, float lifeTime) {
		this(textureRegion, position, SPEED, rotation);
		this.lifeTime = lifeTime;
	}

	public BasicProjectile(TextureRegion textureRegion, int FRAME_COLS, int FRAME_ROWS, float frameDuration,
	      int playMode, Vector2 position, float SPEED, float rotation) {
		super(textureRegion, FRAME_ROWS, FRAME_COLS, frameDuration, playMode, position, SPEED, rotation);
		velocity = new Vector2(1f, 1f);
	}

	/**
	 * Calls the move method, and then updates position, bounds and animation status (if it is an animated projectile) based on the rotation.
	 * To change the movement style override the {@code move(float delta)} method.
	 */
	public void update(float delta) {
		move(delta);

		// If the projectile has an animation, calls the super update method to update the animation.
		if (this.animation != null) {
			stateTime += delta;
			super.update(delta);
		}
		// If the projectile does not have animation, just updates the bounds to the new position.
		else {
			super.update();
		}

		// It only counts when the projectile has to disappear after some time
		if (lifeTime > 0) {
			if (currentTime <= lifeTime) {
				currentTime += delta;
			} else {
				finished = true;
			}
		}

	}

	/**
	 * Updates the position based on it's rotation. Override this method if you want to change the movement style.
	 * @param delta
	 */
	private void move(float delta) {
		this.position.x += SPEED * velocity.x * delta * MathUtils.cosDeg(rotation);
		this.position.y += SPEED * velocity.y * delta * MathUtils.sinDeg(rotation);
	}

	public boolean isFinished() {
		return finished;
	}
}
