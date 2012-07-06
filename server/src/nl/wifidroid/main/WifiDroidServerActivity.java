package nl.wifidroid.main;

import java.net.ServerSocket;

import nl.wifidroid.main.WifidroidService.LocalBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class WifiDroidServerActivity extends Activity {

	private WifidroidService Backgroundservice = null;
	private Button startService;
	private Button stopService;

	
	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Toast.makeText(this, "Starting Intent", Toast.LENGTH_SHORT).show();
        startService = (Button) findViewById(R.id.startService);
        stopService = (Button) findViewById(R.id.stopService);
        
        doBindService();
	}

    private ServiceConnection service_connection = new ServiceConnection(){
    	public void onServiceConnected(ComponentName classname, IBinder service){
    		Backgroundservice = ((LocalBinder) service).getService();
    		Toast.makeText(WifiDroidServerActivity.this, "Connected",Toast.LENGTH_LONG).show();
    	}
    	
    	public void onServiceDisconnected(ComponentName className){
    		Backgroundservice = null;
    	}
    };

    public void shownotification(CharSequence text)
    {
    	Toast.makeText(WifiDroidServerActivity.this, text, Toast.LENGTH_SHORT).show();
    }
    
    public void doBindService(){
    	bindService(new Intent(this,WifidroidService.class), service_connection,Context.BIND_AUTO_CREATE);
    }
    
}





