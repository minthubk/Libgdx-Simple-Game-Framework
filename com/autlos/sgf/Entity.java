package com.autlos.sgf;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Autlos
 * 
 */
public abstract class Entity {
	// TODO: fix the parameters order in the constructors.
	// The frame is going to be drawn:
	protected TextureRegion currentFrame;

	// Basic information about an Entity:
	protected float width;
	protected float height;
	protected Vector2 origin;
	protected Vector2 position;
	protected float rotation;
	protected Rectangle bounds;

	// ScaleX and ScaleY for the entity:
	protected float scaleX;
	protected float scaleY;

	// To create a padding in the bounding rectangle:
	protected float minBoundsX;
	protected float minBoundsY;

	// For animations or multiple frame entities:
	protected TextureRegion frames[];
	int FRAME_ROWS = 1;
	int FRAME_COLS = 1;
	protected Animation animation;
	protected float stateTime;

	/************ CONSTRUCTORS ************/

	/**
	 * Creates an Entity with texture, position, rotation. Scale will be 1f.
	 * 
	 * @param textureRegion
	 * @param position
	 * @param rotation
	 */
	public Entity(TextureRegion textureRegion, Vector2 position, float rotation) {
		this(textureRegion, position, rotation, 1f, 1f);
	}

	/**
	 * Creates an Entity with texture, position, rotation and scale in both axis.
	 * 
	 * @param textureRegion
	 * @param position
	 * @param rotation
	 * @param scaleX
	 * @param scaleY
	 */
	public Entity(TextureRegion textureRegion, Vector2 position, float rotation, float scaleX, float scaleY) {
		this.position = position;
		this.rotation = rotation;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		setInitialFrame(textureRegion);
		setDimensions();
	}

	/**
	 * Creates an Entity with multiple frames but not animated. Scale will be 1f.
	 * 
	 * @param textureRegion
	 * @param position
	 * @param rotation
	 * @param FRAME_ROWS
	 * @param FRAME_COLS
	 */
	public Entity(TextureRegion textureRegion, Vector2 position, float rotation, int FRAME_ROWS, int FRAME_COLS) {
		this(textureRegion, position, rotation, 1f, 1f, FRAME_ROWS, FRAME_COLS);
	}

	/**
	 * Creates an Entity with multiple frames but not animated.
	 * 
	 * @param textureRegion
	 * @param position
	 * @param rotation
	 * @param scaleX
	 * @param scaleY
	 * @param FRAME_ROWS
	 * @param FRAME_COLS
	 */
	public Entity(TextureRegion textureRegion, Vector2 position, float rotation, float scaleX, float scaleY,
	      int FRAME_ROWS, int FRAME_COLS) {
		createFrames(textureRegion, FRAME_ROWS, FRAME_COLS);
		this.position = position;
		this.rotation = rotation;
		this.scaleX = scaleX;
		this.scaleY = scaleY;

		setInitialFrame(frames[0]);
		setDimensions();
		stateTime = 0f;
	}

	/**
	 * Creates an Animated entity. Scale will be 1f.
	 * 
	 * @param textureRegion
	 * @param position
	 * @param rotation
	 * @param FRAME_ROWS
	 *           of the textureRegion
	 * @param FRAME_COLS
	 *           of the textureRegion
	 * @param frameDuration
	 *           time, in seconds, for each frame in the animation
	 * @param playMode
	 *           for example Animation.LOOP {@link Animation}
	 */
	public Entity(TextureRegion textureRegion, Vector2 position, float rotation, int FRAME_ROWS, int FRAME_COLS,
	      float frameDuration, int playMode) {
		this(textureRegion, position, rotation, 1f, 1f, FRAME_ROWS, FRAME_COLS, frameDuration, playMode);
	}

	/**
	 * Creates an Animated entity
	 * 
	 * @param textureRegion
	 * @param position
	 * @param rotation
	 * @param scaleX
	 * @param scaleY
	 * @param FRAME_ROWS
	 *           of the textureRegion
	 * @param FRAME_COLS
	 *           of the textureRegion
	 * @param frameDuration
	 *           time, in seconds, for each frame in the animation
	 * @param playMode
	 *           for example Animation.LOOP {@link Animation}
	 */
	public Entity(TextureRegion textureRegion, Vector2 position, float rotation, float scaleX, float scaleY,
	      int FRAME_ROWS, int FRAME_COLS, float frameDuration, int playMode) {

		createFrames(textureRegion, FRAME_ROWS, FRAME_COLS);
		this.animation = new Animation(frameDuration, frames);
		animation.setPlayMode(playMode);

		this.position = position;
		this.rotation = rotation;
		this.scaleX = scaleX;
		this.scaleY = scaleY;

		setInitialFrame(animation.getKeyFrame(0f));
		setDimensions();
		stateTime = 0f;
	}

	/**
	 * Creates the frames array. This method will be called if the constructor for multiple frame entity or animation is used.
	 * 
	 * @param textureRegion
	 * @param FRAME_ROWS
	 * @param FRAME_COLS
	 */
	protected void createFrames(TextureRegion textureRegion, int FRAME_ROWS, int FRAME_COLS) {
		int tileWidth = textureRegion.getRegionWidth() / FRAME_COLS;
		frames = new TextureRegion[FRAME_ROWS * FRAME_COLS];
		int cont = 0;
		for (int j = 0; j < FRAME_ROWS; j++) {
			for (int i = 0; i < FRAME_COLS; i++) {
				frames[cont] = new TextureRegion(textureRegion, i * tileWidth, j * textureRegion.getRegionHeight()
				      / FRAME_ROWS, tileWidth, textureRegion.getRegionHeight() / FRAME_ROWS);
				cont++;
			}
		}
	}

	/**
	 * Sets the initial frame. If the textureRegion isn't multiframe/animated, the currentFrame will be the texture region.
	 */
	protected void setInitialFrame(TextureRegion frame) {
		currentFrame = frame;
	}

	/**
	 * Sets the scaleX and Y for the entity. After changing the scale, {@code setDimensions()} and {@code createBounds()} is called.
	 * 
	 * @param scaleX
	 * @param scaleY
	 */
	public void setScale(float scaleX, float scaleY) {
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		setDimensions();
		createBounds(minBoundsX, minBoundsY);
	}

	/**
	 * Sets the default dimensions (relative to the textureRegion frame). This method is called after {@code setScale}
	 */
	protected void setDimensions() {
		setDimensions(currentFrame.getRegionWidth() / FRAME_COLS, currentFrame.getRegionHeight() / FRAME_ROWS);
	}

	/**
	 * Sets the entity dimensions. width will be {@code width*scaleX}, height {@code height*scaleY}, and creates the origin Vector2.
	 * 
	 * @param width
	 * @param height
	 */
	protected void setDimensions(float width, float height) {
		this.width = width * scaleX;
		this.height = height * scaleY;
		this.origin = new Vector2(this.width / 2, this.height / 2);
	}

	/**
	 * Creates the default Bounding rectangle for the Entity. It will call the {@code createBounds(float minX, float minY)} with 0f,0f params
	 */
	protected void createBounds() {
		createBounds(0f, 0f);
	}

	/**
	 * Sets a margin to make the bounds smaller than the entity's rectangle. It will act like a padding for the original rectangle.
	 * 
	 * @param minX
	 * @param minY
	 */
	public void createBounds(float minX, float minY) {
		this.minBoundsX = minX * scaleX;
		this.minBoundsY = minY * scaleY;
		this.bounds = new Rectangle(position.x + minBoundsX, position.y + minBoundsY, width - 2 * minBoundsX, height - 2
		      * minBoundsY);
	}

	/**
	 * Updates the stateTime and currentFrame of the animation
	 * 
	 * @param delta
	 */
	public void update(float delta) {
		// If it has animation, it updates the stateTime and changes the current frame.
		if (animation != null) {
			stateTime += delta;
			currentFrame = animation.getKeyFrame(stateTime);
		}
	}

	/**
	 * Draws the currentFrame;
	 * 
	 * @param batch
	 */
	public void draw(SpriteBatch batch) {
		batch.draw(currentFrame, position.x, position.y, origin.x, origin.y, width, height, 1.0f, 1.0f, rotation);
	}

	/**
	 * @return the entity's position
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * @param position
	 *           the {@code Vector2} to be set
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
	}

	/**
	 * @param x
	 * @param y
	 */
	public void setPosition(float x, float y) {
		this.position.x = x;
		this.position.y = y;
	}

	/**
	 * Returns the origin of the entity in screen coordinates. i.e if entity's position in screen is 100-100, and origin 20-20, it will return 120-120.
	 * 
	 * @return {@code Vector2}
	 */
	public Vector2 getOriginCoordinates() {
		return new Vector2(position.x + origin.x, position.y + origin.y);
	}

	/**
	 * Returns the origin vector. i.e if width is 80 and height 60, it will return 40-30
	 * 
	 * @return
	 */
	public Vector2 getOrigin() {
		return origin;
	}

	/**
	 * @return the bounds
	 */
	public Rectangle getBounds() {
		return bounds;
	}

	/**
	 * @param bounds
	 *           the bounds to set
	 */
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	/**
	 * Sets the width for the entity. If it is scalable, {@code this.width = width*scaleX}, if not, {@code this.width = width}. Then, it recalculates
	 * the origin.
	 * 
	 * @param width
	 * @param scalable
	 */
	public void setWidth(float width, boolean scalable) {
		if (scalable) {
			this.width = width * scaleX;
		} else {
			this.width = width;
		}
		origin.x = this.width / 2;
	}

	/**
	 * @return the width
	 */
	public float getWidth() {
		return width;
	}

	public void setHeight(float height, boolean scalable) {
		if(scalable){
			this.height = height * scaleY;			
		}else{
			this.height = height;
		}
		origin.y = this.height / 2;
	}

	/**
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}

}
