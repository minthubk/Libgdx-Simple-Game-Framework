package com.autlos.sgf;

/**
 * @author Autlos
 * 
 */
public class Timer {
	// Time remaining
	protected float remaining;
	// Time interval for the timer
	protected float interval;

	/**
	 * Creates a timer with a time interval
	 * 
	 * @param interval
	 */
	public Timer(float interval) {
		this.interval = interval;
		this.remaining = interval;
	}

	/**
	 * @return if the time remaining is <=0.
	 */
	public boolean hasTimeElapsed() {
		return remaining < 0.0f;
	}

	/**
	 * Restarts the timer, setting the remaining time to it's interval again.
	 */
	public void reset() {
		remaining = interval;
	}

	/**
	 * Restarts the timer with a new interval.
	 * @param interval
	 */
	public void reset(float interval) {
		this.interval = interval;
		this.remaining = interval;
	}

	/**
	 * Decreases the remaining time.
	 * @param delta
	 */
	public void update(float delta) {
		remaining -= delta;
	}
}
