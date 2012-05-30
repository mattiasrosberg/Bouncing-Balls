package se.jayway.android.course;

import android.app.Activity;
import android.os.Bundle;
import android.os.Debug;
import android.view.View;

public class BouncingBalls extends Activity {
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        Debug.startMethodTracing("bouncing-balls.trace");
        View view = findViewById(R.id.bounceView);
        new BouncingBallsController(view).start();
       
    }
    
    
    @Override
    protected void onPause() {
    	super.onPause();
//    	Debug.stopMethodTracing();
    }
}