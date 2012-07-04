package nl.wifidroid.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

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

	private Handler handler;

	public class LocalBinder extends Binder {
		WifidroidService getService() {
			return WifidroidService.this;
		}
	}

	@Override
	public void onCreate() {
		handler = new Handler();

		notifier = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// showNotification();
		thread = new ServerThread();
		thread.start();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		showMessage("Started" + startId + ": " + intent);
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		notifier.cancel(R.string.hello_world);
		thread.quit();

		showMessage("Service stopped");
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	private void showMessage(final CharSequence text) {
		Log.d("WifiDroidService", text.toString());
		handler.post(new Runnable() {

			public void run() {
				Toast.makeText(WifidroidService.this, text, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	// private void showNotification() {
	// CharSequence text = "Service started";
	//
	// Notification notification = new Notification(R.drawable.ic_launcher,
	// text, System.currentTimeMillis());
	//
	// PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
	// new Intent(this, WifiDroidServerActivity.class), 0);
	//
	// notification.setLatestEventInfo(this, "Service started", text,
	// contentIntent);
	//
	// notifier.notify(R.string.hello_world, notification);
	// }

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
			showMessage("Starting server thread");
			try {
				server = new ServerSocket(PORT);
				Socket client = null;
				while ((client = server.accept()) != null) {
					ClientThread clientThread = new ClientThread(client);
					clientThread.start();
				}
				server.close();
				Looper.myLooper().quit();
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

	private class ClientThread extends Thread {
		private Socket client;

		public ClientThread(Socket client) {
			this.client = client;

			setName("client thread");
			setDaemon(true);
		}

		@Override
		public void run() {
			Looper.prepare();
			InputStream in = null;
			String line = null;
			showMessage("client " + client.getInetAddress() + " connected");
			try {
				in = client.getInputStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in));
				while ((line = reader.readLine()) != null) {
					showMessage("client: " + line);
				}
				client.close();
				Looper.myLooper().quit();
			} catch (IOException e) {
				Log.e("WifidroidService",
						"and error occured while trying to read messages from the client");
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					// dont care
				}
			}
			Looper.loop();
		}
	}

}
