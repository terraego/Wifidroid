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

	public static final String TAG = "WifiDroid service";
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
		private ServerSocket serverSocket;

		public ServerThread() {
			setName("server thread");
			setDaemon(true);
		}

		@Override
		public void run() {
			Looper.prepare();
			looper = Looper.myLooper();

			ServerSocket serverSocket = null;
			boolean listening = true;
			try {
				serverSocket = new ServerSocket(PORT);
				showMessage("Started listening on port " + PORT);

				int id = 0;
				while (listening) {
					new ClientThread(id++, serverSocket.accept()).start();
				}

				Looper.loop();
			} catch (IOException e) {
				listening = false;
				Log.e(TAG, "an error occured in the server", e);
			} finally {
				try {
					serverSocket.close();
					showMessage("Stopped listening on port " + PORT);
				} catch (IOException e) {
					Log.e(TAG, "failed to close server socket", e);
				}
				Looper.myLooper().quit();
			}
		}

		public void quit() {
			if (serverSocket != null) {
				try {
					serverSocket.close();
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

		private Socket clientSocket;
		private boolean listening;

		public ClientThread(int id, Socket clientSocket) {
			if (clientSocket == null) {
				throw new NullPointerException();
			}

			this.clientSocket = clientSocket;

			setName("client thread " + id);
			setDaemon(true);
		}

		@Override
		public void run() {
			Looper.prepare();
			BufferedReader in = null;
			listening = true;
			try {
				in = new BufferedReader(new InputStreamReader(
						clientSocket.getInputStream()));
				String input;
				Log.d(TAG, "start listening for messages");
				while (listening && (input = in.readLine()) != null) {
					proccesCommand(input);
				}
				Log.d(TAG, "stop listening for messages");
			} catch (IOException ex) {
				Log.e(TAG, "An error occured while listening from the socket",
						ex);
			} finally {
				try {
					if (in != null) {
						in.close();
					}
					clientSocket.close();
				} catch (IOException ex) {
					Log.e(TAG, "Failed to close connection", ex);
				}
			}

			Looper.loop();
		}

		private void proccesCommand(String input) {
			showMessage("message: " + input);
		}
	}

}
