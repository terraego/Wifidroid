package nl.wifidroid.main;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import android.os.Binder;

public class WifidroidService extends Service {
	
	private NotificationManager Notifier;
	
	private int NOTIFICATION = R.string.hello_world;
	
	private final IBinder mBinder = new LocalBinder();

	public class LocalBinder extends Binder {
		WifidroidService getService(){
			return WifidroidService.this;
		}
	}
	
	@Override
	public void onCreate(){
		Notifier = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		showNotification();

		}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Log.i("Wifidroid", "Started" + startId + ": " + intent);
		return START_STICKY;
	}
	
	@Override
	public void onDestroy()
	{
		Notifier.cancel(NOTIFICATION);
		
		Toast.makeText(this,"Service stopped", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public IBinder onBind(Intent intent)
	{
		return mBinder;
	}
	
	private void showNotification(){
		CharSequence text = "Service started";
		
		Notification notification = new Notification(R.drawable.ic_launcher, text, System.currentTimeMillis());
		
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, WifiDroidServerActivity.class), 0);
		
		notification.setLatestEventInfo(this,"Service started",text,contentIntent);
		
		Notifier.notify(NOTIFICATION,notification);
	}
	

}
