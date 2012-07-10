package nl.wifidroid.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DebugActivity extends Activity {
	TextView tv;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.debug_act);
		
		 tv = (TextView) findViewById(R.id.console);
	}
	
	public void HandleInput(View view){
		tv.append("juistem!\n");
	}
}
