package nl.wifidroid;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectionHandler extends ConsoleCommandHandler {

	public static final int PORT = 8888;
	private Socket socket;

	@Override
	public void handleCommand(String command) {
		try {
			if (command.startsWith("connect")) {
				String ip = command.substring("set host".length());
				connect(ip);
			} else if (command.equals("disconnect")) {
				disconnect();
			} else if (command.startsWith("send message")) {
				if (!isConnected()) {
					print("not connected");
					return;
				}

				// retrieve the message to send
				String input = command.substring("send message".length());

				// actually send the method
				sendMessage(input);
			}
		} catch (Exception e) {
			error(e);
			try {
				disconnect();
			} catch (IOException ex) {
				error(e);
			}
		}
	}

	private void connect(String ip) throws UnknownHostException, IOException {
		print("connecting to " + ip + ":" + PORT);
		socket = new Socket(ip, PORT);
		print("connected");
	}

	private void disconnect() throws IOException {
		if (socket != null) {
			socket.close();
			socket = null;
			print("disconnected");
		}
	}

	private boolean isConnected() {
		return socket != null;
	}

	private void sendMessage(String message) throws IOException {
		if (!isConnected()) {
			throw new RuntimeException("not connected");
		}

		Writer out = null;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.write(message.trim());
			out.flush();
			
			print("message sent: "+message);
		} catch (IOException e) {
			throw e;
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
