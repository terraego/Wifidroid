package nl.wifidroid;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 *
 * @author Maarten Blokker
 */
public class WifiService {

    private ConsoleController console;
    private String host;
    private int port;

    public WifiService() {
        console = new ConsoleController();
        console.addConsoleCommandHandler(new CommandHandler());
        console.show();
    }

    public void start() {
        print("Server started");
    }

    private void print(String message) {
        console.print("[Server] " + message);
    }

    private void error(Exception e) {
        console.print("[Server] " + e.getClass() + ":" + e.getLocalizedMessage());
        e.printStackTrace();
    }

    private void error(Exception e, String message) {
        error(e);
        print(message);
    }

    public static void main(String[] args) {
        WifiService server = new WifiService();
        server.start();
    }

    private class CommandHandler implements ConsoleCommandHandler {

        @Override
        public void handleCommand(String command) {
            if (command.equals("help")) {
                showHelp();
            } else if (command.equals("show host")) {
                print("host currently set to: " + host + ":" + port);
            } else if (command.startsWith("set host")) {
                String input = command.substring("set host".length());
                StringTokenizer tokenizer = new StringTokenizer(input.trim(), ":");

                try {
                    //retrieve the host/port settings
                    String host = tokenizer.nextToken();
                    if(!tokenizer.hasMoreTokens()) {
                        throw new RuntimeException("no port number found");
                    }                    
                    int port = Integer.parseInt(tokenizer.nextToken());

                    //if the line above doesnt give exceptions, store the settings
                    WifiService.this.host = host;
                    WifiService.this.port = port;

                    print("host set to: " + host + ":" + port);
                } catch (Exception e) {
                    error(e);
                }
            } else if (command.startsWith("send message")) {
                if (host == null || port == 0) {
                    print("oops, no host is set!");
                    print("please use 'set host [ip:port] first");
                    return;
                }

                //retrieve the message to send
                String input = command.substring("send message".length());

                //actually send the method
                sendMessage(input);
            }
        }

        private void sendMessage(String message) {
            Socket socket = null;
            Writer out = null;
            try {
                socket = new Socket(host, port);
                out = new PrintWriter(socket.getOutputStream(), true);
                out.write(message.trim());
            } catch (IOException e) {
                error(e);
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    error(e, "failed to free resources");
                }
            }
        }

        private void showHelp() {
            print("set host [ip:port]\t\tsets the host to the given address");
            print("show host\t\t\tshows the current host and port");
            print("send message [message]\tsends the given message to the host.");
        }
    }
}
