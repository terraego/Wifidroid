package nl.wifidroid.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

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
        Toast.makeText(this, "Listening", Toast.LENGTH_LONG).show();//System.out.println("Listening :8888");
        MyServer cm = new MyServer();
        cm.Start()
        //Toast.makeText(this, "fixed", Toast.LENGTH_SHORT).show();
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
    

    
    private class MyServer {
    	 
    	 public void Start() {
    		 Thread t = new Thread() {
    				@Override
    	    		public void run(){
    	    			while(true)
    	    			{
    	    				try{
    	    					Thread.sleep(5000);
    	    					listen();
    	    			} catch(InterruptedException e) {
    	    				e.printStackTrace();
    	    			}
    	    		}
    			 }};
    			 t.start();
    	 }
    	
    	 public void listen(){
    		 
       	  ServerSocket serverSocket = null;
       	  Socket socket = null;
       	  DataInputStream dataInputStream = null;
       	  DataOutputStream dataOutputStream = null;
       	  
    	  try {
    	   serverSocket = new ServerSocket(8888);
    	   
    	   Toast.makeText(WifidroidclientActivity.this.getApplicationContext(), "Listening", Toast.LENGTH_SHORT).show();//System.out.println("Listening :8888");
    	  } catch (IOException e) {
    	   // TODO Auto-generated catch block
    	   e.printStackTrace();
    	  }
    	  
    	  try {
    	    socket = serverSocket.accept();
    	    dataInputStream = new DataInputStream(socket.getInputStream());
    	    dataOutputStream = new DataOutputStream(socket.getOutputStream());
    	    System.out.println("ip: " + socket.getInetAddress());
    	    System.out.println("message: " + dataInputStream.readUTF());
    	    dataOutputStream.writeUTF("Hello!");
    	   } catch (IOException e) {
    	    // TODO Auto-generated catch block
    	    e.printStackTrace();
    	   }
    	   finally{
    	    if( socket!= null){
    	     try {
    	      socket.close();
    	     } catch (IOException e) {
    	      // TODO Auto-generated catch block
    	      e.printStackTrace();
    	     }
    	    }
    	    
    	    if( dataInputStream!= null){
    	     try {
    	      dataInputStream.close();
    	     } catch (IOException e) {
    	      // TODO Auto-generated catch block
    	      e.printStackTrace();
    	     }
    	    }
    	    
    	    if( dataOutputStream!= null){
    	     try {
    	      dataOutputStream.close();
    	     } catch (IOException e) {
    	      // TODO Auto-generated catch block
    	      e.printStackTrace();
    	     }
    	    }
    	   }
    	  }
    	 }
    }





