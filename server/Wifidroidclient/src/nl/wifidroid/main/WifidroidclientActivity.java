package nl.wifidroid.main;


import nl.wifidroid.connection.ConnectionManager;
import nl.wifidroid.main.WifidroidService.LocalBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;



public class WifidroidclientActivity extends Activity {

	private WifidroidService Backgroundservice;
	private boolean isBound;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* if(Backgroundservice == null){
        	doBindService();
        	}
        }*/
        //Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();
        ConnectionManager cm = new ConnectionManager(this);
        Toast.makeText(this, "fixed", Toast.LENGTH_SHORT).show();
        //cm.listen();
    }

    
    private ServiceConnection service_connection = new ServiceConnection(){
    	public void onServiceConnected(ComponentName classname, IBinder service){
    		Backgroundservice = ((LocalBinder)service).getService();
    		Toast.makeText(WifidroidclientActivity.this, "Connected",Toast.LENGTH_LONG).show();
    	}
    	
    	public void onServiceDisconnected(ComponentName className){
    		Backgroundservice = null;
    	}
    };

    public void shownotification(CharSequence text)
    {
    	Toast.makeText(WifidroidclientActivity.this, text, Toast.LENGTH_SHORT).show();
    }
    
    public void doBindService(){
    	bindService(new Intent(this,WifidroidService.class), service_connection,Context.BIND_AUTO_CREATE);
    }
}




