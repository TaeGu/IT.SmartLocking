package com.aboullaite.client;

import android.util.Log;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
//메세지를 보내고, 클라이언트를 스탑하고, 서버와 연결하는 역할 
	private String serverMessage;
	public static  String SERVERIP = "10.0.2.2"; // your computer Local IP
															// address
	public static final int SERVERPORT = 2222;
	private OnMessageReceived mMessageListener = null;
	private boolean mRun = false;
	Socket socket;
	PrintWriter out;
	BufferedReader in;

	/**
	 * Constructor of the class. OnMessagedReceived listens for the messages
	 * received from server
	 */
	public Client(OnMessageReceived listener) {
		mMessageListener = listener;
		
	}

	/**
	 * Sends the message entered by client to the server
	 * 
	 * @param message
	 *            text entered by client
	 */
	public void sendMessage(String message) { //서버로 메세지를 보낼때 쓰인다.
		if (out != null && !out.checkError()) {
			out.println(message);
			out.flush();
		}
	}

	public void stopClient() {
		mRun = false;
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {

		mRun = true;

		try {
			
			// here you must put your computer's IP address.
			InetAddress serverAddr = InetAddress.getByName(SERVERIP);
			Log.e("serverAddr", serverAddr.toString());
			Log.e("TCP Client", "C: Connecting...");

			// create a socket to make the connection with the server
			socket = new Socket(serverAddr, SERVERPORT); //서버와 마찬가지로 소켓으로 연결
			Log.e("TCP Server IP", SERVERIP);
			try {

				// send the message to the server
				out = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(socket.getOutputStream())), true);
					//보낼 메세지(out)
				Log.e("TCP Client", "C: Sent.");

				Log.e("TCP Client", "C: Done.");

				// receive the message which the server sends back
				in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
						//받을 메세지(in)
				// in this while the client listens for the messages sent by the
				// server
				while (mRun) {
					serverMessage = in.readLine();
					//넘어온 버퍼에서 메세지를 한라인씩 빼내온다.
					if (serverMessage != null && mMessageListener != null) {
						// call the method messageReceived from MyActivity class
						mMessageListener.messageReceived(serverMessage);
					}	//메세지를 받으면 리시브를 이용해서 리스너에 받은 메세지를 넣어준다.
					serverMessage = null;

				}

				Log.e("RESPONSE FROM SERVER", "S: Received Message: '"
						+ serverMessage + "'");

			} catch (Exception e) {

				Log.e("TCP", "S: Error", e);

			} finally {
				// the socket must be closed. It is not possible to reconnect to
				// this socket
				// after it is closed, which means a new socket instance has to
				// be created.
				socket.close();
			}

		} catch (Exception e) {

			Log.e("TCP", "C: Error", e);

		}

	}

	// Declare the interface. The method messageReceived(String message) will
	// must be implemented in the MyActivity
	// class at on asynckTask doInBackground
	public interface OnMessageReceived {
		public void messageReceived(String message);
	}
}