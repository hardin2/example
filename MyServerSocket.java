package testSample1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class MyServerSocket {
	Scanner sc = new Scanner(System.in);
	
	public void runServer() {
		MyServer ms = new MyServer();
		Thread th = new Thread(ms);
		th.start();

		try {
			th.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ms.close();
	}	
}

class MyServer implements Runnable {
	public static final int PORT = 27015;
	public static final int BUF_SIZE = 4096;
	private ServerSocket sSock;
	private boolean isStop;

	public ServerSocket getServerSocket() {
		return sSock;
	}

	public String ByteArrayToString(byte[] a, int len) {
		byte[] ba = new byte[len];
		for (int i=0; i<len; i++) {
			ba[i] = a[i];
		}
		return new String(ba);
	}
	
	public String CharArrayToStrig(char[] a, int len) {
		char[] buff = new char[len];
		for (int i=0; i<len; i++) {
			buff[i] = a[i];
		}
		return new String(buff);
	}
	
	public boolean isNumber(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	// receive data 
	public void run() {
		sSock = null;

		byte[] buffer = new byte[BUF_SIZE];
		int length;
	
		try {
			sSock = new ServerSocket(PORT);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println("new ServerSocket");
		
		//while (!isStop) {
			Socket socket = null;
			DataInputStream is = null;
			DataOutputStream os = null;
			String filename = null;
			String strTemp = "";
			byte[] ba = null;
			
			try {
				System.out.println("start accept");
				socket = sSock.accept();
				System.out.println("accept..ok");
				
				is = new DataInputStream(socket.getInputStream());
				os = new DataOutputStream(socket.getOutputStream());
				
				// receive : filename
				length =  is.read(buffer, 0, buffer.length);
				filename = ByteArrayToString(buffer, length);
				System.out.println("recv(filename): " + filename);
				
				// send : "start"
				strTemp = "start";
				ba = strTemp.getBytes();
				os.write(ba, 0, ba.length);
				System.out.println("send(first): " + ByteArrayToString(ba, ba.length));
				
				int cnt = 0;
				while (true) {
					// receive data
					length = is.read(buffer, 0, buffer.length);
					strTemp = ByteArrayToString(buffer, length);
					System.out.println("recv(data): " + strTemp);
					if (strTemp.equals("end")) { break; }
					
					// send data
					strTemp = String.valueOf(cnt++);
					ba = strTemp.getBytes();
					os.write(ba, 0, ba.length);
					System.out.println("send: " + ByteArrayToString(ba, ba.length));					
				}
			} catch (EOFException e) {
				// Do Nothing
			} catch (SocketException e) {
				System.out.println("server socket is colsed");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				System.out.println("socket end");
				//if (is != null) { is.close(); }
				//if (socket != null) {socket.close();}
			}
		//}

	}
	
	public synchronized void close() {
		isStop = true;
		if (sSock != null) {
			try {
				sSock.close();
				sSock = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}

