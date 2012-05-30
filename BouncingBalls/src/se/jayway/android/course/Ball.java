package se.jayway.android.course;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Ball {

	private int x;
	private int y;
	private int vx;
	private int vy;
	static int radius = 4;
	private static Random random = new Random(123456789L);

	Ball(int x, int y, int vx, int vy) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
	}

	public void update(Canvas c) {
		if (getX() + getVx() + (radius/2) > c.getWidth()
				|| getX() + getVx() - (radius/2) < 0) {
			setVx(-getVx());
		} else {
			setX(getX() + getVx());
		}
		if (getY() + getVy() + (radius/2) > (c.getHeight() -25)
				|| getY() + getVy() - (radius/2) < 0) {
			setVy(-getVy());
		} else {
			setY(getY() + getVy());
		}
	}

	public void draw(Canvas c) {
		Paint paint = new Paint();
		paint.setARGB(255, 0, 255, 0);
		c.drawCircle(getX(), getY(), radius, paint);
	}

	public boolean collidesWith(Ball otherBall) {

		return Math.sqrt(Math.pow(getX() - otherBall.getX(), 2)
				+ Math.pow(getY() - otherBall.getY(), 2)) < 2 * radius;
	}

	public static void resolveCollision(Canvas c, Ball ball1, Ball ball2) {
		if (Math.signum(ball1.getVx()) != Math.signum(ball2.getVx())) {
			ball1.setVx(-ball1.getVx());
			ball2.setVx(-ball2.getVx());
		}
		if (Math.signum(ball1.getVy()) != Math.signum(ball2.getVy())) {
			ball1.setVy(-ball1.getVy());
			ball2.setVy(-ball2.getVy());
		}
		ball1.update(c);
		ball2.update(c);
	}

	public static List<Ball> createRandomBalls(int nbrOfBalls, int maxX,
			int maxY, int maxVx, int maxVy) {
		List<Ball> balls = new ArrayList<Ball>(nbrOfBalls);
		for (int i = 0; i < nbrOfBalls; i++) {
			balls.add(randomBall(maxX, maxY, maxVx, maxVy));
		}
		return balls;
	}

	// Create a random ball
	private static Ball randomBall(int maxX, int maxY, int maxVx, int maxVy) {
		int x = 0 + radius + (int) (Math.random() * (float) (maxX - radius));
		int y = 0 + radius + (int) (Math.random() * (float) (maxY - radius));
		int vx = (int) (random.nextFloat() * (float) (maxVx - 1)) + 1;
		int vy = (int) (random.nextFloat() * (float) (maxVy - 1)) + 1;
		return new Ball(x, y, vx, vy);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getVx() {
		return vx;
	}

	public void setVx(int vx) {
		this.vx = vx;
	}

	public int getVy() {
		return vy;
	}

	public void setVy(int vy) {
		this.vy = vy;
	}

}
