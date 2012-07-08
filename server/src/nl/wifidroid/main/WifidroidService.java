package nl.wifidroid.main;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;

import nl.wifidroid.network.AuthenticationHandler;
import nl.wifidroid.network.DefaultWifiDroidServer;
import nl.wifidroid.network.DeviceConnection;
import nl.wifidroid.network.Message;
import nl.wifidroid.network.MessageHandler;
import nl.wifidroid.network.WifiDroidServer;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class WifidroidService extends Service {

	public static final String TAG = "WifidroidService";
	private NotificationManager notifier;
	private final IBinder mBinder = new LocalBinder();
	private Handler handler;
	private WifiDroidServer server;

	public class LocalBinder extends Binder {
		WifidroidService getService() {
			return WifidroidService.this;
		}
	}

	@Override
	public void onCreate() {
		handler = new Handler();
		notifier = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		server = new DefaultWifiDroidServer();
		server.addMessageHandler(new AuthenticationHandler());
		server.addMessageHandler(new MyMessageHandler());

		try {
			server.start();
			Log.d(TAG, "Started listening on port " + WifiDroidServer.PORT_TCP);
		} catch (IOException e) {
			Log.e(TAG, "Failed to start server", e);
		}
	}

	private void showMessage(final CharSequence message) {
		handler.post(new Runnable() {

			public void run() {
				Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_SHORT);
				Log.d(TAG, message.toString());
			}
		});
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		notifier.cancel(R.string.hello_world);
		try {
			server.stop();
		} catch (IOException e) {
			Log.e(TAG, "Failed to stop server", e);
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	private class MyMessageHandler extends MessageHandler {

		@Override
		public int handleMessage(DeviceConnection con, Message command) {
			String actionCommand = command.getActionCommand();
			String from = con.getComputerName();

			showMessage("received command: " + actionCommand + " from " + from);
			
			return 0;
		}

	}

}
