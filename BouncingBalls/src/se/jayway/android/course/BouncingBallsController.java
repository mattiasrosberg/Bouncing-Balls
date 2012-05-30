package se.jayway.android.course;

import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

class BouncingBallsController extends Thread {

	private boolean isAlive = true;
	private int minFps = Integer.MAX_VALUE;
	private int maxFps = Integer.MIN_VALUE;
	private boolean startMeasure;
	private SurfaceHolder surfaceHolder;

	private List<Ball> balls;

	Paint clear, text;
	long start, end;

	BouncingBallsController(View view) {
		surfaceHolder = ((SurfaceView) view).getHolder();
		int screenWidth = view.getWidth();
		int screenHeight = view.getHeight();
		int maxVelX = 8;
		int maxVelY = 8;
		int nbrOfBalls = 120;
		balls = Ball.createRandomBalls(nbrOfBalls, screenWidth, screenHeight,
				maxVelX, maxVelY);
		clear = new Paint();
		text = new Paint();

		clear.setARGB(255, 0, 0, 0);
		text.setARGB(255, 255, 255, 255);
	}

	void terminate() {
		isAlive = false;
	}

	public void run() {
		int iterations = 0;
		while (isAlive) {
			try {
				if (iterations > 50) {
					startMeasure = true;
				}
				iterations++;
				Thread.sleep(5);
			} catch (InterruptedException e) {
			}
			start = System.currentTimeMillis();
			Canvas c = surfaceHolder.lockCanvas();

			if (c != null) {

				c.drawRect(0, 0, c.getWidth(), c.getHeight() - 25, clear);

				for (Ball ball : balls) {
					ball.update(c);
				}

				for (int i = 0; i < balls.size() - 1; i++) {
					for (int j = i + 1; j < balls.size(); j++) {
						if (balls.get(i).collidesWith(balls.get(j))) {
							Ball.resolveCollision(c, balls.get(i), balls.get(j));
							continue;
						}
					}
				}
				for (int i = 0; i < balls.size(); i++) {
					balls.get(i).draw(c);
				}

				
				//** DON'T CHANGE ANYTHING BELOW THIS LINE*******
				end = System.currentTimeMillis();
				int fps = (int) (1000 / (end - start) > 0 ? 1000 / (end - start)
						: 1);
				// Wait a while before starting to measure
				if (startMeasure) {
					minFps = fps < minFps ? fps : minFps;
					maxFps = fps > maxFps ? fps : maxFps;
					c.drawText("FrameRate=" + fps + " fps, Min: " + minFps
							+ " fps, Max: " + maxFps, 0, c.getHeight() - 30,
							text);
				} else {
					c.drawText("FrameRate=" + fps + " fps", 0,
							c.getHeight() - 30, text);
				}

				surfaceHolder.unlockCanvasAndPost(c);
			}
		}
	}

}
