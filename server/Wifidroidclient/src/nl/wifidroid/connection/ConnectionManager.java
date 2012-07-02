package nl.wifidroid.connection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import 	java.net.ServerSocket;
import java.net.Socket;

import nl.wifidroid.main.WifidroidclientActivity;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


public class ConnectionManager {
	
	private ServerSocket svsock;
	private WifidroidclientActivity activit;
	
	public ConnectionManager(WifidroidclientActivity act) {
		activit = act;
		activit.shownotification("Text");
		this.listen();
	}

	public void onCreate(){
			}
	
	public void listen()
	{
		boolean end = false;
		//Log.d("TCP", "luisterend");
		activit.shownotification("Listening");
		try {
			svsock = new ServerSocket(12345);
			while(!end)
			{
				/*Socket s = svsock.accept();
				BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
				PrintWriter output = new PrintWriter(s.getOutputStream(),true);
				String st = input.readLine();
				activit.shownotification("TCP from :" + st);*/
				activit.shownotification("TCP");
				//s.close();
			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

}


