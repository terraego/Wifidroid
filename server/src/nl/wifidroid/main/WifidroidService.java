package nl.wifidroid.main;

import java.io.IOException;
import java.net.ServerSocket;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import android.os.Binder;

public class WifidroidService extends Service {

	public static final int PORT = 12345;
	private NotificationManager notifier;
	private final IBinder mBinder = new LocalBinder();
	private ServerThread thread;

	private Handler notificationHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			String currentThread = Thread.currentThread().getName();
			Toast.makeText(WifidroidService.this,
					"currentThread = " + currentThread, Toast.LENGTH_LONG)
					.show();
		}
	};

	public class LocalBinder extends Binder {
		WifidroidService getService() {
			return WifidroidService.this;
		}
	}

	@Override
	public void onCreate() {
		notifier = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		showNotification();
		thread = new ServerThread();
		thread.start();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("Wifidroid", "Started" + startId + ": " + intent);
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		notifier.cancel(R.string.hello_world);
		thread.quit();

		Toast.makeText(this, "Service stopped", Toast.LENGTH_SHORT).show();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	private void showNotification() {
		CharSequence text = "Service started";

		Notification notification = new Notification(R.drawable.ic_launcher,
				text, System.currentTimeMillis());

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, WifiDroidServerActivity.class), 0);

		notification.setLatestEventInfo(this, "Service started", text,
				contentIntent);

		notifier.notify(R.string.hello_world, notification);
	}

	private class ServerThread extends Thread {
		private Looper looper;
		private ServerSocket server;

		public ServerThread() {
			setName("server thread");
			setDaemon(true);
		}

		@Override
		public void run() {
			Looper.prepare();
			looper = Looper.myLooper();
			try {
				server = new ServerSocket(PORT);
				while ((server.accept()) != null) {
					Log.d("WifiService", "incoming connection!!!");
					notificationHandler.handleMessage(null);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			Looper.loop();
		}

		public void quit() {
			if (server != null) {
				try {
					server.close();
				} catch (IOException e) {
					// TODO: handle it
				}
			}
			if (looper != null) {
				looper.quit();
			}

		}
	}

}
