package com.autlos.sgf.models;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * @author Autlos
 * 
 */
public abstract class Entity {
	// The frame is going to be drawn:
	protected TextureRegion currentFrame;

	// Basic information about an Entity:
	protected float width;
	protected float height;
	protected Vector2 origin;
	protected Vector2 originCoordinates;
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
	protected float stateTime = 0f;

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
		setPosition(position);
		this.rotation = rotation;
		currentFrame = textureRegion;
		setScale(scaleX, scaleY);
		setDimensions();
		createBounds(minBoundsX, minBoundsY); 
	}

	/**
	 * Creates an Entity with multiple frames but not animated. Scale will be 1f.
	 * 
	 * @param textureRegion
	 * @param FRAME_ROWS
	 * @param FRAME_COLS
	 * @param position
	 * @param rotation
	 */
	public Entity(TextureRegion textureRegion, int FRAME_ROWS, int FRAME_COLS, Vector2 position, float rotation) {
		this(textureRegion, FRAME_ROWS, FRAME_COLS, position, rotation, 1f, 1f);
	}

	/**
	 * Creates an Entity with multiple frames but not animated.
	 * 
	 * @param textureRegion
	 * @param FRAME_ROWS
	 * @param FRAME_COLS
	 * @param position
	 * @param rotation
	 * @param scaleX
	 * @param scaleY
	 */
	public Entity(TextureRegion textureRegion, int FRAME_ROWS, int FRAME_COLS, Vector2 position, float rotation,
	      float scaleX, float scaleY) {
		createFrames(textureRegion, FRAME_ROWS, FRAME_COLS);
		setPosition(position);
		this.rotation = rotation;

		currentFrame = frames[0];
		setScale(scaleX, scaleY);
		setDimensions();
		createBounds(minBoundsX, minBoundsY);
		stateTime = 0f;
	}

	/**
	 * Creates an Animated entity. Scale will be 1f.
	 * 
	 * @param textureRegion
	 * @param FRAME_ROWS
	 *           of the textureRegion
	 * @param FRAME_COLS
	 *           of the textureRegion
	 * @param position
	 * @param rotation
	 * @param frameDuration
	 *           time, in seconds, for each frame in the animation
	 * @param playMode
	 *           for example Animation.LOOP {@link Animation}
	 */
	public Entity(TextureRegion textureRegion, int FRAME_ROWS, int FRAME_COLS, Vector2 position, float rotation,
	      float frameDuration, int playMode) {
		this(textureRegion, FRAME_ROWS, FRAME_COLS, position, rotation, 1f, 1f, frameDuration, playMode);
	}

	/**
	 * Creates an Animated entity
	 * 
	 * @param textureRegion
	 * @param FRAME_ROWS
	 *           of the textureRegion
	 * @param FRAME_COLS
	 *           of the textureRegion
	 * @param position
	 * @param rotation
	 * @param scaleX
	 * @param scaleY
	 * @param frameDuration
	 *           time, in seconds, for each frame in the animation
	 * @param playMode
	 *           for example Animation.LOOP {@link Animation}
	 */
	public Entity(TextureRegion textureRegion, int FRAME_ROWS, int FRAME_COLS, Vector2 position, float rotation,
	      float scaleX, float scaleY, float frameDuration, int playMode) {

		createFrames(textureRegion, FRAME_ROWS, FRAME_COLS);
		this.animation = new Animation(frameDuration, frames);
		animation.setPlayMode(playMode);
		setPosition(position);
		this.rotation = rotation;

		currentFrame = animation.getKeyFrame(0f);
		setScale(scaleX, scaleY);
		setDimensions();
		createBounds(minBoundsX, minBoundsY);
	}

	/**
	 * Creates the frames array. This method will be called if the constructor for multiple frame entity or animation is used.
	 * 
	 * @param textureRegion
	 * @param FRAME_ROWS
	 * @param FRAME_COLS
	 */
	protected void createFrames(TextureRegion textureRegion, int FRAME_ROWS, int FRAME_COLS) {
		int frameWdith = textureRegion.getRegionWidth() / FRAME_COLS;
		frames = new TextureRegion[FRAME_ROWS * FRAME_COLS];
		int cont = 0;

		for (int j = 0; j < FRAME_ROWS; j++) {
			for (int i = 0; i < FRAME_COLS; i++) {
				frames[cont] = new TextureRegion(textureRegion, i * frameWdith, j * textureRegion.getRegionHeight()
				      / FRAME_ROWS, frameWdith, textureRegion.getRegionHeight() / FRAME_ROWS);
				cont++;
			}
		}
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
		if (origin == null) {
			origin = new Vector2(this.width / 2, this.height / 2);
		} else {
			this.origin.set(this.width / 2, this.height / 2);
		}
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
		if (bounds == null) {
			bounds = new Rectangle(position.x + minBoundsX, position.y + minBoundsY, width - 2 * minBoundsX, height - 2
			      * minBoundsY);
		} else {
			bounds.set(position.x + minBoundsX, position.y + minBoundsY, width - 2 * minBoundsX, height - 2 * minBoundsY);
		}
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
	
	public void setAnimation(Animation animation){
			this.animation = animation;		
	}

	/**
	 * Return true if the entity is overlaping one of the elements of the Array.
	 * 
	 * @param entities
	 * @return
	 */
	public boolean overlaps(Array<Entity> entities) {
		for (Entity e : entities) {
			if (overlaps(e)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * If the bounds are colliding with the arg entity's bounds.
	 * 
	 * @param entity
	 * @return
	 */
	public boolean overlaps(Entity entity) {
		return this.bounds.overlaps(entity.bounds);
	}

	/**
	 * Draws the bound's shape. {@code shapeRenderer.begin()} and color have to be set before calling this method.
	 * 
	 * @param sr
	 */
	public void drawShape(ShapeRenderer sr) {
		sr.rect(bounds.x, bounds.y, bounds.width, bounds.height);
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
		if(position == null){
			setPosition(0f, 0f);
		}else{
			setPosition(position.x, position.y);
		}
	}

	/**
	 * @param x
	 * @param y
	 */
	public void setPosition(float x, float y) {
		if (this.position == null) {
			this.position = new Vector2(x, y);
//			this.origin = new Vector2(width/2, height/2);
		} else {
			this.position.set(x, y);
//			this.origin.set(width/2, height/2);
		}
	}

	/**
	 * Returns the origin of the entity in screen coordinates. i.e if entity's position in screen is 100-100, and origin 20-20, it will return 120-120.
	 * 
	 * @return {@code Vector2}
	 */
	public Vector2 getOriginCoordinates() {
		if(originCoordinates == null){
		  originCoordinates = new Vector2(position.x + origin.x, position.y + origin.y);
		}else{
			originCoordinates.set(position.x + origin.x, position.y + origin.y);
		}
		return originCoordinates;
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
	 * Sets the width for the entity. If it is scalable, {@code this.width = width*scaleX}, if not, {@code this.width = width}.
	 * 
	 * @param width
	 * @param scalable
	 *           if true scaleX will be applied to the new width or .
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

	/**
	 * Sets the height for the entity. If it is scalable, {@code this.width = width*scaleX}, if not, {@code this.width = width}.
	 * 
	 * @param height
	 * @param scalable
	 *           if true scaleY will be applied to the new width or not.
	 */
	public void setHeight(float height, boolean scalable) {
		if (scalable) {
			this.height = height * scaleY;
		} else {
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
