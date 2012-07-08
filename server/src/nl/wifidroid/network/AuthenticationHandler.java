package nl.wifidroid.network;

import android.os.Build;
import android.util.Log;
import nl.wifidroid.model.AuthenticationRequest;
import nl.wifidroid.model.AuthenticationResponse;

public class AuthenticationHandler extends MessageHandler{

	public static final String TAG = "AuthenticationHandler";
	
	@Override
	public int handleMessage(DeviceConnection con, Message command) {
		String actionCommand = command.getActionCommand();

		if (actionCommand.equals("authenticate")) {
			if (command.getType()==Message.TYPE_REQUEST) {
				AuthenticationRequest request = (AuthenticationRequest) command;
				String computerName = request.getComputerName();
				
				Log.d(TAG, computerName + " requested authentication, sending device info");
				
				AuthenticationResponse response = new AuthenticationResponse();
				response.setBrand(Build.MODEL);
				response.setBrand(Build.BRAND);
				response.setSdkLevel(Build.VERSION.SDK_INT);

				con.setComputerName(computerName);
				con.sendTCP(response);
			}
		}
		
		return 0;
	}

}
